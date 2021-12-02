// импорт пакетов

//содержит класс WebDriver, необходимый для создания нового браузера,загруженного конкретным драйвером
import org.openqa.selenium.WebDriver;
//содержит класс ChromeDriver, необходимый для создания драйвера, специфичного для chrome, в браузере, созданном классом WebDriver
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class LoginAndLogoutTest {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        //создание объекта драйвера без параметров
        driver = new ChromeDriver();
        //запуск нового сеанса браузера и направляет на URL
        driver.get("http://opensource-demo.orangehrmlive.com/index.php/auth/login");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void teardown() {
        //закрытие всех окон браузера
        driver.quit();
    }

    @Test
    public void testLogin() {
        //define elements
        WebElement loginElement = driver.findElement(By.id("txtUsername"));
        WebElement passwElement = driver.findElement(By.xpath("//input [@name='txtPassword']"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));

        //check and login
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
        loginElement.sendKeys("Admin");
        passwElement.sendKeys("admin123");
        submitButton.click();

        //check result
        WebElement marketplaceMark= driver.findElement(By.id("MP_link"));
        Assert.assertEquals(marketplaceMark.getAttribute("value"),"Marketplace");
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));

        //back to previous page
        driver.navigate().back();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
        driver.navigate().refresh();
        WebElement loginPanel =driver.findElement(By.id("logInPanelHeading"));
        Assert.assertEquals(loginPanel.getText(), "LOGIN Panel");
    }


}
    