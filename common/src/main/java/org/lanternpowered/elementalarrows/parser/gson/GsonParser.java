/*
 * This file is part of ElementalArrows, licensed under the MIT License (MIT).
 *
 * Copyright (c) LanternPowered <https://github.com/Cybermaxke/ElementalArrows>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the Software), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.lanternpowered.elementalarrows.parser.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.common.base.Throwables;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.LanternReflectiveBoundField;
import com.google.gson.internal.bind.LanternTypeAdapterRuntimeTypeWrapper;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.lanternpowered.elementalarrows.parser.Field;
import org.lanternpowered.elementalarrows.parser.SerializedType;
import org.lanternpowered.elementalarrows.parser.SerializedTypeProvider;

import javax.annotation.Nullable;

public final class GsonParser {

    private final GsonBuilder builder;
    @Nullable private Gson gson;

    /**
     * Creates a new gson parser instance.
     */
    public GsonParser() {
        this.builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        this.builder
                .setFieldNamingStrategy(field -> field.getAnnotation(Field.class).value())
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        return fieldAttributes.getAnnotation(Field.class) == null;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                });

    }

    private Gson bake() throws Exception {
        if (this.gson != null) {
            return this.gson;
        }
        this.gson = this.builder.create();

        // Some tricky stuff is happening here... We replace the @Fields with
        // a SerializedType provider to use the serialized type provided by the
        // provider. Gson doesn't provide a method to do this easily.
        java.lang.reflect.Field field = Gson.class.getDeclaredField("factories");
        field.setAccessible(true);

        // Get the factories of the gson
        //noinspection unchecked
        List<TypeAdapterFactory> factories = (List<TypeAdapterFactory>) field.get(this.gson);

        // The reflective factory is at the last index
        final ReflectiveTypeAdapterFactory factory = (ReflectiveTypeAdapterFactory) factories.get(factories.size() - 1);

        // The factories is modifiable, lets fix that
        field = factories.getClass().getDeclaredField("list");
        field.setAccessible(true);
        //noinspection unchecked
        factories = (List<TypeAdapterFactory>) field.get(factories);

        // Replace the default type adapter factory
        factories.set(factories.size() - 1, new TypeAdapterFactory() {
            @Nullable
            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                final TypeAdapter adapter = factory.create(gson, (TypeToken) typeToken);
                if (adapter == null) {
                    return null;
                }
                try {
                    final java.lang.reflect.Field field1 = ReflectiveTypeAdapterFactory.Adapter.class.getDeclaredField("boundFields");
                    field1.setAccessible(true);

                    // Get the bound fields
                    //noinspection unchecked
                    final Map<String, Object> boundFields = (Map<String, Object>) field1.get(adapter);

                    Class<?> raw = typeToken.getRawType();
                    while (raw != Object.class) {
                        final java.lang.reflect.Field[] fields = raw.getDeclaredFields();
                        for (final java.lang.reflect.Field field2 : fields) {
                            if (field2.getAnnotation(Field.class) == null) {
                                continue;
                            }
                            final String name = field2.getName();
                            if (boundFields.containsKey(name)) {
                                final SerializedType serializedType = field2.getAnnotation(SerializedType.class);
                                if (serializedType != null) {
                                    final SerializedTypeProvider provider = (SerializedTypeProvider) serializedType.value().newInstance();
                                    final Object boundField = boundFields.get(name);
                                    boundFields.put(name, new LanternReflectiveBoundField(boundField) {

                                        @Override
                                        protected void write(JsonWriter writer, Object value) throws IOException, IllegalAccessException {
                                            final Object fieldValue = field2.get(value);
                                            //noinspection unchecked
                                            final TypeToken<?> type = provider.get(name, value);
                                            final TypeAdapter typeAdapter = gson.getAdapter(type);
                                            final TypeAdapter typeAdapterWrapper = LanternTypeAdapterRuntimeTypeWrapper.create(
                                                    gson, typeAdapter, type.getType());
                                            //noinspection unchecked
                                            typeAdapterWrapper.write(writer, fieldValue);
                                        }

                                        @Override
                                        protected void read(JsonReader reader, Object value) throws IOException, IllegalAccessException {
                                            //noinspection unchecked
                                            final TypeToken<?> type = provider.get(name, value);
                                            final TypeAdapter typeAdapter = gson.getAdapter(type);

                                            final Object fieldValue = typeAdapter.read(reader);
                                            if (fieldValue != null) {
                                                field2.set(value, fieldValue);
                                            }
                                        }
                                    });
                                }
                            }
                        }

                        raw = typeToken.getRawType();
                    }
                } catch (Exception e) {
                    throw Throwables.propagate(e);
                }
                //noinspection unchecked
                return adapter;
            }
        });
        return this.gson;
    }

    /**
     * Registers a type adapter factory.
     *
     * @param factory the type adapter factory
     */
    public void registerTypeAdapterFactory(TypeAdapterFactory factory) {
        this.builder.registerTypeAdapterFactory(factory);
        this.gson = null;
    }

    /**
     * Registers a type adapter for the specified type.
     *
     * @param type the type
     * @param object the type adapter
     */
    public void registerTypeAdapter(Type type, Object object) {
        this.builder.registerTypeAdapter(type, object);
        this.gson = null;
    }

    /**
     * Gets the {@link Gson} instance.
     *
     * @return the gson
     */
    public Gson getGson() {
        try {
            return bake();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
