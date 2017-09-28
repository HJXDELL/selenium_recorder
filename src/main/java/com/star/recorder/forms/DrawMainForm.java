package com.star.recorder.forms;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.openqa.selenium.WebDriver;
import java.util.LinkedHashMap;

public class DrawMainForm {
	private final FormSettings FORM = new FormSettings();
	private JTabbedPane mainForm;
	private WebDriver driver;
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	//激活指定的tab页签
	public void activeTab(JPanel activePanel) {
		mainForm.setSelectedComponent(activePanel);
		mainForm.updateUI();
	}
	
	public void refreshUI() {
		mainForm.updateUI();
	}
	
	//配置窗口关闭事件的监听
	public void setWindowCloseEvent(JFrame frame){
		frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				if (driver != null){
					try{
						driver.close();
					} catch (Exception ex){
						JOptionPane.showMessageDialog(mainForm, "WebDriver error occured, now quiting !");
					} finally{
						driver.quit();
					}
				}
				System.exit(0);
			}
		});
	}

	//绘制主窗口form
	public void defineMainForm(LinkedHashMap<String, JPanel> panels) {
		mainForm = new JTabbedPane();
		JFrame frame = new JFrame();

		Iterator<String> it = panels.keySet().iterator();// 谁鄙视我不用entrySet谁傻逼
		while (it.hasNext()) {
			String tabName = it.next();
			mainForm.addTab(tabName, panels.get(tabName));
		}

		frame.getContentPane().add(mainForm);
		frame.setVisible(true);
		frame.setTitle(FORM.FORM_TITLE);
		frame.setSize(FORM.FORM_WIDTH, FORM.FORM_HIGHTH);
		frame.setAlwaysOnTop(FORM.KEEP_ON_TOP);
		frame.setResizable(FORM.RESIZABLE);
		frame.setBackground(FORM.FORM_BACKGROUD_COLOR);
		setWindowCloseEvent(frame);
	}
}