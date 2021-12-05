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

public class ProductPage {

    protected TestSetupTearDown testSetup;

    public ProductPage(TestSetupTearDown testSetup){
        this.testSetup = testSetup;
        // Using the pagefactory class to initialize the page elements
        PageFactory.initElements(new AppiumFieldDecorator(testSetup.driver), this);
    }

    //Use the find by annotations to locate the elements

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PRODUCTS\")")
    private AndroidElement txt_productTitle;

    public String getProductPageTitle() {
        return Tools.getElementText(testSetup, txt_productTitle, 5);
    }
}
