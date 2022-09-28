package org.example;

import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestThirdTask {

    static WebDriver driver;
    static Params parameters = new Params("Tom", "tomson", "qwerty1234", UUID.randomUUID().toString() + "@gmail.com");
    static BillingData billingData = new BillingData("Vilnius", "Didlaukio 59", "LT-08302", "+37000000000");

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", String.format("%s/%s", System.getProperty("user.dir"), "chromedriver"));
        ChromeOptions option = new ChromeOptions();
        option.addArguments("headless");
        driver = new ChromeDriver(option);
        driver.get("https://demowebshop.tricentis.com/");
        driver.findElement(By.xpath("//a[text() = 'Log in']")).click();
        driver.findElement(By.xpath("//input[@value = 'Register']")).click();
        driver.findElement(By.xpath("//input[@value = 'M']")).click();
        driver.findElement(new By.ById("FirstName")).sendKeys(parameters.name);
        driver.findElement(new By.ById("LastName")).sendKeys(parameters.surname);
        driver.findElement(new By.ById("Email")).sendKeys(parameters.email);
        driver.findElement(new By.ById("Password")).sendKeys(parameters.password);
        driver.findElement(new By.ById("ConfirmPassword")).sendKeys(parameters.password);
        driver.findElement(By.xpath("//input[@value = 'Register']")).click();
        driver.findElement(By.xpath("//input[@value = 'Continue']")).click();
        driver.quit();
    }

    @BeforeEach
    public void InitTest() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void CleanupTest() {
        driver.quit();
    }

    @Test
    public void OrderTwoThirdAlbums() throws InterruptedException {
        driver.get("https://demowebshop.tricentis.com/");
        LogInUser();
        driver.findElement(By.xpath("//ul[@class = 'list']/li/a[@href = '/digital-downloads']")).click();
        AddToCartItems("data1.txt");
        EnterCheckoutDetailsAndContinue();
        SelectPaymentMethodAndConfirmOrder();
        FluentWaitMethod("//div[@class = 'section order-completed']/div/strong");
        Assert.isTrue(driver.findElement(By.xpath("//div[@class = 'section order-completed']/div/strong")).getText().contains("Your order has been successfully processed!"), "order failed");
    }

    @Test
    public void OrderTwoThirdAlbumsAndMusic() throws InterruptedException {
        driver.get("https://demowebshop.tricentis.com/");
        LogInUser();
        driver.findElement(By.xpath("//ul[@class = 'list']/li/a[@href = '/digital-downloads']")).click();
        AddToCartItems("data2.txt");
        EnterCheckoutDetailsAndContinue();
        SelectPaymentMethodAndConfirmOrder();
        FluentWaitMethod("//div[@class = 'section order-completed']/div/strong");
        Assert.isTrue(driver.findElement(By.xpath("//div[@class = 'section order-completed']/div/strong")).getText().contains("Your order has been successfully processed!"), "order failed");
    }

    private void LogInUser() {
        driver.findElement(By.xpath("//a[@href = '/login']")).click();
        driver.findElement(By.xpath("//input[@class = 'email']")).sendKeys(parameters.email);
        driver.findElement(By.xpath("//input[@class = 'password']")).sendKeys(parameters.password);
        driver.findElement(By.xpath("//input[@value  = 'Log in']")).click();
    }

    private void AddToCartItems(String fileName) throws InterruptedException {
        List<String> items = ReadDataFromFile(fileName);
        for (String item : items) {
            driver.findElement(By.xpath(String.format("//div[@class = 'product-item']/div[@class = 'details']//a[text() = '%s']/parent::*/following-sibling::div[@class = 'add-info']/div[@class = 'buttons']/input", item))).click();
            TimeUnit.SECONDS.sleep(2);//timing explicit wait?
        }
        driver.findElement(By.xpath("//a[@href = '/cart']/span[text() = 'Shopping cart']")).click();
        driver.findElement(By.xpath("//input[@name = 'termsofservice']")).click();
        driver.findElement(By.xpath("//button[@value = 'checkout']")).click();
    }

    private void EnterCheckoutDetailsAndContinue() {
        if(driver.findElements(By.xpath("//label[@for = 'billing-address-select']")).isEmpty()) {
            driver.findElement(By.xpath("//select[@name = 'BillingNewAddress.CountryId']")).click();
            driver.findElement(By.xpath("//select[@name = 'BillingNewAddress.CountryId']/option[text() = 'Lithuania']")).click();
            driver.findElement(By.xpath("//input[@name = 'BillingNewAddress.City']")).sendKeys(billingData.city);
            driver.findElement(By.xpath("//input[@name = 'BillingNewAddress.Address1']")).sendKeys(billingData.address);
            driver.findElement(By.xpath("//input[@name = 'BillingNewAddress.ZipPostalCode']")).sendKeys(billingData.zip);
            driver.findElement(By.xpath("//input[@name = 'BillingNewAddress.PhoneNumber']")).sendKeys(billingData.phoneNumber);
        }
        driver.findElement(By.xpath("//input[@value = 'Continue' and @title = 'Continue']")).click();
    }

    private void SelectPaymentMethodAndConfirmOrder() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        FluentWaitMethod("//input[@value = 'Continue' and @class = 'button-1 payment-method-next-step-button']");
        driver.findElement(By.xpath("//input[@value = 'Continue' and @class = 'button-1 payment-method-next-step-button']")).click();
        FluentWaitMethod("//input[@value = 'Continue' and @class = 'button-1 payment-info-next-step-button']");
        driver.findElement(By.xpath("//input[@value = 'Continue' and @class = 'button-1 payment-info-next-step-button']")).click();
        FluentWaitMethod("//input[@value = 'Confirm' and @class = 'button-1 confirm-order-next-step-button']");
        WebElement confirmButton = driver.findElement(By.xpath("//input[@value = 'Confirm' and @class = 'button-1 confirm-order-next-step-button']"));
        js.executeScript("arguments[0].scrollIntoView();", confirmButton);
        confirmButton.click();
    }

    private void FluentWaitMethod(String locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500));

        wait.until(driver1 -> {
            List<WebElement> elements = driver1.findElements(By.xpath(locator));
            return !elements.isEmpty() && elements.size() == 1 && elements.get(0).isDisplayed();
        });
    }

    private List<String> ReadDataFromFile(String fileName) {
        List<String> result = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(String.format("%s/%s/%s", System.getProperty("user.dir"), "src/main/java/org/example", fileName)), Charset.defaultCharset())) {
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
