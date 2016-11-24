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
package org.lanternpowered.elementalarrows.parser.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.lanternpowered.elementalarrows.registry.TypeRegistry;

public class JsonTypeRegistryObjectDeserializer<T> implements JsonDeserializer<T> {

    private final TypeRegistry<T> registry;

    public JsonTypeRegistryObjectDeserializer(TypeRegistry<T> recipeTypeRegistry) {
        this.registry = recipeTypeRegistry;
    }

    @Override
    public T deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        final JsonObject json = element.getAsJsonObject();
        if (!json.has("object-type")) {
            throw new JsonParseException("The object type entry is missing!");
        }
        final String identifier = json.get("object-type").getAsString();
        final Class<? extends T> type0 = this.registry.get(identifier);
        if (type0 == null) {
            throw new JsonParseException("The object type entry is invalid! (" + identifier + ")");
        }
        json.remove("object-type");
        return ctx.deserialize(json, type0);
    }
}
