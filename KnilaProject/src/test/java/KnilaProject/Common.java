package KnilaProject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Common extends Baseclass  {
    
    WebDriver driver;
    WebDriverWait wait; 

    public Common(WebDriver driver ) {
        this.driver= driver;
        
    }

    public void waitForElement(WebElement element) {
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
