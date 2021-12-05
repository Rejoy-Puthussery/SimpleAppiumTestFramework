package pageObjects;
/*
    Page object file to declare and define login page elements
    To create a page object create a class with TestSetupTearDown instance variable
    and a constructor to instantiate the variable and PageFactory init.
*/

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utilities.TestSetupTearDown;
import utilities.Tools;

public class LoginPage {

    protected TestSetupTearDown testSetup;

    public LoginPage(TestSetupTearDown testSetup){
        this.testSetup = testSetup;
        // Using the pagefactory class to initialize the page elements
        PageFactory.initElements(new AppiumFieldDecorator(testSetup.driver), this);
    }

    //Use the find by annotations to locate the elements
    
    @AndroidFindBy(accessibility = "test-Username")
    private AndroidElement txt_userName;

    @AndroidFindBy(accessibility = "test-Password")
    private AndroidElement txt_password;

    @AndroidFindBy(accessibility = "test-LOGIN")
    private AndroidElement btn_login;

    // Method to perform login action
    public ProductPage login(String userName, String password){
        Tools.setText(testSetup, txt_userName, userName, 5);
        Tools.setText(testSetup, txt_password, password, 5);
        Tools.clickElement(testSetup, btn_login, 5);
        return new ProductPage(testSetup);
    }
    
}
