import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrderingCardTest {
    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        driver = new ChromeDriver(options);
    }
    @AfterEach
    void teardown() {
        driver.quit();
    }

        @Test
        void shouldTestOrderingCard () throws InterruptedException {
            driver.get("http://localhost:9999/");
            WebElement form = driver.findElement(By.cssSelector("[method=post]"));
            form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Олег");
            form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
            form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
            form.findElement(By.cssSelector("[class=button__text]")).click();
            String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
            assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
//            Thread.sleep(5000);
        }

        @Test
        void InvalidName () throws InterruptedException {
            driver.get("http://localhost:9999/");
            WebElement form = driver.findElement(By.cssSelector("[method=post]"));
            form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Oleg");
            form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
            form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
            form.findElement(By.cssSelector("[class=button__text]")).click();
            String text = driver.findElement(By.className("input__sub")).getText();
            assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
//            Thread.sleep(5000);
        }
    }

