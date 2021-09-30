package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Font;
import javax.swing.SwingConstants;
import view.CustomComponent.DropShadowBorder;
import view.CustomComponent.PageChangeButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.JLayeredPane;

public class View_main extends JFrame {
	private Color color_stepDefault = new Color(78, 118, 187);
	private Color color_stepActivate = new Color(107, 148, 218);
	
	private int outer_border_size = 10;
	private int outer_border_size_all = 20;
	
	private JPanel contentPane;
	
	private JButton btn_close;
	private JButton btn_mini;
	
	/*** Left ***/
	private JPanel panel_left_selectfile;
	private JPanel panel_left_compressformat;
	private JPanel panel_left_illegalformat;
	private JPanel panel_left_processing;
	private JPanel panel_left_report;
	
	/*** Right ***/
	private ViewPageSelect panel_right_selectfile;
	private ViewPageCompressed panel_right_compressed;
	private ViewPageIllegal panel_right_illegal;
	private ViewPagePrograming panel_right_programing;
	private ViewPageReport panel_right_report;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_main frame = new View_main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */
	/**
	 * Create the frame.
	 */
	public View_main() {
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600+ outer_border_size_all, 450 +outer_border_size_all);
		setTitle("Unzip and Search");
		ImageIcon imageIcon = resizeImage_SameScale("analyzing.png", 48, 48);
		setIconImage(imageIcon.getImage());
		//setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));

