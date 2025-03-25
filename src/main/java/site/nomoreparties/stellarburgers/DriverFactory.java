package site.nomoreparties.stellarburgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver createDriver(String browserName) {
        final String YANDEX_PATH = System.getenv("YANDEX_BROWSER_PATH");
        WebDriver driver;

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "yandex":
                WebDriverManager.chromedriver().driverVersion("132.0.6834.0").setup();
                ChromeOptions options = new ChromeOptions();
                options.setBinary(YANDEX_PATH);
                driver = new ChromeDriver(options);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        return driver;
    }
}