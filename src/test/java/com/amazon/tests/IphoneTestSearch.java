//  # the test itself, only calls page methods
package com.amazon.tests;

import com.amazon.base.BaseTest;
import org.openqa.selenium.By;
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

    }
}
