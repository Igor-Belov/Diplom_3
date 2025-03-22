package site.nomoreparties.stellarburgers.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {
    private final WebDriver driver;
    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String MAIN_PAGE_URL = PAGE_URL;
    // Константы для локаторов
    private static final By BUNS_TAB = By.xpath("//div[contains(text(), 'Булки')]");
    private static final By SAUCES_TAB = By.xpath("//div[contains(text(), 'Соусы')]");
    private static final By FILLINGS_TAB = By.xpath("//div[contains(text(), 'Начинки')]");
    private static final By INGREDIENT_LIST = By.xpath("//ul[@class='BurgerIngredients_ingredients__list__2A-mT']/li");
    private static final By TOP_BUN = By.xpath("//span[contains(text(), '(верх)')]");
    private static final By BOTTOM_BUN = By.xpath("//span[contains(text(), '(низ)')]");
    public static final By ORDER_BUTTON = By.xpath("//button[contains(text(), 'Оформить заказ')]");
    private static final By ENTER_IN_ACCOUNT = By.xpath("//button[contains(text(), 'Войти в аккаунт')]");
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }

    @Step("Действие - Метод для проверки, открыта ли главная страница. true, если текущий URL соответствует главной странице, иначе false.")
    public WebElement isMainPageForAuthUser() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ORDER_BUTTON));
    }

    @Step("Действие - нажать на закладку 'Булки'")
    public void clickBunsTab() {
        driver.findElement(BUNS_TAB).click();
    }
    @Step("Действие - нажать на закладку 'Соусы'")
    public void clickSaucesTab() {
        driver.findElement(SAUCES_TAB).click();
    }

    @Step("Действие - нажать на закладку 'Начинки'")
    public void clickFillingsTab() {
        driver.findElement(FILLINGS_TAB).click();
    }

    @Step("Действие - получить список ингридиентов в виде объекта driver")
    public List<WebElement> getIngredients() {
        return driver.findElements(INGREDIENT_LIST);
    }

    @Step("Действие - выбрать ингридиент по названию")
    public void selectIngredient(String ingredientName) {
        driver.findElement(By.xpath("//p[contains(text(), '" + ingredientName + "')]")).click();
    }

    @Step("Действие - убедится, что есть элемент 'Верх булочки'")
    public boolean isTopBunDisplayed() {
        return driver.findElement(TOP_BUN).isDisplayed();
    }

    @Step("Действие - убедится, что есть элемент 'Низ булочки'")
    public boolean isBottomBunDisplayed() {
        return driver.findElement(BOTTOM_BUN).isDisplayed();
    }
    @Step("Действие - нажать на кнопку 'Оформить заказ' на главной странице")
    // Метод для оформления заказа
    public void clickOrderButton() {
        driver.findElement(ORDER_BUTTON).click();
    }

    @Step("Действие - нажать на кнопку 'Войти в аккаунт' на главной странице")
    public void clickEnterInAccountButton() {
        driver.findElement(ENTER_IN_ACCOUNT).click();
    }
}