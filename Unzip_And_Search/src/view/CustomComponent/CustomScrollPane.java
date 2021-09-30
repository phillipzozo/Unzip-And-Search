package view.CustomComponent;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollPane extends JScrollPane{
	protected Color color_tit = new Color(122, 185, 252);
	protected Color color_content = new Color(102, 178, 255);
	protected Color color_border = new Color(209, 233, 255);
	protected Color color_white = new Color(255, 255, 255);
	
	public CustomScrollPane() {
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, color_border));
		getVerticalScrollBar().setUnitIncrement(16);
		getVerticalScrollBar().setUI(new UI());
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		getHorizontalScrollBar().setUnitIncrement(16);
		getHorizontalScrollBar().setUI(new UI());
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
	}
	
	/*
	 * Internal class
	 */
	private class UI extends BasicScrollBarUI {
		/*
		 *  https://stackoverflow.com/questions/7633354/how-to-hide-the-arrow-buttons-in-a-jscrollbar/7661467
		 */
		@Override
	    protected void configureScrollBarColors() {
	        this.thumbColor = color_tit;
	        this.trackColor = new Color(230, 250, 255);
	        this.scrollBarWidth = 10;
	    }
		
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override    
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(0, 0));
            btn.setMinimumSize(new Dimension(0, 0));
            btn.setMaximumSize(new Dimension(0, 0));
            return btn;
        }
	}
}
