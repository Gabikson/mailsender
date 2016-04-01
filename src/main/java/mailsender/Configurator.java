package mailsender;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Configurator {

	private final String PROPERTY_FILE_NAME = "serverconfig";

	private File propertyFile;
	private ClassLoader loader;
	private ResourceBundle bundle;

	public Configurator readConfigs() throws Exception {
		this.propertyFile = new File("");
		try {
			URL[] urls = { propertyFile.toURI().toURL() };
			loader = URLClassLoader.newInstance(urls);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.bundle = ResourceBundle.getBundle(PROPERTY_FILE_NAME, Locale.getDefault(), loader);
		return this;
	}

	public Properties toProperties() {
		Properties properties = new Properties();
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = bundle.getString(key);
			properties.put(key, value);
		}
		return properties;
	}
}
