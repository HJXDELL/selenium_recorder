package com.star.recorder.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.ScrollPaneConstants;

public class DrawScriptsPanel implements ClipboardOwner{
	private JPanel stepsViewPanel;
	private JTextArea scriptArea;

	private final FormSettings FORM = new FormSettings();
	private Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();

	//绘制已生成测试脚本试图页签
	public void drawScriptsPanel() throws Exception {
		stepsViewPanel = new JPanel();
		JLabel label = new JLabel("Test Scripts Generated", SwingUtilities.LEFT);
		label.setFont(FORM.getBigTextFont());
		label.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH, (int) label.getPreferredSize().getHeight()));
		stepsViewPanel.add(label);

		JButton copyButton = new JButton("Copy To Clipboard");
		int btnHighth = (int) copyButton.getPreferredSize().getHeight();
		scriptArea = new JTextArea();
		scriptArea.setBackground(FORM.editBack);
		scriptArea.setForeground(FORM.editFront);
		scriptArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));

		JScrollPane scroller = new JScrollPane(scriptArea);
		scroller.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH, FORM.FORM_HIGHTH - FORM.getBigTextSize() * 4
				- FORM.GAP_HIGHTH * 5 - btnHighth));
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		stepsViewPanel.add(scroller);
		
		copyButton.setFont(FORM.getButtonFont());
		copyButton.setPreferredSize(new Dimension(FORM.TEXT_AREA_WIDTH / 4, btnHighth));
		copyButton.addActionListener(new CopyListener());
		stepsViewPanel.add(copyButton);
		
		stepsViewPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, FORM.GAP_WIDTH, FORM.GAP_HIGHTH));
		stepsViewPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
	}
	
	//复制到粘贴板操作的监听实现
	public class CopyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clipboard.setContents(new StringSelection(scriptArea.getText()), DrawScriptsPanel.this);			
		}
	}

	public JPanel getStepsViewPanel() {
		return stepsViewPanel;
	}

	public JTextArea getScript() {
		return scriptArea;
	}

	public void setScript(JTextArea script) {
		this.scriptArea = script;
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}
}
