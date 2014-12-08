package me.cybermaxke.elementarrows.common.util.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Resources {

	public static InputStream find(String path) throws IOException {
		InputStream is = Resources.class.getResourceAsStream(path);

		/**
		 * This is just for test purposes. When starting forge from the workspace.
		 */
		if (is == null) {
			File file = new File("src/main/resources/" + path);

			if (file.exists()) {
				is = new FileInputStream(file);
			}
		}

		return is;
	}

}