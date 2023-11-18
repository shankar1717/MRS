package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {

	Properties prop = new Properties();

	public ReadConfig() {

		try {
			FileInputStream source = new FileInputStream(
					System.getProperty("user.dir") + "/configuration/config.properties");
			prop.load(source);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getUrl() {
		return prop.getProperty("url");
	}

	public String getUsername() {
		return prop.getProperty("username");
	}

	public String getPassword() {
		return prop.getProperty("password");
	}

}
