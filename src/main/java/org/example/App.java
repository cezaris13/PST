package org.example;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.example.SecondTask.SecondTaskA;
import static org.example.SecondTask.SecondTaskB;

public class App {
    public static void main(String[] args) {
        String email = UUID.randomUUID().toString() + "@gmail.com";
        System.setProperty("webdriver.chrome.driver", "/home/pijus/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // nustatoma globaliai
//        wait.pollingEvery(Duration.ofSeconds(2));// fluent

//        FirstTask.FullXpath(driver, parameters);
//        FirstTask.ShortXpath(driver, parameters);

//        driver.quit()
//        SecondTaskA(driver);
        try{
            SecondTaskB(driver);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

