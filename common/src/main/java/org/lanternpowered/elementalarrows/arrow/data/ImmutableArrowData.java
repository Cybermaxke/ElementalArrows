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
package org.lanternpowered.elementalarrows.arrow.data;

import org.lanternpowered.elementalarrows.arrow.ArrowKeys;
import org.lanternpowered.elementalarrows.arrow.CustomArrow;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public final class ImmutableArrowData extends AbstractImmutableSingleData<CustomArrow, ImmutableArrowData, ArrowData> {

    protected ImmutableArrowData(CustomArrow value) {
        super(value, ArrowKeys.ARROW_TYPE);
    }

    @Override
    protected ImmutableValue<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory().createValue(ArrowKeys.ARROW_TYPE, getValue()).asImmutable();
    }

    @Override
    public ArrowData asMutable() {
        return new ArrowData(getValue());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(ArrowKeys.ARROW_TYPE.getQuery(), getValue());
    }
}
