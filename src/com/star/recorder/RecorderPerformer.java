package com.star.recorder;

import java.util.LinkedHashMap;
import java.util.Set;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;

import com.star.recorder.drivers.DriverStartUp;
import com.star.recorder.drivers.SetChromeDriver;
import com.star.recorder.drivers.SetFirefoxDriver;
import com.star.recorder.drivers.SetInternetExplorerDriver;
import com.star.recorder.forms.DrawMainForm;
import com.star.recorder.forms.DrawRecordPanel;
import com.star.recorder.forms.DrawScriptsPanel;
import com.star.recorder.forms.DrawStartPanel;
import com.star.recorder.forms.FormSettings;
import com.star.recorder.rules.ScriptParser;
import com.star.recorder.test.RunCurrentStep;
import com.star.recorder.tools.StringBufferUtils;

public class RecorderPerformer {
	private WebDriver driver;
	private DriverStartUp wdOption;
	private DrawMainForm mainForm;
	private DrawStartPanel start;
	private DrawRecordPanel record;
	private DrawScriptsPanel script;

	private JPanel startPanel;
	private JPanel recordPanel;
	private JPanel scriptPanel;

	private JComboBox codeStyle;
	private JComboBox operator;
	private JComboBox findBy;

	private JComboBox windows;
	private JTextField currentURL;
	private JTextField id;
	private JTextField name;
	private JTextField tagName;
	private JTextField className;
	private JTextField xpath;
	private JTextField text;
	private JTextField onclick;
	private JTextField href;
	private JTextField value;
	private JTextField dataValue;

	private JTextArea stepArea;
	private JTextArea scriptArea;

	private ScriptParser parser;
	private Set<String> operKeys;

	private final FormSettings FORM = new FormSettings();
	private final RunCurrentStep run = new RunCurrentStep();
	private final StringBufferUtils str = new StringBufferUtils();
	private final String splitChar = " - ";
	private final String selectWindow = "selectWindow";
	private LinkedHashMap<String, String> attrMap = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, String> jsMap;
	private LinkedHashMap<String, By> frameMap = new LinkedHashMap<String, By>();
	private LinkedHashMap<String, LinkedHashMap<String, String>> frameList = new LinkedHashMap<String, LinkedHashMap<String, String>>();
	private LinkedHashMap<String, String> frameInfo;
	private LinkedHashMap<String, String> attributeMap = new LinkedHashMap<String, String>();
	private final static int MaxFrameLevel = 10;
	private LinkedHashMap<String, JPanel> panelMap;
	private String currentStyle;
	private int frameLevel = 0;
	private int oldFrameLevel = 0;
	private By currentFrame;

	public RecorderPerformer() {
		start = new DrawStartPanel();
		record = new DrawRecordPanel();
		script = new DrawScriptsPanel();

		// 确认浏览器模式
		if (FORM.BROWSER_MODE.contains("ie")) {
			this.wdOption = new SetInternetExplorerDriver();
		} else if (FORM.BROWSER_MODE.contains("chrome")) {
			this.wdOption = new SetChromeDriver();
		} else if (FORM.BROWSER_MODE.contains("firefox")) {
			this.wdOption = new SetFirefoxDriver();
		} else {
			throw new RuntimeException("unsupported dirver type used, please extend it yourself!");
		}
	}

