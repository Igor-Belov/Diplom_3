package site.nomoreparties.stellarburgers.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private final WebDriver driver;
    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String MAIN_PAGE_URL = PAGE_URL;
    // Константы для локаторов
    private static final By BUNS_TAB = By.xpath("//span[text()='Булки']");
    private static final By SAUCES_TAB = By.xpath("//span[text()='Соусы']");
    private static final By FILLINGS_TAB = By.xpath("//span[text()='Начинки']");
    public static final By ORDER_BUTTON = By.xpath("//button[contains(text(), 'Оформить заказ')]");
    private static final By ENTER_IN_ACCOUNT = By.xpath("//button[contains(text(), 'Войти в аккаунт')]");

    private static final By ACTIVE_BUNS_TAB = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Булки']");
    private static final By ACTIVE_SAUCES_TAB = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Соусы']");
    private static final By ACTIVE_FILLINGS_TAB = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Начинки']");

    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }

    @Step("Действие - Метод для проверки, есть ли кнопка заказа, доступная только зарегистрированным пользователям")
    public WebElement isMainPageForAuthUser() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ORDER_BUTTON));
    }

    @Step("Действие - Метод для проверки, есть ли кнопка заказа, доступная только зарегистрированным пользователям")
    public WebElement isMainPageForNoAuthUser() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ENTER_IN_ACCOUNT));
    }

    @Step("Действие - нажать на закладку 'Булки'")
    public void clickBunsTab() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(BUNS_TAB));
        loginButton.click();
    }

    @Step("Действие - нажать на закладку 'Соусы'")
    public void clickSaucesTab() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(SAUCES_TAB));
        loginButton.click();
    }

    @Step("Действие - нажать на закладку 'Начинки'")
    public void clickFillingsTab() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(FILLINGS_TAB));
        loginButton.click();
    }

    @Step("Проверка - убедиться, что раздел 'Булки' активен")
    public boolean isBunsSectionActive() {
        return driver.findElement(ACTIVE_BUNS_TAB).isDisplayed();
    }

    @Step("Проверка - убедиться, что раздел 'Соусы' активен")
    public boolean isSaucesSectionActive() {
        return driver.findElement(ACTIVE_SAUCES_TAB).isDisplayed();
    }

    @Step("Проверка - убедиться, что раздел 'Начинки' активен")
    public boolean isFillingsSectionActive() {
        return driver.findElement(ACTIVE_FILLINGS_TAB).isDisplayed();
    }

    @Step("Действие - нажать на кнопку 'Войти в аккаунт' на главной странице")
    public void clickEnterInAccountButton() {
        driver.findElement(ENTER_IN_ACCOUNT).click();
    }

    @Step("Действие - убедится, что есть элемент 'Низ булочки'")
    public boolean isMainPageOpen() {
        System.out.println("Текущий URL перед ожиданием: " + driver.getCurrentUrl());
        return wait.until(ExpectedConditions.urlToBe(MainPage.MAIN_PAGE_URL + "/"));
    }
}