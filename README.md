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
 - src/test/java/pageObjects - The folder where the pageobject files should be placed
 - src/test/java/tests - The folder where the test files should be placed
 - src/test/java/utilities - Framework related files
