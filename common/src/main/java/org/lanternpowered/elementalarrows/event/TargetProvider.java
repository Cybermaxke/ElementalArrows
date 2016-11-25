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
package org.lanternpowered.elementalarrows.event;

import com.google.common.base.Throwables;

import java.lang.reflect.Method;
import java.util.function.Function;

public final class TargetProvider {

    public static <S, R> Function<S, R> of(Class<S> source, String targetName) {
        for (Method method : source.getMethods()) {
            final Target target = method.getAnnotation(Target.class);
            if (target == null) {
                continue;
            }
            if (target.value().equals(targetName)) {
                if (method.getParameterTypes().length != 0) {
                    throw new IllegalStateException("A target provider method may not have parameters, method: " +
                            method.getName() + " in the class: " + method.getDeclaringClass());
                }
                return s -> {
                    try {
                        //noinspection unchecked
                        return (R) method.invoke(s);
                    } catch (Exception e) {
                        throw Throwables.propagate(e);
                    }
                };
            }
        }
        throw new IllegalArgumentException("Unable to find the target \"" + targetName + "\" in the class \"" + source.getName() + "\"");
    }
}
