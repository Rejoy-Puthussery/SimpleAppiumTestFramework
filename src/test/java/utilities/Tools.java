package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Tools {
	
	public static MobileElement waitForElementVisiblity(AppiumDriver<?> driver, MobileElement element, int waitTimeInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitTimeInSeconds);
		try {
			element = (MobileElement) wait.until(ExpectedConditions.visibilityOf(element));
		}catch (Exception e) {
			throw e;
		}
		return element;
	}
	
	public static boolean clickElement(TestSetupTearDown testSetup, MobileElement element, int waitTimeInSeconds) {
		element =  waitForElementVisiblity(testSetup.driver, element, waitTimeInSeconds);
		return checkNdClickElement(element, testSetup.reporter);
	}
	

	public static boolean setText(TestSetupTearDown testSetup, MobileElement element, String text, int waitTimeInSeconds) {
		element = waitForElementVisiblity(testSetup.driver, element, waitTimeInSeconds);
		return checkNdSetElementText(element, text, testSetup.reporter);
	}

	public static String getElementText(TestSetupTearDown testSetup, MobileElement element, int waitTimeInSeconds) {
		element = waitForElementVisiblity(testSetup.driver, element, waitTimeInSeconds);
		return element.getText();
	}
	
	private static boolean checkNdClickElement(MobileElement element, TestReporter reporter) {
		if(element != null) {
			element.click();
			reporter.logInfo("Clicked on " + element.toString());
			return true;
		}else {
			return false;
		}
	}
	
	private static boolean checkNdSetElementText(MobileElement element, String text, TestReporter reporter) {
		if(element != null) {
			element.setValue(text);
			reporter.logInfo("Typed (" + text + ") in " + element.toString());
			return true;
		}else {
			return false;
		}
	}

	public static String captureScreenshot(AppiumDriver<?> driver, String fileName){
		File screenShotFile = driver.getScreenshotAs(OutputType.FILE);
		File targetFile = new File(fileName+".png");
		try {
			FileUtils.copyFile(screenShotFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return targetFile.getAbsolutePath();
	}

	public static String getProperty(String key){
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("src/test/resources/config.properties"));
			return prop.getProperty(key);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return "";
		}
	}

	public static void createFolder(String path){
		File dir = new File(path);
		if (!dir.exists()) dir.mkdirs();
	}
	
}
