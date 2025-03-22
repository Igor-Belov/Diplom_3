package site.nomoreparties.stellarburgers.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public static final String REGISTRATION_PAGE_URL = MainPage.PAGE_URL + "/register";

    private static final By NAME_INPUT = By.cssSelector("input[name='name']");
    private static final By EMAIL_INPUT = By.xpath("//label[contains(text(), 'Email')]/following-sibling::input");
    private static final By PASSWORD_INPUT = By.cssSelector("input[name='Пароль']");
    private static final By REGISTER_BUTTON = By.cssSelector("button.button_button__33qZ0");
    private static final By ERROR_MESSAGE = By.cssSelector("p.input__error");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }

    public void enterName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(NAME_INPUT));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterEmail(String email) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickRegisterButton() {
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(REGISTER_BUTTON));
        registerButton.click();
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/login"),
                        ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE)
                )
        );
    }

    public String getErrorMessage() {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        return errorMessage.getText();
    }
}