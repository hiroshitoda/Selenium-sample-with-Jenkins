package jp.selenium.sample.webdriver.WebDriverSample;

import static org.junit.Assert.fail;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Twitterの設定変更と自動ツイートを行うテスト。 Twitterの言語設定を日本語にしていないとうまく動作しません。
 *
 */
public class TweetTest {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        Properties properties = System.getProperties();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(properties.getProperty("browserName"));

        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
                capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        baseUrl = "http://twitter.com/";
    }

    @Test
    public void testSample() throws Exception {
        Properties properties = System.getProperties();
        String userId = properties.getProperty("twitterUser");
        String password = properties.getProperty("twitterPass");
        if (userId == null) {
            throw new RuntimeException("twitterUserシステムプロパティが指定されていません");
        }
        if (password == null) {
            throw new RuntimeException("twitterPassシステムプロパティが指定されていません");
        }
        String tweetMsg = "デブサミ2014 コミュニティブースのデモ作成中..";

        By sessionUserOrEmail = By
                .cssSelector("div.clearfix.field > input[name=\"session[username_or_email]\"]");
        By sessionPassword = By
                .cssSelector("div.clearfix.field > input[name=\"session[password]\"]");
        By signIn = By.xpath("(//button[@type='submit'])[3]");
        By userDropdownToggle = By
                .xpath("//a[@id='user-dropdown-toggle']/span");
        By setting = By.linkText("設定");
        By signOut = By.cssSelector("#signout-button > button.dropdown-link");
        By home = By.xpath("//li[@id='global-nav-home']/a/span[2]");
        By tweetBox = By.id("tweet-box-mini-home-profile");
        By tweet = By.xpath("//div[@id='page-container']/div[2]/div[1]/div[2]/form/div[2]/div[2]/button");

        By settingsSave = By.id("settings_save");
        By authPassword = By.id("auth_password");
        By savePassword = By.id("save_password");

        By userInfo = By.linkText("ユーザー情報");
        By userTimeZone = By.id("user_time_zone");
        By userCountry = By.id("user_country");
        By userNsfwView = By.id("user_nsfw_view");
        By userNsfwUser = By.id("user_nsfw_user");

        By securityAndPrivacy = By.linkText("セキュリティとプライバシー");
        By userNoUsernameOnlyPasswordReset = By
                .id("user_no_username_only_password_reset");
        By userProtected = By.id("user_protected");
        By userGeoEnabled = By.id("user_geo_enabled");
        By userDiscoverableByEmail = By.id("user_discoverable_by_email");
        By useCookiePersonalization = By.id("use_cookie_personalization");
        By allowAdsPersonalization = By.id("allow_ads_personalization");

        By mailNotification = By.linkText("メール通知");
        By preference = By.cssSelector("select.preference-dropdown");
        By favoriteMentionEmail = By
                .cssSelector("#send-favorite-mention-email-dropdown > select.preference-dropdown");
        By retweetedEmail = By
                .cssSelector("#send-retweeted-email-dropdown > select.preference-dropdown");
        By retweetEmail = By
                .cssSelector("#send-retweet-mention-email-dropdown > select.preference-dropdown");
        By mentionEmail = By
                .cssSelector("#send-mention-email-dropdown > select.preference-dropdown");
        By favoritedRetweetEmail = By
                .cssSelector("#send-favorited-retweet-email-dropdown > select.preference-dropdown");
        By retweetedRetweetEmail = By
                .cssSelector("#send-retweeted-retweet-email-dropdown > select.preference-dropdown");
        By emailPreference = By.cssSelector("select.email-preference");

        driver.get(baseUrl + "login");

        // サインイン
        driver.findElement(sessionUserOrEmail).sendKeys(userId);
        driver.findElement(sessionPassword).sendKeys(password);
        driver.findElement(signIn).click();

        // 設定画面へ移動。時々タイミング的にドロップダウンを表示し損ねたらリトライ
        while (driver.findElements(setting).size() == 0) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
            }
            driver.findElement(userDropdownToggle).click();
        }
        driver.findElement(setting).click();

        // 設定を初期状態に戻す(ユーザー情報)
        driver.findElement(userInfo).click();
        new Select(driver.findElement(userTimeZone))
                .selectByVisibleText("(GMT+09:00) Tokyo");
        new Select(driver.findElement(userCountry)).selectByVisibleText("日本");
        if (driver.findElement(userNsfwView).isSelected()) {
            driver.findElement(userNsfwView).click();
        }
        if (driver.findElement(userNsfwUser).isSelected()) {
            driver.findElement(userNsfwUser).click();
        }
        if (driver.findElement(settingsSave).isEnabled()) {
            driver.findElement(settingsSave).click();
            new WebDriverWait(driver, 30).until(ExpectedConditions
                    .presenceOfElementLocated(authPassword));
            driver.findElement(authPassword).sendKeys(password);
            driver.findElement(savePassword).click();
        }

        // 設定を初期状態に戻す(セキュリティとプライバシー)
        driver.findElement(securityAndPrivacy).click();
        if (driver.findElement(userNoUsernameOnlyPasswordReset).isSelected()) {
            driver.findElement(userNoUsernameOnlyPasswordReset).click();
        }
        if (driver.findElement(userProtected).isSelected()) {
            driver.findElement(userProtected).click();
        }
        if (driver.findElement(userGeoEnabled).isSelected()) {
            driver.findElement(userGeoEnabled).click();
        }
        if (!driver.findElement(userDiscoverableByEmail).isSelected()) {
            driver.findElement(userDiscoverableByEmail).click();
        }
        if (!driver.findElement(useCookiePersonalization).isSelected()) {
            driver.findElement(useCookiePersonalization).click();
        }
        if (!driver.findElement(allowAdsPersonalization).isSelected()) {
            driver.findElement(allowAdsPersonalization).click();
        }
        if (driver.findElement(settingsSave).isEnabled()) {
            driver.findElement(settingsSave).click();
            new WebDriverWait(driver, 30).until(ExpectedConditions
                    .presenceOfElementLocated(authPassword));
            driver.findElement(authPassword).sendKeys(password);
            driver.findElement(savePassword).click();
        }

        // 設定を初期状態に戻す(メール通知)
        driver.findElement(mailNotification).click();
        new Select(driver.findElement(preference))
                .selectByVisibleText("あなた向けにカスタマイズされています");
        new Select(driver.findElement(favoriteMentionEmail))
                .selectByVisibleText("あなた向けにカスタマイズされています");
        new Select(driver.findElement(retweetedEmail))
                .selectByVisibleText("あなた向けにカスタマイズされています");
        new Select(driver.findElement(retweetEmail))
                .selectByVisibleText("あなた向けにカスタマイズされています");
        new Select(driver.findElement(mentionEmail))
                .selectByVisibleText("あなた向けにカスタマイズされています");
        new Select(driver.findElement(favoritedRetweetEmail))
                .selectByVisibleText("あなた向けにカスタマイズされています");
        new Select(driver.findElement(retweetedRetweetEmail))
                .selectByVisibleText("あなた向けにカスタマイズされています");
        new Select(driver.findElement(emailPreference))
                .selectByVisibleText("ウイークリーメールを送信");
        if (driver.findElement(settingsSave).isEnabled()) {
            driver.findElement(settingsSave).click();
        }

        // ユーザー情報
        driver.findElement(userInfo).click();
        new Select(driver.findElement(userTimeZone))
                .selectByVisibleText("(GMT+09:00) Tokyo");
        new Select(driver.findElement(userCountry)).selectByVisibleText("日本");
        driver.findElement(userNsfwView).click();
        driver.findElement(userNsfwUser).click();
        driver.findElement(settingsSave).click();
        new WebDriverWait(driver, 60).until(ExpectedConditions
                .presenceOfElementLocated(authPassword));
        driver.findElement(authPassword).sendKeys(password);
        driver.findElement(savePassword).click();

        // 設定を初期状態に戻す(セキュリティとプライバシー)
        driver.findElement(securityAndPrivacy).click();
        driver.findElement(userNoUsernameOnlyPasswordReset).click();
        driver.findElement(userProtected).click();
        driver.findElement(userGeoEnabled).click();
        driver.findElement(userDiscoverableByEmail).click();
        driver.findElement(useCookiePersonalization).click();
        driver.findElement(allowAdsPersonalization).click();
        driver.findElement(settingsSave).click();
        new WebDriverWait(driver, 60).until(ExpectedConditions
                .presenceOfElementLocated(authPassword));
        driver.findElement(authPassword).sendKeys(password);
        driver.findElement(savePassword).click();

        // 設定を初期状態に戻す(メール通知)
        driver.findElement(mailNotification).click();
        new Select(driver.findElement(preference)).selectByVisibleText("誰でも");
        new Select(driver.findElement(favoriteMentionEmail))
                .selectByVisibleText("誰でも");
        new Select(driver.findElement(retweetedEmail))
                .selectByVisibleText("誰でも");
        new Select(driver.findElement(retweetEmail)).selectByVisibleText("誰でも");
        new Select(driver.findElement(mentionEmail)).selectByVisibleText("誰でも");
        new Select(driver.findElement(favoritedRetweetEmail))
                .selectByVisibleText("誰でも");
        new Select(driver.findElement(retweetedRetweetEmail))
                .selectByVisibleText("誰でも");
        new Select(driver.findElement(emailPreference))
                .selectByVisibleText("デイリーメールを送信");
        driver.findElement(settingsSave).click();

        // ツイート
        driver.findElement(home).click();
        long timeMillisEnd = System.currentTimeMillis();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        driver.findElement(tweetBox).sendKeys(
                tweetMsg + " [" + sdf1.format(new Date(timeMillisEnd)) + "]");
        driver.findElement(tweet).click();
        // 待たないと、twitterが内部エラーを出す。。
        new WebDriverWait(driver, 60).until(ExpectedConditions
                .invisibilityOfElementLocated((tweet)));

        // サインアウト
        driver.findElement(userDropdownToggle).click();
        driver.findElement(signOut).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
