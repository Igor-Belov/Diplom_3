package site.nomoreparties.stellarburgers.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogInPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public static final String LOGIN_PAGE_URL = MainPage.PAGE_URL + "/login";

    // Локаторы элементов страницы
    private static final By EMAIL_INPUT = By.xpath("//label[contains(text(), 'Email')]/following-sibling::input");
    private static final By PASSWORD_INPUT = By.cssSelector("input[name='Пароль']");
    private static final By LOGIN_BUTTON = By.cssSelector("button.button_button__33qZ0");
    private static final By ERROR_MESSAGE = By.cssSelector("p.input__error");

    public LogInPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
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

    @Step("Действие - Нажатие кнопки 'Войти'")
    public void clickLoginButton() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        loginButton.click();
    }

    @Step("Проверка - Открыта ли страница авторизации")
    public boolean isLoginPageOpen() {
        return wait.until(ExpectedConditions.urlToBe(LOGIN_PAGE_URL));
    }

    @Step("Действие - Получение сообщения об ошибке")
    public String getErrorMessage() {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        return errorMessage.getText();
    }
}