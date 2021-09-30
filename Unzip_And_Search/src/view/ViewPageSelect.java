package view;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import view.CustomComponent.BasicButton;
import view.CustomComponent.PageChangeButton;

public class ViewPageSelect extends ViewRightPage{
	
	private BasicButton btn_selectfile;
	private JTextArea text_path;
	private JButton btn_next;
	
	public ViewPageSelect(String title, String content) {
		super(title, content);
		
		JPanel panel_selectfile_area = new JPanel();
		panel_selectfile_area.setBounds(80, 190, 265, 130);
		panel_selectfile_area.setBackground(color_white); //new Color(173, 214, 255)color_white
		panel_selectfile_area.setLayout(null);
		this.add(panel_selectfile_area);
		
		btn_selectfile = new BasicButton("Drag a file or Browser");
		btn_selectfile.setBounds(0, 0, 265, 80);
		btn_selectfile.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
		btn_selectfile.setBorder(BorderFactory.createDashedBorder(color_border, 5, 3, 2, false));
		btn_selectfile.setStyle(color_white, new Color(240, 245, 255), color_tit, color_border);
		panel_selectfile_area.add(btn_selectfile);
		
		JLabel label_path = new JLabel("Path:");
		label_path.setForeground(color_tit);
		label_path.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		label_path.setBounds(0, 85, 265, 20);
		label_path.setHorizontalAlignment(SwingConstants.LEADING);
		panel_selectfile_area.add(label_path);
		
		JLabel label_error_msg = new JLabel();
		label_error_msg.setForeground(new Color(255, 82, 82));
		label_error_msg.setFont(new Font("Source Sans Pro", Font.PLAIN, 12));
		label_error_msg.setBounds(panel_selectfile_area.getWidth()-65, 85, 265, 20);
		label_error_msg.setHorizontalAlignment(SwingConstants.LEADING);
		panel_selectfile_area.add(label_error_msg);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 105, 265, 22);
		panel_selectfile_area.add(scrollPane);
		
		text_path = new JTextArea();
		text_path.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 14));
		text_path.setBackground(new Color(240, 245, 255));//new Color(173, 214, 255)
		text_path.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, color_border));
		text_path.setDragEnabled(true);
		text_path.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		text_path.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				verify();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				verify();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				verify();
			}
			
			public boolean verify(){
				File file = new File(text_path.getText());
				//System.out.println("path "+ file.getName());
				if(file.isDirectory() || file.isFile()) {	
			        label_error_msg.setText("");
			        btn_next.setEnabled(true);
			        //return true;
			    }else{
			        label_error_msg.setText("Invalid Path.");
			        btn_next.setEnabled(false);
			        //return false;
			    }
				return text_path.isValid();
		    }
		});
		
		scrollPane.setColumnHeaderView(text_path);
		
		btn_next = new PageChangeButton().getNextButton(this.getWidth(), this.getHeight());
		btn_next.setEnabled(false);
		this.add(btn_next);
	}
	
	public JButton getBtnSelectFile() {
		return btn_selectfile;
	}
	
	public JTextArea getTextAreaPath() {
		return text_path;
	}
	
	public void setTextPath(String path) {
		text_path.setText(path);
	}
	
	public String getTextPath() {
		return text_path.getText();
	}
	
	public JButton getBtnNext() {
		return btn_next;
	}
}
