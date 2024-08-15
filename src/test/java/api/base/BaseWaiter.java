package api.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;



public class BaseWaiter {
    WebDriverWait wait2;
    WebDriverWait wait5;
    WebDriverWait wait10;

    WebDriver driver;

    public void getWait2() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void getWait5() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    }

    public void getWait10() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

}
