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
package org.lanternpowered.elementalarrows.registry;

import java.util.Optional;

public interface TypeRegistry<T> {

    /**
     * Registers a type with the specified identifier.
     *
     * @param identifier The identifier
     * @param type The type
     * @param secondaryIdentifiers Identifiers that will also be matched to the
     *        given type, but the primary one is used for serializing the
     *        objects.
     */
    void register(String identifier, Class<? extends T> type, String... secondaryIdentifiers);

    /**
     * Gets the identifier by using it's type.
     *
     * @param type The type
     * @return The identifier if found, otherwise {@link Optional#empty()}
     */
    Optional<String> getIdentifier(Class<? extends T> type);

    /**
     * Gets the type by using it's identifier.
     *
     * @param identifier The identifier
     * @return The type if found, otherwise null
     */
    Class<? extends T> get(String identifier);
}
