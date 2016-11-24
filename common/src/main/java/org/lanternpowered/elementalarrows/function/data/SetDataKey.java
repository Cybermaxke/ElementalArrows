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
package org.lanternpowered.elementalarrows.function.data;

import com.google.gson.reflect.TypeToken;
import org.lanternpowered.elementalarrows.parser.Field;
import org.lanternpowered.elementalarrows.function.ObjectConsumer;
import org.lanternpowered.elementalarrows.parser.SerializedType;
import org.lanternpowered.elementalarrows.parser.SerializedTypeProvider;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.value.mutable.CompositeValueStore;

import javax.annotation.Nullable;

public class SetDataKey<S extends CompositeValueStore<S, H>, H extends ValueContainer<?>> implements ObjectConsumer<S> {

    @Field("key")
    private Key key;

    @Nullable
    @Field("element")
    @SerializedType(TypeProvider.class)
    private Object element;

    @Override
    public void accept(S s) {
        if (this.element == null) {
            s.remove(this.key);
        } else {
            s.offer(this.key, this.element);
        }
    }

    private static final class TypeProvider implements SerializedTypeProvider<SetDataKey<?,?>> {

        @Override
        public TypeToken<?> get(String field, SetDataKey<?,?> function) {
            return TypeToken.get(function.key.getElementToken().getType());
        }
    }
}
