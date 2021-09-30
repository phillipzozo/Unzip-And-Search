package view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicProgressBarUI;

import view.CustomComponent.PageChangeButton;

public class ViewPageReport extends ViewRightPage{
	private Color color_pass = new Color(168, 255, 168);
	private Color color_fail = new Color(255, 100, 100);
	private ArrayList<String> item_list = new ArrayList<String>();
	private ArrayList<Boolean> status_list = new ArrayList<Boolean>();

	private JPanel panel_list_area;
	private JProgressBar summary;
	private JButton btn_open_report;
	private JButton btn_exit;
	
	public ViewPageReport(String title, String content) {
		super(title, content);

		panel_list_area = new JPanel();
		panel_list_area.setBounds(80, 150, 265, 200);
		panel_list_area.setBackground(color_white);
		panel_list_area.setLayout(null);
		this.add(panel_list_area);

		JPanel panel_summary = new JPanel();
		panel_summary.setBounds(0, 0, 265, 40);
		panel_summary.setBackground(color_white);
		panel_summary.setLayout(null);
		panel_list_area.add(panel_summary);
		
		JLabel label_summary = new JLabel("Summary:");
		label_summary.setForeground(color_tit);
		label_summary.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 16));
		label_summary.setBounds(0, 0, 120, 20);
		label_summary.setHorizontalAlignment(SwingConstants.LEADING);
		panel_summary.add(label_summary);
		
		summary = new JProgressBar(0, 100);
		summary.setBounds(0, 20, 265, 12);
		summary.setValue(50);
		summary.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 12));
		summary.setUI(new BasicProgressBarUI() {
		      protected Color getSelectionBackground() { return Color.white; }
		      protected Color getSelectionForeground() { return Color.black; }
	    });
		summary.setStringPainted(true);
		summary.setBackground(color_fail);
		summary.setForeground(color_pass);
		summary.setBorderPainted(false);
		summary.setBorder(null);
		panel_summary.add(summary);
		/*
		int item = item_list.size();
		for(int i=0; i<item; i++) {
			panel_list_area.add(result_row(0, 50+ 20*i, 265, 20, item_list.get(i), status_list.get(i)));
		}*/
		
		btn_open_report = new PageChangeButton().getOpenReportButton(panel_list_area.getWidth(), panel_list_area.getHeight());
		panel_list_area.add(btn_open_report);
		
		btn_exit = new PageChangeButton().getExitButton(this.getWidth(), this.getHeight());
		this.add(btn_exit);
	}
	
	private JPanel result_row(int x, int y, int width, int height, String name, boolean status){
		JPanel panel_item = new JPanel();
		panel_item.setBounds(x, y, width, height);
		panel_item.setBackground(color_white);
		panel_item.setLayout(null);
		
		JLabel label_row = new JLabel(name);
		label_row.setBounds(0, 3, width-120, 14);
		label_row.setForeground(new Color(71, 163, 255));
		label_row.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		label_row.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_item.add(label_row);
		
		JPanel panel_row = new JPanel();
		panel_row.setBounds(width-100, 4, 100, 12);
		if(status) panel_row.setBackground(color_pass);
		else panel_row.setBackground(color_fail);
		panel_row.setLayout(null);
		panel_item.add(panel_row);
		
		return panel_item;
	}

	public JButton getBtnOpenReport() {
		return btn_open_report;
	}
	
	public JButton getBtnExit() {
		return btn_exit;
	}
	
	public void setItemStatusList(ArrayList<String> items, ArrayList<Boolean> item_status) {
		item_list.clear();
		if (items != null) {
		    for (String item : items) {
		        //System.out.println(item);
		        item_list.add(item);
		    }
		}
		status_list.clear();
		if (item_status != null) {
		    for (Boolean item : item_status) {
			    //System.out.println(item);
			    status_list.add(item);
		    }
		}

		int pass_amount = 0;
		int item = item_list.size();
		for(int i=0; i<item; i++) {
			panel_list_area.add(result_row(0, 50+ 20*i, 265, 20, item_list.get(i), status_list.get(i)));
			if(status_list.get(i) == true) pass_amount += 1;
		}
		summary.setMaximum(item);
		summary.setValue(pass_amount);
		summary.setString(pass_amount+ " / "+ item);
	}

}
