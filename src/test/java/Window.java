import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.util.Set;
import static java.lang.Thread.sleep;

public class Window {
    private static final String WINDOWS_MAIN_PAGE ="https://the-internet.herokuapp.com/windows" ;
    WebDriver driver;
    private String originalWindowHandle;
    private String newWindowHandle;

    @BeforeSuite
    public void testSuiteSetup(){
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
        driver = new FirefoxDriver();


    }
    @AfterSuite
    public void tearDown(){
        driver.quit();
    }
    @Test
    public void test1() {
        String expectedNewWindowTitle = "New Window";
        String expectedNewWindowText = "New Window";
        String expectedOriginalWindowTitle = "The Internet";
        int expectedAmountOfWindows = 2;
       
        openWindowsPage();
        clickOnLink();
        verifyAmountOfWindows(expectedAmountOfWindows);
        switchToNewWindow();
        verifyWindowTitle(expectedNewWindowTitle);
        verifyWindowText(expectedNewWindowText);
        switchToOriginalWindow();
        verifyWindowTitle(expectedOriginalWindowTitle);
    }

    private void switchToNewWindow() {
        driver.switchTo().window(newWindowHandle);
    }
    private void switchToOriginalWindow() {
        driver.switchTo().window(originalWindowHandle);
    }


    private void verifyWindowText(String expectedText) {
        Assert.assertTrue(driver.getPageSource().contains(expectedText));
    }

    private void verifyWindowTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    private void verifyAmountOfWindows(int expectedAmount)
    {
        try {
           sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        Set<String> windowHandles = driver.getWindowHandles();
        int actualAmountOfHandles = windowHandles.size();

        Assert.assertEquals(actualAmountOfHandles,expectedAmount);

        Object[] arrayOfObjects = windowHandles.toArray();
        Object firstElement = arrayOfObjects[0];
        originalWindowHandle = (String) firstElement;
        newWindowHandle = (String) arrayOfObjects[1];
    }

    private void clickOnLink() {
        By expectedElement = By.linkText("Click Here");
        WebElement webElement = waitForElement(expectedElement);
        webElement.click();
    }

    private WebElement waitForElement(By expectedElement) {
       WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
       WebElement foundedElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(expectedElement));
       return foundedElement;
    }
    private void openWindowsPage() {
        driver.get(WINDOWS_MAIN_PAGE);
    }
}
