import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public WebDriver driver;
    public WebDriverWait wait;
    public String baseUrl = "https://www.e-junkie.com/wiki/demo/paypal";

    @BeforeClass
    @Parameters("webDriver")
    public void init(String webDriver) {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.SEVERE);

        switch (webDriver)
        {
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

    @BeforeMethod
    void openUrl()
    {
        driver.navigate().to(baseUrl);
    }

    // @assigned=
    @Test
    void US1AddingIntoBasket()
    {

    }

    // @assigned=Umut Can Güzel
    @Test
    void US2NullPayment()
    {

    }

    // @assigned=
    @Test
    void US3NegativePayment()
    {

    }

    // @assigned=Ümit Boyraz
    @Test
    void US4ValidPayment()
    {

    }

    // @assigned=
    @Test
    void US5Download()
    {

    }

    // @assigned=
    @AfterMethod
    void US6Contact()
    {

    }

    // @assigned=
    @Test
    void US7VideoPlaying()
    {

    }

    @AfterClass
    public void close() {
        driver.quit();
    }
}
