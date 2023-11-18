package KnilaProject;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.ITestResult;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(ScreenshotListener.class)
public class HealthcareApplicationTest extends Baseclass {

	public WebDriver driver;
	public static Logger log = Logger.getLogger(HealthcareApplicationTest.class);
	public WebDriverWait wait;
	Select select;

	// patient details
	String givenName = "Jane";
	String fName = "Walker";
	String gender = "Male";
	String date = "01";
	String month = "January";
	String year = "2000";

	// Input values for height and weight
	public String heightValue = "150";
	public String weightValue = "50";

	public String expectedBMIString;

	public String deleteReason = "For Testing";

	@BeforeClass
	public void setUp() throws Exception {

		PropertyConfigurator.configure("src/test/resources/log4j.properties");
		// PropertyConfigurator.configure("log4j.properties");
		
		// Set up WebDriver, open the application URL, and log in
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		log.info("Webpage Launched");
		driver.get(browser);
		// driver.get("https://qa-refapp.openmrs.org/openmrs/login.htm");

	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		// Call captureScreenshot method from ScreenshotListener
		ScreenshotListener screenshotListener = new ScreenshotListener();
		screenshotListener.setDriver(driver); // Set the driver instance
		screenshotListener.captureScreenshot(result.getMethod().getMethodName());
	}

	@AfterClass
	public void tearDown() {
		// Quit the WebDriver
		driver.quit();
	}

	@Test(priority = 0)
	public void userLogin() {

		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		username.sendKeys(userName);
		WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
		password.sendKeys(userPassword);

		// select Location
		driver.findElement(By.xpath("//li[@id='Outpatient Clinic']")).click();

		// click Login
		driver.findElement(By.xpath("//input[@id='loginButton']")).click();

	}

	@Test(priority = 1)
	public void verifyDashboardRedirect() {
		// Redirect to the dashboard and verify
		String actualdashboardTitle = driver.getTitle();
		String expecteddashboardTitle = "Home";
		Assert.assertEquals(actualdashboardTitle, expecteddashboardTitle, "Dashboard page not reached.");
		// System.out.println("Dadboard page verified");
		log.info("Dashboard page is Displayed");

	}

	@Test(priority = 2)
	public void registerPatient() throws Exception {
		// Steps 3-8: Register a patient and verify details

		// select register a parent
		driver.findElement(By.xpath("//a[normalize-space()='Register a patient']")).click();

		// String givenName = "Jack";
		WebElement patientName = driver.findElement(By.xpath("//input[@name='givenName']"));
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(patientName));
		patientName.sendKeys(givenName);

		// String fName = "Smith";
		WebElement familyName = driver.findElement(By.xpath("//input[@name='familyName']"));
		familyName.sendKeys(fName);

		WebElement nextButton = driver.findElement(By.xpath("//button[@id='next-button']"));
		nextButton.click();

		// String gender = "Male";
		WebElement selectGender = driver.findElement(By.xpath("//select[@id='gender-field']"));
		select = new Select(selectGender);
		select.selectByVisibleText(gender);

		nextButton.click();

		// birth date
		// String date ="01";
		WebElement birthDate = driver.findElement(By.xpath("//input[@id='birthdateDay-field']"));
		birthDate.sendKeys(date);
		// birth month
		// String month ="January";
		WebElement selectMonth = driver.findElement(By.xpath("//select[@id='birthdateMonth-field']"));
		select = new Select(selectMonth);
		select.selectByVisibleText(month);
		// bith year
		// String year ="2000";
		WebElement birthYear = driver.findElement(By.xpath("//input[@id='birthdateYear-field']"));
		birthYear.sendKeys(year);

		nextButton.click();

		// address
		String actualAddress = "13B Block";
		WebElement address = driver.findElement(By.xpath("//input[@id='address1']"));
		address.sendKeys(actualAddress);

		// City
		String city = "Newyork";
		driver.findElement(By.xpath("//input[@id='cityVillage']")).sendKeys(city);

		// State
		String state = "NY";
		driver.findElement(By.xpath("//input[@id='stateProvince']")).sendKeys(state);

