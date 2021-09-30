package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import view.CustomComponent.CustomScrollPane;
import view.CustomComponent.PageChangeButton;

public class ViewPagePrograming extends ViewRightPage{
	
	private JProgressBar progressbar;
	private JTextArea text_log;
	private JButton btn_next;
	private JLabel label_programing;
	private JLabel label_programing_bg;
	
	public ViewPagePrograming(String title, String content) {
		super(title, content);

		JPanel panel_list_area = new JPanel();
		panel_list_area.setBounds(60, 150, 305, 200);
		panel_list_area.setBackground(color_white); //new Color(173, 214, 255)color_white
		panel_list_area.setLayout(null);
		this.add(panel_list_area);
		
		JScrollPane scrollPane = new CustomScrollPane();
		scrollPane.setBounds(0, 0, 305, 175);
		panel_list_area.add(scrollPane);

		text_log = new JTextArea();
		text_log.setFont(new Font("Source Sans Pro", Font.PLAIN, 12));
		text_log.setBackground(new Color(255, 255, 255));//new Color(173, 214, 255)
		text_log.setDragEnabled(true);
		text_log.setEditable(false);
		text_log.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		scrollPane.setViewportView(text_log);
		
		progressbar = new JProgressBar(0, 100);
		progressbar.setBounds(0, 180, 305, 18);
		progressbar.setValue(50);
		progressbar.setStringPainted(true);
		progressbar.setBackground(new Color(230, 250, 255));
		progressbar.setForeground(color_tit);
		progressbar.setBorderPainted(false);
		//panel_list_area.add(progressbar);

		label_programing = new JLabel("Completed", JLabel.CENTER);
		label_programing.setBounds(0, 180, 305, 18);
		label_programing.setForeground(color_white);
		label_programing.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		label_programing.setVisible(false);
		panel_list_area.add(label_programing);
		
		ImageIcon loading = new ImageIcon(View_main.class.getResource("/resource/images/programing.gif"));
		Image image = loading.getImage();
		image = image.getScaledInstance(305, 18, Image.SCALE_REPLICATE);
		loading = new ImageIcon(image);
		label_programing_bg = new JLabel(loading, JLabel.CENTER);
		label_programing_bg.setBounds(0, 180, 305, 18);
		label_programing_bg.setForeground(color_tit);
		label_programing_bg.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		panel_list_area.add(label_programing_bg);
		
		btn_next = new PageChangeButton().getNextButton(this.getWidth(), this.getHeight());
		this.add(btn_next);
	}
	
	public void setMax(int max) {
		progressbar.setMaximum(max);
	}
	
	public void setNum(int num) {
		progressbar.setValue(num);
	}
	
	public void program_completed() {
		ImageIcon loading = new ImageIcon(View_main.class.getResource("/resource/images/programing_completed.png"));
		Image image = loading.getImage();
		image = image.getScaledInstance(305, 18, Image.SCALE_REPLICATE);
		loading = new ImageIcon(image);
		label_programing_bg.setIcon(loading);
		
		label_programing.setVisible(true);
	}
	
	public JTextArea getTextarea() {
		return text_log;
	}
	
	public JButton getBtnNext() {
		return btn_next;
	}
}
