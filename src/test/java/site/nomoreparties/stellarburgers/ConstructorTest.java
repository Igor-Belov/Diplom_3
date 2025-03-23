package site.nomoreparties.stellarburgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import site.nomoreparties.stellarburgers.pageObject.*;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@DisplayName("Тесты для конструктора бургеров")
@RunWith(Parameterized.class)
public class ConstructorTest {
    private WebDriver driver;
    private MainPage mainPage;

    private String browserName;

    public ConstructorTest(String browserName) {
        this.browserName = browserName;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{
                {"yandex"},
                {"chrome"}
        });
    }

    @Before
    public void setUp() {
        // Настройка драйвера в зависимости от выбранного браузера
        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equals("yandex")) {
            WebDriverManager.chromedriver().driverVersion("132.0.6834.0").setup();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:/Users/belov/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
            driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.get(MainPage.MAIN_PAGE_URL);
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            }
    }

    @DisplayName("Тест - раздел 'Булки' открыт по умолчанию")
    @Test
    public void testDefaultActiveBunsSection() {
        assertTrue(mainPage.isBunsSectionActive());
    }

    @DisplayName("Тест - Переход к разделу 'Булки' (Из другого раздела)")
    @Test
    public void testNavigateToBunsSection() {
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();
        assertTrue(mainPage.isBunsSectionActive());
    }

    @DisplayName("Тест - Переход к разделу 'Соусы'")
    @Test
    public void testNavigateToSaucesSection() {
        mainPage.clickSaucesTab();
        assertTrue(mainPage.isSaucesSectionActive());
    }

    @DisplayName("Тест - Переход к разделу 'Начинки'")
    @Test
    public void testNavigateToFillingsSection() {
        mainPage.clickFillingsTab();
        assertTrue(mainPage.isFillingsSectionActive());
    }
}