		// country
		String country = "US";
		driver.findElement(By.xpath("//input[@id='country']")).sendKeys(country);

		// postalcode
		String pincode = "1022";
		driver.findElement(By.xpath("//input[@id='postalCode']")).sendKeys(pincode);

		nextButton.click();

		// phonenumber
		String number = "9884098840";
		WebElement phoneNumber = driver.findElement(By.xpath("//input[@name='phoneNumber']"));
		phoneNumber.sendKeys(number);
		Thread.sleep(2000);
		nextButton.click();

		// skip relatives
		nextButton.click();
		log.info("Patient details Entered suceesfully");

		Thread.sleep(2000);

		// 7. Then at Confirm page, verify the given Name, Gender, Birthdate, Address,
		// Phone number are populated correctly using Assertion.

		// verify patient name
		WebElement nameElement = driver.findElement(By.xpath("//*[@id='dataCanvas']//div//p[1]"));
		String actualName = nameElement.getText();
		// log.info("Actual:" + actualName);
		// Split the actual name to extract first and last names
		String[] actualNames = actualName.split(", ");
		String actualFirstName = actualNames[0].substring(6); // Assuming "Name: " is fixed
		String actualLastName = actualNames[1];

		// Assertion
		log.info("Actual Name:" + actualFirstName + "," + actualLastName);
		log.info("Expected Name:" + givenName + "," + fName);
		log.info("Patient Name Verified");
		Assert.assertEquals(actualFirstName, givenName, "First names do not match.");
		Assert.assertEquals(actualLastName, fName, "Last names do not match.");
		Thread.sleep(2000);

		// verify Gender
		WebElement genderElement = driver.findElement(By.xpath("//*[@id='dataCanvas']//div//p[2]"));
		String actualgender = genderElement.getText();

		String genderParts[] = actualgender.split(": ");
		String actualGender = genderParts[1];

		log.info("Actual Gender:" + actualGender);
		log.info("Expected Gender:" + gender);

		log.info("Gender verified");
		// Assertion
		Assert.assertEquals(actualGender, gender, "Gender assertion failed");

		// verify Birthdate
		WebElement birthDateElement = driver.findElement(By.xpath("//*[@id='dataCanvas']//child::div//p[3]"));
		String actualBirthDate = birthDateElement.getText();

		// Extract day, month, and year //split 'gender:' and select index[1] after the
		// split with", "
		String[] birthdateParts = actualBirthDate.split(": ")[1].split(", ");
		// Using a loop to print each element
		for (String part : birthdateParts) {
			System.out.print("Actual Birthdare:" + part);
		}
		String actualDay = birthdateParts[0];
		String actualMonth = birthdateParts[1];
		String actualYear = birthdateParts[2];
		log.info("Birthdate verified");
		// Assertions
		Assert.assertEquals(actualDay, date, "Day assertion failed");
		Assert.assertEquals(actualMonth, month, "Month assertion failed");
		Assert.assertEquals(actualYear, year, "Year assertion failed");

		// verify Address
		WebElement addressElement = driver.findElement(By.xpath("//*[@id='dataCanvas']//child::div//p[4]"));
		String actualDisplayaddress = addressElement.getText();
		// System.out.println("A:"+actualDisplayaddress);
		String expectedAddress = "13B Block, Newyork, NY, US, 1022";
		String actualAdd = actualDisplayaddress.split(": ")[1];
		log.info("Actual Address:" + actualAdd);
		log.info("Expected Address:" + expectedAddress);
		log.info("Address Verified");
		// Assertions
		Assert.assertEquals(expectedAddress, actualAdd, "Birthdate assertion failed");

