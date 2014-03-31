package jp.selenium.sample.webdriver.WebDriverSample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SimpleTest
{

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private int width = 1024;
    private int height = 768;

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
        driver.manage().window().setSize(new Dimension(width, height));

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
        driver.findElement(By.name("commit")).click();

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
