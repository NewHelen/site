package com.weather.steps;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class MySteps {
    private static final Logger log = Logger.getLogger(MySteps.class);
    WebDriver driver;
    Double minTempCelsius;
    Double maxTempCelsius;
    Double minTempfarengeyt;
    Double maxTempfarengeyt;

    @BeforeStory //("chrome started")
    public void startBrowser () {
        System.setProperty("webdriver.chrome.driver", "D://web_drivers//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://www.weather-forecast.com/");
        driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);
    }

    @When("search for $good")
    public void searchGood (String good) {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.xpath(".//*[@id='location']")).sendKeys(good);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Then("choose the desired result")
    public void chooseRezult() {
        WebElement locatorResult = driver.findElement(By.xpath(".//*[@id='location_autocomplete']//div//nobr/span[text()='Kiev']/parent::nobr[text()=', ']/span[text()='Ukraine']"));
        locatorResult.click();
    }

    @Then ("choose the desired result2")
    public void chooseRezult2() {
        WebElement locatorResult = driver.findElement(By.xpath(".//*[@id='location_autocomplete']//div//nobr/span[text()='Madrid']/parent::nobr[text()=', ']/span[text()='Spain']"));
        locatorResult.click();
    }

    @When("get text max temperature C")
    public void getMaxTempC (){
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
          WebElement locatorMaxTempC = driver.findElement(By.xpath("html/body/div[5]/div[2]/div[1]/div[3]/table/tbody/tr[9]/td[1]/span"));
          String maxTempC = locatorMaxTempC.getText();
           log.info("Max temp is "+maxTempC);

           maxTempCelsius = Double.parseDouble(maxTempC);
    }

    @When("get text min temperature C")
    public void getMinTempC (){
        WebElement locatorMinTempC = driver.findElement(By.xpath("html/body/div[5]/div[2]/div[1]/div[3]/table/tbody/tr[10]/td[1]/span"));
        String minTempC = locatorMinTempC.getText();
         log.info("Max temp is "+minTempC);

        minTempCelsius = Double.parseDouble(minTempC);
    }

    @Then ("compare min and max C")
    public void compareMinMax (){
        if(minTempCelsius!=null&& maxTempCelsius !=null){
            Double result  = minTempCelsius / maxTempCelsius;
            Assert.assertTrue("The difference between min and max temperature is more than 10%", result<1);
        }
    }

    @When("chose temperature F")
    public void choseTemperatureF (){
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement locatorTemperatureF = driver.findElement(By.cssSelector(".units.imperial"));
        locatorTemperatureF.click();
    }

    @Then ("get text max temperature F")
    public void getMaxTempF () {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement locatorMaxTempF = driver.findElement(By.xpath("html/body/div[5]/div[2]/div[1]/div[3]/table/tbody/tr[9]/td[1]/span"));
        String maxTempF = locatorMaxTempF.getText();
        log.info("Max temp is "+maxTempF);

        maxTempfarengeyt = Double.parseDouble(maxTempF);
    }

    @When("get text min temperature F")
    public void getMinTempF (){
        WebElement locatorMinTempF = driver.findElement(By.xpath("html/body/div[5]/div[2]/div[1]/div[3]/table/tbody/tr[10]/td[1]/span"));
        String minTempF = locatorMinTempF.getText();
        log.info("Mix temp is "+minTempF);

        minTempfarengeyt = Double.parseDouble(minTempF);
    }

    @Then ("compare min and max F")
    public void compareMinMaxF (){
        if(minTempfarengeyt!=null&& maxTempfarengeyt !=null){
            Double result  = minTempfarengeyt / maxTempfarengeyt;
            Assert.assertTrue("The difference between min and max temperature is more than 10%", result<1);
        }
    }

    @AfterStory
    public void closeBrowser (){
        driver.quit();
    }
}
