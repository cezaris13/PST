package org.example;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

public class SecondTask {
    public static void SecondTaskA(WebDriver driver) {
        driver.get("https://demoqa.com/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.findElement(By.xpath("//div[@class = 'card mt-4 top-card']//div[@class = 'card-body']/h5[text() = 'Widgets']")).click();
        WebElement progressBarButton = driver.findElement(By.xpath("//ul[@class = 'menu-list']//span[text() = 'Progress Bar']"));
        js.executeScript("arguments[0].scrollIntoView();", progressBarButton);
        progressBarButton.click();
        driver.findElement(By.xpath("//div[text() = 'Progress Bar']/following-sibling::button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@class = 'progress']/div[@class = 'progress-bar bg-success']"), "100%"));

        driver.findElement(By.xpath("//div[@class = 'progress']/following-sibling::button[text() = 'Reset']")).click();
        Assert.isTrue(driver.findElement(By.xpath("//div[@class = 'progress']/div")).getText().contains("0%"), "failed");
    }

    public static void SecondTaskB(WebDriver driver) throws InterruptedException {
        driver.get("https://demoqa.com/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.findElement(By.xpath("//div[@class = 'card mt-4 top-card']//div[@class = 'card-body']/h5[text() = 'Elements']")).click();
        driver.findElement(By.xpath("//ul[@class = 'menu-list']//span[text() = 'Web Tables']")).click();

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500));

        wait.until(driver1 -> {
            if (!driver1.findElement(By.xpath("//div[@class = 'pagination-bottom']/div[@class = '-pagination']/div/button[text() = 'Next']")).isEnabled()) {
                CreateTableEntry(driver1);
            }

            return driver1.findElement(By.xpath("//div[@class = 'pagination-bottom']/div[@class = '-pagination']/div/button[text() = 'Next']")).isEnabled();
        });
//        while (!driver.findElement(By.xpath("//div[@class = 'pagination-bottom']/div[@class = '-pagination']/div/button[text() = 'Next']")).isEnabled()) {
//            CreateTableEntry(driver);
//        }
//        TimeUnit.SECONDS.sleep(1);
        WebElement nextButton = driver.findElement(By.xpath("//div[@class = 'pagination-bottom']/div[@class = '-pagination']/div/button[text() = 'Next']"));
        js.executeScript("arguments[0].scrollIntoView();", nextButton);
        nextButton.click();
        driver.findElement(By.xpath("//div[@class = 'action-buttons']/span/following-sibling::span")).click();
        driver.findElement(By.xpath("//span[@class = '-pageInfo']/div/input")).click();
        driver.findElement(By.xpath("//span[@class = '-pageInfo']/span[@class = '-totalPages']")).click();
        Assert.isTrue(driver.findElement(By.xpath("//span[@class = '-pageInfo']/span[@class = '-totalPages']")).getText().contains("1"), "failure of total pagination");
        Assert.isTrue(driver.findElement(By.xpath("//span[@class = '-pageInfo']/div/input")).getAttribute("value").contains("1"), "failure of pagination");
    }

    public static void CreateTableEntry(WebDriver driver) {
        driver.findElement(By.xpath("//div[@class = 'web-tables-wrapper']//following-sibling::div//button[text() = 'Add']")).click();
        driver.findElement(By.xpath("//form/*[@id = 'firstName-wrapper']/div//input")).sendKeys("tom");
        driver.findElement(By.xpath("//form/*[@id = 'lastName-wrapper']/div//input")).sendKeys("tomson");
        driver.findElement(By.xpath("//form/*[@id = 'userEmail-wrapper']/div//input")).sendKeys(UUID.randomUUID().toString() + "@gmail.com");
        driver.findElement(By.xpath("//form/*[@id = 'age-wrapper']/div//input")).sendKeys("22");
        driver.findElement(By.xpath("//form/*[@id = 'salary-wrapper']/div//input")).sendKeys("123");
        driver.findElement(By.xpath("//form/*[@id = 'department-wrapper']/div//input")).sendKeys("Management");

        driver.findElement(By.xpath("//button[@id = 'submit']")).click();
    }
}
