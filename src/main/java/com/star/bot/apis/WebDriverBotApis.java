package com.star.bot.apis;

/**  
 * @author 测试仔刘毅
 */

import static org.testng.AssertJUnit.assertTrue;

import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WebDriverBotApis {
	private WebDriver driver;
	Actions actionDriver;
	private final int maxWaitfor = 30;
	private final int maxLoadTime = 90;
	private final Long stepTimeUnit = Long.valueOf(500);

	public WebDriverBotApis(WebDriver webdriver) {
		this.driver = webdriver;
		actionDriver = new Actions(driver);
	}

	/**
	 * execute js functions to do something</BR> 使用remote webdriver执行JS函数。
	 * 
	 * @param js
	 *            js function string
	 * @param report
	 *            text content to be reported
	 * @param args
	 *            js execute parameters
	 */
	public void jsExecutor(String js, String report, Object args) {
		((JavascriptExecutor) driver).executeScript(js, args);
	}

	/**
	 * execute js functions to do something</BR> 使用remote webdriver执行JS函数。
	 * 
	 * @param js
	 *            js function string
	 * @param report
	 *            text content to be reported
	 */
	public void jsExecutor(String js, String report) {
		((JavascriptExecutor) driver).executeScript(js);
	}

	/**
	 * get some value from js functions.</BR> 使用remote webdriver执行JS函数并且获得返回值。
	 * 
	 * @param js
	 *            js function string
	 */
	public Object jsReturner(String js) {
		return ((JavascriptExecutor) driver).executeScript(js);
	}

	/**
	 * judge if the alert is existing</BR> 判断弹出的对话框（Dialog）是否存在。
	 */
	public boolean alertExists() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ne) {
			return false;
		}
	}

	/**
	 * judge if the alert is present in specified seconds</BR>
	 * 在指定的时间内判断弹出的对话框（Dialog）是否存在。
	 * 
	 * @param seconds
	 *            timeout in seconds
	 */
	public boolean alertExists(int seconds) {
		long start = System.currentTimeMillis();
		while ((System.currentTimeMillis() - start) < seconds * 1000) {
			try {
				driver.switchTo().alert();
				return true;
			} catch (NoAlertPresentException ne) {
			}
		}
		return false;
	}

	/**
	 * judge if the element is existing</BR> 判断指定的对象是否存在。
	 * 
	 * @param by
	 *            the element locator By
	 */
	public boolean elementExists(By by) {
		try {
			return (findElements(by).size() > 0) ? true : false;
		} catch (NoSuchElementException ne) {
			return false;
		}
	}

	/**
	 * judge if the element is present in specified seconds</BR>
	 * 在指定的时间内判断指定的对象是否存在。
	 * 
	 * @param by
	 *            the element locator By
	 * @param seconds
	 *            timeout in seconds
	 */
	public boolean elementExists(By by, int seconds) {
		long start = System.currentTimeMillis();
		boolean exists = false;
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		while (!exists && ((System.currentTimeMillis() - start) < seconds * 1000)) {
			exists = findElements(by).size() > 0;
		}
		driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
		return exists;
	}

	/**
	 * judge if the browser is existing, using part of the page title</BR>
	 * 按照网页标题判断页面是否存在，标题可使用部分内容匹配。
	 * 
	 * @param browserTitle
	 *            part of the title to see if browser exists
	 */
	public boolean browserExists(String browserTitle) {
		try {
			String defaultHandle = driver.getWindowHandle();
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (int i = 0; i <= 20; i++) {
				if (driver.getWindowHandles().equals(windowHandles)) {
					break;
				}
				if (i == 20 && !driver.getWindowHandles().equals(windowHandles)) {
					return false;
				}
			}
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
				if (driver.getTitle().contains(browserTitle)) {
					driver.switchTo().window(defaultHandle);
					return true;
				}
			}
			driver.switchTo().window(defaultHandle);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * refresh current browser page by url re-navigate</BR>
	 * 通过当前页面URL跳转的方式重新加载当前页面。
	 */
	public void browserRefresh() {
		driver.navigate().to(driver.getCurrentUrl());
	}

	/**
	 * judge if the browser is present by title reg pattern in specified
	 * seconds</BR> 在指定时间内按照网页标题判断页面是否存在，标题可使用部分内容匹配。
	 * 
	 * @param browserTitle
	 *            part of the title to see if browser exists
	 * @param seconds
	 *            timeout in seconds
	 */
	public boolean browserExists(String browserTitle, int seconds) {
		long start = System.currentTimeMillis();
		boolean isExist = false;
		while (!isExist && (System.currentTimeMillis() - start) < seconds * 1000) {
			isExist = browserExists(browserTitle);
		}
		return isExist;
	}

	/**
	 * maximize browser window: support ie, ff3.6 and lower</BR> 网页窗口最大化操作。
	 */
	public void maximizeWindow() {
		windowMaximize();
		// jsExecutor(JScriptCollection.MAXIMIZE_WINDOW.getValue(),
		// "current window maximized");
	}

	/**
	 * maximize browser window</BR> 网页窗口最大化操作。
	 */
	public void windowMaximize() {
		driver.manage().window().maximize();
	}

	/**
	 * select default window and default frame</BR> 在当前页面中自动选择默认的页面框架（frame）。
	 */
	public void selectDefaultWindowFrame() {
		driver.switchTo().defaultContent();
	}

	/**
	 * switch to active element</BR> 在当前操作的页面和对象时自动选择已被激活的对象。
	 */
	public void focusOnActiveElement() {
		driver.switchTo().activeElement();
	}

	/**
	 * switch to new window supporting, by deleting first hanlder</BR>
	 * 选择最新弹出的窗口，需要预存第一个窗口的handle。
	 * 
	 * @param firstHandle
	 *            the first window handle
	 */
	public void selectNewWindow(String firstHandle) {
		Set<String> handles = null;
		Iterator<String> it = null;
		handles = driver.getWindowHandles();
		handles = driver.getWindowHandles();
		handles.remove(firstHandle);
		it = handles.iterator();
		while (it.hasNext()) {
			driver.switchTo().window(it.next());
		}
		driver.switchTo().defaultContent();
	}

	/**
	 * switch to new window supporting, by deleting original hanlde</BR>
	 * 选择最新弹出的窗口，需要预存所有已有窗口的handles。
	 * 
	 * @param originalHandles
	 *            the old window handles
	 */
	public void selectNewWindow(Set<String> originalHandles) {
		Set<String> newHandles = driver.getWindowHandles();
		Iterator<String> olds = originalHandles.iterator();
		while (olds.hasNext()) {
			newHandles.remove(olds.next());
		}
		Iterator<String> news = newHandles.iterator();
		while (news.hasNext()) {
			driver.switchTo().window(news.next());
		}
		driver.switchTo().defaultContent();
	}

	/**
	 * switch to window by title</BR> 按照网页标题选择窗口，标题内容需要全部匹配。
	 * 
	 * @param windowTitle
	 *            the title of the window to be switched to
	 */
	public void selectWindow(String windowTitle) {
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles = clearHandleCache(windowHandles);
		for (String handle : windowHandles) {
			driver.switchTo().window(handle);
			String title = driver.getTitle();
			if (windowTitle.equals(title)) {
				driver.switchTo().defaultContent();
				return;
			}
		}
	}

	/**
	 * switch to window by title</BR> 按照网页标题选择窗口，标题内容需要全部匹配，超时未出现则报错。
	 * 
	 * @param windowTitle
	 *            the title of the window to be switched to.
	 * @param timeout
	 *            time to wait for the window appears, unit of seconds.
	 */
	public void selectWindowWithTimeout(String windowTitle, int timeout) {
		assertTrue("window is not present after " + timeout + "seconds!", browserExists(windowTitle, timeout));
		selectWindow(windowTitle);
	}

	/**
	 * switch to parent window when child was closed unexpectly.</BR>
	 * 在打开的子窗口被意外（被动、非工具预期的行为）关闭之后，切换回父窗口。
	 * 
	 * @param handles
	 *            handles set when child windows are still alive.
	 * @param childHandle
	 *            child window whitch to be closed.
	 * @param parentHandle
	 *            the parent handle of windows.
	 */
	public void selectParentWindow(Set<String> handles, String childHandle, String parentHandle) {
		if (!handles.toString().contains(childHandle) || !handles.toString().contains(parentHandle)) {
			throw new IllegalArgumentException("you are using the wrong parameters!");
		}
		handles.remove(childHandle);
		driver.switchTo().window(parentHandle);
		waitForAlertDisappear(5);
	}

	/**
	 * Description: switch to a window handle that exists now.</BR>
	 * 切换到一个存在句柄（或者说当前还存在的）的窗口。
	 */
	public void selectExistWindow() {
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles = clearHandleCache(windowHandles);
		String exist_0 = windowHandles.toArray()[0].toString();
		driver.switchTo().window(exist_0);
	}

	/**
	 * close window by window title and its index if has the same title, by
	 * string full pattern</BR> 按照网页标题选择并且关闭窗口，重名窗口按照指定的重名的序号关闭，标题内容需要全部匹配。
	 * 
	 * @param windowTitle
	 *            the title of the window to be closed.
	 * @param index
	 *            the index of the window which shared the same title, begins
	 *            with 1.
	 */
	public void closeWindow(String windowTitle, int index) {
		List<String> winList = new ArrayList<String>();
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles = clearHandleCache(windowHandles);
		for (String handle : windowHandles) {
			driver.switchTo().window(handle);
			if (windowTitle.equals(driver.getTitle())) {
				winList.add(handle);
			}
		}
		driver.switchTo().window(winList.get(index - 1));
		driver.switchTo().defaultContent();
		driver.close();
		selectExistWindow();
	}

	/**
	 * close the last window by the same window title, by string full
	 * pattern</BR> 按照网页标题选择窗口，适用于无重名的窗口，标题内容需要全部匹配。
	 * 
	 * @param windowTitle
	 *            the title of the window to be closed.
	 */
	public void closeWindow(String windowTitle) {
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles = clearHandleCache(windowHandles);
		for (String handle : windowHandles) {
			driver.switchTo().window(handle);
			if (windowTitle.equals(driver.getTitle())) {
				driver.switchTo().defaultContent();
				driver.close();
				break;
			}
		}
		selectExistWindow();
	}

	/**
	 * close windows except specified window title, by string full pattern</BR>
	 * 关闭除了指定标题页面之外的所有窗口，适用于例外窗口无重名的情况，标题内容需要全部匹配。
	 * 
	 * @param windowTitle
	 *            the title of the window not to be closed
	 */
	public void closeWindowExcept(String windowTitle) {
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles = clearHandleCache(windowHandles);
		for (String handle : windowHandles) {
			driver.switchTo().window(handle);
			String title = driver.getTitle();
			if (!windowTitle.equals(title)) {
				driver.switchTo().defaultContent();
				driver.close();
			}
		}
		selectExistWindow();
	}

	/**
	 * close windows except specified window hanlde, by string full pattern</BR>
	 * 关闭除了指定句柄之外的所有窗口。
	 * 
	 * @param windowHandle
	 *            the hanlde of the window not to be closed.
	 */
	public void closeWindowExceptHandle(String windowHandle) {
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles = clearHandleCache(windowHandles);
		for (String handle : windowHandles) {
			if (!windowHandle.equals(handle)) {
				driver.switchTo().window(handle);
				driver.switchTo().defaultContent();
				driver.close();
			}
		}
		driver.switchTo().window(windowHandle);
	}

	/**
	 * Description: clear error handles does not actruely.</BR>
	 * 清理掉实际上并不存在的窗口句柄缓存。
	 * 
	 * @param windowHandles
	 *            the window handles Set.
	 */
	public Set<String> clearHandleCache(Set<String> windowHandles) {
		List<String> errors = new ArrayList<String>();
		for (String handle : windowHandles) {
			try {
				driver.switchTo().window(handle);
				driver.getTitle();
			} catch (Exception e) {
				errors.add(handle);
			}
		}
		for (int i = 0; i < errors.size(); i++) {
			windowHandles.remove(errors.get(i));
		}
		return windowHandles;
	}

	/**
	 * close windows except specified window title, by string full pattern</BR>
	 * 关闭除了指定标题页面之外的所有窗口，例外窗口如果重名，按照指定的重名顺序关闭，标题内容需要全部匹配。
	 * 
	 * @param windowTitle
	 *            the title of the window not to be closed
	 * @param index
	 *            the index of the window to keep shared the same title with
	 *            others, begins with 1.
	 */
	public void closeWindowExcept(String windowTitle, int index) {
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles = clearHandleCache(windowHandles);
		for (String handle : windowHandles) {
			driver.switchTo().window(handle);
			String title = driver.getTitle();
			if (!windowTitle.equals(title)) {
				driver.switchTo().defaultContent();
				driver.close();
			}
		}

		Object[] winArray = driver.getWindowHandles().toArray();
		winArray = driver.getWindowHandles().toArray();
		for (int i = 0; i < winArray.length; i++) {
			if (i + 1 != index) {
				driver.switchTo().defaultContent();
				driver.close();
			}
		}
		selectExistWindow();
	}

	/**
	 * wait for new window which has no title in few seconds</BR>
	 * 判断在指定的时间内是否有新的窗口弹出，无论其是否有标题。
	 * 
	 * @param browserCount
	 *            windows count before new window appears.
	 * @param seconds
	 *            time unit in seconds.
	 */
	public boolean isNewWindowExits(int browserCount, int seconds) {
		Set<String> windowHandles = null;
		boolean isExist = false;
		long begins = System.currentTimeMillis();
		while ((System.currentTimeMillis() - begins < seconds * 1000) && !isExist) {
			windowHandles = driver.getWindowHandles();
			windowHandles = driver.getWindowHandles();
			isExist = (windowHandles.size() > browserCount) ? true : false;
		}
		return isExist;
	}

	/**
	 * wait for new window which has no title in few seconds</BR>
	 * 判断在指定的时间内是否有新的窗口弹出，无论其是否有标题。
	 * 
	 * @param oldHandles
	 *            windows handle Set before new window appears.
	 * @param seconds
	 *            time unit in seconds.
	 */
	public boolean isNewWindowExits(Set<String> oldHandles, int seconds) {
		boolean isExist = false;
		Set<String> windowHandles = null;
		long begins = System.currentTimeMillis();
		while ((System.currentTimeMillis() - begins < seconds * 1000) && !isExist) {
			windowHandles = driver.getWindowHandles();
			windowHandles = driver.getWindowHandles();
			isExist = (windowHandles.size() > oldHandles.size()) ? true : false;
		}
		return isExist;
	}

	/**
	 * select a frame by index</BR> 按照序号选择框架（frame）。
	 * 
	 * @param index
	 *            the index of the frame to select
	 */
	public void selectFrame(int index) {
		driver.switchTo().frame(index);
	}

	/**
	 * select a frame by name or id</BR> 按照名称或者ID选择框架（frame）。
	 * 
	 * @param nameOrId
	 *            the name or id of the frame to select
	 */
	public void selectFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	/**
	 * select a frame by frameElement</BR> 按照框架对象本身选择框架（frame）。
	 * 
	 * @param frameElement
	 *            the frame element to select
	 */
	public void selectFrame(WebElement frameElement) {
		driver.switchTo().frame(frameElement);
	}

	/**
	 * select a frame by frame element locator: By</BR> 按照指定的元素定位方式选择框架（frame）。
	 * 
	 * @param by
	 *            the frame element locator
	 */
	public void selectFrame(By by) {
		driver.switchTo().frame(findElement(by));
	}

	/**
	 * select a frame by name or id, throw exception when timeout.</BR>
	 * 按照名称或者ID选择框架（frame），在指定时间内frame不存在则报错。
	 * 
	 * @param nameOrId
	 *            the name or id of the frame to select.
	 * @param timeout
	 *            time to wait for the frame available, unit of seconds.
	 */
	public void selectFrameWithTimeout(String nameOrId, int timeout) {
		waitForAndSwitchToFrame(nameOrId, timeout);
	}

	/**
	 * select a frame by frame element locator: By.</BR>
	 * 按照指定的元素定位方式选择框架（frame），在指定时间内frame不存在则报错。
	 * 
	 * @param by
	 *            the frame element locator.
	 * @param timeout
	 *            time to wait for the frame available, unit of seconds.
	 */
	public void selectFrameWithTimeout(By by, int timeout) {
		waitForElementPresent(by, timeout);
		driver.switchTo().frame(findElement(by));
	}

	/**
	 * edit a content editable iframe</BR> 编辑指定框架（frame）内的最直接展示文本内容。
	 * 
	 * @param by
	 *            the frame element locaotr
	 * @param text
	 *            the text string to be input
	 */
	public void editFrameText(By by, String text) {
		driver.switchTo().frame(findElement(by));
		driver.switchTo().activeElement().sendKeys(text);
	}

	/**
	 * rewrite the get method, adding user defined log</BR>
	 * 地址跳转方法，使用WebDriver原生get方法，加入失败重试的次数定义。
	 * 
	 * @param url
	 *            the url you want to open.
	 * @param actionCount
	 *            retry times when load timeout occuers.
	 */
	public void get(String url, int actionCount) {
		for (int i = 0; i < actionCount; i++) {
			if (i == 0) {
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			}
			try {
				driver.get(url);
				return;
			} catch (TimeoutException e) {
			} finally {
				driver.manage().timeouts().pageLoadTimeout(maxLoadTime, TimeUnit.SECONDS);
			}
		}
	}

	/**
	 * rewrite the get method, adding user defined log</BR>
	 * 地址跳转方法，使用WebDriver原生get方法，默认加载超重试【1】次。
	 * 
	 * @param url
	 *            the url you want to open.
	 */
	public void get(String url) {
		get(url, 2);
	}

	/**
	 * navigate to some where by url</BR>
	 * 地址跳转方法，与WebDriver原生navigate.to方法内容完全一致。
	 * 
	 * @param url
	 *            the url you want to open
	 */
	public void navigateTo(String url) {
		driver.navigate().to(url);
	}

	/**
	 * navigate back</BR> 地址跳转方法，与WebDriver原生navigate.back方法内容完全一致。
	 * 
	 * @throws RuntimeException
	 */
	public void navigateBack() {
		driver.navigate().back();
	}

	/**
	 * navigate forward</BR> 地址跳转方法，与WebDriver原生navigate.forward方法内容完全一致。
	 */
	public void navigateForward() {
		driver.navigate().forward();
	}

	/**
	 * rewrite the click method, adding user defined log</BR> 在等到对象可见之后点击指定的对象。
	 * 
	 * @param element
	 *            the WebElement you want to click.
	 */
	public void click(WebElement element) {
		waitForElementVisible(element, maxWaitfor);
		element.click();
	}

	/**
	 * rewrite the click method, click on the element to be find by By</BR>
	 * 在等到对象可见之后点击指定的对象。
	 * 
	 * @param by
	 *            the locator you want to find the element.
	 */
	public void click(By by) {
		waitForElementVisible(by, maxWaitfor);
		findElement(by).click();
	}

	/**
	 * forcely click, by executing javascript</BR>
	 * 在等到对象可见之后点击指定的对象，使用JavaScript执行的方式去操作，</BR>
	 * 这种方法使用过后一般需要调用一次selectDefaultWindowFrame以确保运行稳定。
	 * 
	 * @param element
	 *            the webelement you want to operate
	 */
	public void clickByJavaScript(WebElement element) {
		waitForElementVisible(element, maxWaitfor);
		jsExecutor(JScriptCollection.CLICK_BY_JAVASCRIPT.getValue(), "click on element", element);
	}

	/**
	 * forcely click, by executing javascript</BR>
	 * 在等到对象可见之后点击指定的对象，使用JavaScript执行的方式去操作，</BR>
	 * 这种方法使用过后一般需要调用一次selectDefaultWindowFrame以确保运行稳定。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void clickByJavaScript(By by) {
		waitForElementVisible(by, maxWaitfor);
		jsExecutor(JScriptCollection.CLICK_BY_JAVASCRIPT.getValue(), "click on element [ " + by.toString()
				+ " ] ", findElement(by));
	}

	/**
	 * doubleclick on the element</BR> 在等到对象可见之后双击指定的对象.
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void doubleClick(By by) {
		waitForElementVisible(by, maxWaitfor);
		actionDriver.doubleClick(findElement(by));
		actionDriver.perform();
	}

	/**
	 * right click on the element to be find by By</BR> 在等到对象可见之后鼠标右键点击指定的对象.
	 * 
	 * @param element
	 *            the webelement you want to operate
	 */
	public void rightClick(WebElement element) {
		waitForElementVisible(element, maxWaitfor);
		actionDriver.contextClick(element);
		actionDriver.perform();
	}

	/**
	 * right click on the element</BR> 在等到对象可见之后鼠标右键点击指定的对象。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void rightClick(By by) {
		waitForElementVisible(by, maxWaitfor);
		actionDriver.contextClick(findElement(by));
		actionDriver.perform();
	}

	/**
	 * rewrite the submit method, adding user defined log</BR>
	 * 在等到指定对象可见之后在该对象上做确认/提交的操作。
	 * 
	 * @param element
	 *            the webelement you want to operate
	 */
	public void submit(WebElement element) {
		waitForElementVisible(element, maxWaitfor);
		element.submit();
	}

	/**
	 * rewrite the submit method, submit on the element to be find by By</BR>
	 * 在等到指定对象可见之后在该对象上做确认/提交的操作。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void submit(By by) {
		waitForElementVisible(by, maxWaitfor);
		findElement(by).submit();
	}

	/**
	 * rewrite the clear method, adding user defined log</BR>
	 * 在等到指定对象可见之后在该对象上做清理操作，一般用于输入框和选择框。
	 * 
	 * @param element
	 *            the webelement you want to operate
	 */
	public void clear(WebElement element) {
		waitForElementVisible(element, maxWaitfor);
		element.clear();
	}

	/**
	 * rewrite the clear method, clear on the element to be find by By</BR>
	 * 在等到指定对象可见之后在该对象上做清理操作，一般用于输入框和选择框。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void clear(By by) {
		waitForElementVisible(by, maxWaitfor);
		findElement(by).clear();
	}

	/**
	 * rewrite the sendKeys method, adding user defined log</BR>
	 * 以追加文本的模式在指定可编辑对象中输入文本，操作之前自动等待到对象可见。
	 * 
	 * @param element
	 *            the webelement you want to operate
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeysAppend(WebElement element, String text) {
		waitForElementVisible(element, maxWaitfor);
		element.sendKeys(text);
	}

	/**
	 * rewrite the sendKeys method, sendKeys on the element to be find by
	 * By</BR> 以追加文本的模式在指定可编辑对象中输入文本，操作之前自动等待到对象可见。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeysAppend(By by, String text) {
		waitForElementVisible(by, maxWaitfor);
		findElement(by).sendKeys(text);
	}

	/**
	 * rewrite the sendKeys method, adding user defined log</BR>
	 * 清理指定对象中已经输入的内容重新输入，操作之前自动等待到对象可见。
	 * 
	 * @param element
	 *            the webelement you want to operate
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeys(WebElement element, String text) {
		waitForElementVisible(element, maxWaitfor);
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * rewrite the sendKeys method, sendKeys on the element to be find by
	 * By</BR> 清理指定对象中已经输入的内容重新输入，操作之前自动等待到对象可见。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeys(By by, String text) {
		waitForElementVisible(by, maxWaitfor);
		WebElement element = findElement(by);
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * readonly text box or richtext box input</BR> 使用DOM（Documnet Object
	 * Modal）修改页面中对象的文本属性值，使用ID定位对象则返回唯一对象，其余返回数组。
	 * 
	 * @param by
	 *            the attribute of the element, default support is
	 *            TagName/Name/Id
	 * @param byValue
	 *            the attribute value of the element
	 * @param text
	 *            the text you want to input to element
	 * @param index
	 *            the index of the elements shared the same attribute value
	 * @throws IllegalArgumentException
	 */
	public void sendKeysByDOM(String by, String byValue, String text, int index) {
		String js = null;

		if (by.equalsIgnoreCase("tagname")) {
			js = "document.getElementsByTagName('" + byValue + "')[" + index + "].value='" + text + "'";
		} else if (by.equalsIgnoreCase("name")) {
			js = "document.getElementsByName('" + byValue + "')[" + index + "].value='" + text + "'";
		} else if (by.equalsIgnoreCase("id")) {
			js = "document.getElementById('" + byValue + "').value='" + text + "'";
		} else {
			throw new IllegalArgumentException("only can find element by TagName/Name/Id");
		}

		jsExecutor(js, "input text [ " + text + " ] to element [ " + by + " ]");
	}

	/**
	 * readonly text box or richtext box input, finding elements by element
	 * id</BR> 按照ID定位页面中对象，并使用DOM（Documnet Object Modal）修改其文本属性值。
	 * 
	 * @param elementId
	 *            the id of the element
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeysById(String elementId, String text) {
		sendKeysByDOM("Id", elementId, text, 0);
	}

	/**
	 * readonly text box or richtext box input, finding elements by element
	 * name</BR> 按照名称（Name）和序号定位页面中对象，并使用DOM（Documnet Object Modal）修改其文本属性值。
	 * 
	 * @param elementName
	 *            the name of the element
	 * @param text
	 *            the text you want to input to element
	 * @param elementIndex
	 *            the index of the elements shared the same name, begins with 0
	 */
	public void sendKeysByName(String elementName, String text, int elementIndex) {
		sendKeysByDOM("Name", elementName, text, elementIndex);
	}

	/**
	 * readonly text box or richtext box input, finding elements by element tag
	 * name</BR> 按照标签名称（TagName）和序号定位页面中对象，并使用DOM（Documnet Object
	 * Modal）修改其文本属性值。
	 * 
	 * @param elementTagName
	 *            the tag name of the element
	 * @param text
	 *            the text you want to input to element
	 * @param elementIndex
	 *            the index of the elements shared the same tag name, begins
	 *            with 0
	 */
	public void sendKeysByTagName(String elementTagName, String text, int elementIndex) {
		sendKeysByDOM("TagName", elementTagName, text, elementIndex);
	}

	/**
	 * sendKeys by using keybord event on element</BR> 使用键盘模拟的方法在指定的对象上输入指定的文本。
	 * 
	 * @param element
	 *            the webelement you want to operate
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeysByKeybord(WebElement element, String text) {
		waitForElementVisible(element, maxWaitfor);
		actionDriver.sendKeys(element, text);
		actionDriver.perform();
	}

	/**
	 * sendKeys by using keybord event on element to be found by By</BR>
	 * 使用键盘模拟的方法在指定的对象上输入指定的文本。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeysByKeybord(By by, String text) {
		waitForElementVisible(by, maxWaitfor);
		actionDriver.sendKeys(findElement(by), text);
		actionDriver.perform();
	}

	/**
	 * edit rich text box created by kindeditor</BR>
	 * 使用JS调用KindEditor对象本身的接口，在页面KindEditor对象中输入指定的文本。
	 * 
	 * @param editorId
	 *            kindeditor id
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeysOnKindEditor(String editorId, String text) {
		String javascript = "KE.html('" + editorId + "','<p>" + text + "</p>');";
		jsExecutor(javascript, "input text [ " + text + " ] to kindeditor");
	}

	/**
	 * select an item from a picklist by index</BR> 按照指定序号选择下拉列表中的选项。
	 * 
	 * @param element
	 *            the picklist element
	 * @param index
	 *            the index of the item to be selected
	 */
	public void selectByIndex(WebElement element, int index) {
		waitForElementVisible(element, maxWaitfor);
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	/**
	 * select an item from a picklist by index</BR> 按照指定序号选择下拉列表中的选项。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param index
	 *            the index of the item to be selected
	 */
	public void selectByIndex(By by, int index) {
		waitForElementVisible(by, maxWaitfor);
		Select select = new Select(findElement(by));
		select.selectByIndex(index);
	}

	/**
	 * select an item from a picklist by item value</BR>
	 * 按照指定选项的实际值（不是可见文本值，而是对象的“value”属性的值）选择下拉列表中的选项。
	 * 
	 * @param element
	 *            the picklist element
	 * @param itemValue
	 *            the item value of the item to be selected
	 */
	public void selectByValue(WebElement element, String itemValue) {
		waitForElementVisible(element, maxWaitfor);
		Select select = new Select(element);
		select.selectByValue(itemValue);
	}

	/**
	 * select an item from a picklist by item value</BR>
	 * 按照指定选项的实际值（不是可见文本值，而是对象的“value”属性的值）选择下拉列表中的选项。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param itemValue
	 *            the item value of the item to be selected
	 */
	public void selectByValue(By by, String itemValue) {
		waitForElementVisible(by, maxWaitfor);
		Select select = new Select(findElement(by));
		select.selectByValue(itemValue);

	}

	/**
	 * select an item from a picklist by item value</BR>
	 * 按照指定选项的可见文本值（用户直接可以看到的文本）选择下拉列表中的选项。
	 * 
	 * @param element
	 *            the picklist element
	 * @param text
	 *            the item value of the item to be selected
	 */
	public void selectByVisibleText(WebElement element, String text) {
		waitForElementVisible(element, maxWaitfor);
		Select select = new Select(element);
		select.selectByVisibleText(text);

	}

	/**
	 * select an item from a picklist by item value</BR>
	 * 按照指定选项的可见文本值（用户直接可以看到的文本）选择下拉列表中的选项。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the item value of the item to be selected
	 */
	public void selectByVisibleText(By by, String text) {
		waitForElementVisible(by, maxWaitfor);
		Select select = new Select(findElement(by));
		select.selectByVisibleText(text);

	}

	/**
	 * set the checkbox on or off</BR> 将指定的复选框对象设置为选中或者不选中状态。
	 * 
	 * @param element
	 *            the checkbox element
	 * @param onOrOff
	 *            on or off to set the checkbox
	 */
	public void setCheckBox(WebElement element, String onOrOff) {
		waitForElementVisible(element, maxWaitfor);
		if ((onOrOff.toLowerCase().contains("on") && !element.isSelected())
				|| (onOrOff.toLowerCase().contains("off") && element.isSelected())) {
			element.click();
		}

	}

	/**
	 * set the checkbox on or off</BR> 将指定的复选框对象设置为选中或者不选中状态。
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param onOrOff
	 *            on or off to set the checkbox
	 */
	public void setCheckBox(By by, String onOrOff) {
		waitForElementVisible(by, maxWaitfor);
		WebElement checkBox = findElement(by);
		if ((onOrOff.toLowerCase().contains("on") && !checkBox.isSelected())
				|| (onOrOff.toLowerCase().contains("off") && checkBox.isSelected())) {
			checkBox.click();
		}

	}

	/**
	 * find elements displayed on the page</BR> 按照指定的定位方式寻找所有可见的对象。
	 * 
	 * @param by
	 *            the way to locate webelements
	 * @return displayed webelement list
	 */
	public List<WebElement> findDisplayedElments(By by) {
		List<WebElement> elementList = new ArrayList<WebElement>();
		WebElement element;
		List<WebElement> elements = driver.findElements(by);
		Iterator<WebElement> it = elements.iterator();
		while ((element = it.next()) != null && element.isDisplayed()) {
			elementList.add(element);
		}
		return elementList;
	}

	/**
	 * find elements displayed on the page</BR> 按照指定的定位方式寻找第一可见的对象。
	 * 
	 * @param by
	 *            the way to locate webelement
	 * @return the first displayed webelement
	 */
	public WebElement findDisplayedElment(By by) {
		List<WebElement> elements = findDisplayedElments(by);
		return (elements.size() > 0) ? elements.get(0) : null;
	}

	/**
	 * rewrite the findElements method, adding user defined log</BR>
	 * 按照指定的定位方式寻找象。
	 * 
	 * @param by
	 *            the locator of the elements to be find
	 * @return the webelements you want to find
	 */
	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	/**
	 * rewrite the findElement method, adding user defined log</BR>
	 * 按照指定的定位方式寻找象。
	 * 
	 * @param by
	 *            the locator of the element to be find
	 * @return the first element accord your locator
	 */
	public WebElement findElement(By by) {
		List<WebElement> elements = findElements(by);
		return (elements.size() > 0) ? (elements.get(0)) : null;
	}

	/**
	 * wait for window appears in the time unit seconds</BR>
	 * 在指定时间内等待窗口出现，超时则报错，用以缓冲运行，增加健壮性。
	 * 
	 * @param browserTitle
	 *            the title of the browser window.
	 * @param seconds
	 *            timeout in timeunit of seconds.
	 * @return if the window exists.
	 */
	public boolean waitForWindowPresent(String browserTitle, int seconds) {
		assertTrue("window is not present after " + seconds + " seconds!",
				browserExists(browserTitle, seconds));
		return true;
	}

	/**
	 * wait for window appears in the time unit seconds</BR>
	 * 在指定时间内等待新窗口出现，超时则报错，用以缓冲运行，增加健壮性。
	 */
	public boolean waitForNewWindowOpened(int oldCount, int seconds) {
		assertTrue("new window did not opened in " + seconds + " seconds!",
				isNewWindowExits(oldCount, seconds));
		return true;
	}

	/**
	 * wait for window appears in the time unit seconds</BR>
	 * 在指定时间内等待新窗口出现，超时则报错，用以缓冲运行，增加健壮性。
	 */
	public boolean waitForNewWindowOpened(Set<String> oldHandles, int seconds) {
		assertTrue("new window did not opened in " + seconds + " seconds!",
				isNewWindowExits(oldHandles, seconds));
		return true;
	}

	/**
	 * wait for and switch to frame when avilable in timeout setting</BR>
	 * 在指定时间内等待，直到指定框架出现并且选择他。
	 * 
	 * @param locator
	 *            the id or name of frames.
	 * @param seconds
	 *            timeout in seconds
	 */
	public boolean waitForAndSwitchToFrame(String locator, int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, seconds, seconds);
			return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator)) != null;
		} finally {
			driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
		}
	}

	/**
	 * use js to judge if the browser load completed. 用js返回值判断页面是否加载完毕。
	 * 
	 * @return load comploete or not.
	 */
	public boolean pageLoadSucceed() {
		Object loadCompleted = jsReturner(JScriptCollection.BROWSER_READY_STATUS.getValue());
		return loadCompleted.toString().toLowerCase().equals("complete");
	}

	/**
	 * use js to judge if the browser load completed.
	 * 用js返回值判断页面是否加载完毕，超时未加载完毕则报错。
	 * 
	 * @param timeout
	 *            max time used to load page.
	 */
	public boolean waitForPageToLoad(int timeout) {
		long start = System.currentTimeMillis();
		boolean loadCompleted = false;
		while (!loadCompleted && ((System.currentTimeMillis() - start) < timeout * 1000)) {
			loadCompleted = pageLoadSucceed();
		}
		assertTrue("the page did not load complete in " + timeout + " seconds!", loadCompleted);
		return true;
	}

	/**
	 * wait for alert appears in the time unit of seconds</BR>
	 * 在指定时间内等待，对话框（Dialog）出现，用以缓冲运行，增加健壮性。
	 */
	public boolean waitForAlertPresent(int seconds) {
		assertTrue("alert does not appear in " + seconds + " seconds!", alertExists(seconds));
		return true;
	}

	/**
	 * wait for alert disappears in the time unit of seconds</BR>
	 * 在指定时间内等待，对话框（Dialog）消失，用以缓冲运行，增加健壮性。
	 */
	public boolean waitForAlertDisappear(int seconds) {
		long start = System.currentTimeMillis();
		boolean exists = true;
		while ((System.currentTimeMillis() - start) < seconds * 1000) {
			try {
				driver.switchTo().alert();
			} catch (NoAlertPresentException ne) {
				exists = false;
				break;
			}
		}
		assertTrue("alert does not disappear in " + seconds + " seconds!", !exists);
		return exists;
	}

	/**
	 * Description: wait until window.
	 * 
	 * @param count
	 *            init window count.
	 * @param timeout
	 *            for waiting.
	 */
	public void waitForPageSyncronize(int count, int timeout) {
		long begins = System.currentTimeMillis();
		int windowCount = driver.getWindowHandles().size();
		windowCount = driver.getWindowHandles().size();
		while (windowCount != count && System.currentTimeMillis() - begins < timeout * 1000) {
			windowCount = driver.getWindowHandles().size();
		}
	}

	/**
	 * wait for the element visiable in timeout setting</BR> 在指定时间内等待，直到对象可见。
	 * 
	 * @param by
	 *            the element locator By
	 * @param seconds
	 *            timeout in seconds
	 */
	public boolean waitForElementVisible(By by, int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, seconds, stepTimeUnit);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(by)) != null;
		} finally {
			driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
		}
	}

	/**
	 * wait for the element visiable in timeout setting</BR> 在指定时间内等待，直到对象可见。
	 * 
	 * @param element
	 *            the element to be found.
	 * @param seconds
	 *            timeout in seconds.
	 */
	public boolean waitForElementVisible(WebElement element, int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, seconds, stepTimeUnit);
			return wait.until(ExpectedConditions.visibilityOf(element)) != null;
		} finally {
			driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
		}
	}

	/**
	 * wait for the element not visiable in timeout setting</BR>
	 * 在指定时间内等待，直到对象不可见。
	 * 
	 * @param by
	 *            the element locator.
	 * @param seconds
	 *            timeout in seconds.
	 */
	public boolean waitForElementNotVisible(By by, int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, seconds, stepTimeUnit);
			return wait.until(ExpectedConditions.invisibilityOfElementLocated(by)) != null;
		} finally {
			driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
		}
	}

	/**
	 * wait for the element present in timeout setting</BR> 在指定时间内等待，直到对象出现在页面上。
	 * 
	 * @param by
	 *            the element locator.
	 * @param seconds
	 *            timeout in seconds.
	 */
	public boolean waitForElementPresent(By by, int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, seconds, stepTimeUnit);
			return wait.until(ExpectedConditions.presenceOfElementLocated(by)) != null;
		} finally {
			driver.manage().timeouts().implicitlyWait(maxWaitfor, TimeUnit.SECONDS);
		}
	}

	/**
	 * wait for the element clickable in timeout setting</BR>
	 * 在指定时间内等待，直到对象能够被点击。
	 * 
	 * @param by
	 *            the element locator By
	 * @param seconds
	 *            timeout in seconds
	 */
	public boolean waitForElementClickable(By by, int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, seconds, stepTimeUnit);
			return wait.until(ExpectedConditions.elementToBeClickable(by)) != null;
		} finally {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		}
	}

	/**
	 * wait for text appears on element in timeout setting</BR>
	 * 在指定时间内等待，直到指定对象上出现指定文本。
	 * 
	 * @param by
	 *            the element locator By
	 * @param text
	 *            the text to be found of element
	 * @param seconds
	 *            timeout in seconds
	 */
	public boolean waitForTextOnElement(By by, String text, int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, seconds, stepTimeUnit);
			return wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text)) != null;
		} finally {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		}
	}

	/**
	 * wait for text appears in element attributes in timeout setting</BR>
	 * 在指定时间内等待，直到指定对象的某个属性值等于指定文本。
	 * 
	 * @param by
	 *            the element locator By
	 * @param text
	 *            the text to be found in element attributes
	 * @param seconds
	 *            timeout in seconds
	 */
	public boolean waitForTextOfElementAttr(By by, String text, int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, seconds, stepTimeUnit);
			return wait.until(ExpectedConditions.textToBePresentInElementValue(by, text)) != null;
		} finally {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		}
	}

	/**
	 * make the alert dialog not to appears</BR>
	 * 通过JS函数重载，在对话框（Alert）出现之前点击掉它，或者说等价于不让其出现。
	 */
	public void ensrueBeforeAlert() {
		jsExecutor(JScriptCollection.ENSRUE_BEFORE_ALERT.getValue(),
				"rewrite js to ensure alert before it appears");
	}

	/**
	 * make the warn dialog not to appears when window.close()</BR>
	 * 通过JS函数重载，在浏览器窗口关闭之前除去它的告警提示。
	 */
	public void ensureBeforeWinClose() {
		jsExecutor(JScriptCollection.ENSURE_BEFORE_WINCLOSE.getValue(),
				"rewrite js to ensure window close event");
	}

	/**
	 * make the confirm dialog not to appears choose default option OK</BR>
	 * 通过JS函数重载，在确认框（Confirm）出现之前点击确认，或者说等价于不让其出现而直接确认。
	 */
	public void ensureBeforeConfirm() {
		jsExecutor(JScriptCollection.ENSURE_BEFORE_CONFIRM.getValue(),
				"rewrite js to ensure confirm before it appears");
	}

	/**
	 * make the confirm dialog not to appears choose default option Cancel</BR>
	 * 通过JS函数重载，在确认框（Confirm）出现之前点击取消，或者说等价于不让其出现而直接取消。
	 */
	public void dismissBeforeConfirm() {
		jsExecutor(JScriptCollection.DISMISS_BEFORE_CONFIRM.getValue(),
				"rewrite js to dismiss confirm before it appears");
	}

	/**
	 * make the prompt dialog not to appears choose default option OK</BR>
	 * 通过JS函数重载，在提示框（Prompt）出现之前点击确认，或者说等价于不让其出现而直接确认。
	 */
	public void ensureBeforePrompt() {
		jsExecutor(JScriptCollection.ENSURE_BEFORE_PROMPT.getValue(),
				"rewrite js to ensure prompt before it appears");
	}

	/**
	 * make the prompt dialog not to appears choose default option Cancel</BR>
	 * 通过JS函数重载，在提示框（Prompt）出现之前点击取消，或者说等价于不让其出现而直接取消。
	 */
	public void dismisBeforePrompt() {
		jsExecutor(JScriptCollection.DISMISS_BEFORE_PROMPT.getValue(),
				"rewrite js to dismiss prompt before it appears");
	}

	/**
	 * choose OK/Cancel button's OK on alerts</BR> 在弹出的对话框（Dialog）上点击确认/是等接受性按钮。
	 */
	public void chooseOKOnAlert() {
		driver.switchTo().alert().accept();

	}

	/**
	 * choose OK/Cancel button's OK on alerts within timeout setting.</BR>
	 * 在弹出的对话框（Dialog）上点击确认/是等接受性按钮，预先判断是否存在，超时不存在则报错。
	 */
	public void chooseOKOnAlert(int timeout) {
		waitForAlertPresent(timeout);
		driver.switchTo().alert().accept();

	}

	/**
	 * choose Cancel on alerts</BR> 在弹出的对话框（Dialog）上点击取消/否等拒绝性按钮。
	 */
	public void chooseCancelOnAlert() {
		driver.switchTo().alert().dismiss();

	}

	/**
	 * choose Cancel on alerts within timeout setting.</BR>
	 * 在弹出的对话框（Dialog）上点击取消/否等拒绝性按钮，预先判断是否存在，超时不存在则报错。
	 */
	public void chooseCancelOnAlert(int timeout) {
		waitForAlertPresent(timeout);
		driver.switchTo().alert().dismiss();

	}

	/**
	 * get the text of the alerts</BR> 返回对话框（Dialog）上的提示信息文本内容。
	 * 
	 * @return alert text string
	 */
	public String getTextOfAlert() {
		String alerts = driver.switchTo().alert().getText();

		return alerts;
	}

	/**
	 * set text on alerts</BR> 向对话框（InputBox）中输入文本。
	 * 
	 * @param text
	 *            the text string you want to input on alerts
	 */
	public void setTextOnAlert(String text) {
		driver.switchTo().alert().sendKeys(text);

	}

	/**
	 * use js to make the element to be un-hidden</BR> 使用JS执行的方法强制让某些隐藏的控件显示出来。
	 * 
	 * @param element
	 *            the element to be operate
	 */
	public void makeElementUnHidden(WebElement element) {
		jsExecutor(JScriptCollection.MAKE_ELEMENT_UNHIDDEN.getValue(),
				"rewrite js to make elements to be visible", element);
	}

	/**
	 * use js to make the element to be un-hidden</BR> 使用JS执行的方法强制让某些隐藏的控件显示出来。
	 * 
	 * @param by
	 *            the By locator to find the element
	 */
	public void makeElementUnHidden(By by) {
		jsExecutor(JScriptCollection.MAKE_ELEMENT_UNHIDDEN.getValue(),
				"rewrite js to make elements to be visible", findElement(by));
	}
}
