package site.nomoreparties.stellarburgers.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class Header {
    private WebDriver driver;

    // Локаторы элементов шапки
    private static final By CONSTRACTOR = By.cssSelector("a.AppHeader_header__link__3D_hX.AppHeader_header__link_active__1IkJo");//просто показать что можно и через css
    private static final By ORDER_FEED = By.xpath("//a[@href='/feed' and @class = 'AppHeader_header__link__3D_hX AppHeader_header__link_active__1IkJo']");// и через xpath
    private static final By PERSONAL_ACCOUNT = By.xpath("//a[@href='/account' and @class = 'AppHeader_header__link__3D_hX']");
    private static final By LOGO = By.xpath(" //div[AppHeader_header__logo__2D0X2]/a[@href='/account' and @class = 'AppHeader_header__link__3D_hX']");

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Действие - нажатие на 'Конструктор'")
    public void clickCONSTRACTOR() {
        driver.findElement(CONSTRACTOR).click();
    }

    @Step("Действие - нажатие на 'Список заказов'")
    public void clickORDER_FEED() {
        driver.findElement(ORDER_FEED).click();
    }

    @Step("Действие - нажатие на 'Вход в личный кабинет'")
    public void clickPERSONAL_ACCOUNT() {
        driver.findElement(PERSONAL_ACCOUNT).click();
    }

    @Step("Действие - нажатие на логотип")
    public void clickLOGO() {
        driver.findElement(LOGO).click();
    }
}