package com.star.recorder.test;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import com.star.bot.apis.WebDriverBotApis;
import com.star.recorder.tools.LoggingManager;

public class RunCurrentStep {
	private final LoggingManager LOG = new LoggingManager(this.getClass().getName());
	private JComboBox<?> operator;
	private JComboBox<?> findBy;
	private JTextField id;
	private JTextField name;
	private JTextField xpath;
	private JTextField className;
	private JTextField text;
	private JTextField dataValue;
	private JTextField currentWindow;
	private WebDriver driver;
	private By by;

	public void setFinder(JComboBox<?> findBy) {
		String byWay = findBy.getSelectedItem().toString();
		if (byWay.equalsIgnoreCase("id")) {
			by = By.id(id.getText());
		} else if (byWay.equalsIgnoreCase("name")) {
			by = By.name(name.getText());
		} else if (byWay.equalsIgnoreCase("xpath")) {
			by = By.xpath(xpath.getText());
		} else if (byWay.equalsIgnoreCase("className")) {
			by = By.className(className.getText());
		} else if (byWay.equalsIgnoreCase("text")) {
			by = By.linkText(text.getText());
		} else if (byWay.equalsIgnoreCase("cssSelector")) {
			by = By.cssSelector(xpath.getText());
		} else {
			by = By.xpath(xpath.getText());
		}
		this.setBy(by);
	}

	public void runCurrentStep() {
		setFinder(findBy);
		String opt = operator.getSelectedItem().toString();
		try {
			if (opt.equalsIgnoreCase("sendKeys")) {
				driver.findElement(this.by).clear();
				driver.findElement(this.by).sendKeys(dataValue.getText());
			} else if (opt.equalsIgnoreCase("sendKeysAppend")) {
				driver.findElement(this.by).sendKeys(dataValue.getText());
			} else if (opt.equalsIgnoreCase("selectByIndex")) {
				new Select(driver.findElement(this.by)).selectByIndex(Integer.parseInt(dataValue.getText()));
			} else if (opt.equalsIgnoreCase("selectByValue")) {
				new Select(driver.findElement(this.by)).selectByValue(dataValue.getText());
			} else if (opt.equalsIgnoreCase("selectByVisibleText")) {
				new Select(driver.findElement(this.by)).selectByVisibleText(dataValue.getText());
			} else if (opt.contains("click")) {
				driver.findElement(this.by).click();
			} else if (opt.equalsIgnoreCase("selectWindow")) {
				new WebDriverBotApis(driver).selectWindow(currentWindow.getText());
			} else if (opt.equalsIgnoreCase("chooseOKOnAlert")) {
				driver.switchTo().alert().accept();
			} else if (opt.equalsIgnoreCase("chooseCancelOnAlert")) {
				driver.switchTo().alert().dismiss();
			} else if (opt.equalsIgnoreCase("selectFrame")) {
				driver.switchTo().frame(driver.findElement(this.by));
			} else if (opt.equalsIgnoreCase("selectDefaultContent")) {
				driver.switchTo().defaultContent();
			} else if (opt.equalsIgnoreCase("selectExistWindow")) {
				driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
			}
		} catch (WebDriverException we) {
			JOptionPane.showMessageDialog(new JPanel(), "WebDriver Run Failed, Please Check The Log!");
			LOG.error(we);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public By getBy() {
		return this.by;
	}

	public void setBy(By by) {
		this.by = by;
	}

	public void setOperator(JComboBox<?> operator) {
		this.operator = operator;
	}

	public void setFindBy(JComboBox<?> findBy) {
		this.findBy = findBy;
	}

	public void setId(JTextField id) {
		this.id = id;
	}

	public void setName(JTextField name) {
		this.name = name;
	}

	public void setXpath(JTextField xpath) {
		this.xpath = xpath;
	}

	public void setClassName(JTextField className) {
		this.className = className;
	}

	public void setText(JTextField text) {
		this.text = text;
	}

	public void setDataValue(JTextField dataValue) {
		this.dataValue = dataValue;
	}

	public void setCurrentWindow(JTextField currentWindow) {
		this.currentWindow = currentWindow;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
