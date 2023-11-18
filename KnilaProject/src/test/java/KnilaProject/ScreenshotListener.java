package KnilaProject;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;



public class ScreenshotListener implements ITestListener {

	private WebDriver driver;

	 public ScreenshotListener() {
	        // Default constructor
	    }

	    // Additional constructor to set the driver instance
	    public ScreenshotListener(WebDriver driver) {
	        this.driver = driver;
	    }

	    public void setDriver(WebDriver driver) {
	        this.driver = driver;
	    }

	@Override
	public void onTestSuccess(ITestResult result) {
		//System.out.println("Test Passed: " + result.getName());
		captureScreenshot(result.getMethod().getMethodName() + "_success");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed: " + result.getName());
		captureScreenshot(result.getMethod().getMethodName() + "_failed");
	}

	// Capture screenshot and handle exceptions
	public void captureScreenshot(String methodName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);

			// directory path for screenshots are manually created
			String screenshotDirectoryPath = "./screenshots/";

			// Create the 'screenshots' directory if it doesn't exist
			// String screenshotDirectoryPath = "./screenshots/";
			// File screenshotDirectory = new File(screenshotDirectoryPath);
			// if (!screenshotDirectory.exists()) {
			// screenshotDirectory.mkdirs(); // Make parent directories as needed
			// }
			
			String screenshotPath = screenshotDirectoryPath + methodName + ".png";
			//String screenshotPath = screenshotDirectoryPath + methodName + "_" + getCurrentTimeStamp() + ".png";
			File destination = new File(screenshotPath);
			FileUtils.copyFile(source, destination);
			//System.out.println("Screenshot taken: " + screenshotPath);
		} 
		catch (Exception e) 
		{
			// Log the exception or handle it based on your needs
			System.out.println("Exception while taking screenshot: " + e.getMessage());
		}
	}

	// Get current timestamp for naming the screenshot
//	private String getCurrentTimeStamp() {
//		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//	}

}
