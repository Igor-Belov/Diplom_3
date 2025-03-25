package site.nomoreparties.stellarburgers.pageObject;

import io.qameta.allure.Step;
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
    private static final By LOGIN = By.cssSelector("a.Auth_link__1fOlj");


    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }
    @Step("Действие - Ввод имени пользователя в соответствующее поле")
    public void enterName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(NAME_INPUT));
        nameInput.clear();
        nameInput.sendKeys(name);
    }
    @Step("Действие - Ввод email пользователя в соответствующее поле")
    public void enterEmail(String email) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT));
        emailInput.clear();
        emailInput.sendKeys(email);
    }
    @Step("Действие - Ввод пароля пользователя в соответствующее поле")
    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    @Step("Действие - нажали кнопку 'Регистрация'")
    public void clickRegisterButton() {
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(REGISTER_BUTTON));
        registerButton.click();
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.urlToBe(LogInPage.LOGIN_PAGE_URL),
                        ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE)
                )
        );
    }

    @Step("Действие - нажали кнопку 'Войти'")
    public void clickLogInButton() {
        WebElement LogInButton = wait.until(ExpectedConditions.elementToBeClickable(LOGIN));
        LogInButton.click();
    }

    @Step("Действие - вернули сообщение об ошибке")
    public String getErrorMessage() {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        return errorMessage.getText();
    }
}