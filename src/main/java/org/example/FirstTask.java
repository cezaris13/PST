package org.example;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTask {
    public static void FullXpath(WebDriver driver, Params parameters) {
        try {
            driver.get("https://demowebshop.tricentis.com/");
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[2]/a")).click();
            TimeUnit.SECONDS.sleep(1);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[1]/div[1]/div[3]/input")).click();
            TimeUnit.SECONDS.sleep(1);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[2]/div[2]/div[1]/div[1]/input")).click();
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[2]/div[2]/div[2]/input")).sendKeys(parameters.name);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[2]/div[2]/div[3]/input")).sendKeys(parameters.surname);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[2]/div[2]/div[4]/input")).sendKeys(parameters.email);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[3]/div[2]/div[1]/input")).sendKeys(parameters.password);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[3]/div[2]/div[2]/input")).sendKeys(parameters.password);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[4]/input")).click();
            TimeUnit.SECONDS.sleep(1);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[2]/input")).click();
            TimeUnit.SECONDS.sleep(1);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ul/li[2]/a")).click();
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ul/li[2]/ul/li[1]/a")).click();

            List<WebElement> elements = driver.findElements(By.className("product-item"));
            boolean first = false;
            for (WebElement element : elements) {
                double cost = Double.parseDouble(element.findElement(By.className("actual-price")).getText());
                if (cost > 1500 && !first) {
                    first = true;
                    element.findElement(By.className("product-box-add-to-cart-button")).click();
                }
            }
            TimeUnit.SECONDS.sleep(2);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/div[2]/div/form/div/div[1]/div[2]/div[8]/div/input[2]")).click();
            TimeUnit.SECONDS.sleep(2);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[3]")).click();
            TimeUnit.SECONDS.sleep(1);
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/div/form/table/tbody/tr/td[1]/input")).click();
            driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/div/form/div[1]/div/input[1]")).click();

            TimeUnit.SECONDS.sleep(1);
            Assert.isTrue(driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div/div[2]/div[2]/div")).getText().contains("Your Shopping Cart is empty!"), "failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShortXpath(WebDriver driver, Params parameters) {
        try {
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
            driver.findElement(By.xpath("//a[@href = '/computers']")).click();
            driver.findElement(By.xpath("//ul[@class = 'sublist']//a[@href = '/desktops']")).click();
            driver.findElement(By.xpath("//div[@class = 'add-info']//span[@class = 'price actual-price' and text() > 1500]/parent::*/following-sibling::*/input")).click();
            driver.findElement(By.xpath("//div[@class = 'add-to-cart-panel']/input[@value = 'Add to cart']")).click();
            driver.findElement(By.xpath("//a[@href = '/cart']/span[text() = 'Shopping cart']")).click();
            driver.findElement(By.xpath("//td[@class = 'remove-from-cart']/input[@type = 'checkbox']")).click();
            driver.findElement(By.xpath("//input[@value = 'Update shopping cart']")).click();
            Assert.isTrue(driver.findElement(By.xpath("//div[@class = 'order-summary-content']")).getText().contains("Your Shopping Cart is empty!"), "failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
