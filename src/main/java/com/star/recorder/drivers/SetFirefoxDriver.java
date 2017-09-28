package com.star.recorder.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class SetFirefoxDriver implements DriverStartUp{

	private WebDriver driver;

	/**
	 * configurate the firefox profiles.
	 */
	private void firefoxProfileSet() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("webdriver.load.strategy", "unstable");
	}

	/**
	 * initialize  and return webdirver.
	 * @return WebDriver
	 */
	@Override
	public WebDriver getDriver() {
		firefoxProfileSet();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		return driver;
	}
}
