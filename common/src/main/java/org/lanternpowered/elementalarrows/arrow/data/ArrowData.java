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
import org.lanternpowered.elementalarrows.item.BaseItem;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public final class ArrowData extends AbstractSingleData<CustomArrow, ArrowData, ImmutableArrowData> {

    public ArrowData(CustomArrow value) {
        super(value, ArrowKeys.ARROW_TYPE);
    }

    @Override
    protected Value<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory().createValue(ArrowKeys.ARROW_TYPE, getValue());
    }

    @Override
    public Optional<ArrowData> fill(DataHolder dataHolder, MergeFunction overlap) {
        // Not sure what here is supposed to happen...
        if (!dataHolder.supports(ArrowKeys.ARROW_TYPE)) {
            return Optional.empty();
        }
        set(ArrowKeys.ARROW_TYPE, dataHolder.get(ArrowKeys.ARROW_TYPE).get());
        return Optional.of(this);
    }

    @Override
    public Optional<ArrowData> from(DataContainer container) {
        final Optional<BaseItem> optBaseItem = container.getCatalogType(ArrowKeys.ARROW_TYPE.getQuery(), BaseItem.class);
        if (optBaseItem.isPresent() && optBaseItem.get() instanceof CustomArrow) {
            return Optional.of(new ArrowData((CustomArrow) optBaseItem.get()));
        }
        return Optional.empty();
    }

    @Override
    public ArrowData copy() {
        return new ArrowData(getValue());
    }

    @Override
    public ImmutableArrowData asImmutable() {
        return new ImmutableArrowData(getValue());
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
