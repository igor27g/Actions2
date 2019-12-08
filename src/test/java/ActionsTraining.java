import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class ActionsTraining {

    WebDriver driver;
    Actions actions;

     @BeforeEach
    public void driverSetup()
    {
        String fakeStore = "https://fakestore.testelka.pl/";
        String fakeStoreWindsurfing = "https://fakestore.testelka.pl/product-category/windsurfing/";
        String fakeStoreKonto = "https://fakestore.testelka.pl/moje-konto/";
        String blog = "https://testelka.pl/blog/";
        String allegro = "https://allegro.pl/";

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        actions = new Actions(driver);
    }

    @AfterEach
    public void driverClose()
    {
        driver.close();
        driver.quit();
    }


    @Test
    public void offsetExample() {
         driver.navigate().to("https://jqueryui.com/draggable/");
         driver.switchTo().frame(0);
         WebElement draggableElement = driver.findElement(By.cssSelector("#draggable"));

         //actions.dragAndDropBy(draggableElement, 20,20).build().perform();
        actions.clickAndHold(draggableElement).moveByOffset(220,220).release().build().perform();
     }

     @Test
    public void toElementExample() {
         driver.navigate().to("https://marcojakob.github.io/dart-dnd/nested_dropzones/");
         WebElement draggableElement = driver.findElement(By.cssSelector(".draggable"));
         WebElement dropElement = driver.findElement(By.cssSelector(".dropzone-inner"));
//         actions.dragAndDrop(draggableElement,dropElement).build().perform();
//         actions.clickAndHold(draggableElement).moveToElement(dropElement).release().build().perform();
         //actions.clickAndHold(draggableElement).release(dropElement).build().perform();
         actions.clickAndHold(draggableElement).moveToElement(dropElement,2,2).release().build().perform();
     }

    //1.Sprawdź czy przesunięcie żółtego kwadratu na różowy działa i czy widoczna jest informacja, że
    // kwadrat został upuszczony poprawnie.

    @Test
    public void moveYellowSquare() throws InterruptedException {
         driver.navigate().to("https://fakestore.testelka.pl/actions/");
         driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
         WebElement yellowSquare = driver.findElement(By.cssSelector("#draggable"));
         WebElement pinkSquare = driver.findElement(By.cssSelector("#droppable"));
         actions.clickAndHold(yellowSquare).moveToElement(pinkSquare).build().perform();
         Thread.sleep(5000);
        Assertions.assertEquals("Dropped", pinkSquare.getText(), "Message in the droppable box was not changer." +
                "Was the element dropped?");

    }


    //2.Sprawdź czy przesunięcie żółtego kwadratu tak, by jego środek znalazł się (mniej więcej)
    // w prawym dolnym rogu różowego działa i czy widoczna jest informacja, że kwadrat został upuszczony poprawnie.

    @Test
    public void dropToBottomRightCornerTest() {
        driver.navigate().to("https://fakestore.testelka.pl/actions/");
        driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
        WebElement yellowSquare = driver.findElement(By.cssSelector("#draggable"));
        WebElement pinkSquare = driver.findElement(By.cssSelector("#droppable"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yellowSquare);
        actions.clickAndHold(yellowSquare).moveToElement(pinkSquare, 74,74).release().build().perform();
        Assertions.assertEquals("Dropped!", pinkSquare.getText(), "Message in the droppable box was not changer." +
                "Was the element dropped?");
    }


    //3.Sprawdź czy przesunięcie żółtego kwadratu o 160 pikseli w prawo i o 40 pikseli
    // w dół spowoduje upuszczenie na różowym kwadracie.

    @Test
    public void moveYellowSquare160() throws InterruptedException {
        driver.navigate().to("https://fakestore.testelka.pl/actions/");
        driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
        WebElement yellowSquare = driver.findElement(By.cssSelector("#draggable"));
        WebElement pinkSquare = driver.findElement(By.cssSelector("#droppable"));
        actions.clickAndHold(yellowSquare).moveToElement(pinkSquare,160,-40).release().build().perform();
        Thread.sleep(5000);
        Assertions.assertEquals("Drop here", pinkSquare.getText(), "Wrong css vakye");

    }

}
