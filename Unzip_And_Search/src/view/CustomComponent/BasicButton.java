package view.CustomComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class BasicButton extends JButton{
	protected Color color_bg, color_bg_press, color_bg_hover, color_fg, color_border;
	int xOffset_press=0, yOffset_press=0;
	int xOffset_hover=0, yOffset_hover=0;
	boolean havePress_2=false;
	protected Color color_bg_press_2;
	
	public BasicButton() {
		
	}
	
	public BasicButton(String text) {
		super(text);
		setOpaque(false);
		setContentAreaFilled(false);
		setUI(new UI());
		setBorder(BorderFactory.createEmptyBorder());
	}

    /**
     * Set colors and setBackground & setForeground. 
     *
     * @param color_bg a Color
     * @param color_bg_press a Color
     * @param color_fg a Color
     * @param color_border a Color
     */
	public void setStyle(Color color_bg, Color color_bg_press, Color color_fg, Color color_border) {
		this.color_bg = color_bg;
		this.color_bg_press = color_bg_press;
		this.color_fg = color_fg;
		this.color_border = color_border;
		int red = color_bg.getRed();
		int green = color_bg.getGreen();
		int blue = color_bg.getBlue();
		int red_p = color_bg_press.getRed();
		int green_p = color_bg_press.getGreen();
		int blue_p = color_bg_press.getBlue();
		this.color_bg_hover = new Color((red+red_p)/2, (green+green_p)/2, (blue+blue_p)/2);
		
		setBackground(color_bg);
		setForeground(color_fg);
	}

    /**
     * setOffset of Pressed. 
     *
     * @param xOffset a int
     * @param yOffset a int
     * @param color a Color of pressed background
     */
	public void setOffsetPressed(int xOffset, int yOffset) {
		xOffset_press = xOffset;
		yOffset_press = yOffset;
		
		havePress_2 = false;
	}

    /**
     * setOffset of Pressed. 
     *
     * @param xOffset a int
     * @param yOffset a int
     * @param color a Color of pressed background
     */
	public void setOffsetPressed(int xOffset, int yOffset, Color color) {
		xOffset_press = xOffset;
		yOffset_press = yOffset;
		
		havePress_2 = true;
		color_bg_press_2 = color;
	}

    /**
     * setOffset of Hover. 
     *
     * @param xOffset a int
     * @param yOffset a int
     * @param color a Color of hover background
     */
	public void setOffsetHover(int xOffset, int yOffset) {
		xOffset_hover = xOffset;
		yOffset_hover = yOffset;
		
		havePress_2 = false;
	}

    /**
     * setOffset of Hover. 
     *
     * @param xOffset a int
     * @param yOffset a int
     * @param color a Color of hover background
     */
	public void setOffsetHover(int xOffset, int yOffset, Color color) {
		xOffset_hover = xOffset;
		yOffset_hover = yOffset;
		
		havePress_2 = true;
		color_bg_press_2 = color;
	}
    
    protected void btnDefault (Graphics g, JComponent c) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color_bg);
        g.fillRoundRect(0, 0, size.width, size.height, 0, 0);
    }

    protected void btnPressed (Graphics g, JComponent c) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(havePress_2) {
	        g.setColor(color_bg_press_2);
	        g.fillRoundRect(0, 0, size.width, size.height, 0, 0);
        }
        g.setColor(color_bg_press);
        g.fillRoundRect(xOffset_press, yOffset_press, size.width- xOffset_press, size.height- yOffset_press, 0, 0);
    }

    protected void btnMoved (Graphics g, JComponent c) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color_bg_hover);
        g.fillRoundRect(xOffset_hover, yOffset_hover, size.width- xOffset_hover, size.height- yOffset_hover, 0, 0);
    }
	
	/*
	 * Internal class
	 */
	private class UI extends BasicButtonUI {
		
		public UI() {
		}
		
	    @Override
	    public void installUI (JComponent c) {
	        super.installUI(c);
	        AbstractButton button = (AbstractButton) c;
	        button.setOpaque(false);
	        //button.setBorderPainted(false);
	        button.setContentAreaFilled(false);
	    }

	    @Override
	    public void paint (Graphics g, JComponent c) {
	        AbstractButton b = (AbstractButton) c;
	        //paintBackground(g, b, b.getModel().isPressed()? 2 : 0);
	        if(b.getModel().isPressed()) { btnPressed(g, b);}
	        else if(b.getModel().isRollover()) { btnMoved(g, b);}
	        else { btnDefault(g, b);}
	        super.paint(g, c);
	    }
	}
}
