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
package org.lanternpowered.elementalarrows.function.misc;

import org.lanternpowered.elementalarrows.function.Input;
import org.lanternpowered.elementalarrows.function.ObjectConsumer;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;

public class CreateExplosion<T extends Locatable> implements ObjectConsumer<T> {

    @Input("can-cause-fire")
    private boolean canCauseFire = true;

    @Input("radius")
    private double radius = 1.0;

    @Input("should-break-blocks")
    private boolean shouldBreakBlocks = true;

    @Input("should-damage-entities")
    private boolean shouldDamageEntities = true;

    @Input("should-play-smoke")
    private boolean shouldPlaySmoke = true;

    @Override
    public void accept(T t) {
        final Location<World> location = t.getLocation();

        final Explosion.Builder builder = Explosion.builder();
        builder.location(location);
        builder.canCauseFire(this.canCauseFire);
        builder.radius((float) this.radius);
        builder.shouldBreakBlocks(this.shouldBreakBlocks);
        builder.shouldDamageEntities(this.shouldDamageEntities);
        builder.shouldPlaySmoke(this.shouldPlaySmoke);

        location.getExtent().triggerExplosion(builder.build(), Cause.source(t).build());
    }
}
