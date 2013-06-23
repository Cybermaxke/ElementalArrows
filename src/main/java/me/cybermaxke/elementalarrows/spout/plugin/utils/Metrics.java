/**
 * Copyright 2011-2013 Tyler Blair. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */
package me.cybermaxke.elementalarrows.spout.plugin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.logging.Level;

import org.spout.api.Platform;
import org.spout.api.Spout;
import org.spout.api.Server;
import org.spout.api.exception.ConfigurationException;
import org.spout.api.plugin.Plugin;
import org.spout.api.plugin.PluginDescriptionFile;
import org.spout.api.scheduler.Task;
import org.spout.api.scheduler.TaskPriority;
import org.spout.api.util.config.yaml.YamlConfiguration;

public class Metrics {

	/**
	 * The current revision number
	 */
	private final static int REVISION = 6;
	/**
	 * The base url of the metrics domain
	 */
	private static final String BASE_URL = "http://mcstats.org";
	/**
	 * The url used to report a server's status
	 */
	private static final String REPORT_URL = "/report/%s";
	/**
	 * Interval of time to ping (in minutes)
	 */
	private final static int PING_INTERVAL = 10;
	/**
	 * Debug mode
	 */
	private final boolean debug;
	/**
	 * The plugin this metrics submits for
	 */
	private final Plugin plugin;
	/**
	 * The plugin configuration file
	 */
	private final YamlConfiguration configuration;
	/**
	 * The plugin configuration file
	 */
	private final File configurationFile;
	/**
	 * Unique server id
	 */
	private final String guid;
	/**
	 * Lock for synchronization
	 */
	private final Object optOutLock = new Object();
	/**
	 * Id of the scheduled task
	 */
	private volatile Task taskId = null;

	public Metrics(Plugin plugin) throws IOException, ConfigurationException {
		if (plugin == null) {
			throw new IllegalArgumentException("Plugin cannot be null");
		}

		this.plugin = plugin;

		// Load the config
		this.configurationFile = getConfigFile();
		this.configuration = new YamlConfiguration(this.configurationFile);
		this.configuration.load();

		// Add some defaults
		this.configuration.getChild("opt-out").getBoolean(false);
		this.configuration.getChild("guid").getString(UUID.randomUUID().toString());
		this.configuration.getChild("debug").getBoolean(false);
		this.configuration.setHeader("http://mcstats.org");
		this.configuration.save();

		// Load the guid then
		this.guid = configuration.getNode("guid").getString();
		this.debug = configuration.getChild("debug").getBoolean(false);
	}

	/**
	 * Start measuring statistics. This will immediately create an async repeating task as the plugin and send the
	 * initial data to the metrics backend, and then after that it will post in increments of PING_INTERVAL * 1200
	 * ticks.
	 *
	 * @return True if statistics measuring is running, otherwise false.
	 */
	public boolean start() {
		synchronized (optOutLock) {
			// Did we opt out?
			if (this.isOptOut()) {
				return false;
			}

			// Is metrics already running?
			if (taskId != null) {
				return true;
			}

			// Begin hitting the server with glorious data
			taskId = plugin.getEngine().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

				private boolean firstPost = true;

				public void run() {
					try {
						// This has to be synchronized or it can collide with the disable method.
						synchronized (Metrics.this.optOutLock) {
							// Disable Task, if it is running and the server owner decided to opt-out
							if (isOptOut() && taskId != null) {
								Metrics.this.plugin.getEngine().getScheduler().cancelTask(Metrics.this.taskId);
								Metrics.this.taskId = null;
							}
						}

						// We use the inverse of firstPost because if it is the first time we are posting,
						// it is not a interval ping, so it evaluates to FALSE
						// Each time thereafter it will evaluate to TRUE, i.e PING!
						Metrics.this.postPlugin(!this.firstPost);

						// After the first post we set firstPost to false
						// Each post thereafter will be a ping
						this.firstPost = false;
					} catch (IOException e) {
						if (Metrics.this.debug) {
							Spout.getLogger().log(Level.INFO, "[Metrics] " + e.getMessage());
						}
					}
				}
			}, 0, PING_INTERVAL * 1200, TaskPriority.LOW);

