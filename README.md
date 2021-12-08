# SimpleAppiumTestFramework
A simple test framework to perform automation testing on Andoird Apps.
This is a simple framework which allows you to get started with android app testing using Appium.
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
 - src/test/resources/testSuites - Folder where the test suite xml should be placed. The xml should have the following parameters set. Refer the demo test xml available in the folder.
	- <parameter name="platform_name" value="ANDROID" />
	- <parameter name="platform_version" value="10" /> -- platform version
	- <parameter name="device_name" value="Pixel_4" /> -- Name of the emulator
	- <parameter name="new_command_timeout" value="120" /> -- Timeout waiting for new command
	- <parameter name="appToTest"	value="<Absolute path to the folder>/src/test/resources/apps/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk" />
	- <parameter name="appPackage" value="com.swaglabsmobileapp" />
	- <parameter name="appActivity" value="com.swaglabsmobileapp.MainActivity" />
	- <parameter name="avd" value="Pixel_4" />
     	- <parameter name="consolePort" value="5556" />
     	- <parameter name="appiumServerURL" value="http://127.0.0.1:4723/wd/hub" /> -- Appium server URL
