package com.star.recorder.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import com.star.bot.apis.WebDriverBotApis;
import com.star.recorder.forms.DrawRecordPanel;
import com.star.recorder.tools.LoggingManager;

public class RunCurrentStep {
	private final LoggingManager LOG = new LoggingManager(this.getClass().getName());
	private final String splitChar = " ~ ";
	private WebDriverBotApis bot;
	private JComboBox operator;
	private JComboBox findBy;
	private JTextField id;
	private JTextField name;
	private JTextField xpath;
	private JTextField className;
	private JTextField text;
	private JTextField dataValue;
	private JComboBox windows;
	private WebDriver driver;
	private By by;

	public void setFinder(JComboBox findBy) {
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
				driver.switchTo().window(windows.getSelectedItem().toString().split(splitChar)[2]);
			} else if (opt.equalsIgnoreCase("closeWindow")) {
				driver.close();
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

	public void setOperator(JComboBox operator) {
		this.operator = operator;
	}

	public void setFindBy(JComboBox findBy) {
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

	public void setWindows(JComboBox windows) {
		this.windows = windows;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
		this.bot = new WebDriverBotApis(driver);
	}
	
	public void setWindows(DrawRecordPanel record){		
		ArrayList<String> items = new ArrayList<String>();
		String defaultHandle = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		handles = bot.clearHandleCache(handles);
		Iterator<String> it = handles.iterator();
		int i = 1;
		while(it.hasNext()){
			String handle = it.next();
			driver.switchTo().window(handle);
			items.add(i + splitChar + driver.getTitle() + splitChar + handle);
			i ++;
		}
		driver.switchTo().window(defaultHandle);
		record.setWindows(items, defaultHandle);
	}
	
	public void maximizeAllWindows(){
		String defaultHandle = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		handles = bot.clearHandleCache(handles);
		Iterator<String> it = handles.iterator();
		while(it.hasNext()){
			String handle = it.next();
			driver.switchTo().window(handle);
			try{
				driver.manage().window().maximize();
			}catch (Exception ex){
				JOptionPane.showMessageDialog(new JPanel(), "Current Window Can Not Be Maximized!");
			}
		}
		driver.switchTo().window(defaultHandle);		
	}
}
