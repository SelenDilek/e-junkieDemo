import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeClass
    @Parameters("webDriver")
    public void init(String webDriver) {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.SEVERE);

        switch (webDriver) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "safari":
                driver = new SafariDriver();
                break;
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // @assigned=
    @Test (enabled = false)
    void US1AddingIntoBasket() {

    }

    // @assigned=Umut Can Güzel
    @Test
    void US2NullPayment() {

    }

    // @assigned=
    @Test(enabled = false)
    void US3NegativePayment() {

    }

    // @assigned=Ümit Boyraz
    @Test
    void US4ValidPayment() throws InterruptedException {
        driver.navigate().to("https://www.e-junkie.com/wiki/demo/paypal");

        Assert.assertTrue(driver.getCurrentUrl().equals("https://www.e-junkie.com/wiki/demo/paypal"), "Web site is wrong");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Add to Cart']"))).click();

        driver.switchTo().frame(0);

        WebElement yourCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h3[@class='Title'])[1]")));
        Assert.assertTrue(yourCart.isDisplayed());

        WebElement proceedToPay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Proceed To Pay']")));
        Assert.assertTrue(proceedToPay.isDisplayed());

        WebElement totalPrice = driver.findElement(By.xpath("//span[text()='0.01 USD']"));

        Double total = Double.parseDouble(totalPrice.getText().replaceAll("[^0-9,.]", ""));

        Assert.assertTrue(total == 0.01, "Prices are not the same");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='Payment-Button CC']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='Pay-Button']"))).click();

        WebElement message = driver.findElement(By.xpath("//*[@id=\"SnackBar\"]/span"));

        Assert.assertTrue(message.isEnabled(),"Message is not appeared");

        Thread.sleep(5000);

        WebElement eMail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Email']")));
        eMail.sendKeys("alicabbar@gmail.com");

        WebElement confirmEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Confirm Email']")));
        confirmEmail.sendKeys("alicabbar@gmail.com");

        WebElement nameOnCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name On Card']")));
        nameOnCard.sendKeys("Ali Cabbar");

        WebElement addressLine1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Address Line 1']")));
        addressLine1.sendKeys("Malkara");

        WebElement city = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='City']")));
        city.sendKeys("Tekirdağ");

        WebElement country = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='Countries-List']")));
        new Select(country).selectByVisibleText("Turkey");

        WebElement zipPostalCode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='ZIP/Postal Code']")));
        zipPostalCode.sendKeys("59300");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='Pay-Button']"))).click();

        WebElement invalidCardMessage = driver.findElement(By.xpath("//span[text()='Invalid Card Number']"));
        Assert.assertTrue(invalidCardMessage.getText().equals("Invalid Card Number"),"Valid card number entered !");

        WebElement CardType = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='Card-Type']//select")));

        new Select(CardType).selectByIndex(1);

        new Select(CardType).selectByIndex(2);

        new Select(CardType).selectByIndex(3);

        new Select(CardType).selectByIndex(0);

        WebElement cardNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='4444 4444 4444 4444']")));
        cardNumber.sendKeys("4242 4242 4242 4242");

        WebElement cardExpiry = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='MM / YY']")));
        cardExpiry.sendKeys("1228");

        WebElement cvvCsc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='000']")));
        cvvCsc.sendKeys("315");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='Pay-Button']"))).click();

        WebElement confirmMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='green_text_margin']")));
        Assert.assertTrue(confirmMessage.getText().contains("your order is confirmed. Thank you!"),"Order has not been confirmed !");

    }

    // @assigned=
    @Test
    void US5Download() throws InterruptedException, IOException {

        WebElement downloadButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='download_btn top10']")));
        downloadButton.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        Thread.sleep(5000);

        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timeStamp = dateFormat.format(new Date());
        String fileName = "eJunkie_" + timeStamp + ".jpg";
        FileUtils.copyFile(screenshotFile, new File("C:\\Users\\user\\Desktop\\Software Engineering\\SDET\\Bootcamp\\Main Program\\Test Tools\\Selenium\\ScreenShots\\"+fileName));
        Thread.sleep(5000);
    }

    // @assigned=
    @Test(enabled = false)
    void US6Contact() {

    }

    // @assigned=
    @Test(enabled = false)
    void US7VideoPlaying() {

    }

    @AfterClass
    void driverQuit(){
        driver.quit();
    }
}
