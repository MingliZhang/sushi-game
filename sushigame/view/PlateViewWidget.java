package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import comp401sushi.Plate;
import comp401sushi.Roll;
import sushigame.model.Belt;

public class PlateViewWidget extends JPanel{
	
	private Belt belt;
	protected JLabel[] beltSlots;
	protected JButton[] detailButton;
	protected String[] details;
	private JOptionPane options;



	public PlateViewWidget(Belt b) {
		if (b != null) {
			this.belt = b;
			this.beltSlots = new JLabel[belt.getSize()];
			this.detailButton = new JButton[belt.getSize()];
			this.details = new String[belt.getSize()];
			setLayout(new GridLayout(belt.getSize(), 1));
			for (int i = 0; i < belt.getSize(); i++) {
				JLabel plates = new JLabel("");
				plates.setMinimumSize(new Dimension(400, 40));
				plates.setPreferredSize(new Dimension(400, 40));
				plates.setOpaque(true);
				plates.setBackground(Color.WHITE);
				plates.setHorizontalAlignment(SwingConstants.CENTER);
				Border border = LineBorder.createGrayLineBorder();
				plates.setToolTipText("<html>" + "This spot is " + "<br>" + "EMPTY" + "</html>");
				plates.setBorder(border);
				add(plates);
				beltSlots[i] = plates;
				
				JButton plateButton = new JButton("details");
				plateButton.setMinimumSize(new Dimension(30, 40));
				plateButton.setPreferredSize(new Dimension(30, 40));
				plateButton.setOpaque(true);
				add(plateButton);
				detailButton[i] = plateButton;
			}
		}
		refresh();
	}

	public void refresh() {
		for( JButton currentButton: detailButton ) {
		    for( ActionListener al : currentButton.getActionListeners() ) {
		        currentButton.removeActionListener( al );
		    }
		}
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JLabel plabel = beltSlots[i];

			if (p == null) {
				plabel.setText("");
				plabel.setBackground(Color.WHITE);
				plabel.setToolTipText("<html>" + "This spot is " + "<br>" + "EMPTY" + "</html>");
				detailButton[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(options, "This spot is still Empty!!");
					}
				});
			} else {
				plabel.setText(p.getContents().getName() + " "
							+ "  Made By: " + p.getChef() + " "
							+ "  Age: " + belt.getAgeOfPlateAtPosition(i));
				if (!(p.getContents() instanceof Roll)) {
					details[i] = "<html>" + "Chef: " + belt.getPlateAtPosition(i).getChef().getName() + "<br>" + " Sushi Name: " + 
							p.getContents().getName() + "<br>" + " Age: " + belt.getAgeOfPlateAtPosition(i) +
							"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
							"<br>" + "Has Rice? " + p.getContents().getHasRice() +
							"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() ;
					
					plabel.setText(p.getContents().getName());
					String temp = details[i];
					detailButton[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(options, temp);
						}
					});
					switch (p.getColor()) {
					case RED:
						plabel.setBackground(Color.RED);
						plabel.setForeground(Color.WHITE);
						plabel.setToolTipText("<html>" + "Total Calories of " +
								"<br>" + "price of: " + p.getPrice() +
								"<br>" + p.getContents().getCalories() +
								"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
								"<br>" + "Has Rice? " + p.getContents().getHasRice() +
								"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() +
								"<html>");
						break;
					case GREEN:
						plabel.setBackground(Color.GREEN);
						plabel.setForeground(Color.BLACK);
						plabel.setToolTipText("<html>" + "Total Calories of " +
								"<br>" + "price of: " + p.getPrice() +
								"<br>" + p.getContents().getCalories() +
								"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
								"<br>" + "Has Rice? " + p.getContents().getHasRice() +
								"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() +
								"<html>");
						break;
					case BLUE:
						plabel.setBackground(Color.BLUE);
						plabel.setForeground(Color.WHITE);
						plabel.setToolTipText("<html>" + "Total Calories of " +
								"<br>" + "price of: " + p.getPrice() +
								"<br>" + p.getContents().getCalories() +
								"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
								"<br>" + "Has Rice? " + p.getContents().getHasRice() +
								"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() +
								"<html>");
						break;
					case GOLD:
						plabel.setBackground(Color.YELLOW);
						plabel.setForeground(Color.BLACK);
						plabel.setToolTipText("<html>" + "Total Calories of " +
								"<br>" + "price of: " + p.getPrice() +
								"<br>" + p.getContents().getCalories() +
								"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
								"<br>" + "Has Rice? " + p.getContents().getHasRice() +
								"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() +
								"<html>");
						break;
					}
				}
				if (p.getContents() instanceof Roll) {
					String ings = "<html>" + p.getContents().getIngredients()[0].getAmount() + " Ounces of " + 
							p.getContents().getIngredients()[0].getName() + "<html>";
					for (int j = 1; j < p.getContents().getIngredients().length;j ++) {
						ings += "<br>" + p.getContents().getIngredients()[j].getAmount() + " Ounces of " + 
								p.getContents().getIngredients()[j].getName() + "<html>";
					}
					details[i] = "<html>" + "Chef: " + belt.getPlateAtPosition(i).getChef().getName() + "<br>" + " Sushi Name: " + 
							p.getContents().getName() + "<br>" + " Age: " + belt.getAgeOfPlateAtPosition(i) + "<br>" + "Ingredents:" + 
							"<br>" + ings + 
							"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
							"<br>" + "Has Rice? " + p.getContents().getHasRice() +
							"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() ;
					plabel.setText(p.getContents().getName());
					String temp = details[i];
					detailButton[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(options, temp);
						}
					});
					switch (p.getColor()) {
					case RED:
						plabel.setBackground(Color.RED);
						plabel.setForeground(Color.WHITE);
						plabel.setToolTipText("<html>" + "<br>" + "Ingredents include: " + "<br>" + ings +
								"<br>" +"Total Calories of " + p.getContents().getCalories() +
								"<br>" + "price of: " + p.getPrice() +
								"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
								"<br>" + "Has Rice? " + p.getContents().getHasRice() +
								"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() +
								"<html>");
						break;
					case GREEN:
						plabel.setBackground(Color.GREEN);
						plabel.setForeground(Color.BLACK);
						plabel.setToolTipText("<html>" + ings +
								"<br>" +"Total Calories of " + p.getContents().getCalories() +
								"<br>" + "price of: " + p.getPrice() +
								"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
								"<br>" + "Has Rice? " + p.getContents().getHasRice() +
								"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() +
								"<html>");
						break;
					case BLUE:
						plabel.setBackground(Color.BLUE);
						plabel.setForeground(Color.WHITE);
						plabel.setToolTipText("<html>" + ings +
								"<br>" +"Total Calories of " + p.getContents().getCalories() +
								"<br>" + "price of: " + p.getPrice() +
								"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
								"<br>" + "Has Rice? " + p.getContents().getHasRice() +
								"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() +
								"<html>");
						break;
					case GOLD:
						plabel.setBackground(Color.YELLOW);
						plabel.setForeground(Color.BLACK);
						plabel.setToolTipText("<html>" + ings +
								"<br>" +"Total Calories of " + p.getContents().getCalories() +
								"<br>" + "price of: " + p.getPrice() +
								"<br>" + "For vegetarian? " + p.getContents().getIsVegetarian() +
								"<br>" + "Has Rice? " + p.getContents().getHasRice() +
								"<br>" + "Has Shellfish? " + p.getContents().getHasShellfish() +
								"<html>");
						break;
					}
				}
			}
		}
	}
}