	// 初始化FORM程序
	public void startUp() throws Exception {
		panelMap = new LinkedHashMap<String, JPanel>();

		initJSMap();
		
		start.setMouseListener(new StartUrlMouseListener());
		start.setItemListener(new CodeStyleChangedListener());
		start.drawStartPanel();
		startPanel = start.getStartPanel();
		codeStyle = start.getCodeStyle();

		record.setMouseMotionListener(new RecordMouseListener());
		record.setSelectNewWindowMouseListener(new SelectNewWindowMouseListener());
		record.setStepRecordMouseListener(new AddToScriptLibMouseListener());
		record.setTestMouseListener(new TestMouseListener());
		record.setOperatorChangedListener(new ItemChangedListener());
		record.setFindByChangedListener(new ItemChangedListener());
		record.setDocumentListener(new DataValueInputListener());
		
		currentStyle = codeStyle.getSelectedItem().toString();
		parser = new ScriptParser(currentStyle);
		operKeys = parser.getCodeMap().keySet();
		record.setParser(parser);
		record.drawRecordPanel();
		operator = record.getOperator();
		stepArea = record.getStepArea();
		findBy = record.getFindBy();
		recordPanel = record.getRecordPanel();

		windows = record.getWindows();
		currentURL = record.getCurrentURL();
		id = record.getId();
		name = record.getName();
		xpath = record.getXpath();
		className = record.getClassName();
		onclick = record.getOnclick();
		text = record.getText();
		href = record.getHref();
		value = record.getValue();
		tagName = record.getTagName();
		dataValue = record.getDataValue();

		script.drawScriptsPanel();
		scriptPanel = script.getStepsViewPanel();
		scriptArea = script.getScript();

		panelMap.put("Start Page", startPanel);
		panelMap.put("Record Steps", recordPanel);
		panelMap.put("View Scripts", scriptPanel);

		mainForm = new DrawMainForm();
		mainForm.defineMainForm(panelMap);
	}

	// 生成当前对象的脚本
	public void setCurrentScript() {
		String operation = (String) operator.getSelectedItem();
		String byWay = (String) findBy.getSelectedItem();
		String currentScript;
		String selectFrame = "";

		attrMap.put("id", id.getText());
		attrMap.put("name", name.getText());
		attrMap.put("className", className.getText());
		attrMap.put("linkText", text.getText());
		attrMap.put("xpath", xpath.getText());
		attrMap.put("cssSelector", xpath.getText());
		attrMap.put("tagName", tagName.getText());

		if (oldFrameLevel > frameLevel) {
			selectFrame += "driver.switchTo.defaultContent();";
			for (int i = 1; i < frameLevel + 1; i++) {
				if (!frameList.get("" + i).get("id").isEmpty()) {
					selectFrame += "\nselectFrame(\"" + frameList.get("" + i).get("id") + "\");";
				} else if (!frameList.get("" + i).get("name").isEmpty()) {
					selectFrame += "\nselectFrame(\"" + frameList.get("" + i).get("name") + "\");";
				} else {
					selectFrame += "\nselectFrame(By.xpath(\"" + frameList.get("" + i).get("xpath") + "\"));";
				}
			}
			selectFrame += "\n";
		} else if (oldFrameLevel < frameLevel) {
			for (int i = oldFrameLevel + 1; i < frameLevel + 1; i++) {
				if (!frameList.get("" + i).get("id").isEmpty()) {
					selectFrame += "\nselectFrame(\"" + frameList.get("" + i).get("id") + "\");";
				} else if (!frameList.get("" + i).get("name").isEmpty()) {
					selectFrame += "\nselectFrame(\"" + frameList.get("" + i).get("name") + "\");";
				} else {
					selectFrame += "\nselectFrame(By.xpath(\"" + frameList.get("" + i).get("xpath") + "\"));";
				}
			}
			selectFrame += "\n";
		}

		String dataText = dataValue.getText();
		if (dataText == null || dataText == "" || dataText.isEmpty()) {
			dataText = "value to be changed";
		}
		
		if (driver != null) {
			String[] winAttr = windows.getSelectedItem().toString().split(splitChar);
			String param = currentStyle.toLowerCase().contains("bot") ? winAttr[1] : winAttr[2];

			currentScript = operation.equalsIgnoreCase(selectWindow) ? (parser.getCurrentStep(operation,
					new Object[] { param })) : (selectFrame + parser.getCurrentStep(operation, new Object[] { byWay,
					attrMap.get(byWay), dataText }));

		} else {
			currentScript = selectFrame
					+ parser.getCurrentStep(operation, new Object[] { byWay, attrMap.get(byWay), dataText });
		}

		stepArea.setText(currentScript);
		attrMap.clear();
	}

