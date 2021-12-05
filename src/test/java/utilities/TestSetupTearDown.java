package utilities;

import com.aventstack.extentreports.ExtentReports;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;


@Listeners(TestEventListener.class)
public class TestSetupTearDown {

	public AppiumDriver<?> driver;
	public TestReporter reporter;
	protected TestAssert testAssert;

	@Parameters({"platform_name", "platform_version", "device_name"})
	@BeforeSuite
	public void testSuiteSetup(ITestContext context, String platform_name, String platform_version, String device_name) throws Exception {
		try {
			System.out.println("Started execution of suite: "+context.getSuite().getName());
			String currentDateTime = new Date().toString();
			String reportLocation = Tools.getProperty("test_report_location")+File.separator+Tools.getProperty("report_name")+"_"+context.getSuite().getName()+"_"+currentDateTime;
			context.setAttribute("reportLocation", reportLocation);
			ExtentReports testReporter = TestReporter.createSparkReporter(reportLocation);
			testReporter.setSystemInfo("Test environment operating system", System.getProperty("os.name"));
			testReporter.setSystemInfo("Test environment operating system arch", System.getProperty("os.arch"));
			testReporter.setSystemInfo("Test environment operating system version", System.getProperty("os.version"));
			testReporter.setSystemInfo("Mobile device name", device_name);
			testReporter.setSystemInfo("Mobile platform name", platform_name);
			testReporter.setSystemInfo("Mobile platform version", platform_version);
			context.setAttribute("testReporter", testReporter);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Parameters({"platform_name", "platform_version", "device_name", "new_command_timeout", "full_reset", "no_reset", "appToTest", "appPackage", "appActivity", "adbPort", "avd", "consolePort", "appiumServerURL"})
	@BeforeClass
	public void testClassSetup(ITestContext context, String platform_name, String platform_version, String device_name, String new_command_timeout, @Optional("false")boolean full_reset  ,
							   @Optional("true")boolean no_reset, String appToTest, String appPackage, String appActivity, @Optional("5037")String adbPort, String avd, @Optional("5554")String consolePort, String appiumServerURL) throws IOException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platform_name);
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platform_version);
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, device_name);
		caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, new_command_timeout);
		caps.setCapability(MobileCapabilityType.FULL_RESET, full_reset);
		caps.setCapability(MobileCapabilityType.NO_RESET, no_reset);
		caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
		caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
		caps.setCapability(MobileCapabilityType.APP, appToTest);
		caps.setCapability(AndroidMobileCapabilityType.ADB_PORT, adbPort);
		caps.setCapability(AndroidMobileCapabilityType.AVD, avd);
		caps.setCapability(AndroidMobileCapabilityType.AVD_ARGS, "-port "+consolePort);
		URL url = new URL(appiumServerURL);
		this.driver = new AndroidDriver<AndroidElement> (url, caps);
		this.reporter = new TestReporter((ExtentReports)context.getAttribute("testReporter"));
		this.testAssert = new TestAssert(this.reporter);
		context.setAttribute("driver", this.driver);
		context.setAttribute("reporter", this.reporter);
	}

	@Parameters({"appPackage"})
	@BeforeMethod
	public void beforeTestMethod(Method testMethod, String appPackage) {
		Test test = testMethod.getAnnotation(Test.class);
		String testDescription = test.description();
		System.out.println(testDescription);
		this.reporter.startTestMethodReporter(testMethod.getName(), testDescription, test.groups());
		driver.activateApp(appPackage);
	}

	@Parameters({"appPackage"})
	@AfterMethod(alwaysRun = true)
	public void afterTestMethod(String appPackage) {
		driver.terminateApp(appPackage);
	}

	@AfterClass(alwaysRun = true)
	public void testClassTearDown(ITestContext context){
		context.removeAttribute("driver");
		context.removeAttribute("reporter");
		driver.quit();
	}

	@Parameters({"consolePort"})
	@AfterSuite(alwaysRun = true)
	public void testSuiteTearDown(ITestContext context, @Optional("5554")String consolePort) throws IOException {
		String cmdstr = "adb -s emulator-"+ consolePort+" emu kill";
        try {
            Runtime.getRuntime().exec(cmdstr);
        } catch (IOException e) {
            e.printStackTrace();
			throw e;
        }
		((ExtentReports)context.getAttribute("testReporter")).flush();
	}
}
