package site.nomoreparties.stellarburgers.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PasswordRecoveryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public static final String PASSWORD_RECOVERY_PAGE_URL = MainPage.PAGE_URL + "/forgot-password";

    // Локаторы элементов страницы восстановления пароля

    private static final By LOGIN = By.cssSelector("a.Auth_link__1fOlj");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }

    @Step("Действие - Нажатие ссылки 'Войти'")
    public void clickLoginButton() {
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(LOGIN));
        loginLink.click();
    }
}