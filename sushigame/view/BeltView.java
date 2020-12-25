package sushigame.view;

import java.awt.Color;import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import comp401sushi.Plate;
import comp401sushi.Roll;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JPanel implements BeltObserver {

	private Belt belt;
	private JLabel[] beltSlots;
	private JButton[] detailButton;
	private JLabel[] details;
	private PlateViewWidget widget;
	

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		beltSlots = new JLabel[belt.getSize()];
		detailButton = new JButton[belt.getSize()];
		details = new JLabel[belt.getSize()];
		widget = new PlateViewWidget(b);
		add(widget);
		widget.refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		widget.refresh();
	}

	private void refresh() {
		widget.refresh();
	}
}
