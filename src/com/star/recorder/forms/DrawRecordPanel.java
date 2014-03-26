package com.star.recorder.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentListener;

import com.star.recorder.rules.ScriptParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class DrawRecordPanel {
	private final FormSettings FORM = new FormSettings();
	private ScriptParser parser;

	private JPanel recordPanel;

	private JComboBox windows;

	private JTextField currentURL;
	private JTextField id;
	private JTextField name;
	private JTextField tagName;
	private JTextField className;
	private JTextField xpath;
	private JTextField text;
	private JTextField value;
	private JTextField onclick;
	private JTextField href;
	private JTextField dataValue;

	private JComboBox operator;
	private JComboBox findBy;
	private JTextArea stepArea;
	private JTextArea scriptArea;

	private MouseListener selectNewWindowMouseListener;
	private MouseListener testMouseListener;
	private MouseListener stepRecordMouseListener;
	private MouseMotionListener mouseMotionListener;
	private ItemListener operatorChangedListener;
	private ItemListener findByChangedListener;
	private DocumentListener documentListener;

	public void drawRecordPanel() throws Exception {
		recordPanel = new JPanel();

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(11, 1));

		JButton recordButton = new JButton("Record [Drag To Object]");
		JButton switchButton = new JButton("Switch To Widnow");
		JButton testButton = new JButton("Test/Run Current Step");
		JButton addToSteps = new JButton("Save Current Step");
		int buttonHighth = (int) recordButton.getPreferredSize().getHeight();
		Dimension buttonSize = new Dimension(FORM.TEXT_AREA_WIDTH * 196 / 500, buttonHighth);

		JLabel textLabelTitle = new JLabel("windows:", SwingUtilities.RIGHT);
		JLabel textLabelId = new JLabel("id:", SwingUtilities.RIGHT);
		JLabel textLabelName = new JLabel("name:", SwingUtilities.RIGHT);
		JLabel textLabelXpath = new JLabel("xpath:", SwingUtilities.RIGHT);
		JLabel textLabelClass = new JLabel("class:", SwingUtilities.RIGHT);
		JLabel textLabelOnclick = new JLabel("onclick:", SwingUtilities.RIGHT);
		JLabel textLabelText = new JLabel("text:", SwingUtilities.RIGHT);
		JLabel textLabelValue = new JLabel("value:", SwingUtilities.RIGHT);
		JLabel textLabelHref = new JLabel("href:", SwingUtilities.RIGHT);
		JLabel textLabelTagName = new JLabel("tagName:", SwingUtilities.RIGHT);
		JLabel textLabelUrl = new JLabel("url:", SwingUtilities.RIGHT);

		textLabelTitle.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelId.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelName.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelXpath.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelClass.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelOnclick.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelText.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelValue.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelHref.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelTagName.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));
		textLabelUrl.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 8, FORM.TEXT_FIELD_HIGHTH));

		textLabelTitle.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelId.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelName.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelXpath.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelClass.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelOnclick.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelText.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelValue.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelHref.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelTagName.setFont(FORM.TEXT_NORMAL_FONT);
		textLabelUrl.setFont(FORM.TEXT_NORMAL_FONT);

		labelPanel.add(textLabelId, BorderLayout.NORTH);
		labelPanel.add(textLabelName, BorderLayout.NORTH);
		labelPanel.add(textLabelXpath, BorderLayout.NORTH);
		labelPanel.add(textLabelClass, BorderLayout.NORTH);
		labelPanel.add(textLabelOnclick, BorderLayout.NORTH);
		labelPanel.add(textLabelText, BorderLayout.NORTH);
		labelPanel.add(textLabelValue, BorderLayout.NORTH);
		labelPanel.add(textLabelHref, BorderLayout.NORTH);
		labelPanel.add(textLabelTagName, BorderLayout.NORTH);
		labelPanel.add(textLabelUrl, BorderLayout.NORTH);
		labelPanel.add(textLabelTitle, BorderLayout.NORTH);

		JPanel attrPanel = new JPanel();
		attrPanel.setLayout(new GridLayout(11, 1));

		windows = new JComboBox();
		windows.setBackground(FORM.editBack);
		windows.setForeground(FORM.editFront);
		id = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		id.setBackground(FORM.editBack);
		id.setForeground(FORM.editFront);
		name = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		name.setBackground(FORM.editBack);
		name.setForeground(FORM.editFront);
		xpath = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		xpath.setBackground(FORM.editBack);
		xpath.setForeground(FORM.editFront);
		className = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		className.setBackground(FORM.editBack);
		className.setForeground(FORM.editFront);
		onclick = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		onclick.setBackground(FORM.editBack);
		onclick.setForeground(FORM.editFront);
		text = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		text.setBackground(FORM.editBack);
		text.setForeground(FORM.editFront);
		value = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		value.setBackground(FORM.editBack);
		value.setForeground(FORM.editFront);
		href = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		href.setBackground(FORM.editBack);
		href.setForeground(FORM.editFront);
		tagName = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		tagName.setBackground(FORM.editBack);
		tagName.setForeground(FORM.editFront);
		currentURL = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		currentURL.setBackground(FORM.editBack);
		currentURL.setForeground(FORM.editFront);

		windows.setFont(FORM.TEXT_NORMAL_FONT);
		id.setFont(FORM.TEXT_NORMAL_FONT);
		name.setFont(FORM.TEXT_NORMAL_FONT);
		xpath.setFont(FORM.TEXT_NORMAL_FONT);
		className.setFont(FORM.TEXT_NORMAL_FONT);
		onclick.setFont(FORM.TEXT_NORMAL_FONT);
		text.setFont(FORM.TEXT_NORMAL_FONT);
		value.setFont(FORM.TEXT_NORMAL_FONT);
		href.setFont(FORM.TEXT_NORMAL_FONT);
		tagName.setFont(FORM.TEXT_NORMAL_FONT);
		currentURL.setFont(FORM.TEXT_NORMAL_FONT);

		windows.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		id.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		name.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		xpath.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		className.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		onclick.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		text.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		value.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		href.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		tagName.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));
		currentURL.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 2, FORM.TEXT_FIELD_HIGHTH));

		attrPanel.add(id);
		attrPanel.add(name);
		attrPanel.add(xpath);
		attrPanel.add(className);
		attrPanel.add(onclick);
		attrPanel.add(text);
		attrPanel.add(value);
		attrPanel.add(href);
		attrPanel.add(tagName);
		attrPanel.add(currentURL);
		attrPanel.add(windows);

		JPanel objectPanel = new JPanel();
		objectPanel.setLayout(new GridLayout(1, 2));
		objectPanel.add(labelPanel);
		objectPanel.add(attrPanel);

		JPanel dataPanel = new JPanel();
		JPanel userLabelPanel = new JPanel();
		JPanel userValuePanel = new JPanel();
		userLabelPanel.setLayout(new GridLayout(2, 1));
		userValuePanel.setLayout(new GridLayout(2, 1));
		dataPanel.setLayout(new GridLayout(1, 2));
		dataPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		JLabel dataLabel = new JLabel("TestData:", SwingUtilities.RIGHT);
		JLabel operLabel = new JLabel("Operations:", SwingUtilities.RIGHT);
		dataLabel.setFont(FORM.TEXT_NORMAL_FONT);
		operLabel.setFont(FORM.TEXT_NORMAL_FONT);
		dataLabel.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 7, FORM.TEXT_FIELD_HIGHTH));
		operLabel.setPreferredSize(new Dimension(FORM.FORM_WIDTH / 7, FORM.TEXT_FIELD_HIGHTH));

		userLabelPanel.add(dataLabel);
		userLabelPanel.add(operLabel);

		dataValue = new JTextField((FORM.TEXT_AREA_WIDTH) / 9);
		dataValue.setBackground(FORM.editBack);
		dataValue.setForeground(FORM.editFront);
		dataValue.setFont(FORM.TEXT_NORMAL_FONT);
		dataValue.getDocument().addDocumentListener(documentListener);

		JPanel choicePanel = new JPanel();
		choicePanel.setLayout(new GridLayout(1, 2));

		operator = new JComboBox();
		operator.setBackground(FORM.editBack);
		operator.setForeground(FORM.editFront);
		setOperations(parser.getCodeMap().keySet());
		operator.addItemListener(operatorChangedListener);
		
		findBy = new JComboBox();
		findBy.setBackground(FORM.editBack);
		findBy.setForeground(FORM.editFront);
		findBy.addItem("id");
		findBy.addItem("name");
		findBy.addItem("xpath");
		findBy.addItem("className");
		findBy.addItem("linkText");
		findBy.addItem("cssSelector");
		findBy.addItemListener(findByChangedListener);

		operator.setFont(FORM.TEXT_ITALIC_FONT);
		findBy.setFont(FORM.TEXT_ITALIC_FONT);

		choicePanel.add(operator);
		choicePanel.add(findBy);

		userValuePanel.add(dataValue);
		userValuePanel.add(choicePanel);
		dataPanel.add(userLabelPanel);
		dataPanel.add(userValuePanel);

		JPanel tAreaPanel = new JPanel();
		stepArea = new JTextArea();
		stepArea.setBackground(FORM.editBack);
		stepArea.setForeground(FORM.editFront);
		stepArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		JScrollPane scroller = new JScrollPane(stepArea);
		int areaHighth = FORM.FORM_HIGHTH - FORM.GAP_HIGHTH * 5 - FORM.TEXT_FIELD_HIGHTH * 16 - buttonHighth * 2;
		scroller.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH, areaHighth));
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tAreaPanel.add(scroller);

		JPanel buttonPanel = new JPanel();
		JPanel buttonPanel1 = new JPanel();
		JPanel buttonPanel2 = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		buttonPanel1.setLayout(new GridLayout(2, 1));
		buttonPanel2.setLayout(new GridLayout(2, 1));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, FORM.GAP_WIDTH, FORM.GAP_HIGHTH));

		recordButton.addMouseMotionListener(mouseMotionListener);
		switchButton.addMouseListener(selectNewWindowMouseListener);
		testButton.addMouseListener(testMouseListener);
		addToSteps.addMouseListener(stepRecordMouseListener);
		
		recordButton.setFont(FORM.getButtonFont());
		switchButton.setFont(FORM.getButtonFont());
		testButton.setFont(FORM.getButtonFont());
		addToSteps.setFont(FORM.getButtonFont());
		
		recordButton.setPreferredSize(buttonSize);
		switchButton.setPreferredSize(buttonSize);
		testButton.setPreferredSize(buttonSize);
		addToSteps.setPreferredSize(buttonSize);

		buttonPanel1.add(recordButton);
		buttonPanel1.add(switchButton);
		buttonPanel2.add(testButton);
		buttonPanel2.add(addToSteps);
		buttonPanel.add(buttonPanel1);
		buttonPanel.add(buttonPanel2);

		objectPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		tAreaPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		recordPanel.add(objectPanel);
		recordPanel.add(dataPanel);
		recordPanel.add(buttonPanel);
		recordPanel.add(tAreaPanel);

		recordPanel.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH, FORM.FORM_HIGHTH));
		recordPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, FORM.GAP_WIDTH, FORM.GAP_HIGHTH));
		recordPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
	}
	
	public void setOperations(Set<String> keySet){
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()){
			operator.addItem(it.next());
		}
	}
	
	public void setWindows(ArrayList<String> items, String defaultHandle){
		windows.removeAllItems();
		Iterator<String> it = items.iterator();
		String fitWindow = null;
		while(it.hasNext()){
			String window = it.next();
			windows.addItem(window);
			if (window.contains(defaultHandle)){
				fitWindow = window;
			}
		}
		if (fitWindow != null && !fitWindow.isEmpty()){
			windows.setSelectedItem(fitWindow);
		}
	}

	public void setParser(ScriptParser parser) {
		this.parser = parser;
	}

	public JPanel getRecordPanel() {
		return recordPanel;
	}

	public JComboBox getWindows() {
		return windows;
	}

	public JTextField getCurrentURL() {
		return currentURL;
	}

	public JTextField getId() {
		return id;
	}

	public JTextField getName() {
		return name;
	}

	public JTextField getTagName() {
		return tagName;
	}

	public JTextField getClassName() {
		return className;
	}

	public JTextField getXpath() {
		return xpath;
	}

	public JTextField getText() {
		return text;
	}

	public JTextField getValue() {
		return value;
	}

	public JTextField getOnclick() {
		return onclick;
	}

	public JTextField getHref() {
		return href;
	}

	public JTextField getDataValue() {
		return dataValue;
	}

	public JComboBox getOperator() {
		return operator;
	}

	public JComboBox getFindBy() {
		return findBy;
	}

	public JTextArea getStepArea() {
		return stepArea;
	}

	public void setTestMouseListener(MouseListener listener) {
		this.testMouseListener = listener;
	}

	public void setSelectNewWindowMouseListener(MouseListener selectNewWindowMouseListener) {
		this.selectNewWindowMouseListener = selectNewWindowMouseListener;
	}

	public void setStepRecordMouseListener(MouseListener stepRecordMouseListener) {
		this.stepRecordMouseListener = stepRecordMouseListener;
	}

	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}
	
	public void setOperatorChangedListener(ItemListener operatorChangedListener) {
		this.operatorChangedListener = operatorChangedListener;
	}

	public void setFindByChangedListener(ItemListener findByChangedListener) {
		this.findByChangedListener = findByChangedListener;
	}

	public void setDocumentListener(DocumentListener documentListener) {
		this.documentListener = documentListener;
	}

	public JTextArea getScriptArea() {
		return scriptArea;
	}

	public void setScriptArea(JTextArea scriptArea) {
		this.scriptArea = scriptArea;
	}

	public void setStepArea(JTextArea stepArea) {
		this.stepArea = stepArea;
	}
}
