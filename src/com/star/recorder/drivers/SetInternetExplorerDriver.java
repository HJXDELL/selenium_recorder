package com.star.recorder.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class SetInternetExplorerDriver implements DriverStartUp{

	private WebDriver driver;

	/**
	 * Description: config the executable exe file of IEDriverServer.exe</BR>
	 */
	private void iExploreDriverArtiSet() {
		if (System.getProperty("os.arch").equalsIgnoreCase("x86")) {
			System.setProperty("webdriver.ie.driver", "./lib/IEDriverServer_X86.exe");
		} else {
			System.setProperty("webdriver.ie.driver", "./lib/IEDriverServer_X64.exe");
		}
	}

	/**
	 * initialize  and return webdirver.
	 * @return WebDriver
	 */
	@Override
	public WebDriver getDriver() {
		iExploreDriverArtiSet();
		driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		return driver;
	}
}
