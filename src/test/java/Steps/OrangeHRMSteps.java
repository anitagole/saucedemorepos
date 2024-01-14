package Steps;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrangeHRMSteps {

	static WebDriver driver;
	static String empid;

	@Given("user is on login page")
	public void user_is_on_login_page() {
		// 1.connect to the actual browser.
		// 2. up casting
		driver = new ChromeDriver();

		// 3. maximize
		driver.manage().window().maximize();

		// 4. implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// 5. page load time out
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

		// 6. delete all cookies
		driver.manage().deleteAllCookies();
		// 7. open a url
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	}

	@When("user enter valid credentails")
	public void user_enter_valid_credentails() {
		// find username text box and enter values
		driver.findElement(By.name("username")).sendKeys("Admin");

		// find Password text box and enter values
		driver.findElement(By.name("password")).sendKeys("admin123");

		// find Login button and click on it
		driver.findElement(By.xpath("//button[text()=' Login ']")).click();

	}

	@Then("user is on home page and validate home page title")
	public void user_is_on_home_page_and_validate_home_page_title() {
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, "OrangeHRM");
	}

	@Then("user validate Home page URL")
	public void user_validate_home_page_url() {
		boolean actualurl = driver.getCurrentUrl().contains("hrm");
		Assert.assertEquals(actualurl, true);
	}

	@When("user is on pim Page")
	public void user_is_on_pim_page() {
		// find PIM Link and click on it
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
	}

	@Then("validate PIM page url")
	public void validate_pim_page_url() {
		boolean actualurl = driver.getCurrentUrl().contains("pim");
		Assert.assertEquals(actualurl, true);
	}

	@When("user click on add Employee")
	public void user_click_on_add_employee() {
		// find Add Employee button and click on it
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
	}

	@When("user enter valid {string},{string}, {string} and click on save button")
	public void user_enter_valid_and_click_on_save_button(String fname, String mname, String lname)
			throws InterruptedException {

		// find First Name text box and enter values
		driver.findElement(By.name("firstName")).sendKeys(fname);

		// find Middle Name text box and enter values
		driver.findElement(By.name("middleName")).sendKeys(mname);

		// find last name text box and enter values
		driver.findElement(By.name("lastName")).sendKeys(lname);

		Thread.sleep(2000);
		// find Save Employee button and click on it
		driver.findElement(By.xpath("//button[text()=' Save ']")).click();
	}

	@When("user capture the employee id")
	public void user_capture_the_employee_id() throws InterruptedException {
		Thread.sleep(2000);
		// find employee id text box and capture values
		empid = driver.findElement(By.xpath("//label[text()='Employee Id']/following::input[1]")).getAttribute("value");

	}

	@When("user click on add employee list")
	public void user_click_on_add_employee_list() throws InterruptedException {

		// find add employee list button and click on it
		driver.findElement(By.xpath("//a[text()='Employee List']")).click();
		Thread.sleep(2000);
	}

	@When("user enter employee id and click on search button")
	public void user_enter_employee_id_and_click_on_search_button() throws InterruptedException {

		// find Employee text box and enter values
		driver.findElement(By.xpath("//label[text()='Employee Id']/following::input[1]")).sendKeys(empid);

		Thread.sleep(10000);
		// find search button and click on it
		driver.findElement(By.xpath("//button[text()=' Search ']")).click();
	}

	@When("user select searched employee and click on delete")
	public void user_select_searched_employee_and_click_on_delete() throws InterruptedException {

		Thread.sleep(2000);
		// find searched employee checkbox and click on it
		WebElement wb = driver.findElement(By.xpath("//div[text()='Id']/following::input[1]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", wb);

		Thread.sleep(2000);
		// find deleted button and click on it
		driver.findElement(By.xpath("//button[text()=' Delete Selected ']")).click();
	}

	@Then("validate confirm delete window and click on yes deleted")
	public void validate_confirm_delete_window_and_click_on_yes_deleted() {
		// find physical text and capture it
		String actualText = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-text--card-body']"))
				.getText();
		Assert.assertEquals(actualText.contains("delete"), true);
		// find confirm delete and click on it
		driver.findElement(By.xpath("//button[text()=' Yes, Delete ']")).click();
	}

	@When("user click on profile image")
	public void user_click_on_profile_image() throws InterruptedException {
		// find profile link and click on it
		Thread.sleep(2000);
		driver.findElement(By.xpath("//img[@alt='profile picture']/following-sibling::p")).click();
	}

	@When("user click on logout button")
	public void user_click_on_logout_button() {
		// find logout button and click on it
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
	}

	@AfterStep
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

			String date = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

			scenario.attach(f, "image/png", date + scenario.getName());

		} else {
			byte[] f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

			String date = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

			scenario.attach(f, "image/png", date + scenario.getName());
		}

	}

}
