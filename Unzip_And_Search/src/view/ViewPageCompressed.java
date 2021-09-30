package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import view.CustomComponent.CustomScrollPane;
import view.CustomComponent.PageChangeButton;

public class ViewPageCompressed extends ViewRightPage{

	private JCheckBox checkbox_decompress;
	private JTextArea text_conditions;
	private JButton btn_previous;
	private JButton btn_next;
	
	public ViewPageCompressed(String title, String content) {
		super(title, content);

		JPanel panel_list_area = new JPanel();
		panel_list_area.setBounds(80, 150, 265, 200);
		panel_list_area.setBackground(color_white);
		panel_list_area.setLayout(null);
		this.add(panel_list_area);
		
		JLabel label_list = new JLabel("Conditions:");
		label_list.setForeground(color_tit);
		label_list.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		label_list.setBounds(5, 5, 255, 20);
		label_list.setHorizontalAlignment(SwingConstants.LEADING);
		panel_list_area.add(label_list);
		
		checkbox_decompress = new JCheckBox("Auto decompressed");
		checkbox_decompress.setBounds(265-133, 5, 133, 20);
		checkbox_decompress.setBackground(color_white);
		checkbox_decompress.setForeground(color_tit);
		checkbox_decompress.setIcon(new CheckBoxIcon());
		checkbox_decompress.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 12));
		checkbox_decompress.setSelected(true);
		panel_list_area.add(checkbox_decompress);
		
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
		
		btn_next = new PageChangeButton().getNextButton(this.getWidth(), this.getHeight());
		this.add(btn_next);
	}
	
	public Boolean isAutoDecompress() {
		return checkbox_decompress.isSelected();
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
	
	public JButton getBtnNext() {
		return btn_next;
	}

	/*
	 * Functions
	 */
	public ImageIcon resizeImage_SameScale(String icon_path, float target_w, float target_h) {
		ImageIcon imageIcon = new ImageIcon(View_main.class.getResource("/resource/images/"+ icon_path));
		Image image = imageIcon.getImage();
		
		float w = image.getWidth(null);
		float h = image.getHeight(null);
		float w_diff = (w>target_w)?(w / target_w):(target_w / w);
		float h_diff = (h>target_h)?(h / target_h):(target_h / h);
		float rate = (w_diff>h_diff)?w_diff:h_diff;
		if(w_diff == h_diff) {
			image = image.getScaledInstance((int)(w/rate), (int)(h/rate), Image.SCALE_REPLICATE);
		}else if(w_diff > h_diff){
			image = image.getScaledInstance((int)target_w, (int)(h/rate), Image.SCALE_REPLICATE);
		}else{
			image = image.getScaledInstance((int)(w/rate), (int)target_h, Image.SCALE_REPLICATE);
		}

		imageIcon = new ImageIcon(image);
		return imageIcon;
	}
	
    private class CheckBoxIcon implements Icon {
		@Override
		public void paintIcon(Component component, Graphics g, int x, int y) {
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        
			AbstractButton abstractButton = (AbstractButton)component;
			ButtonModel buttonModel = abstractButton.getModel();
			
			g2.setColor(new Color(100, 100, 100));
			if(buttonModel.isSelected()) {
				g2.fillRoundRect(4, 4, 15, 15, 0, 0);
			}else {
				g2.drawRect(4, 4, 14, 14);
			}
		}

		@Override
		public int getIconWidth() {
			return 14;
		}

		@Override
		public int getIconHeight() {
			return 14;
		}
	}
}
