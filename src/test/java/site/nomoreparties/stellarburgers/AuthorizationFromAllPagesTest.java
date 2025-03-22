//Вход
//Проверь:
//вход по кнопке «Войти в аккаунт» на главной,
//вход через кнопку «Личный кабинет»,
//вход через кнопку в форме регистрации,
//вход через кнопку в форме восстановления пароля.

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
import site.nomoreparties.stellarburgers.pageObject.Header;
import site.nomoreparties.stellarburgers.pageObject.LogInPage;
import site.nomoreparties.stellarburgers.pageObject.MainPage;
import site.nomoreparties.stellarburgers.pageObject.RegistrationPage;
import site.nomoreparties.stellarburgers.pageObject.PasswordRecoveryPage;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты, авторизация возможна через все точки входа в авторизацию")
@RunWith(Parameterized.class)
public class AuthorizationFromAllPagesTest {
    private String email;
    private String password = "P@ssword123";
    private String name = "Тестовый юзер";
    private String accessToken;

    private WebDriver driver;
    private MainPage mainPage;
    private LogInPage loginPage;
    private Header header;
    private RegistrationPage registrationPage;
    private PasswordRecoveryPage passwordRecoveryPage;

    private String browserName;

    public AuthorizationFromAllPagesTest(String browserName) {
        this.browserName = browserName;
    }

    // Параметры для тестов: Google Chrome и Яндекс.Браузер
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
        loginPage = new LogInPage(driver);
        registrationPage = new RegistrationPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);

        email = LocalDateTime.now() + "@example.com";
        StaffApi.createUser(email, password, name);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        accessToken = StaffApi.getAccessToken(email, password);
        if (accessToken != null) {
            StaffApi.deleteUser(accessToken.replace("Bearer ", ""));
        }
    }

    @DisplayName("Тест - вход по кнопке «Войти в аккаунт» на главной")
    @Test
    public void testLoginFromMainPage() {
        driver.get(MainPage.MAIN_PAGE_URL);
        mainPage.clickEnterInAccountButton();
        assertTrue(loginPage.isLoginPageOpen());
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        assertNotNull(mainPage.isMainPageForAuthUser());

    }

    @DisplayName("Тест - вход через кнопку «Личный кабинет»")
    @Test
    public void testLoginFromPersonalAccount() {
        driver.get(MainPage.MAIN_PAGE_URL);
        header.clickPERSONAL_ACCOUNT();
        assertTrue(loginPage.isLoginPageOpen());
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        assertNotNull(mainPage.isMainPageForAuthUser());
    }

    @DisplayName("Тест - вход через кнопку в форме регистрации")
    @Test
    public void testLoginFromRegistrationPage() {
        driver.get(RegistrationPage.REGISTRATION_PAGE_URL);
        registrationPage.clickLogInButton();
        assertTrue(loginPage.isLoginPageOpen());
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        assertNotNull(mainPage.isMainPageForAuthUser());
    }

    @DisplayName("Тест - вход через кнопку в форме восстановления пароля")
    @Test
    public void testLoginFromPasswordRecoveryPage() {
        driver.get(PasswordRecoveryPage.PASSWORD_RECOVERY_PAGE_URL);
        passwordRecoveryPage.clickLoginButton();
        assertTrue(loginPage.isLoginPageOpen());
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        assertNotNull(mainPage.isMainPageForAuthUser());
    }
}
