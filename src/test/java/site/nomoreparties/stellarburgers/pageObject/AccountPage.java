package site.nomoreparties.stellarburgers.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage {
    private final WebDriver driver;
    public static final String ACCOUNT_PAGE_URL = MainPage.PAGE_URL + "/account";
    private WebDriverWait wait;


    private static final By LOGOUT_BUTTON = By.xpath("//button[contains(text(), 'Выход')]");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);

    }

    @Step("Действие - Выход из аккаунта")
    public void clickLogoutButton() {
        WebElement logOutButton = wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON));
        logOutButton.click();
    }

    @Step("Проверка - Открыта ли страница личного кабинета")
    public boolean isAccountPageOpen() {
        return wait.until(ExpectedConditions.urlContains(ACCOUNT_PAGE_URL));
    }
}