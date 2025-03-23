//Проверь:
//Успешную регистрацию.
//Ошибку для некорректного пароля. Минимальный пароль — шесть символов.

package site.nomoreparties.stellarburgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import site.nomoreparties.stellarburgers.pageObject.LogInPage;
import site.nomoreparties.stellarburgers.pageObject.RegistrationPage;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты, что пользователь может зарегистрироваться")
@RunWith(Parameterized.class)
public class SignUpTest {
    private static final String VALID_PASSWORD = "P@ssword123";
    private static final String SHORT_PASSWORD = "Aa345";
    private static final String MINIMAL_PASSWORD = "Aa3456";

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private String password;
    private String email;
    private LogInPage loginPage;

    private String browserName;

    public SignUpTest(String browserName) {
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
        driver.get(RegistrationPage.REGISTRATION_PAGE_URL);
        registrationPage = new RegistrationPage(driver);
        loginPage = new LogInPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            String accessToken = (StaffApi.getAccessToken(email, password));
            if (accessToken != null) {
                StaffApi.deleteUser(accessToken.replace("Bearer ", ""));
            }
        }
    }

    @Step("Создали уникальный email")
    private void CreateEmail() {
        email = LocalDateTime.now() + "@test.ui";
    }

    @DisplayName("Тест - все поля заполнены верно. Регистрация успешна")
    @Test
    public void testSuccessfulRegistrationOk() {
        registrationPage.enterName("Test User");
        CreateEmail();
        registrationPage.enterEmail(email);
        password = VALID_PASSWORD;
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();
        assertTrue(loginPage.isLoginPageOpen());
    }

    @DisplayName("Тест - все поля заполнены, пароль короче допустимого на 1 символ. Ошибка регистрации")
    @Test
    public void testTooShortPasswordError() {
        registrationPage.enterName("Test User");
        CreateEmail();
        registrationPage.enterEmail(email);
        password = SHORT_PASSWORD;
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();
        String errorMessage = registrationPage.getErrorMessage();
        assertEquals("Некорректный пароль", errorMessage);
    }

    @DisplayName("Тест - все поля заполнены, пароль минимально допустимой длинны. Регистрация успешна")
    @Test
    public void testMinShortPasswordOk() {
        registrationPage.enterName("Test User");
        CreateEmail();
        registrationPage.enterEmail(email);
        password = MINIMAL_PASSWORD;
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();
        assertTrue(loginPage.isLoginPageOpen());
    }
}