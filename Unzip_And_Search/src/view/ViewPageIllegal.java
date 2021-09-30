package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import view.CustomComponent.CustomScrollPane;
import view.CustomComponent.PageChangeButton;

public class ViewPageIllegal extends ViewRightPage{

	private JTextArea text_conditions;
	private JButton btn_previous;
	private JButton btn_finish;
	
	public ViewPageIllegal(String title, String content) {
		super(title, content);

		JPanel panel_list_area = new JPanel();
		panel_list_area.setBounds(80, 150, 265, 200);
		panel_list_area.setBackground(color_white); //new Color(173, 214, 255)color_white
		panel_list_area.setLayout(null);
		this.add(panel_list_area);
		
		JLabel label_list = new JLabel("Conditions:");
		label_list.setForeground(color_tit);
		label_list.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		label_list.setBounds(5, 5, 255, 20);
		label_list.setHorizontalAlignment(SwingConstants.LEADING);
		panel_list_area.add(label_list);
		
		JScrollPane scrollPane = new CustomScrollPane();
		scrollPane.setBounds(5, 30, 255, 165);
		panel_list_area.add(scrollPane);

		text_conditions = new JTextArea();
		text_conditions.setFont(new Font("Source Sans Pro", Font.PLAIN, 12));
		text_conditions.setBackground(new Color(255, 255, 255));//new Color(173, 214, 255)
		text_conditions.setDragEnabled(true);
		scrollPane.setViewportView(text_conditions);
		
		btn_previous = new PageChangeButton().getPreviousButton(this.getWidth(), this.getHeight());
		this.add(btn_previous);
		
		btn_finish = new PageChangeButton().getFinishButton(this.getWidth(), this.getHeight());
		this.add(btn_finish);
	}
	
	public void setConditions(String conditions) {
		text_conditions.setText(conditions);
	}
	
	public String getConditions() {
		return text_conditions.getText();
	}
	
	public JButton getBtnPrevious() {
		return btn_previous;
	}
	
	public JButton getBtnFinish() {
		return btn_finish;
	}
}
