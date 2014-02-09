package jp.selenium.sample.webdriver.WebDriverSample;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

public class SimpleTest
{

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception
    {
        Properties properties = System.getProperties();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(properties.getProperty("browserName"));
        
        driver = new RemoteWebDriver(
                new URL("http://localhost:4444/wd/hub"),
                capabilities
                );
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        baseUrl = "http://www.adventar.org/";
    }

    @Test
    public void testSample() throws Exception
    {
        driver.get(baseUrl);
        driver.findElement(By.linkText("2013年の Advent Calendar 一覧を見る")).click();
        driver.findElement(By.linkText("Selenium")).click();
        driver.findElement(By.cssSelector("a.mod-calendar-entryLink > img.mod-userIcon")).click();
        driver.findElement(By.id("q")).clear();
        driver.findElement(By.id("q")).sendKeys("selenium");
        driver.findElement(By.id("search-button")).click();

        String expected = "seleniumの検索結果";
        String actual = driver.findElement(By.className("page-title")).getText();
        
        assertThat(actual, is(expected));
    }

    @After
    public void tearDown() throws Exception
    {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString))
        {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by)
    {
        try
        {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    private boolean isAlertPresent()
    {
        try
        {
            driver.switchTo().alert();
            return true;
        }
        catch (NoAlertPresentException e)
        {
            return false;
        }
    }

    private String closeAlertAndGetItsText()
    {
        try
        {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert)
            {
                alert.accept();
            }
            else
            {
                alert.dismiss();
            }
            return alertText;
        }
        finally
        {
            acceptNextAlert = true;
        }
    }
}
