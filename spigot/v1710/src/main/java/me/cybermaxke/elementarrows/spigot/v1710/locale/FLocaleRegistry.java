/**
 * This file is part of ElementalArrows.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.elementarrows.spigot.v1710.locale;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.minecraft.server.v1_7_R4.LocaleLanguage;
import net.minecraft.util.org.apache.commons.io.IOUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import me.cybermaxke.elementarrows.common.locale.LocaleRegistry;
import me.cybermaxke.elementarrows.spigot.v1710.util.Fields;

public class FLocaleRegistry implements LocaleRegistry {
	private final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
	private final Splitter splitter = Splitter.on('=').limit(2);

	private Map<String, String> map;
	private LocaleLanguage language;

	public FLocaleRegistry() {
		Field field0 = Fields.findField(LocaleLanguage.class, LocaleLanguage.class, 0);
		field0.setAccessible(true);

		Field field1 = Fields.findField(LocaleLanguage.class, Map.class, 0);
		field1.setAccessible(true);

		try {
			this.language = (LocaleLanguage) field0.get(null);
			this.map = (Map<String, String>) field1.get(this.language);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read(InputStream input) throws IOException {
		this.read(input, "en_US");
	}

	@Override
	public void read(InputStream input, String language) throws IOException {
		Preconditions.checkNotNull(language);
		Preconditions.checkNotNull(input);

		Map<String, String> values = new HashMap<String, String>();

		for (String string : IOUtils.readLines(input, Charsets.UTF_8)) {
			if (!string.isEmpty() && string.charAt(0) != '#') {
				String[] parts = Iterables.toArray(this.splitter.split(string), String.class);

				if (parts != null && parts.length == 2) {
					String value = this.pattern.matcher(parts[1]).replaceAll("%$1s");
					String key = parts[0];

					values.put(key, value);
				}
			}
		}

		this.map.putAll(values);
	}

	@Override
	public String get(String path) {
		return this.language.a(path);
	}

	@Override
	public String get(String path, Object... args) {
		return this.language.a(path, args);
	}

	@Override
	public String get(String language, String path) {
		return this.language.a(path);
	}

	@Override
	public String get(String language, String path, Object... args) {
		return this.language.a(path, args);
	}

}