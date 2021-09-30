package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewRightPage extends JPanel{
	protected Color color_tit = new Color(99, 161, 226);
	protected Color color_content = new Color(99, 161, 226);
	protected Color color_border = new Color(209, 233, 255);
	protected Color color_white = new Color(255, 255, 255);
	private String title="", content="";
	
	private void init() {
		setBounds(0, 0, 425, 430);
		setBackground(color_white);
		setLayout(null);
		addTitleAndContent();
	}
	
	protected ViewRightPage(String title, String content) {
		setTitle(title);
		setContent(content);
		init();
	}
	
	private void addTitleAndContent() {
		JPanel panel_tit_bg = new JPanel();
		panel_tit_bg.setBounds(0, 30, 425, 100);
		panel_tit_bg.setBackground(new Color(230, 250, 255));
		panel_tit_bg.setLayout(null);
		this.add(panel_tit_bg);
		
		JLabel label_tit = new JLabel(title);
		label_tit.setForeground(color_tit);
		label_tit.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 34));
		label_tit.setBounds(15, 10, 380, 40);
		label_tit.setHorizontalAlignment(SwingConstants.LEADING);
		panel_tit_bg.add(label_tit);
		
		JLabel label_content = new JLabel("<html><body>"+ content+ "</body></html>");
		label_content.setForeground(color_content);
		label_content.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		label_content.setBounds(15, 50, 380, 50);
		//label_content.setBorder(BorderFactory.createDashedBorder(color_border, 4, 3, 2, false));
		label_content.setVerticalAlignment(SwingConstants.TOP);
		panel_tit_bg.add(label_content);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
