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

import org.lanternpowered.elementalarrows.function.Input;
import org.lanternpowered.elementalarrows.function.ObjectConsumer;
import org.lanternpowered.elementalarrows.util.PotionEffectHelper;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.value.mutable.CompositeValueStore;
import org.spongepowered.api.effect.potion.PotionEffect;

import java.util.List;
import java.util.Optional;

public class AddPotionEffects<S extends CompositeValueStore<S, H>, H extends ValueContainer<?>> implements ObjectConsumer<S> {

    @Input("potion-effects")
    private List<PotionEffect> potionEffects;

    @Override
    public void accept(S store) {
        final Optional<List<PotionEffect>> potionEffects = store.get(Keys.POTION_EFFECTS);
        if (potionEffects.isPresent()) {
            store.offer(Keys.POTION_EFFECTS, PotionEffectHelper.merge(potionEffects.get(), this.potionEffects));
        } else {
            store.offer(Keys.POTION_EFFECTS, this.potionEffects);
        }
    }
}