	//拖动鼠标的事件定义
	private void defineDraggedMouse() {
		try {
			Point mousepoint = MouseInfo.getPointerInfo().getLocation();

			int oldX = mousepoint.x;
			int oldY = mousepoint.y;
			int x = oldX;
			int y = oldY;
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String scrollLeft = "";
			String scrollTop = "";
			for (int i = 0; i < frameLevel; i++) {
				scrollLeft = scrollLeft + jsMap.get("" + (i + 1)) + ".scrollLeft";
				scrollTop = scrollTop + jsMap.get("" + (i + 1)) + ".scrollTop";
			}

			String atrList = (String) (js.executeScript(
					WebElements.SCREEN_LOCATOR.getValue() + scrollLeft
							+ WebElements.SCREEEN_OFFSET.getValue() + scrollTop
							+ WebElements.ELEMENT_LOCATOR.getValue(), x, y));
			attributeMap = stringToMap(atrList, "[#%#]+", "[$$$]+");

			id.setText(attributeMap.get("id"));
			name.setText(attributeMap.get("name"));
			tagName.setText(attributeMap.get("tagName"));
			className.setText(attributeMap.get("className"));
			text.setText(attributeMap.get("linkText"));
			href.setText(attributeMap.get("href"));
			onclick.setText(attributeMap.get("onclick"));
			value.setText(attributeMap.get("value"));
			currentURL.setText(driver.getCurrentUrl());

			String elementXpath = (String) (js.executeScript(WebElements.SCREEN_LOCATOR.getValue()
					+ scrollLeft + WebElements.SCREEEN_OFFSET.getValue() + scrollTop
					+ WebElements.ELEMENT_XPATH.getValue(), x, y));
			xpath.setText(elementXpath);
			attributeMap.put("xpath", elementXpath);

			if (attributeMap.get("tagName").equalsIgnoreCase("frame")
					|| attributeMap.get("tagName").equalsIgnoreCase("iframe")) {
				if (!By.xpath(elementXpath).equals(currentFrame)) {
					frameInfo = new LinkedHashMap<String, String>();
					driver.switchTo().frame(driver.findElement(By.xpath(elementXpath)));
					frameLevel += 1;
					frameMap.put("" + frameLevel, By.xpath(elementXpath));
					frameInfo.put("id", attributeMap.get("id"));
					frameInfo.put("name", attributeMap.get("name"));
					frameInfo.put("className", attributeMap.get("className"));
					frameInfo.put("xpath", elementXpath);
					frameList.put("" + frameLevel, frameInfo);
					currentFrame = By.xpath(elementXpath);
				}
			}
		} catch (Exception e) {
			driver.switchTo().defaultContent();
			if (frameLevel > 1) {
				for (int i = 1; i < frameLevel; i++) {
					driver.switchTo().frame(driver.findElement(frameMap.get("" + i)));
				}
				frameMap.remove("" + frameLevel);
				frameList.remove("" + frameLevel);
				frameLevel -= 1;
				currentFrame = frameMap.get("" + frameLevel);
			} else if (frameLevel > 0) {
				frameMap.remove("" + frameLevel);
				frameList.remove("" + frameLevel);
				frameLevel -= 1;
				currentFrame = frameMap.get("" + frameLevel);
			}
		}
	}

	// 配置JS，用以获取对象
	private void initJSMap() throws Exception {
		jsMap = new LinkedHashMap<String, String>();
		String js1 = " + window";
		String js2 = "";
		String js3 = ".document.documentElement";
		for (int i = 1; i < MaxFrameLevel; i++) {
			js2 = js2 + ".parent";
			jsMap.put("" + i, js1 + js2 + js3);
		}
	}

	//将字符串使用特定字符拆分计入map
	private LinkedHashMap<String, String> stringToMap(String str, String mrk1, String mrk2) {
		String[] strList = str.split(mrk1, -1);
		LinkedHashMap<String, String> atrMap = new LinkedHashMap<String, String>();
		for (int index = 0; index < strList.length; index++) {
			String[] temp = strList[index].split(mrk2, -1);
			atrMap.put(temp[0], temp[1]);
		}
		return atrMap;
	}
	
