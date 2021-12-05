/*
 * Author: Rejoy D Puthussery
 * Date: 09/15/2017
 * Purpose: Class used to generate test reports. The class utilizes the extent report jar
 * 			version - 2.41.2
 */
package utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestReporter {
    private ExtentReports testReporter;
    private ExtentTest testMethodReporter;
    private Map<Long, ExtentTest> extentMethodTestMap = new HashMap<>();

    public TestReporter(ExtentReports testReporter){
        this.testReporter = testReporter;
    }

    public static synchronized ExtentReports createSparkReporter(String reportLocation) throws IOException {
        String htmlReport = reportLocation+File.separator;
        ExtentSparkReporter testSparkReporter = new ExtentSparkReporter(htmlReport);
        testSparkReporter.loadXMLConfig(new File(Tools.getProperty("test_report_config")));
        ExtentReports testReporter = new ExtentReports();
        testReporter.attachReporter(testSparkReporter);
        testReporter.setAnalysisStrategy(AnalysisStrategy.TEST);
        return testReporter;
    }

    /*
     * Method to start the method level test report object.
     * Accepts name of the test method name and test description in String format
     * Method does not return any value
     */
    public synchronized void startTestMethodReporter(String testMethodName, String testDescription, String[] groupList){
        this.testMethodReporter = this.testReporter.createTest(testMethodName, testDescription);
        if(groupList.length > 0) this.testMethodReporter.assignCategory(groupList) ;
        extentMethodTestMap.put(Thread.currentThread().getId(), this.testMethodReporter);
    }

    /*
     * Method to log fail status in method test report object.
     * Accepts description and screenshot location in String format.
     * Method does not return any value
     */
    public synchronized void logFail(String description) {
        extentMethodTestMap.get(Thread.currentThread().getId()).fail(description);
    }

    public synchronized void logFail(String description, String screenshotPath) {
        extentMethodTestMap.get(Thread.currentThread().getId()).fail(description).addScreenCaptureFromPath(screenshotPath);
    }

    public synchronized void logFail(Throwable description, String screenshotPath) {
        extentMethodTestMap.get(Thread.currentThread().getId()).fail(description).addScreenCaptureFromPath(screenshotPath);
    }

    /*
     * Method to log pass status in method test report object.
     * Accepts description in String format.
     * Method does not return any value
     */
    public synchronized void logPass(String description) {
        extentMethodTestMap.get(Thread.currentThread().getId()).pass(description);
    }

    /*
     * Method to log info status in method test report object.
     * Accepts description in String format.
     * Method does not return any value
     */
    public synchronized void logInfo(String description) {
        extentMethodTestMap.get(Thread.currentThread().getId()).info(description);
    }

    /*
     * Method to log skip status in method test report object.
     * Accepts description in String format.
     * Method does not return any value
     */
    public synchronized void logSkip(String description) {
        extentMethodTestMap.get(Thread.currentThread().getId()).skip(description);
    }
}
