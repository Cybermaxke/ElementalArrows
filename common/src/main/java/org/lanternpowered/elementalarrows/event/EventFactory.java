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

import java.util.HashMap;
import org.lanternpowered.elementalarrows.arrow.event.ArrowEvent;
import org.lanternpowered.elementalarrows.arrow.event.ArrowHitEntityEvent;
import org.lanternpowered.elementalarrows.arrow.event.ArrowHitGroundEvent;
import org.lanternpowered.elementalarrows.arrow.event.ArrowShotEvent;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.entity.projectile.source.ProjectileSource;

public class EventFactory {
    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.lanternpowered.elementalarrows.arrow.event.ArrowEvent}.
     * 
     * @param shooter The shooter
     * @param targetEntity The target entity
     * @return A new arrow event
     */
    public static ArrowEvent createArrowEvent(ProjectileSource shooter, Arrow targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("shooter", shooter);
        values.put("targetEntity", targetEntity);
        return EventFactoryUtils.createEventImpl(ArrowEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.lanternpowered.elementalarrows.arrow.event.ArrowHitEntityEvent}.
     * 
     * @param hitEntity The hit entity
     * @param shooter The shooter
     * @param targetEntity The target entity
     * @return A new arrow hit entity event
     */
    public static ArrowHitEntityEvent createArrowHitEntityEvent(Entity hitEntity, ProjectileSource shooter, Arrow targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("hitEntity", hitEntity);
        values.put("shooter", shooter);
        values.put("targetEntity", targetEntity);
        return EventFactoryUtils.createEventImpl(ArrowHitEntityEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.lanternpowered.elementalarrows.arrow.event.ArrowHitGroundEvent}.
     * 
     * @param shooter The shooter
     * @param targetEntity The target entity
     * @return A new arrow hit ground event
     */
    public static ArrowHitGroundEvent createArrowHitGroundEvent(ProjectileSource shooter, Arrow targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("shooter", shooter);
        values.put("targetEntity", targetEntity);
        return EventFactoryUtils.createEventImpl(ArrowHitGroundEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link org.lanternpowered.elementalarrows.arrow.event.ArrowShotEvent}.
     * 
     * @param shooter The shooter
     * @param targetEntity The target entity
     * @return A new arrow shot event
     */
    public static ArrowShotEvent createArrowShotEvent(ProjectileSource shooter, Arrow targetEntity) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("shooter", shooter);
        values.put("targetEntity", targetEntity);
        return EventFactoryUtils.createEventImpl(ArrowShotEvent.class, values);
    }
}

