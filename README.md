# SimpleAppiumTestFramework
A simple test framework to perform automation testing on Andoird Apps.
This is a simple framework which allows you to get started with android app testing using Appium. The test reports are generated using extent reports (https://github.com/extent-framework/extentreports-java)
The framework can act as a base for your test automation framework over which you can make necessary modifications and upgrade.
A demo app testing(https://github.com/saucelabs/sample-app-mobile) is included in the framework to quickly setup and run.

# Pre- Requesites
 - JDK 1.8
 - Appium (1.22.0)
 - Maven (3.8.3)
 - Android Emulator (testing on real device is also possible, slight modification in the code will be required)

# Setting up the framework
Download or clone the code to your local machine. Open the project folder in your favourite IDE and set it as a maven project.
All the required dependencies listed in the pom.xml will be downloaded.

The folder structure of the framework is as follows:
 - src/test/java/pageObjects - The folder where the pageobject files should be placed. Page object file is to declare and define page elements. To create a page object create a class with TestSetupTearDown instance variable and a constructor to instantiate the variable and PageFactory init. Login page pageobject for the demo app will be present in folder for reference.
 - src/test/java/tests - The folder where the test files should be placed. Create class which extends the TestSetupTearDown class in the utilities package to define tests. Use the normal TestNG convention to define the test method.To use data provider, give the data provider method name (dataProvider = "data-provider")and class (dataProviderClass = TestDataProvider.class) in the Test annotation.The data provider method returns a hashmap of the data set in the sqlite db. Sample test methods are available in the tests folder for reference.
 - src/test/java/utilities - Framework related files.
 - src/test/resources/apps - Folder where the test app apk file should be placed to install
 - src/test/resources/testSuites - Folder where the testng suite xml should be placed. The xml should have the following parameters set at suite level. Refer the demo test xml available in the folder.
	- parameter name="platform_name" value="ANDROID
	- parameter name="platform_version" value="10" (platform version)
	- parameter name="device_name" value="Pixel_4" (Name of the emulator)
	- parameter name="new_command_timeout" value="120" (Appium timeout waiting for new command)
	- parameter name="appToTest" value="Absolute path to the folder/src/test/resources/apps/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk"
	- parameter name="appPackage" value="com.swaglabsmobileapp"
	- parameter name="appActivity" value="com.swaglabsmobileapp.MainActivity"
	- parameter name="avd" value="Pixel_4"
	- parameter name="consolePort" value="5556"
	- parameter name="appiumServerURL" value="http://127.0.0.1:4723/wd/hub" (Appium server URL)
 - src/test/resources/config.properties - Propertie file to set framewoke properties.
	- db_url: The URL to the test data sqlite db
	- db_testCaseDataColumns_table_name: The name of the table where the test method and the test data table mapping is done. This table is important for fetching the test data. This table has 3 columns, testMethodName(Name of the testmethod in test class), testDataTableName(table where the test data is stored) and testDataId(rowId of the testDataTableName from which the data should be fetched)
	- test_report_location: Location where the test report should be generated
	- test_report_config: Location where the extent test report config file is located (Can be left as it is)
	- report_name: Name of the test report
 - src/test/resources/testData.db - Sqlite test data db location
 - pom.xml - Maven pom file. The test is run using the maven surefire plugin. Make sure the testng suite xml is provided in the pom build -> plugin section. Refer the exisitng pom xml.

# Running the demo test app
- After setting up the framework in your machine make sure following updates are done in src/test/resources/testSuites/suite.xml.
	- parameter name="device_name" value="Should be set to the emulator name in your device"
	- parameter name="appToTest" value="Absolute path/src/test/resources/apps/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk"
	- parameter name="avd" value="Should be set to the emulator name in your device"
	- parameter name="consolePort" value="Port on which you intent to run the emulator. Can leave default value if the port is available"
	- parameter name="appiumServerURL" value="The appium server url. Can use the existing value if local server with default port is used"
- Make sure appium server is running.
- Make sure adb service is running (adb start-server).
- Once the above updates and checks are done run 'mvn clean test' from the 'pom.xml' location.
- After the test is run the report will be generated in the /testReport location.
