package com.star.recorder.forms;

import java.awt.Color;
import java.awt.Font;

import com.star.recorder.config.ParseProperties;

public class FormSettings {
	private Font buttonFont;
	private Font bigTextFont;
	private int bigTextSize;

	public final ParseProperties config = new ParseProperties("config/record_config.properties");
	public final int FORM_WIDTH = Integer.valueOf(config.get("form.width"));
	public final int FORM_HIGHTH = Integer.valueOf(config.get("form.highth"));
	public final boolean KEEP_ON_TOP = Boolean.parseBoolean(config.get("form.keep_on_top"));
	public final boolean RESIZABLE = Boolean.parseBoolean(config.get("form.resizable"));
	public final Color editBack = new Color(200, 230, 200);
	public final Color editFront = Color.darkGray;

	public FormSettings() {
		if (FORM_HIGHTH < 550) {
			throw new RuntimeException("please config a bigger image resolution, " + FORM_WIDTH + " X " + FORM_HIGHTH
					+ " is to small for this tool!");
		} else if (FORM_HIGHTH >= 550 && FORM_HIGHTH < 768) {
			bigTextSize = 16;
			buttonFont = new Font("Arial", Font.BOLD, 12);
		} else if (FORM_HIGHTH >= 768 && FORM_HIGHTH < 900) {
			bigTextSize = 18;
			buttonFont = new Font("Arial", Font.BOLD, 13);
		} else {
			bigTextSize = 20;
			buttonFont = new Font("Arial", Font.BOLD, 14);
		}
		bigTextFont = new Font("Arial", Font.BOLD, bigTextSize);
	}

	public final Font TEXT_NORMAL_FONT = new Font("幼圆", Font.ROMAN_BASELINE, 14);
	public final Font TEXT_ITALIC_FONT = new Font("幼圆", Font.ITALIC, 14);
	public final int GAP_WIDTH = 20;
	public final int GAP_HIGHTH = 20;
	public final int TEXT_AREA_WIDTH = FORM_WIDTH - GAP_WIDTH * 3;
	public final int TEXT_FIELD_HIGHTH = (FORM_HIGHTH - GAP_HIGHTH) / 25;
	public final String BROWSER_MODE = config.get("browser");
	public final String CODE_STYLES = config.get("code.styles");
	public final String FORM_TITLE = config.get("form.title");
	public final Color FORM_BACKGROUD_COLOR = Color.GRAY;

	public Font getButtonFont() {
		return this.buttonFont;
	}

	public void setButtonFont(Font buttonFont) {
		this.buttonFont = buttonFont;
	}

	public Font getBigTextFont() {
		return bigTextFont;
	}

	public void setBigTextFont(Font bigTextFont) {
		this.bigTextFont = bigTextFont;
	}

	public int getBigTextSize() {
		return bigTextSize;
	}

	public void setBigTextSize(int bigTextSize) {
		this.bigTextSize = bigTextSize;
	}
}