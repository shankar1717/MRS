package KnilaProject;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.ReadConfig;


public class Baseclass {

	public WebDriver driver;
	public static Logger log;
	//public static Logger log = Logger.getLogger(Baseclass.class);
	//Common common;
	//read config.properties
	ReadConfig rd = new ReadConfig();

	public String browser = rd.getUrl();
	public String userName= rd.getUsername();
	public String userPassword = rd.getPassword();
	
	
	
	
	

}