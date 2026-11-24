//  # the test itself, only calls page methods
package com.amazon.tests;

import com.amazon.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class IphoneTestSearch extends BaseTest {

    @Test
    public void openAmazon() {
        driver.get("https://www.amazon.in/");

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        searchBox.sendKeys("iphone 18 pro");
        searchBox.sendKeys(Keys.ENTER);

        By iphone = By.xpath("//a[not(contains(@href,'sspa')) and h2[contains(@aria-label,'iPhone 18 Pro') "
                + "and not(contains(@aria-label,'Max')) and contains(@aria-label,'256') "
                + "and contains(@aria-label,'Black')]]");

        String mainTab = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(iphone)).click();

        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(mainTab)) {
                driver.switchTo().window(tab);
            }
        }
        System.out.println("Opened product page: " + driver.getTitle());
    }

    @Test
    public void mobilesUnder20000() {

        driver.get("https://www.amazon.in/");
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        searchBox.sendKeys("mobile");
        searchBox.sendKeys(Keys.ENTER);

        // 2. Max-price handle (it has its own id)
        By maxHandleLocator = By.id("p_36/range-slider_slider-item_upper-bound-slider");
        WebElement maxHandle = wait.until(ExpectedConditions.presenceOfElementLocated(maxHandleLocator));

        // 3. Scroll to it and focus it with JS (no click, the overlay blocks clicks)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", maxHandle);
        js.executeScript("arguments[0].focus();", maxHandle);

        // 4. Press LEFT until max price <= 20,000
        for (int i = 0; i < 200; i++) {
            maxHandle = driver.findElement(maxHandleLocator); // re-find each time
            String shown = maxHandle.getAttribute("aria-valuetext"); // e.g. "₹286,400+"
            int value = Integer.parseInt(shown.replaceAll("[^0-9]", ""));
            System.out.println("Slider max now: " + value);
            if (value <= 20000)
                break;
            maxHandle.sendKeys(Keys.ARROW_LEFT);
        }

        // 6. Wait until the page reloads with the price filter in the URL
        wait.until(ExpectedConditions.urlContains("p_36"));

    }
}
