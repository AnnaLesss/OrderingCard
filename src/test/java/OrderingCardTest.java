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
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldTestOrderingCard() {
        WebElement form = driver.findElement(By.cssSelector("[method=post]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Олег");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[class=button__text]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void invalidName() {
        WebElement form = driver.findElement(By.cssSelector("[method=post]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Oleg");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[class=button__text]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void invalidPhone() {
        WebElement form = driver.findElement(By.cssSelector("[method=post]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Олег");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("99999999999");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[class=button__text]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void noCheckBox() {
        WebElement form = driver.findElement(By.cssSelector("[method=post]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Олег");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79012345678");
        form.findElement(By.cssSelector("[class=button__text]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
    @Test
    void noNameNoPhone() {
        WebElement form = driver.findElement(By.cssSelector("[method=post]"));
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[class=button__text]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
}