	//选择代码风格的监听
	public class CodeStyleChangedListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (operator != null){
					operator.removeAllItems();
					parser = new ScriptParser(codeStyle.getSelectedItem().toString());
					operKeys = parser.getCodeMap().keySet();
					currentStyle = codeStyle.getSelectedItem().toString();
					record.setOperations(operKeys);
				}
			}
		}
	}
	
	//选择测试对象定位方式、操作方式的监听
	public class ItemChangedListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			String currentScript = stepArea.getText().trim();
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (currentScript != null && currentScript != "null" && !currentScript.isEmpty()) {
					setCurrentScript();
				} else {
					if (operator != null) {
						String operSelected = operator.getSelectedItem().toString();
						if (str.countStrRepeat(parser.getCodeMap().get(operSelected), "%s") == 0) {
							setCurrentScript();
						}
					}
				}
			}
		}
	}
	
	//输入测试数据值的监听
	public class DataValueInputListener implements DocumentListener {
		public void removeUpdate(DocumentEvent e) {}
		public void changedUpdate(DocumentEvent e) {}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			setCurrentScript();
		}
	}

	// 录制按钮的鼠标操作监听
	public class RecordMouseListener implements MouseMotionListener {
		public void mouseMoved(MouseEvent arg0) {}
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			if (driver == null) {
				JOptionPane.showMessageDialog(recordPanel, "WebDriver Has Not Been Started !");
			} else {
				defineDraggedMouse();				
				if (operator != null && !attributeMap.isEmpty()){
					parser.setSuitableOperator(operator, attributeMap);					
				}
				if (findBy != null && !attributeMap.isEmpty()){
					parser.setSuitableBy(findBy, attributeMap);					
				}
				setCurrentScript();
				run.setDriver(driver);
				run.setWindows(record);
			}
		}
	}

	// 测试/运行当前步骤按钮的监听
	public class TestMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}

		public void mousePressed(MouseEvent arg0) {
			if (driver == null) {
				JOptionPane.showMessageDialog(recordPanel, "WebDriver Has Not Been Started !");
			} else {
				run.setDriver(driver);
				run.setOperator(operator);
				run.setFindBy(findBy);
				run.setId(id);
				run.setName(name);
				run.setXpath(xpath);
				run.setText(text);
				run.setClassName(className);
				run.setWindows(windows);
				run.setDataValue(dataValue);
				run.runCurrentStep();
				run.maximizeAllWindows();
				run.setWindows(record);
			}
		}
	}
	
	// 切换到弹出新窗口按钮的监听
	public class SelectNewWindowMouseListener implements MouseListener {
		@Override
		public void mousePressed(MouseEvent arg0) {
			if (driver == null) {
				JOptionPane.showMessageDialog(recordPanel, "WebDriver Has Not Been Started !");
			} else{
				String[] allWindows = windows.getSelectedItem().toString().split(splitChar);
				String windowTitle = allWindows[1];
				String windowHandle = allWindows[2];
				if (currentStyle.toLowerCase().contains("bot")){
					stepArea.setText("selectWindow(\"" + windowTitle + "\");");
				} else{
					stepArea.setText("driver.switchTo().window(\"" + windowHandle + "\");");
				}
				if (!operKeys.contains(selectWindow)){
					operator.addItem(selectWindow);
				}
				driver.switchTo().window(windowHandle);
				operator.setSelectedItem(selectWindow);				
			}			
		}
		
		public void mouseClicked(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}

	// 开始录制按钮操作的监听
	public class StartUrlMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			String url = start.getUrlEdit().getText();
			if (url == null || url == "" || url.isEmpty()) {
				JOptionPane.showMessageDialog(startPanel, "Url Can Not Be Empty !");
			} else {
				mainForm.activeTab(recordPanel);
				operKeys = parser.getCodeMap().keySet();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			String url = start.getUrlEdit().getText();
			driver = wdOption.getDriver();
			mainForm.setDriver(driver);
			if (url != null && url != "" && !url.isEmpty()) {
				driver.get(url);
			}
			run.setDriver(driver);
			run.setWindows(record);
		}
	}

	// 将当前生成的脚本追加到脚本库
	public class AddToScriptLibMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
		public void mousePressed(MouseEvent e) {
			if (stepArea.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(recordPanel, "Not Scripts In The Step Area !");
			} else {
				scriptArea.append(stepArea.getText().trim() + "\n");
			}			
		}
	}
}