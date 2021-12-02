import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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

public class CssDinnerPlayWithWaits {
            private WebDriver driver;


        @BeforeTest
        public void setUp() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver();
            driver.get("https://flukeout.github.io");
            driver.manage().window().setSize(new Dimension(1024,768));
    }

        @AfterTest
        public void tearDown() {
            driver.quit();
        }

        @Test
        public void letsPlay() throws InterruptedException {
            System.out.println("Now I'm gonna play the game: " + driver.getTitle());
            System.out.println("==============================================");

            //define some locators for this game
            WebElement currentGameTask = driver.findElement(By.className("order"));
            WebElement gameLevel = driver.findElement(By.className("level-text"));
            WebElement rowForAnswer = driver.findElement(By.xpath("//*[@class='input-strobe' and @type='text']"));
            WebElement enterButton = driver.findElement(By.xpath("//div[contains(@class,'enter-button') and contains(text(),'enter')]"));

            //define explicit waits
            WebDriverWait waitBeforeInputAnswer = (new WebDriverWait(driver, 8));
            WebDriverWait waitAfterInputAnswer = (new WebDriverWait(driver, 8));

            //Expected result for the game
            String[] expectedResults = new String[] {"plate","bento","#fancy","plate apple","#fancy pickle",
                    ".small","orange.small","bento orange.small","plate, bento", "*",
                    "plate *","plate + apple","bento ~ pickle","plate>apple","orange:first-child",
                    "plate > :only-child",".small:last-child","plate:nth-child(3)","bento:nth-child(2)","apple:first-of-type",
                    "plate:nth-of-type(even)","plate:nth-of-type(2n+3)","plate apple:only-of-type","orange:last-of-type, apple:last-of-type","bento:empty",
                    "apple:not(.small, .medium)","*[for]","plate[for]","[for='Vitaly']","[for^='Sa']",
                    "[for$='ato']","[for*='obb']"};

            for (int i=0;i < expectedResults.length;i++){
                System.out.println("I'm on level: " + gameLevel.getText());
                System.out.println("My task is: " + currentGameTask.getText());
                System.out.println("My answer is: " + expectedResults[i]);
                waitBeforeInputAnswer.until(ExpectedConditions.elementToBeClickable(rowForAnswer));
                rowForAnswer.sendKeys(expectedResults[i]);
                Thread.sleep(500);
                enterButton.click();

                if ((i+1)==expectedResults.length){
                    break;
                }

                waitAfterInputAnswer.until(ExpectedConditions.textToBe(By.className("level-text"), String.format("Level %d of 32",i+2)));

                if (gameLevel.getText().equals(String.format("Level %d of 32",i+2))) {
                    System.out.println("OK, I am winner!");
                } else {
                    System.out.println("I'm loose");
                }
            }
            Assert.assertTrue(driver.findElement(By.xpath("//strong[text()='You did it!']")).isDisplayed());
    }
}

