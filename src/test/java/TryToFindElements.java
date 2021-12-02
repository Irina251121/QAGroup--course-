import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class TryToFindElements {
          private WebDriver driver;

        @BeforeTest
        public void setUp() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            driver = new ChromeDriver();
            driver.get("https://rozetka.com.ua");
            driver.manage().window().maximize();
        }

        @Test (description = "get page elements and check, match, extract")
        public void findElement (){
        String mainPageSource = driver.getPageSource();
        WebElement searchField = driver.findElement(By.xpath("//input[@name='search']"));

        // 1. check the certain text exists on the page
        Assert.assertTrue(mainPageSource.contains("Доставка по всей Украине"));

        // 2. check equality of the expected and actual element (text)
        WebElement notebookLink = driver.findElement(By.xpath("//ul[@class='menu-categories menu-categories_type_main']/li[1]/a"));
        String notebookLinkText = notebookLink.getText();
        Assert.assertEquals(notebookLinkText, "Ноутбуки и компьютеры");

        // 3. check is element enabled on the page
        Assert.assertTrue(searchField.isEnabled());

        //4. check attribute value
        Assert.assertEquals(searchField.getAttribute("placeholder"),"Я ищу...");
        Assert.assertNotEquals(searchField.getAttribute("type"),"file");

        //5. check the quantity of elements on the page
        List<WebElement> linkList = driver.findElements(By.xpath("//ul[@class='menu-categories menu-categories_type_main']/li"));
        Assert.assertEquals(linkList.size(),19);

    }
        @AfterTest
        public void tearDown() {
            driver.quit();
        }

    }

