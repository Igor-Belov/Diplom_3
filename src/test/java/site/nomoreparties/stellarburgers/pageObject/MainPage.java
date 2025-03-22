package site.nomoreparties.stellarburgers.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site";
    private static final String MAIN_PAGE_URL = PAGE_URL;
    // Константы для локаторов
    private static final By BUNS_TAB = By.xpath("//div[contains(text(), 'Булки')]");
    private static final By SAUCES_TAB = By.xpath("//div[contains(text(), 'Соусы')]");
    private static final By FILLINGS_TAB = By.xpath("//div[contains(text(), 'Начинки')]");
    private static final By INGREDIENT_LIST = By.xpath("//ul[@class='BurgerIngredients_ingredients__list__2A-mT']/li");
    private static final By TOP_BUN = By.xpath("//span[contains(text(), '(верх)')]");
    private static final By BOTTOM_BUN = By.xpath("//span[contains(text(), '(низ)')]");
    private static final By ORDER_BUTTON = By.xpath("//button[contains(text(), 'Оформить заказ')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Методы для работы с вкладками ингредиентов
    public void clickBunsTab() {
        driver.findElement(BUNS_TAB).click();
    }

    public void clickSaucesTab() {
        driver.findElement(SAUCES_TAB).click();
    }

    public void clickFillingsTab() {
        driver.findElement(FILLINGS_TAB).click();
    }

    // Метод для получения списка ингредиентов
    public List<WebElement> getIngredients() {
        return driver.findElements(INGREDIENT_LIST);
    }

    // Метод для выбора ингредиента по названию
    public void selectIngredient(String ingredientName) {
        driver.findElement(By.xpath("//p[contains(text(), '" + ingredientName + "')]")).click();
    }

    // Методы для работы с конструктором бургера
    public boolean isTopBunDisplayed() {
        return driver.findElement(TOP_BUN).isDisplayed();
    }

    public boolean isBottomBunDisplayed() {
        return driver.findElement(BOTTOM_BUN).isDisplayed();
    }

    // Метод для оформления заказа
    public void clickOrderButton() {
        driver.findElement(ORDER_BUTTON).click();
    }
}