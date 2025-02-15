package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageBase;

import java.time.Duration;

public class LoginPage extends PageBase{
    private WebDriver driver;
    private WebDriverWait wait;

    private By emailInput = By.xpath("//*[@id='ap_email' or @id='ap_email_login']");
    private By continueBtn	 =	By.id("continue");
    private By passwordTXT	 =	By.id("ap_password");
    private By submitBtn	 =	By.id("signInSubmit");
    private By accountIdentifier = By.id("nav-link-accountList-nav-line-1");
	
	public LoginPage(WebDriver driver) {
		super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

	public void myAccountLoginClick()
	{
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys("01200802273");
		click(continueBtn);


        // Enter password
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordTXT)).sendKeys("Marina@12");
        driver.findElement(submitBtn).click();

        // Validate if sign-in was successful
        try {
            WebElement accountName = wait.until(ExpectedConditions.visibilityOfElementLocated(accountIdentifier));
            System.out.println("Successfully signed in as: " + accountName.getText());
        } catch (TimeoutException e) {
            throw new AssertionError("Sign-in failed. Please check credentials.");
        }
	}

}
