package view.CustomComponent;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

public class PageChangeButton extends BasicButton {
	
	public PageChangeButton() {
	}
	
	public PageChangeButton(String text) {
		super(text);
	}
	
	public BasicButton getCloseButton(int width_bg, int height_bg) {
		BasicButton btn = new PageChangeButton("❌");
		color_bg = new Color(255, 255, 255);
		color_bg_press = new Color(255, 179, 179);
		color_fg = new Color(140, 140, 140);
		
		btn.setBounds(width_bg-40, 0, 40, 20);
		//btn.setFont(new Font("PMingLiU", Font.PLAIN, 16));
		btn.setFont(UIManager.getFont("Button.font"));
		btn.setStyle(color_bg, color_bg_press, color_fg, color_border);
		return btn;
	}
	
	public BasicButton getMinimizeButton(int width_bg, int height_bg) {
		BasicButton btn = new PageChangeButton("➖");
		color_bg = new Color(255, 255, 255);
		color_bg_press = new Color(240, 240, 240);
		color_fg = new Color(140, 140, 140);
		
		btn.setBounds(width_bg-80, 0, 40, 20);
		//btn.setFont(new Font("PMingLiU", Font.PLAIN, 16));
		btn.setFont(UIManager.getFont("Button.font"));
		btn.setStyle(color_bg, color_bg_press, color_fg, color_border);
		return btn;
	}
	
	public BasicButton getPreviousButton(int width_bg, int height_bg) {
		BasicButton btn = new PageChangeButton("<< Previous");
		color_bg = new Color(240, 240, 240);
		color_bg_press = new Color(230, 230, 230);
		Color color_bg_press_2 = new Color(190, 190, 190);
		color_fg = new Color(180, 180, 190);

		btn.setBounds(10, height_bg-10-40, 120, 40);
		btn.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 16));
		btn.setStyle(color_bg, color_bg_press, color_fg, color_border);
		btn.setOffsetPressed(4, 4, color_bg_press_2);
		return btn;
	}
	
	public BasicButton getNextButton(int width_bg, int height_bg) {
		BasicButton btn = new PageChangeButton("Next >>");
		color_bg = new Color(230, 250, 255); //color_border
		color_bg_press = new Color(220, 240, 245);
		Color color_bg_press_2 = new Color(142, 205, 252);
		color_fg = new Color(122, 185, 252); //color_tit

		btn.setBounds(width_bg-10-120, height_bg-10-40, 120, 40);
		btn.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 16));
		btn.setStyle(color_bg, color_bg_press, color_fg, color_border);
		btn.setOffsetPressed(4, 4, color_bg_press_2);
		return btn;
	}
	
	public BasicButton getOpenReportButton(int width_bg, int height_bg) {
		BasicButton btn = new PageChangeButton("Open Report");
		color_bg = new Color(122, 185, 252); //color_border
		color_bg_press = new Color(84, 165, 252);
		Color color_bg_press_2 = new Color(142, 205, 252);
		color_fg = new Color(255, 255, 255); //color_tit

		btn.setBounds(width_bg-100, height_bg-10-25, 100, 25);
		btn.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 12));
		btn.setStyle(color_bg, color_bg_press, color_fg, color_border);
		//btn.setOffsetPressed(4, 4, color_bg_press_2);
		return btn;
	}
	
	public BasicButton getFinishButton(int width_bg, int height_bg) {
		BasicButton btn = new PageChangeButton("Finish");
		color_bg = new Color(215, 215, 234);
		color_bg_press = new Color(198, 198, 226);
		Color color_bg_press_2 = new Color(116, 116, 185);
		color_fg = new Color(116, 116, 185);

		btn.setBounds(width_bg-10-120, height_bg-10-40, 120, 40);
		btn.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 16));
		btn.setStyle(color_bg, color_bg_press, color_fg, color_border);
		btn.setOffsetPressed(4, 4, color_bg_press_2);
		return btn;
	}
	
	public BasicButton getExitButton(int width_bg, int height_bg) {
		BasicButton btn = new PageChangeButton("Exit");
		color_bg = new Color(255, 179, 179);
		color_bg_press = new Color(255, 117, 117);
		Color color_bg_press_2 = new Color(255, 82, 82);
		color_fg = new Color(255, 255, 255);

		btn.setBounds(width_bg-10-120, height_bg-10-40, 120, 40);
		btn.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 16));
		btn.setStyle(color_bg, color_bg_press, color_fg, color_border);
		btn.setOffsetPressed(4, 4, color_bg_press_2);
		return btn;
	}
}

