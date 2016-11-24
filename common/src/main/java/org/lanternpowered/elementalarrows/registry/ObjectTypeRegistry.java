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

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ObjectTypeRegistry<T> {

    /**
     * Attempts to register a new object type.
     *
     * @param objectType The object type
     */
    void register(String identifier, T objectType);

    /**
     * Gets the identifier of the object type.
     *
     * @param objectType The object type
     * @return The identifier
     */
    Optional<String> getIdentifier(T objectType);

    /**
     * Attempts to get the object type with the specified identifier.
     *
     * @param identifier The identifier
     * @return The object type if found, otherwise {@link Optional#empty()}
     */
    Optional<T> get(String identifier);

    /**
     * Gets all the object types of the specified object type class.
     *
     * @param objectType The object type class
     * @return The object types
     */
    <E extends T> List<E> getAll(Class<E> objectType);

    /**
     * Gets all the object types.
     *
     * @return The object types
     */
    List<T> getAll();

    /**
     * Gets all the object types with as key their identifier.
     *
     * @return The object types
     */
    Map<String, T> getAllWithIdentifiers();

    /**
     * Gets all the item types with as key their identifier of the specified
     * object type class.
     *
     * @param objectType The object type class
     * @return The object types
     */
    <E extends T> Map<String, E> getAllWithIdentifiers(Class<E> objectType);

    /**
     * Attempts to get the object type with the specified identifier and matches
     * found object type with the specified type.
     *
     * @param identifier The identifier
     * @return The object type if found and matching, otherwise {@link Optional#empty()}
     */
    <E extends T> Optional<E> get(String identifier, Class<E> objectType);

    /**
     * Removes the object type and gets whether the type was actually registered.
     *
     * @param objectType The object type
     * @return Whether the type was actually registered
     */
    boolean remove(T objectType);
}
