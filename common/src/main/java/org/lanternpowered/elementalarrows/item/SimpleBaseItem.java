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
package org.lanternpowered.elementalarrows.item;

import org.lanternpowered.elementalarrows.parser.Field;
import org.spongepowered.api.text.Text;

import java.util.Optional;

import javax.annotation.Nullable;

public class SimpleBaseItem implements BaseItem {

    @Field("id")
    private String id;

    @Field("name")
    private Text name;

    @Nullable
    @Field("item-model")
    private String model;

    @Nullable private String plainName;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        if (this.plainName == null) {
            this.plainName = this.name.toPlain();
        }
        return this.plainName;
    }

    public Optional<String> getItemModel() {
        return Optional.ofNullable(this.model);
    }
}
