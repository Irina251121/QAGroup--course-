import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class MainPageSearchField {
        public WebDriver driver;
        JavascriptExecutor js;


    @BeforeTest
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver();
        driver.get("https://rozetka.com.ua");
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        //implicit wait
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //searchField = driver.findElement (By.xpath ("//input[@name='search']"));
    }
    @Test
    public void findValidValue(){
        WebElement searchField = driver.findElement (By.xpath ("//input[@name='search' and@placeholder='Я ищу...']"));
        WebElement searchSubmitButton = driver.findElement(By.xpath("//button[text()=' Найти ']"));
        searchField.sendKeys("Пылесос");
        searchSubmitButton.click();
        new WebDriverWait(driver, 10).
        //until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//h1[@class='catalog-heading ng-star-inserted']")),"Пылесосы"));
        until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[@class='catalog-heading ng-star-inserted']")));
        Assert.assertTrue(driver.findElement(By.xpath("//span[@class='goods-tile__title']")).getText().contains ("Пылесос"));
    }


    @Test (priority = 2, description = "negative test case")
    public void findInvalidValue(){
        WebElement searchField = driver.findElement (By.xpath ("//input[@name='search' and@placeholder='Я ищу...']"));
        WebElement searchSubmitButton = driver.findElement (By.xpath("//button[text()=' Найти ']"));
        searchField.sendKeys("*&^%$");
        searchSubmitButton.click();
        new WebDriverWait(driver, 10).
        until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='ng-star-inserted']")));
        Assert.assertTrue(driver.findElement (By.xpath("//span[@class='ng-star-inserted']")).isDisplayed());
    }

    @Test (priority =3)
    public void findSomeValue(){
        WebElement searchField = driver.findElement (By.xpath ("//input[@name='search']"));
        WebElement searchSubmitButton = driver.findElement (By.xpath("//button[text()=' Найти ']"));
        searchField.sendKeys("Пылесос");
        searchSubmitButton.click();
        new WebDriverWait(driver, 10).
                until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='goods-tile__title']")));
        js.executeScript("window.scrollBy(0,600)");
                WebElement bosch = driver.findElement(By.xpath("//span[@class='goods-tile__title' and contains(text(),'BGS2POW1')]"));
        new WebDriverWait(driver, 10).
                until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='goods-tile__title']")));
       //' js.executeScript("window.scrollBy(0,500)");
        Assert.assertTrue(bosch.getText().contains ("Пылесос"));

    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
