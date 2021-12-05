package tests;

import java.util.HashMap;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import utilities.TestDataProvider;
import utilities.TestSetupTearDown;

/* 
    Create class which extends the TestSetupTearDown class in the utilities package to define tests.
*/

public class SampleTest extends TestSetupTearDown{
/* Use the normal TestNG convention to define the test method 
   To use data provider, give the data provider method name (dataProvider = "data-provider") 
   and class (dataProviderClass = TestDataProvider.class) in the Test annotation.
   The data provider method returns a hashmap of the data set in the sqlite db
*/

    @Test(description = "Sample pass test method", dataProvider = "data-provider", dataProviderClass = TestDataProvider.class, groups = {"Pass Test group"})
    public void loginTest(HashMap<String, String> loginData) {
        String productPageTitle;
        productPageTitle = new LoginPage(this)
                            .login(loginData.get("userName"), loginData.get("password"))
                            .getProductPageTitle();
        testAssert.assertEquals(productPageTitle, loginData.get("productPageTitle"), "Step to verify whether login is successful");
    }

    @Test(description = "Sample fail test method", dataProvider = "data-provider", dataProviderClass = TestDataProvider.class, groups = {"Fail Test group"})
    public void loginTest_Fail(HashMap<String, String> loginData) {
        String productPageTitle;
        productPageTitle = new LoginPage(this)
                            .login(loginData.get("userName"), loginData.get("password"))
                            .getProductPageTitle();
        testAssert.assertEquals(productPageTitle, loginData.get("productPageTitle"), "Step to verify whether login is successful");
    }

    @Test(description = "Sample skip test method", groups = {"Skip Test group"}, dependsOnMethods = {"loginTest_Fail"})
    public void loginTest_Skip() {
        System.out.println("Inside Skipped test");
    }
    
}
