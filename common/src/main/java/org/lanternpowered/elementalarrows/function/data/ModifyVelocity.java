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

import com.flowpowered.math.vector.Vector3d;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.lanternpowered.elementalarrows.function.ObjectConsumer;
import org.lanternpowered.elementalarrows.parser.Field;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.value.mutable.CompositeValueStore;

public class ModifyVelocity<S extends CompositeValueStore<S, H>, H extends ValueContainer<?>> implements ObjectConsumer<S> {

    public enum Type {
        set     ('+') {
            @Override
            public Vector3d apply(Vector3d vectorA, Vector3d vectorB) {
                return vectorB;
            }
        },
        add     ('+') {
            @Override
            public Vector3d apply(Vector3d vectorA, Vector3d vectorB) {
                return vectorA.add(vectorB);
            }
        },
        sub     ('-') {
            @Override
            public Vector3d apply(Vector3d vectorA, Vector3d vectorB) {
                return vectorA.sub(vectorB);
            }
        },
        div     ('/') {
            @Override
            public Vector3d apply(Vector3d vectorA, Vector3d vectorB) {
                return vectorA.div(vectorB);
            }
        },
        mul     ('*') {
            @Override
            public Vector3d apply(Vector3d vectorA, Vector3d vectorB) {
                return vectorA.mul(vectorB);
            }
        },
        ;

        private final char code;

        Type(char code) {
            this.code = code;
        }

        public abstract Vector3d apply(Vector3d vectorA, Vector3d vectorB);
    }

    public static class TypeDeserializer implements JsonDeserializer<Type> {

        @Override
        public Type deserialize(JsonElement element, java.lang.reflect.Type type, JsonDeserializationContext ctx)
                throws JsonParseException {
            final String s = element.getAsString();
            for (Type type1 : Type.values()) {
                if ((s.length() == 1 && s.charAt(0) == type1.code) ||
                        s.equalsIgnoreCase(type1.name())) {
                    return type1;
                }
            }
            throw new IllegalArgumentException("Unknown velocity modifier type: " + s);
        }
    }

    @Field("type")
    private Type type;

    @Field("vector")
    private Vector3d vector3d;

    @Override
    public void accept(S s) {
        s.offer(Keys.VELOCITY, this.type.apply(s.get(Keys.VELOCITY).orElse(Vector3d.ZERO), this.vector3d));
    }
}