        FrameDragListener frameDragListener = new FrameDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);
        
        contentPane = new JPanel();
		//contentPane = new ShadowPane(600, 450);
		//contentPane.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
        contentPane.setBackground(new Color(0, 0, 0, 0));
        contentPane.setBorder(new DropShadowBorder(new Color(255, 255, 255), 10, 10, .2f, 8, true, true, true, true));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel_bg = new JPanel();
		panel_bg.setBackground(SystemColor.control);
		panel_bg.setBounds(outer_border_size, outer_border_size, 600, 450);
		panel_bg.setLayout(null);
		contentPane.add(panel_bg);
		
		JPanel panel_top_right = new JPanel();
		panel_top_right.setBounds(175, 0, 425, 20);
		panel_top_right.setBackground(new Color(255, 255, 255));
		panel_top_right.setLayout(null);
		panel_bg.add(panel_top_right);
		
		btn_close = new PageChangeButton().getCloseButton(panel_top_right.getWidth(), panel_top_right.getHeight());
		panel_top_right.add(btn_close);
		
		btn_mini = new PageChangeButton().getMinimizeButton(panel_top_right.getWidth(), panel_top_right.getHeight());
		panel_top_right.add(btn_mini);
		
		JPanel panel_left = Area_left();
		panel_bg.add(panel_left);
		
		JLayeredPane panel_right = Area_right();
		panel_bg.add(panel_right);
	}
	
	private JPanel Area_left() {
		JPanel panel_left = new JPanel();
		panel_left.setBounds(0, 0, 175, 450);
		panel_left.setBackground(new Color(63, 97, 158));
		panel_left.setLayout(null);
		
		JLabel panel_left_tit_1 = new JLabel("Unzip");
		panel_left_tit_1.setBounds(20, 15, 130, 20);
		panel_left_tit_1.setForeground(new Color(230, 250, 255));
		panel_left_tit_1.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
		panel_left.add(panel_left_tit_1);
		
		JLabel panel_left_tit_and = new JLabel("&");
		panel_left_tit_and.setBounds(80, 35, 130, 20);
		panel_left_tit_and.setForeground(new Color(255, 255, 255));
		panel_left_tit_and.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
		panel_left.add(panel_left_tit_and);
		
		JLabel panel_left_tit_2 = new JLabel("Search");
		panel_left_tit_2.setBounds(100, 35, 130, 20);
		panel_left_tit_2.setForeground(new Color(122, 185, 252));
		panel_left_tit_2.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
		panel_left.add(panel_left_tit_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(25, 60, 135, 2);
		panel_left.add(separator);

		//panel_left_selectfile = JPanel_Step(0, 85, "Select File", "file_16.png");
		panel_left_selectfile = JPanel_Step(0, 86, "Select File", "file_16.png");
		panel_left.add(panel_left_selectfile);
		
		panel_left_compressformat = JPanel_Step(0, 125, "Compressed Format", "winrar_16.png");
		panel_left.add(panel_left_compressformat);
		
		panel_left_illegalformat = JPanel_Step(0, 165, "Illegal Format", "security_16.png");
		panel_left.add(panel_left_illegalformat);
		
		panel_left_processing = JPanel_Step(0, 205, "In Programing", "program_16.png");
		panel_left.add(panel_left_processing);
		
		panel_left_report = JPanel_Step(0, 245, "Report", "report_16.png");
		panel_left.add(panel_left_report);
		
		return panel_left;
	}
	
	private JLayeredPane Area_right() {
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(175, 20, 425, 430);
		
		panel_right_selectfile = new ViewPageSelect("Select File", "Drag or select a compressed file or folder from your computer.");
		layeredPane.add(panel_right_selectfile, new Integer(1));

		panel_right_compressed = new ViewPageCompressed("Compressed Format", "Filter conditions for that need to be Found, and also you can click checkbox to auto decompressed those files.");
		panel_right_compressed.setVisible(false);
		layeredPane.add(panel_right_compressed, new Integer(2));
		
		panel_right_illegal = new ViewPageIllegal("Illegal Format", "Filter conditions for that need to be Found.");
		panel_right_illegal.setVisible(false);
		layeredPane.add(panel_right_illegal, new Integer(3));
		
		panel_right_programing = new ViewPagePrograming("In Programing", "Please wait for the processing complete.");
		panel_right_programing.setVisible(false);
		layeredPane.add(panel_right_programing, new Integer(4));
		
		panel_right_report = new ViewPageReport("Report", "Completed! You can click \"Open Report\" to check result.");
		panel_right_report.setVisible(false);
		layeredPane.add(panel_right_report, new Integer(5));
		
		return layeredPane;
	}

	/*
	 * Custom component
	 */
	public JPanel JPanel_Step(int x, int y, String text, String icon_path) {
		JPanel step = new JPanel();
		step.setBounds(x, y, 175, 40);
		step.setBackground(color_stepDefault);
		step.setLayout(null);
		
		int icon_size = 16;
		
		//ImageIcon imageIcon = resizeImage_SameScale(icon_path, icon_size, icon_size);
		ImageIcon imageIcon = new ImageIcon(View_main.class.getResource("/resource/images/"+ icon_path));
	    
		JLabel label_icon = new JLabel("");
		label_icon.setHorizontalAlignment(SwingConstants.CENTER);
		label_icon.setIcon(imageIcon);
		label_icon.setBounds(18, 12, icon_size, icon_size);
		step.add(label_icon);
		
		JLabel label_text = new JLabel(text);
		label_text.setForeground(new Color(240, 240, 240));
		label_text.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 12));
		label_text.setBounds(40, 12, 120, icon_size);
		step.add(label_text);
		
		return step;
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
	

	public JButton getBtnClose() {
		return btn_close;
	}
	public JButton getBtnMinimize() {
		return btn_mini;
	}
	
	/*
	 *  Left 
	 */

	public void setLeftStepActivate(JPanel step) {
		step.setBackground(color_stepActivate);
	}

	public void setLeftStepDefault(ArrayList<JPanel> LeftSteps) {
		for(JPanel step : LeftSteps) {
			step.setBackground(color_stepDefault);
		}
	}
	
	public JPanel getLeftSelectfile() {
		return panel_left_selectfile;
	}
	public JPanel getLeftCompressfomat() {
		return panel_left_compressformat;
	}
	public JPanel getLeftIllegal() {
		return panel_left_illegalformat;
	}
	public JPanel getLeftProcessing() {
		return panel_left_processing;
	}
	public JPanel getLeftReport() {
		return panel_left_report;
	}
	
	/*
	 * Right 
	 */

	public void setRIghtPageShow(Object page) {
		((ViewRightPage)page).setVisible(true);
		//System.out.print(((ViewRightPage)page).getName()+ ".isVisible: "+ ((ViewRightPage)page).isVisible());
	}

	public void setRIghtPageHidden(ArrayList<Object> RIghtPages) {
		for(Object page : RIghtPages) {
			((ViewRightPage)page).setVisible(false);
		}
	}
	
	public JPanel getRightSelectfile() {
		return panel_right_selectfile;
	}
	public JPanel getRightCompressfomat() {
		return panel_right_compressed;
	}
	public JPanel getRightIllegal() {
		return panel_right_illegal;
	}
	public JPanel getRightProcessing() {
		return panel_right_programing;
	}
	public JPanel getRightReport() {
		return panel_right_report;
	}

	/*
	 * Internal class
	 */
	public static class FrameDragListener extends MouseAdapter {
	
	    private final JFrame frame;
	    private Point mouseDownCompCoords = null;
	
	    public FrameDragListener(JFrame frame) {
	        this.frame = frame;
	    }
	
	    public void mouseReleased(MouseEvent e) {
	        mouseDownCompCoords = null;
	    }
	
	    public void mousePressed(MouseEvent e) {
	        mouseDownCompCoords = e.getPoint();
	    }
	
	    public void mouseDragged(MouseEvent e) {
	        Point currCoords = e.getLocationOnScreen();
	        frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
	    }
	}
}
