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

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.lanternpowered.elementalarrows.item.ConstructableItem;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;

import java.io.IOException;

import javax.annotation.Nullable;

public class CatalogTypeAdapterFactory implements TypeAdapterFactory {

    @Nullable
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        final Class<? super T> type = typeToken.getRawType();
        if (!CatalogType.class.isAssignableFrom(type) || ConstructableItem.class.isAssignableFrom(type)) {
            return null;
        }
        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter jsonWriter, T t) throws IOException {
                gson.toJson(new JsonPrimitive(((CatalogType) t).getId()), jsonWriter);
            }

            @Override
            public T read(JsonReader jsonReader) throws IOException {
                final String id = gson.fromJson(jsonReader, String.class);
                //noinspection unchecked
                return (T) Sponge.getRegistry().getType((Class<CatalogType>) type, id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "There doesn't exist a catalog type " + id + " of the type " + type.getName()));
            }
        };
    }
}