			return true;
		}
	}

	/**
	 * Has the server owner denied plugin metrics?
	 *
	 * @return true if metrics should be opted out of it
	 */
	public boolean isOptOut() {
		synchronized (this.optOutLock) {
			try {
				// Reload the metrics file.
				this.configuration.load();
			} catch (ConfigurationException ex) {
				if (this.debug) {
					Spout.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
				}
				return true;
			}
			return this.configuration.getNode("opt-out").getBoolean(false);
		}
	}

	/**
	 * Enables metrics for the server by setting "opt-out" to false in the config file and starting the metrics task.
	 *
	 * @throws java.io.IOException
	 * @throws org.spout.api.exception.ConfigurationException
	 */
	public void enable() throws IOException, ConfigurationException {
		// This has to be synchronized or it can collide with the check in the task.
		synchronized (this.optOutLock) {
			// Check if the server owner has already set opt-out, if not, set it.
			if (this.isOptOut()) {
				this.configuration.getNode("opt-out").setValue(false);
				this.configuration.save();
			}

			// Enable Task, if it is not running
			if (this.taskId == null) {
				this.start();
			}
		}
	}

	/**
	 * Disables metrics for the server by setting "opt-out" to true in the config file and canceling the metrics task.
	 *
	 * @throws java.io.IOException
	 * @throws org.spout.api.exception.ConfigurationException
	 */
	public void disable() throws IOException, ConfigurationException {
		// This has to be synchronized or it can collide with the check in the task.
		synchronized (this.optOutLock) {
			// Check if the server owner has already set opt-out, if not, set it.
			if (!this.isOptOut()) {
				this.configuration.getNode("opt-out").setValue(true);
				this.configuration.save();
			}

			// Disable Task, if it is running.
			if (this.taskId != null) {
				this.plugin.getEngine().getScheduler().cancelTask(this.taskId);
				this.taskId = null;
			}
		}
	}

	/**
	 * Gets the File object of the config file that should be used to store data such as the GUID and opt-out status
	 *
	 * @return the File object for the config file
	 */
	public File getConfigFile() {
		// I believe the easiest way to get the base folder (e.g craftbukkit set via -P) for plugins to use
		// is to abuse the plugin object we already have
		// plugin.getDataFolder() => base/plugins/PluginA/
		// pluginsFolder => base/plugins/
		// The base is not necessarily relative to the startup directory.
		File pluginsFolder = this.plugin.getDataFolder().getParentFile();

		// return => base/plugins/PluginMetrics/config.yml
		return new File(new File(pluginsFolder, "PluginMetrics"), "config.yml");
	}

	/**
	 * Generic method that posts a plugin to the metrics website
	 */
	private void postPlugin(boolean isPing) throws IOException {
		// Server software specific section.
		PluginDescriptionFile description = this.plugin.getDescription();
		String pluginName = description.getName();
		String pluginVersion = description.getVersion();
		String serverVersion = "Spout " + Spout.getEngine().getVersion();
		int playersOnline;
		if (Spout.getPlatform() == Platform.SERVER) {
			playersOnline = ((Server) Spout.getEngine()).getOnlinePlayers().length;
		} else {
			playersOnline = 1;
		}
		// END server software specific section -- all code below does not use any code outside of this class / Java

		// Construct the post data.
		final StringBuilder data = new StringBuilder();

		// The plugin's description file containg all of the plugin data such as name, version, author, etc
		data.append(encode("guid")).append('=').append(encode(this.guid));
		encodeDataPair(data, "version", pluginVersion);
		encodeDataPair(data, "server", serverVersion);
		encodeDataPair(data, "players", Integer.toString(playersOnline));
		encodeDataPair(data, "revision", String.valueOf(REVISION));

		// New data as of R6
		String osname = System.getProperty("os.name");
		String osarch = System.getProperty("os.arch");
		String osversion = System.getProperty("os.version");
		String java_version = System.getProperty("java.version");
		int coreCount = Runtime.getRuntime().availableProcessors();

		// normalize os arch .. amd64 -> x86_64
		if (osarch.equals("amd64")) {
			osarch = "x86_64";
		}

		encodeDataPair(data, "osname", osname);
		encodeDataPair(data, "osarch", osarch);
		encodeDataPair(data, "osversion", osversion);
		encodeDataPair(data, "cores", Integer.toString(coreCount));
		encodeDataPair(data, "java_version", java_version);

		// If we're pinging, append it.
		if (isPing) {
			encodeDataPair(data, "ping", "true");
		}

		// Create the url.
		URL url = new URL(BASE_URL + String.format(REPORT_URL, encode(pluginName)));

		// Connect to the website.
		URLConnection connection = url.openConnection();;
		connection.setDoOutput(true);

		// Write the data.
		final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(data.toString());
		writer.flush();

		// Now read the response.
		final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		final String response = reader.readLine();

		// Close resources.
		writer.close();
		reader.close();

		if (response == null || response.startsWith("ERR")) {
			throw new IOException(response); //Throw the exception.
		}
	}

	/**
	 * <p>Encode a key/value data pair to be used in a HTTP post request. This INCLUDES a & so the first key/value pair
	 * MUST be included manually, e.g:</p>
	 * <code>
	 * StringBuffer data = new StringBuffer();
	 * data.append(encode("guid")).append('=').append(encode(guid));
	 * encodeDataPair(data, "version", description.getVersion());
	 * </code>
	 *
	 * @param buffer the stringbuilder to append the data pair onto
	 * @param key the key value
	 * @param value the value
	 */
	private static void encodeDataPair(final StringBuilder buffer, final String key, final String value) throws UnsupportedEncodingException {
		buffer.append('&').append(encode(key)).append('=').append(encode(value));
	}

	/**
	 * Encode text as UTF-8
	 *
	 * @param text the text to encode
	 * @return the encoded text, as UTF-8
	 */
	private static String encode(final String text) throws UnsupportedEncodingException {
		return URLEncoder.encode(text, "UTF-8");
	}
}