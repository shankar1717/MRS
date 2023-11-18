package KnilaProject;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestLogger {

    public static WebDriver driver;
    public static Logger log = Logger.getLogger(TestLogger.class);

    public static void main(String[] args) {
    	PropertyConfigurator.configure("src/test/resources/log4j.properties");

       // PropertyConfigurator.configure("log4j.properties");

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);

        driver.get("https://www.myntra.com/");
        log.info("Logged into Myntra");

        try {
            boolean text = driver.findElement(By.xpath("//a[contains(text(),'Women')]")).isDisplayed();
            log.info(text);
        } catch (Exception e) {
            log.error("Exception occurred", e);
        } finally {
            driver.quit();
            log.info("Quitting the driver" );
        }
    }
}
