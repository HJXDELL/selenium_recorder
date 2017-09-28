package com.star.recorder.drivers;

import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SetChromeDriver implements DriverStartUp{

	private WebDriver driver;
	private final String chromeExe = "\\Google\\Chrome\\Application\\chrome.exe";

	/**
	 * Description: config the executable exe file of chromedriver.exe</BR>
	 */
	private void chromeDriverSet() {
		String _installed_p1 = System.getenv("LOCALAPPDATA");
		String _installed_p2 = System.getenv("ProgramFiles");
		String _installed_p3 = System.getenv("ProgramFiles(x86)");
		
		if (new File(_installed_p1 + chromeExe).exists()) {
			System.setProperty("webdriver.chrome.bin", _installed_p1 + chromeExe);
		} else if (new File(_installed_p2 + chromeExe).exists()) {
			System.setProperty("webdriver.chrome.bin", _installed_p2 + chromeExe);
		} else if (new File(_installed_p3 + chromeExe).exists()) {
			System.setProperty("webdriver.chrome.bin", _installed_p3 + chromeExe);
		} else {
			throw new RuntimeException("Chrome was not installed correctly!");
		}

		System.setProperty("webdriver.chrome.driver", "./exec/chromedriver.exe");
	}

	/**
	 * initialize  and return webdirver.
	 * @return WebDriver
	 */
	@Override
	public WebDriver getDriver() {
		chromeDriverSet();

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
}