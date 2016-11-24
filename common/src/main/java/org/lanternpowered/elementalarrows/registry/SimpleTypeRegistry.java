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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleTypeRegistry<T> implements TypeRegistry<T> {

    private final Map<String, Class<? extends T>> idToType = new HashMap<>();
    private final Map<Class<? extends T>, String> typeToId = new HashMap<>();

    @Override
    public void register(String identifier, Class<? extends T> type, String... secondaryIdentifiers) {
        checkNotNull(identifier, "identifier");
        checkNotNull(secondaryIdentifiers, "secondaryIdentifiers");
        identifier = identifier.toLowerCase();
        checkArgument(!identifier.isEmpty(), "identifier is empty");
        checkArgument(!this.idToType.containsKey(identifier), "identifier is already used! (" + identifier + ")");

        for (int index = 0; index < secondaryIdentifiers.length; index++) {
            checkNotNull(secondaryIdentifiers[index], "secondaryIdentifier[" + index + "]");
            String secondaryIdentifier = secondaryIdentifiers[index].toLowerCase();
            checkArgument(!identifier.isEmpty(), "secondaryIdentifier[" + index + "] is empty");
            checkArgument(!this.idToType.containsKey(secondaryIdentifier),
                    "secondaryIdentifier[" + index + "] is already used! (" + secondaryIdentifier + ")");
        }

        this.idToType.put(identifier, type);
        this.typeToId.put(type, identifier);

        for (String secondaryIdentifier : secondaryIdentifiers) {
            secondaryIdentifier = secondaryIdentifier.toLowerCase();
            this.idToType.put(secondaryIdentifier, type);
        }
    }

    @Override
    public Optional<String> getIdentifier(Class<? extends T> type) {
        return Optional.ofNullable(this.typeToId.get(checkNotNull(type, "type")));
    }

    @Override
    public Class<? extends T> get(String identifier) {
        return this.idToType.get(checkNotNull(identifier, "identifier"));
    }
}
