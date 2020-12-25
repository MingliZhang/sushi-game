package sushigame.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver, ActionListener {

	private SushiGameModel game_model;
	private JLabel display;
	private boolean displayBalance;
	private boolean displayConsumed;
	private boolean displaySpoiled;
	private JLabel sort;
	private JButton byBalance;
	private JButton byConsumed;
	private JButton bySpoiled;
	private JPanel buttons;
	
	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);
		
		display = new JLabel();
		setLayout(new GridLayout(2,1));
		add(display, BorderLayout.CENTER);
		display.setText(makeScoreboardBalanceHTML());
		this.displayBalance = true;
		this.displayConsumed = false;
		this.displaySpoiled = false;
		
		buttons = new JPanel();
		
		byBalance = new JButton("Balance");
		byBalance.addActionListener(this);
		byBalance.setSize(50, 20);
		byBalance.setActionCommand("byBalance");
		
		byConsumed = new JButton("Consumed");
		byConsumed.addActionListener(this);
		byConsumed.setActionCommand("byConsumed");
		
		bySpoiled = new JButton("Spoiled");
		bySpoiled.addActionListener(this);
		bySpoiled.setActionCommand("bySpoiled");
		
		buttons.add(byBalance, BorderLayout.NORTH);
		buttons.add(byConsumed, BorderLayout.CENTER);
		buttons.add(bySpoiled, BorderLayout.SOUTH);
		
		add(buttons);
	}

	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "byBalance":
			this.displayBalance = true;
			this.displayConsumed = false;
			this.displaySpoiled = false;
			break;
		case "byConsumed":
			this.displayBalance = false;
			this.displayConsumed = true;
			this.displaySpoiled = false;
			break;
		case "bySpoiled":
			this.displayBalance = false;
			this.displayConsumed = false;
			this.displaySpoiled = true;
			break;
		}
		refresh();
	}
	private String makeScoreboardBalanceHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		Arrays.sort(chefs, new HighToLowBalanceComparator());
		
		for (Chef c : chefs) {
			sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
		}
		sb_html += "<h1>Sort By:</h1>";
		return sb_html;
	}

	private String makeScoreboardConsumedHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		Arrays.sort(chefs, new HighToLowConsumeWeightComparator());
		
		for (Chef c : chefs) {
			sb_html += c.getName() + " (Weight Consumed:" + Math.round(c.getWeightComsumed()*100.0)/100.0 + ") <br>";
		}
		sb_html += "<h1>Sort By:</h1>";
		return sb_html;
	}
	
	private String makeScoreboardSpoiledHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		Arrays.sort(chefs, new LowToHighSpoiledWeightComparator());
		
		for (Chef c : chefs) {
			sb_html += c.getName() + " (Weight Spoiled" + Math.round(c.getWeightSpoiled()*100.0)/100.0 + ") <br>";
		}
		sb_html += "<h1>Sort By:</h1>";
		return sb_html;
	}
	
	public void refresh() {
		if (displayBalance == true) {
			display.setText(makeScoreboardBalanceHTML());
		} else if (displayConsumed == true) {
			display.setText(makeScoreboardConsumedHTML());
		} else if (displaySpoiled == true) {
			display.setText(makeScoreboardSpoiledHTML());
		} else {
			JOptionPane.showMessageDialog(null, "An unknown error occured"); 
		}
	}
	
	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}		
	}

}
