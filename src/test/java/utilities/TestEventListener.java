package utilities;

import io.appium.java_client.AppiumDriver;

import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.File;
import java.util.Date;
import java.util.List;

public class TestEventListener implements ITestListener {
    
    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        try {
            TestReporter reporter = (TestReporter) result.getTestContext().getAttribute("reporter");
            reporter.logPass("Test Passed: " + result.getMethod().getDescription());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        try {
            String reportLocation = (String)result.getTestContext().getAttribute("reportLocation");
            String testMethodName = result.getName();
            String testMethodScreenshotFolder = reportLocation + File.separator + "screenshots" + File.separator + testMethodName;
            Tools.createFolder(testMethodScreenshotFolder);
            String fileName = testMethodScreenshotFolder + File.separator + testMethodName + "_" + new Date();
            AppiumDriver<?> driver = (AppiumDriver<?>) result.getTestContext().getAttribute("driver");
            TestReporter reporter = (TestReporter) result.getTestContext().getAttribute("reporter");
            reporter.logFail(result.getThrowable(), Tools.captureScreenshot(driver, fileName));
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        try {
            TestReporter reporter = (TestReporter) result.getTestContext().getAttribute("reporter");
            reporter.startTestMethodReporter(result.getMethod().getMethodName(), result.getMethod().getDescription(), result.getMethod().getGroups());
            reporter.logSkip("Test Skipped: " + result.getMethod().getDescription());
            List<ITestNGMethod> methodsCausedSkip = result.getSkipCausedBy();
            for (ITestNGMethod testMethod : methodsCausedSkip) {
                reporter.logInfo("Test skip caused by " + testMethod.getMethodName() + " test method");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
