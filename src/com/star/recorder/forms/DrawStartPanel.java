package com.star.recorder.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import com.star.recorder.config.ConfigurationSupport;

public class DrawStartPanel {
	private final String rootFolder = System.getProperty("user.dir");
	private final String anounceFile = rootFolder + "/config/form_anounces";
	private final FormSettings FORM = new FormSettings();
	private JPanel startPanel;
	private JTextField urlEdit;
	private JComboBox codeStyle;
	private MouseListener listener;
	private ItemListener itemListener;

	public void drawStartPanel() throws Exception {
		startPanel = new JPanel();
		JPanel labelPanel = new JPanel();
		JPanel choicePanel = new JPanel();
		JPanel userPanel = new JPanel();

		JLabel label = new JLabel("Welcome To Selenium2/WebDriver Recorder: ", SwingUtilities.LEFT);
		label.setFont(FORM.getBigTextFont());
		label.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH, (int) label.getPreferredSize().getHeight()));

		JTextArea userAnounce = new JTextArea();
		userAnounce.setLineWrap(true);
		userAnounce.setWrapStyleWord(true);
		userAnounce.setBackground(FORM.editBack);
		userAnounce.setForeground(FORM.editFront);

		new ConfigurationSupport().fillStringToForms(userAnounce, anounceFile); // 将常用的地址等用户所需信息记录在主窗体中

		userAnounce.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		userAnounce.setFont(FORM.TEXT_NORMAL_FONT);

		JScrollPane scroller = new JScrollPane(userAnounce);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH, (FORM.FORM_HIGHTH - FORM.GAP_HIGHTH * 5) * 2 / 3));

		JLabel urlLabel = new JLabel("Url Of The Web Application: ", SwingUtilities.RIGHT);
		urlLabel.setFont(FORM.TEXT_NORMAL_FONT);
		JLabel styleLabel = new JLabel("Choose Your Favourite Style: ", SwingUtilities.RIGHT);
		styleLabel.setFont(FORM.TEXT_NORMAL_FONT);

		labelPanel.add(urlLabel);
		labelPanel.add(styleLabel);
		labelPanel.setLayout(new GridLayout(2, 1));

		urlEdit = new JTextField(FORM.TEXT_AREA_WIDTH / 10);
		codeStyle = new JComboBox();
		urlEdit.setBackground(FORM.editBack);
		urlEdit.setForeground(FORM.editFront);
		codeStyle.setBackground(FORM.editBack);
		codeStyle.setForeground(FORM.editFront);
		codeStyle.addItemListener(itemListener);
		String[] codeStyles = FORM.CODE_STYLES.split(",");
		for (int i = 0; i < codeStyles.length; i++) {
			codeStyle.addItem(codeStyles[i]);
		}

		choicePanel.setLayout(new GridLayout(2, 1));
		choicePanel.add(urlEdit);
		choicePanel.add(codeStyle);
		urlEdit.setFont(FORM.TEXT_NORMAL_FONT);
		codeStyle.setFont(FORM.TEXT_ITALIC_FONT);

		userPanel.setLayout(new GridLayout(1, 2));
		userPanel.add(labelPanel);
		userPanel.add(choicePanel);
		userPanel.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH, FORM.FORM_HIGHTH / 12));

		JButton startButton = new JButton("Start Record");
		startButton.addMouseListener(listener);
		startButton.setFont(FORM.getButtonFont());

		startPanel.add(label);
		startPanel.add(scroller);
		startPanel.add(userPanel);
		startPanel.add(startButton);
		startPanel.setLayout(new GridLayout(3, 1));
		startPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, FORM.GAP_WIDTH, FORM.GAP_HIGHTH));
		startPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
	}

	public void setMouseListener(MouseListener lis) {
		listener = lis;
	}
	
	public void setItemListener(ItemListener lis) {
		itemListener = lis;
	}

	public JPanel getStartPanel() {
		return startPanel;
	}

	public void setUrlEdit(JTextField textField) {
		urlEdit = textField;
	}

	public JTextField getUrlEdit() {
		return urlEdit;
	}

	public void setCodeStyle(JComboBox combox) {
		codeStyle = combox;
	}

	public JComboBox getCodeStyle() {
		return codeStyle;
	}
}