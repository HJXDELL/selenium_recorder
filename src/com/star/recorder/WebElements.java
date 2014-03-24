package com.star.recorder;

public enum WebElements {

	SCREEN_LOCATOR(" var offX,offY; offX = arguments[0] - window.screenLeft + document.documentElement.scrollLeft"),

	SCREEEN_OFFSET(";offY = arguments[1] - window.screenTop + document.documentElement.scrollTop"),

	ELEMENT_LOCATOR(";var element = document.elementFromPoint(offX, offY);"
			+ "var s = 'id$$$' + element.getAttribute('id');s = s + '#%#name$$$' + element.getAttribute('name');"
			+ "s = s + '#%#tagName$$$' + element.tagName;s = s + '#%#className$$$' + element.className;"
			+ "s = s + '#%#linkText$$$' + element.innerText;s = s + '#%#href$$$' + element.getAttribute('href');"
			+ "s = s + '#%#onclick$$$' + element.getAttribute('onclick');s = s + '#%#type$$$' + element.getAttribute('type');"
			+ "s = s + '#%#src$$$' + element.getAttribute('src');"
			+ "s = s + '#%#value$$$' + element.getAttribute('value');return s;"),

	ELEMENT_XPATH(";var element = document.elementFromPoint(offX, offY);"
			+ "getXPath=function(node){if (node.id !== ''){return '//' + "
			+ "node.tagName.toLowerCase() + '[@id=\\\'' + node.id + '\\\']'}if (node === "
			+ "document.body){return node.tagName.toLowerCase()} var nodeCount = 0;var "
			+ "childNodes = node.parentNode.childNodes;for (var i=0; i<childNodes.length; "
			+ "i++){var currentNode = childNodes[i];if (currentNode === node){return "
			+ "getXPath(node.parentNode) + '/' + node.tagName.toLowerCase() + '[' + (nodeCount+1) "
			+ "+ ']'}if (currentNode.nodeType === 1 && currentNode.tagName.toLowerCase() "
			+ "=== node.tagName.toLowerCase()){nodeCount++}}};return getXPath(element);");

	private String javaScript;

	private WebElements(String jsContext) {
		this.javaScript = jsContext;
	}

	public String getValue() {
		return this.javaScript;
	}
}