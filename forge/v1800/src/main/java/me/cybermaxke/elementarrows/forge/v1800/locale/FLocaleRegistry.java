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
package me.cybermaxke.elementarrows.forge.v1800.locale;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.regex.Pattern;

import net.minecraftforge.fml.common.registry.LanguageRegistry;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import me.cybermaxke.elementarrows.common.locale.LocaleRegistry;

public class FLocaleRegistry implements LocaleRegistry {
	private final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
	private final Splitter splitter = Splitter.on('=').limit(2);

	@Override
	public void read(InputStream input) throws IOException {
		this.read(input, "en_US");
	}

	@Override
	public void read(InputStream input, String language) throws IOException {
		Preconditions.checkNotNull(language);
		Preconditions.checkNotNull(input);

		HashMap<String, String> values = new HashMap<String, String>();

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

		LanguageRegistry.instance().injectLanguage(language, values);
	}

	@Override
	public String get(String path) {
		return LanguageRegistry.instance().getStringLocalization(path);
	}

	@Override
	public String get(String path, Object... args) {
		String string = LanguageRegistry.instance().getStringLocalization(path);

		try {
			return String.format(string, args);
		} catch (IllegalFormatException e) {
			return "Format error: " + string;
		}
	}

	@Override
	public String get(String language, String path) {
		return LanguageRegistry.instance().getStringLocalization(path, language);
	}

	@Override
	public String get(String language, String path, Object... args) {
		String string = LanguageRegistry.instance().getStringLocalization(path, language);

		try {
			return String.format(string, args);
		} catch (IllegalFormatException e) {
			return "Format error: " + string;
		}
	}
}