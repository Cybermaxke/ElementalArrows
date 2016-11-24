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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.lanternpowered.elementalarrows.event.EventActionSet;
import org.lanternpowered.elementalarrows.event.TargetProvider;
import org.lanternpowered.elementalarrows.function.ObjectConsumer;
import org.lanternpowered.elementalarrows.registry.TypeRegistry;
import org.spongepowered.api.event.Event;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

public class EventActionSetDeserializer implements JsonDeserializer<EventActionSet> {

    private final TypeRegistry<Event> eventTypeRegistry;

    public EventActionSetDeserializer(TypeRegistry<Event> eventTypeRegistry) {
        this.eventTypeRegistry = eventTypeRegistry;
    }

    @Override
    public EventActionSet deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        final JsonObject obj = element.getAsJsonObject();
        final EventActionSet actionSet = new EventActionSet();

        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            final String[] events = entry.getKey().split(" ");
            final JsonObject value = entry.getValue().getAsJsonObject();
            final JsonElement action = value.get("action");
            parseAction(action, actionSet, events, ctx);
        }

        return actionSet;
    }

    private void parseAction(JsonElement element, EventActionSet actionSet, String[] events, JsonDeserializationContext ctx) {
        if (element.isJsonArray()) {
            for (JsonElement element1 : element.getAsJsonArray()) {
                parseAction(element1, actionSet, events, ctx);
            }
            return;
        }
        final JsonObject obj = element.getAsJsonObject();
        for (String event : events) {
            final Class eventType = this.eventTypeRegistry.get(event);
            if (eventType == null) {
                throw new JsonParseException("Unknown event type: " + event);
            }
            final String target = obj.get("target").getAsString();
            final JsonObject function = obj.getAsJsonObject("function");
            final ObjectConsumer consumer = ctx.deserialize(function, ObjectConsumer.class);
            final Function targetProvider = TargetProvider.of(eventType, target);
            //noinspection unchecked
            actionSet.add(eventType, e -> consumer.accept(targetProvider.apply(e)));
        }
    }
}