		// verify phonenumber
		WebElement phoneNumberElement = driver.findElement(By.xpath("//*[@id='dataCanvas']//child::div//p[5]"));
		String actualPhoneNumber = phoneNumberElement.getText();
		// System.out.println("A:"+actualPhoneNumber);
		// Remove the "Phone Number: " prefix
		String actualPhoneNumberWithoutPrefix = actualPhoneNumber.replace("Phone Number: ", "");
		System.out.println(actualPhoneNumberWithoutPrefix);
		log.info("Actual PhoneNumber:" + actualPhoneNumberWithoutPrefix);
		log.info("Expected PhoneNumber:" + number);
		log.info("PhoneNumber Verified");
		// Assertion
		Assert.assertEquals(actualPhoneNumberWithoutPrefix, number, "Phone numbers do not match.");
		Thread.sleep(3000);
	}

	@Test(priority = 3)
	public void calculateAge() throws Exception {

		// 8. Click on Confirm and verify Patient details page is redirected and verify
		// the age is calculated correctly based on the date of birth provided.
		WebElement confirmButton = driver.findElement(By.xpath("//input[@id='submit']"));
		confirmButton.click();
		Thread.sleep(5000);

		// verify Patient details page is redirected
		String patientDetailsPage = driver.getTitle();
		log.info("Redirected to patient details page");
		// Assertion
		Assert.assertEquals("OpenMRS Electronic Medical Record", patientDetailsPage,
				"Patient details page is not Visible");
		Thread.sleep(5000);

		// verify the age is calculated correctly based on the date of birth provided.

		// Get the WebElement representing the patient's age
		WebElement patientAge = driver.findElement(By.xpath("(//*[@class='gender-age col-auto']//following::span)[2]"));

		wait.until(ExpectedConditions.visibilityOf(patientAge));

		// Extract the numeric part of the patient's age and trim spaces
		String actualageText = patientAge.getText();
		String splitActualValue = actualageText.split(" ")[0].trim();

		// Calculate the expected age using METHOD the provided date, month, and year
		int expectedAge = expetcedcalculateAge(date, month, year);

		log.info("Actual Age:" + splitActualValue);
		log.info("Expected Age:" + String.valueOf(expectedAge));
		log.info("Age is verified calculated correctly based on the date of birth provided");
		// Assertion to check if actual and expected values are equal
		Assert.assertEquals(splitActualValue, String.valueOf(expectedAge),
				"Actual value does not match the expected value");
		log.info("Age is calculated correctly based on the date of birth provided");
	}

	public static int expetcedcalculateAge(String date, String month, String year) {

		// Combine the date, month, and year into a string in "dd MMMM yyyy" format
		String dateString = date + " " + month + " " + year;

		// Parse the string into a LocalDate object using a specific formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		LocalDate birthDate = LocalDate.parse(dateString, formatter);

		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Calculate the age
		int age = currentDate.getYear() - birthDate.getYear();

		// Adjust age based on the birthdate in the current year
		if (birthDate.getMonthValue() > currentDate.getMonthValue()
				|| (birthDate.getMonthValue() == currentDate.getMonthValue()
				&& birthDate.getDayOfMonth() > currentDate.getDayOfMonth())) {
			age--;
		}
		return age;
	}

	@Test(priority = 4)
	public void uploadAttachment() throws Exception {
		// 9. Click on Start Visit and Confirm the visit.

		// wait= new WebDriverWait(driver,10);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[normalize-space()='Start Visit'])[2]")));
		// click on Start Visit
		driver.findElement(By.xpath("(//div[normalize-space()='Start Visit'])[2]")).click();

		// click on Confirm Button
		driver.findElement(By.xpath("//*[@id=\"start-visit-with-visittype-confirm\"]")).click();
		Thread.sleep(5000);
		// click on attachemt
		driver.findElement(By.xpath("//a[@id='attachments.attachments.visitActions.default']")).click();

		Thread.sleep(10000);

		WebElement captionName = driver.findElement(By.xpath("//textarea[@placeholder='Enter a caption']"));
		captionName.sendKeys("Testfile");

		// 10. Click on Attachment and complete the upload process.

		driver.findElement(By.xpath("//*[text()='Upload file']")).click();

		// XPath of the pop-up element // toaster message
		By popupXPath = By.xpath("//*[contains(text(), 'The attachment was successfully uploaded')]");

		// Wait for the pop-up to be present
		WebElement popupElement = wait.until(ExpectedConditions.presenceOfElementLocated(popupXPath));

		// Wait for the pop-up to be visible
		wait.until(ExpectedConditions.visibilityOf(popupElement));

		// Verify the text content of the pop-up message
		String actualPopupText = popupElement.getText();
		String expectedPopupText = "The attachment was successfully uploaded.";
		log.info("ActualPopupText: " + actualPopupText + '\n' + "ExpectedPopupText: " + expectedPopupText);
		log.info("Toaster message is Displayed for the successful attachment");

		// Assertion
		Assert.assertEquals(actualPopupText, expectedPopupText,
				"Toaster message text does not match the expected text");
		Thread.sleep(2000);
	}

	@Test(priority = 5)
	public void verifyAttachment() throws Exception {
		// Thread.sleep(4000);

		// 12. Redirect to Patient details screen.

		// patient details screen
		driver.findElement(By.xpath("//a[contains(@href,'/openmrs/coreapps/clinicianfacing/patient.page?patientId')]"))
		.click();

		Thread.sleep(5000);

		// 13. Verify Attachment section has attachment.

		// Identify the Attachments section
		WebElement attachmentsSection = driver.findElement(By.xpath("//*[@id=\"content\"]//h3[text()='ATTACHMENTS']"));

		// Check if the Attachments section is present
		if (attachmentsSection.isDisplayed()) {
			// Identify the attachment elements within the Attachments section
			List<WebElement> attachmentElements = attachmentsSection
					.findElements(By.xpath("//*[@class='att_thumbnail-image-section att_click-pointer']"));

			// Check if there is at least one attachment
			if (!attachmentElements.isEmpty()) {
				log.info("Attachment section has attachment");

			} else {
				System.out.println("Attachments section does not have any attachments.");
			}
		} else {
			System.out.println("Attachments section is not visible on the page.");
		}
		Thread.sleep(5000);
	}

	@Test(priority = 6)
	public void recentEntry() {
		// 14. Verfiy Recent Visit has one entry for current date with Attachment Upload
		// tag.

		// Identify the Recent Visits section
		WebElement recentVisitsSection = driver.findElement(By.xpath("//h3[text()='Recent Visits']"));

		// Check if the Recent Visits section is present
		if (recentVisitsSection.isDisplayed()) {
			// Identify the visit entry elements within the Recent Visits section
			List<WebElement> visitEntryElements = recentVisitsSection.findElements(By.xpath("//li[@class='ng-scope']"));

			// Get the current date in the "dd.MMM.yyyy" format
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
			String currentDateText = LocalDate.now().format(formatter);
			System.out.println(currentDateText);
			String tag = null;

			// Iterate through visit entries to find the one with the current date and
			// 'Attachment Upload' tag
			boolean attachmentUploadFound = false;
			for (WebElement visitEntry : visitEntryElements) {
				// Check if the visit entry contains the current date
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='ng-binding']")));
				WebElement dateElement = visitEntry.findElement(By.xpath("//a[@class='ng-binding']"));
				log.info("Actual Date:" + dateElement.getText());

				if (dateElement.isDisplayed() && dateElement.getText().equals(currentDateText)) {
					// Check if the visit entry contains the 'Attachment Upload' tag
					WebElement tagElement = visitEntry.findElement(
							By.xpath(".//div[@class='tag ng-binding ng-scope' and text()='Attachment Upload']"));
					tag = tagElement.getText();
					log.info("Expected tagElement:" + tag);

					if (tagElement.isDisplayed()) {
						attachmentUploadFound = true;
						break;
					}
				}
			}

			log.info("Verfied Recent entry with current date:" + currentDateText + "  and : " + tag + "-tag found");
			// Assertion
			Assert.assertTrue(attachmentUploadFound,
					"No visit entry found with current date (" + currentDateText + ") and 'Attachment Upload' ");
		}
	}

	@Test(priority = 7)
	public void verifyDisplayedBMI() throws Exception {

		// 15. Click on the End Visit action at RHS.
		driver.findElement(By.xpath(
				"//ul[@class='float-left d-none d-lg-block']//div[@class='col-11 col-lg-10'][normalize-space()='End Visit']"))
		.click();
		Thread.sleep(1000);
		// click yes
		driver.findElement(By.xpath("//div[@id='end-visit-dialog']//button[@class='confirm right'][text()='Yes']"))
		.click();
		Thread.sleep(5000);

		// 16. Start Visit again and Click on Capture Vitals menu.
		driver.findElement(By.xpath("//div[contains(text(),'Start Visit')]")).click();
		// confirm
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='start-visit-with-visittype-confirm']")).click();

		// driver.findElement(By.xpath("//div[@id='end-visit-dialog']//button[@class='confirm
		// right']")).click();
		Thread.sleep(2000);

		// click on Capture Vitals menu
		driver.findElement(By.xpath("//a[@id='referenceapplication.realTime.vitals']")).click();
		Thread.sleep(3000);

		// 17. Enter Height & Weight and Verify displayed BMI is calculated correctly
		// using BMI formula.

		// Height
		driver.findElement(By.xpath("//*[@id=\"w8\"]")).sendKeys(heightValue);
		// // driver.findElement(By.xpath("//span[@id='height']")).sendKeys("150");

		// click next
		WebElement clickNextButton = driver.findElement(By.xpath("//button[@id='next-button']"));
		clickNextButton.click();

		// weight
		driver.findElement(By.xpath("//input[@id='w10']")).sendKeys(weightValue);

		// click next
		clickNextButton.click();

		// Find the element that displays the calculated BMI
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='calculated-bmi']")));

		WebElement bmiResultElement = driver.findElement(By.xpath("//*[@id='calculated-bmi']"));

		// Get the BMI result in the webpage
		String displayedBMI = bmiResultElement.getText();
		double actualBMI = Double.parseDouble(displayedBMI);
		log.info(" Actual displayedBMI:" + actualBMI);

		// Convert input values to double
		double height = Double.parseDouble(heightValue);
		double weight = Double.parseDouble(weightValue);

		// getting data from Method name - calculateBMI
		double expectedBMI = calculateBMI(height, weight);

		// Format the BMI to have one digit after the decimal point
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		String formattedBMI = decimalFormat.format(expectedBMI);
		expectedBMIString = String.valueOf(formattedBMI);// Using String.valueOf()

		log.info(" ExpectedBMI:" + expectedBMIString);
		log.info("Verified displayed BMI is calculated using BMI formula");

		// Assert that the displayed BMI matches the expected BMI
		Assert.assertEquals(expectedBMIString, String.valueOf(actualBMI),
				"actual BMI does not match the expected BMI.");
	}

	@Test(priority = 8)
	public void calculatedBMI() throws Exception {

		// 18. Click on Save Form and Save button.
		driver.findElement(By.xpath("//a[@id='save-form']")).click();

		// savebutton
		driver.findElement(By.xpath("//button[@class='submitButton confirm right focused']")).click();
		Thread.sleep(2000);

		// 19. Click on End Visit and redirect to Patient details page.
		driver.findElement(By.xpath("//i[@class='icon-off']")).click();
		Thread.sleep(2000);

		// confirm yes
		driver.findElement(By.xpath("//div[@id='end-visit-dialog']//button[@class='confirm right']")).click();
		Thread.sleep(3000);

		// navigate to patient details page
		driver.findElement(By.xpath("//span[@class='PersonName-givenName']")).click();
		Thread.sleep(5000);
	}

	// Method to calculate BMI
	private static double calculateBMI(double heightInCentimeters, double weightInKilograms) {
		// Convert height to meters
		double heightInMeters = heightInCentimeters / 100.0;

		// BMI formula: weight / (height * height)
		double calculatedBMI = weightInKilograms / (heightInMeters * heightInMeters);

		return calculatedBMI;
	}

	@Test(priority = 9)
	public void captureVitals() throws Exception {
		// 20. In Patient details screen, verify the given Height and Weight is
		// displayed correctly along with calculated BMI.

		// Replace these values with the Height, Weight, and expected BMI
		String expectedHeight = (heightValue + "cm");
		String expectedWeight = (weightValue + "kg");

		// Wait for the Patient details screen to load
		wait = new WebDriverWait(driver, 10);
		WebElement heightElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("height")));
		WebElement weightElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("weight")));
		WebElement bmiElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("calculated-bmi")));

		// Get the text values from the elements
		String actualHeight = heightElement.getText();
		String actualWeight = weightElement.getText();
		String actualBMI = bmiElement.getText();
		log.info(" ActualHeight: " + actualHeight + "\n actualWeight: " + actualWeight + "\n actualBMI: " + actualBMI);
		log.info("In Patient details screen, verified the given Height, Weight and BMI");

		// Assert that the displayed Height, Weight, and BMI match the expected values
		Assert.assertEquals(actualHeight, expectedHeight, "Height does not match the expected value.");
		Assert.assertEquals(actualWeight, expectedWeight, "Weight does not match the expected value.");
		Assert.assertEquals(actualBMI, expectedBMIString, "BMI does not match with expected value");

		// 21. Verfiy Recent Visit has one more new entry for current date with Vitals
		// tag.

		// Identify the Recent Visits section
		WebElement recentVisitsSection = driver.findElement(By.xpath("//h3[text()='Recent Visits']"));

		// Check if the Recent Visits section is present
		if (recentVisitsSection.isDisplayed()) {
			// Get the current date in the "dd.MMM.yyyy" format
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
			String currentDate = LocalDate.now().format(formatter);
			log.info(" Expected Current Date: " + currentDate);

			// Identify the visit entry elements within the Recent Visits section
			List<WebElement> entries = recentVisitsSection.findElements(By.xpath("//li[@class='ng-scope']"));
			Thread.sleep(5000);
			// Check if there is entry
			if (!entries.isEmpty()) {
				// Iterate over the entries
				for (WebElement entry : entries) {
					// Check if the entry contains the current date and the 'Vitals' tag
					WebElement dateElement = entry.findElement(By.xpath("//a[@class='ng-binding']"));
					String actualdate = dateElement.getText();
					System.out.println(" Actual Date in Entry: " + actualdate);

					// verify current date
					Assert.assertEquals(actualdate, currentDate, "Current date not matched");

					WebElement tagElement = entry
							.findElement(By.xpath("//div[@class='tag ng-binding ng-scope' and text()='Vitals']"));
					String tag = tagElement.getText();

					// Assert that the tagElement is displayed
					Assert.assertTrue(tagElement.isDisplayed(),
							"Vitals tag is not displayed for the entry with date: " + actualdate);

					// Log the verification
					log.info("New entry for current date '" + actualdate + "' with '" + tag + "' tag is verified.");

					// Exit the loop after the first matching entry is found
					break;
				}
			} else {
				// Log a message indicating no entries are found
				log.info("No entries found in the Recent Visits section.");
			}
		}

	}

	@Test(priority = 10)
	public void mergeVisits() throws Exception {
		// 22.Click on Merge Visits, select these 2 visit and click on Merge Selected
		// Visits button.

		// click merge visit button.
		driver.findElement(
				By.xpath("//*[@id='org.openmrs.module.coreapps.mergeVisits']//div[contains(text(),'Merge Visits')]"))
		.click();
		Thread.sleep(5000);

		// 1
		driver.findElement(By.xpath("(//*[@class='selectVisit'])[1]")).click();
		Thread.sleep(2000);

		// 2 delete
		driver.findElement(By.xpath("(//*[@class='selectVisit'])[2]")).click();
		Thread.sleep(2000);

		// merge button
		driver.findElement(By.xpath("//input[@id='mergeVisitsBtn']")).click();
		Thread.sleep(3000);

		// 23. Redirect to patient details page by clicking on Return button.
		driver.findElement(By.xpath("//input[@value='Return']")).click();
		Thread.sleep(5000);

		// 24. Verfiy Recent Visit has become one entry for current date with
		// Vitals,Attachment Upload tag.

		// Identify the Recent Visits section
		WebElement recentVisitsSection = driver.findElement(By.xpath("//h3[text()='Recent Visits']"));

		// Check if the Recent Visits section is present
		if (recentVisitsSection.isDisplayed()) {
			// Get the current date in the "dd.MMM.yyyy" format
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
			String expectedcurrentDate = LocalDate.now().format(formatter);
			log.info(" Expected Current Date: " + expectedcurrentDate);

			// entries for the current date with both 'Vitals' and 'Attachment Upload' tags
			WebElement dateElement = driver.findElement(By.xpath(".//a[@class='ng-binding']"));
			String actualdate = dateElement.getText();
			log.info(" Actual Date:" + actualdate);

			String expectedTags = "Vitals, Attachment Upload";
			WebElement vitalsTagElement = driver.findElement(
					By.xpath(".//div[@class='tag ng-binding ng-scope' and text()='Vitals, Attachment Upload']"));
			String actualtags = vitalsTagElement.getText();
			// Log the verification
			log.info("One entry for current date '" + actualdate + "'and'" + actualtags + "'tags is verified.");

			// Assert that there is exactly one matching entry
			Assert.assertEquals(actualdate, expectedcurrentDate);
			Assert.assertEquals(actualtags, expectedTags);
		}
	}

	@Test(priority = 11)
	public void addPastVisit() throws Throwable {

		// 25. Click on Add Past Visit and verify the future date is not clickable in
		// the date picker.
		driver.findElement(By.xpath("//div[contains(text(),'Add Past Visit')]")).click();

		Thread.sleep(10000);
		// click cancel button
		driver.findElement(By.xpath(" //div[@id='retrospective-visit-creation-dialog']//button[@class='cancel']"))
		.click();
		Thread.sleep(10000);

	}

	@Test(priority = 12)
	public void deletePatient() throws Exception {

		// 26. Redirect to patient details page
		WebElement patientIdElement = driver.findElement(By.xpath("//div[@class='float-sm-right']//child::span"));
		String patientID = patientIdElement.getText();
		System.out.println("patientID:" + patientID);

		// 27. Click on Delete Patient and provide the reason.
		driver.findElement(By.xpath("//div[contains(text(),'Delete Patient')]")).click();
		// reason
		driver.findElement(By.xpath("//input[@id='delete-reason']")).sendKeys(deleteReason);

		// 28. Click on confirm button and verify the toaster message.
		driver.findElement(By.xpath("//div[@id='delete-patient-creation-dialog']//button[@class='confirm right']"))
		.click();

		// Wait for the toaster message to appear
		WebElement toasterElement = driver
				.findElement(By.xpath("//*[contains(text(), 'Patient has been deleted successfully')]"));

		wait.until(ExpectedConditions.visibilityOf(toasterElement));

		// Retrieve the text of the toaster message
		String actualToasterText = toasterElement.getText();
		log.info(" Actual ToasterText: " + actualToasterText);
		String expectedToasterText = "Patient has been deleted successfully";
		log.info(" Expected Toaster:" + expectedToasterText);
		log.info(" Verifeid Toaster Message and Displayed:" + actualToasterText);

		// Verify the text of the toaster message
		Assert.assertEquals(actualToasterText, expectedToasterText, "Toaster message not displayed");

		// 29. It will redirect you to Find Patient Record menu where verify the deleted

		// Enter the deleted patient's ID in the search input
		WebElement searchInput = driver.findElement(By.xpath("//input[@id='patient-search']"));
		searchInput.clear(); // Clear any existing text in the search input
		searchInput.sendKeys(patientID); // Use the patient ID or other relevant search criteria
		Thread.sleep(5000);
		// Wait for the search results to be updated
		WebElement locateTable = driver.findElement(By.xpath("//table[@id='patient-search-results-table']"));

		List<WebElement> rows = locateTable.findElements(By.tagName("tr"));
		List<WebElement> cells = rows.get(1).findElements(By.tagName("td")); // Assuming you want the first row

		// Check if there is at least one cell
		if (!cells.isEmpty()) {
			String result = cells.get(0).getText();
			log.info(" Search Result" + result);
			log.info("Verified that the deleted patient is not listed in the Find Patient Record menu.");

			// Assertion
			Assert.assertEquals(result, "No matching records found",
					"Deleted patient name is listed in the Find Patient Record menu.");
		}
	}

}