package com.star.recorder.rules;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.JComboBox;
import com.star.recorder.config.ConfigurationSupport;
import com.star.recorder.tools.StringBufferUtils;

public class ScriptParser {
	private String fileName = "config/java_bot_style";
	private StringBufferUtils str = new StringBufferUtils();
	private ConfigurationSupport config = new ConfigurationSupport();
	private String codeStyle;

	public ScriptParser(String codeStyle) {
		if (codeStyle == null || codeStyle == "" || codeStyle.isEmpty()) {
			throw new IllegalArgumentException("the code style can not be null!");
		} else if (codeStyle.toLowerCase().contains("bot")) {
			fileName = "config/bot_style";
		} else if (codeStyle.toLowerCase().contains("primal")) {
			fileName = "config/primal_style";
		} else {
			fileName = "config/user_defined_style";
		}

		this.codeStyle = codeStyle;
		if (!new File(fileName).exists()) {
			throw new RuntimeException("the code style config file " + fileName + " does not exist!");
		}
	}

	public String getCurrentStep(String operation, Object[] params) {
		LinkedHashMap<String, String> codeMap = getCodeMap();
		String script = codeMap.get(operation);
		if (script == null || script.isEmpty() || script == "") {
			throw new RuntimeException("the operation:[ " + operation + " ] does not configed in " + fileName);
		}

		String paramString = "";
		for (int i = 0; i < params.length; i++) {
			paramString = paramString + params[i];
		}

		if (str.countStrRepeat(script, "%s") > params.length) {
			throw new RuntimeException("your operation parameters:[ " + paramString
					+ " ] does not match current operation : [ " + script + " ] !");
		}
		return String.format(script, params);
	}

	public LinkedHashMap<String, String> getCodeMap() {
		LinkedHashMap<String, String> codeMap = new LinkedHashMap<String, String>();
		ArrayList<String> keyList = config.readTextToList(fileName);
		for (int i = 0; i < keyList.size(); i++) {
			String[] currentLine = keyList.get(i).split("=");
			codeMap.put(currentLine[0], currentLine[1]);
		}
		return codeMap;
	}

	public void setSuitableOperator(JComboBox<?> operator, LinkedHashMap<String, String> attributeMap) {
		String tagName = attributeMap.get("tagName").toLowerCase();
		String tagType = attributeMap.get("type").toLowerCase();
		if (tagName.equals("input")) {
			if (tagType == null || tagType.equals("text") || tagType.equals("password")) {
				operator.setSelectedItem("sendKeys");
			} else if (tagType.equals("file")) {
				if (codeStyle.toLowerCase().contains("bot")) {
					operator.setSelectedItem("sendKeysAppend");
				} else {
					operator.setSelectedItem("sendKeys");
				}
			} else {
				operator.setSelectedItem("click");
			}
		} else if (tagName.equals("select")) {
			operator.setSelectedItem("selectByVisibleText");
		} else if (tagName.equals("tr") || tagName.equals("span") || tagName.equals("td")) {
			operator.setSelectedItem("getText");
		} else if (tagName.equals("textarea")) {
			operator.setSelectedItem("sendKeys");
		} else {
			operator.setSelectedItem("click");
		}
	}

	public void setSuitableBy(JComboBox<?> findBy, LinkedHashMap<String, String> elementAtrs) {
		if (elementAtrs.get("id") != null && elementAtrs.get("id").length() > 0
				&& !elementAtrs.get("id").equalsIgnoreCase("null")
				&& !elementAtrs.get("id").equalsIgnoreCase("undefined")) {
			findBy.setSelectedItem("id");
		} else if (elementAtrs.get("name") != null && elementAtrs.get("name").length() > 0
				&& !elementAtrs.get("name").equalsIgnoreCase("null")
				&& !elementAtrs.get("name").equalsIgnoreCase("undefined")) {
			findBy.setSelectedItem("name");
		} else if (elementAtrs.get("tagName").equalsIgnoreCase("a") && elementAtrs.get("text") != null
				&& elementAtrs.get("text").length() > 0 && !elementAtrs.get("text").equalsIgnoreCase("null")
				&& elementAtrs.get("text").equalsIgnoreCase("undefined")) {
			findBy.setSelectedItem("linkText");
		} else {
			findBy.setSelectedItem("xpath");
		}
	}
}
