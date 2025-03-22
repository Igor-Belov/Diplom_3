//Проверь:
//Успешную регистрацию.
//Ошибку для некорректного пароля. Минимальный пароль — шесть символов.

package site.nomoreparties.stellarburgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import site.nomoreparties.stellarburgers.pageObject.RegistrationPage;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class SignUpTest {
    private WebDriver driver;
    private RegistrationPage registrationPage;
    private String password;
    private String email;

    private String browserName;

    // Конструктор для параметризованных тестов
    public SignUpTest(String browserName) {
        this.browserName = browserName;
    }

    // Параметры для тестов: Google Chrome и Яндекс.Браузер
    @Parameterized.Parameters
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{
                {"yandex"}
                // {"chrome"}
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
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            String AccessToken = StaffApi.getAccessToken(email, password);
            if (AccessToken != null) {
                StaffApi.deleteUser(AccessToken);
            }
        }

    }

    @Test
    public void testSuccessfulRegistrationOk() {
        registrationPage.enterName("Test User");
        email = LocalDateTime.now() + "@test.ui";
        registrationPage.enterEmail(email);
        password = "P@ssword123";
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();
        assertTrue(driver.getCurrentUrl().contains("https://stellarburgers.nomoreparties.site/login"));
    }

    @Test
    public void testTooShortPasswordError() {
        registrationPage.enterName("Test User");
        email = LocalDateTime.now() + "@example.com";
        registrationPage.enterEmail(email);
        password = "Aa345";
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();
        String errorMessage = registrationPage.getErrorMessage();
        assertEquals("Некорректный пароль", errorMessage);
    }

    @Test
    public void testMinShortPasswordOk() {
        registrationPage.enterName("Test User");
        email = LocalDateTime.now() + "@example.com";
        registrationPage.enterEmail(email);
        password = "Aa3456";
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButton();
        assertTrue(driver.getCurrentUrl().contains("https://stellarburgers.nomoreparties.site/login"));
    }
}