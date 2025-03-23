
//Проверь переход по клику на «Личный кабинет».

//Проверь переход по клику на «Конструктор»
// и на логотип Stellar Burgers.

//Проверь выход по кнопке «Выйти» в личном кабинете.

package site.nomoreparties.stellarburgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import site.nomoreparties.stellarburgers.pageObject.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты для личного кабинета")
@RunWith(Parameterized.class)
public class AccountPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private Header header;
    private AccountPage accountPage;
    private LogInPage loginPage;
    private String email;
    private String password;
    private String name;

    private String browserName;

    public AccountPageTest(String browserName) {
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
        mainPage = new MainPage(driver);
        header = new Header(driver);
        accountPage = new AccountPage(driver);
        loginPage = new LogInPage(driver);

        // Создание пользователя для тестов
        email = LocalDateTime.now() + "@example.com";
        password = "P@ssword123";
        name = "Test User";
        StaffApi.createUser(email, password, name);
        Map<String, String> tokens = StaffApi.getTokensAndLogin(email, password);
        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");
        driver.get(MainPage.MAIN_PAGE_URL);
        if (accessToken != null) {
            // Сохранение токенов в localStorage
            String scriptAccessToken = String.format("window.localStorage.setItem('accessToken', '%s');", accessToken);
            ((JavascriptExecutor) driver).executeScript(scriptAccessToken);
            String scriptRefreshToken = String.format("window.localStorage.setItem('refreshToken', '%s');", refreshToken);
            ((JavascriptExecutor) driver).executeScript(scriptRefreshToken);
            driver.navigate().refresh();
        } else {
            System.out.println("Access token is null");

        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            String accessToken = StaffApi.getAccessToken(email, password);
            if (accessToken != null) {
                StaffApi.deleteUser(accessToken.replace("Bearer ", ""));
            }
        }
    }

    @DisplayName("Тест - Переход в личный кабинет по клику на 'Личный кабинет'")
    @Test
    public void testNavigateToPersonalAccount() {
        header.clickPERSONAL_ACCOUNT();
        assertTrue(accountPage.isAccountPageOpen());
    }

    @DisplayName("Тест - Переход из личного кабинета в конструктор по клику на 'Конструктор'")
    @Test
    public void testNavigateFromAccountToConstructor() {
        header.clickPERSONAL_ACCOUNT();
        header.clickCONSTRACTOR();
        assertTrue(mainPage.isMainPageOpen());
    }

    @DisplayName("Тест - Переход из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    @Test
    public void testNavigateFromAccountToConstructorViaLogo() {
        header.clickPERSONAL_ACCOUNT();
        header.clickLOGO();
        assertTrue(mainPage.isMainPageOpen());
    }

    @DisplayName("Тест - Выход из аккаунта по кнопке 'Выйти' в личном кабинете")
    @Test
    public void testLogoutFromAccount() {
        header.clickPERSONAL_ACCOUNT();
        accountPage.clickLogoutButton();
        assertTrue(loginPage.isLoginPageOpen());
        header.clickCONSTRACTOR();
        assertNotNull(mainPage.isMainPageForNoAuthUser());
    }
}