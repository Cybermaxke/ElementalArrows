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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.api.util.GuavaCollectors;

public class SimpleObjectTypeRegistry<T> implements ObjectTypeRegistry<T> {

    private final BiMap<String, T> objects = HashBiMap.create();

    @Override
    public void register(String identifier, T objectType) {
        checkNotNull(identifier, "identifier");
        checkNotNull(objectType, "objectType");

        identifier = identifier.toLowerCase();

        checkArgument(!identifier.isEmpty(), "identifier is empty");
        checkArgument(!this.objects.containsKey(identifier), "identifier is already used (" + identifier + ")");
        checkArgument(!this.objects.containsValue(objectType), "objects type is already registered (" + identifier + ")");

        this.objects.put(identifier, objectType);
    }

    @Override
    public Optional<T> get(String identifier) {
        return Optional.ofNullable(this.objects.get(checkNotNull(identifier, "identifier").toLowerCase()));
    }

    @Override
    public <E extends T> Optional<E> get(String identifier, Class<E> objectType) {
        checkNotNull(identifier, "identifier");
        checkNotNull(objectType, "objectType");

        identifier = identifier.toLowerCase();
        if (this.objects.containsKey(identifier)) {
            T type = this.objects.get(identifier);
            if (objectType.isInstance(type)) {
                return Optional.of(objectType.cast(type));
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<String> getIdentifier(T objectType) {
        return Optional.ofNullable(this.objects.inverse().get(checkNotNull(objectType, "objectType")));
    }

    @Override
    public <E extends T> List<E> getAll(Class<E> objectType) {
        checkNotNull(objectType, "objectType");
        //noinspection unchecked
        return (List) this.objects.values().stream().filter(objectType::isInstance).collect(GuavaCollectors.toImmutableList());
    }

    @Override
    public List<T> getAll() {
        return ImmutableList.copyOf(this.objects.values());
    }

    @Override
    public Map<String, T> getAllWithIdentifiers() {
        return ImmutableMap.copyOf(this.objects);
    }

    @Override
    public <E extends T> Map<String, E> getAllWithIdentifiers(Class<E> objectType) {
        checkNotNull(objectType, "objectType");
        final ImmutableMap.Builder<String, E> builder = ImmutableMap.builder();
        for (Entry<String, T> en : this.objects.entrySet()) {
            final T type = en.getValue();
            if (objectType.isInstance(type)) {
                builder.put(en.getKey(), objectType.cast(type));
            }
        }
        return builder.build();
    }

    @Override
    public boolean remove(T objectType) {
        return this.objects.inverse().remove(checkNotNull(objectType, "objectType")) != null;
    }
}
