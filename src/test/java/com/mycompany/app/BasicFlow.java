package com.mycompany.app.test;

import org.testng.annotations.BeforeTest;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mycompany.app.dataprovider.NonCatalogRequestDataProvider;
import com.mycompany.app.pages.LeftMenu;
import com.mycompany.app.pages.LoginPage;
import com.mycompany.app.pages.ProcurementShopPage;
import com.mycompany.app.resources.Constants;
import com.mycompany.app.workflows.BrowserLeftMenu;
import com.mycompany.app.workflows.NonCatalogRequestPageActions;

public class BasicFlow {
	private	WebDriver driver;
	private String URL = "http://infoebs02.infolob.com:8000";
	private LoginPage lp;
	private LeftMenu lm ;
	private BrowserLeftMenu blm;
	private NonCatalogRequestPageActions ncrpAction;
	private ProcurementShopPage psp;
	@BeforeSuite
	public void setUp(){

		//System.setProperty("webdriver.chrome.driver","C:\\Users\\aseem\\Documents\\Chrome drivers\\Chrome 2.35\\chromedriver_win32 (1)\\chromedriver.exe");
		//driver = new ChromeDriver();
		try {

				//DesiredCapabilities dc = DesiredCapabilities.chrome();
				//dc.setCapability(ChromeOptions.CAPABILITY, options);
				//dc.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
				//dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

				ChromeOptions options = new ChromeOptions();
				options.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
				options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
				options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
				options.addArguments("disable-popup-blocking");

				//options.merge(dc);

		    driver = new RemoteWebDriver(new URL("http://192.168.7.121:4444/wd/hub"), options);
				//driver.manage().window().maximize() ;
				driver.get(URL);
		    //more code goes here
		} catch(MalformedURLException ex){
		//do exception handling here
		}
	}
	@Test
	@Parameters({"username","password"})
	public void Test001(String username,String password) throws InterruptedException {
		lp = new LoginPage(driver);
		lp.login(username, password);
		//	  System.out.println("In test Test001");
		lm = new LeftMenu(driver);
		blm = new  BrowserLeftMenu(lm);
		blm.navigateToItem(Constants.IPROCUREMENT);
		psp = new ProcurementShopPage(driver);
		psp.setNonCatalogRequest();
		ncrpAction = new NonCatalogRequestPageActions(driver,NonCatalogRequestDataProvider.getNCRObject());
		ncrpAction.performAction("input data");
		ncrpAction.performAction("addToCart");
		ncrpAction.performAction("view cart");
		ncrpAction.performAction("submit");

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	  assertTrue(lm.isLeftMenuPresent(), "Could not find left menu");
	}

	@AfterTest  					 void tearDown()
	{
		driver.quit();
	}

}
