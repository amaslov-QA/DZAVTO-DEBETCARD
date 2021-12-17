import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendSuccessfulForm() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Каролина Торгариан");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112546585");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = String.valueOf(driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim());
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }
    @Test
    void shouldSendSuccessfulFormNameHyphen() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ромманов Сан-Жак");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112546585");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = String.valueOf(driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim());
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }
    @Test
    void shouldSendFormNotName() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112546585");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid [class='input__sub']")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendFormNotPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Каролина Торгариан");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid [class='input__sub']")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendFormNotAll()  {
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector(".input_invalid [class='input__sub']")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendFormOnEnglishName()  {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Dalay Lama");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112546585");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid [class='input__sub']")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendFormWithPartPhone()  {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Каролина Торгариан");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7911254");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid [class='input__sub']")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }
    @Test
    void shouldSendFormWithPhoneOneElement()  {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Каролина Торгариан");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid [class='input__sub']")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }
    @Test
    void shouldSendFormWithPartPhoneTwelveNumber()  {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Каролина Торгариан");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+791125435223");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid [class='input__sub']")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }
    @Test
    void shouldSendFormWithPartPhoneInvalid()  {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Каролина Торгариан");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79112543552+");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid [class='input__sub']")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }
    @Test
    void shouldSendFormOnNameInvalid()  {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ромманов Сан+Жак");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112546585");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid [class='input__sub']")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }
    @Test
    void shouldSendFormWithPartPhoneInvalidTwo()  {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Каролина Торгариан");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7 911 254 35 52");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid [class='input__sub']")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }
}
