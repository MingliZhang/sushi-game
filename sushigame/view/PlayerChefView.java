package sushigame.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import comp401sushi.AvocadoPortion;
import comp401sushi.CrabPortion;
import comp401sushi.EelPortion;
import comp401sushi.Ingredient;
import comp401sushi.IngredientPortion;
import comp401sushi.Nigiri;
import comp401sushi.Nigiri.NigiriType;
import comp401sushi.Plate.Color;
import comp401sushi.Plate;
import comp401sushi.RedPlate;
import comp401sushi.RicePortion;
import comp401sushi.Roll;
import comp401sushi.Sashimi;
import comp401sushi.Sashimi.SashimiType;
import comp401sushi.SeaweedPortion;
import comp401sushi.ShrimpPortion;
import comp401sushi.Sushi;
import comp401sushi.TunaPortion;
import comp401sushi.YellowtailPortion;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private Sushi kmp_roll;
	private Sushi crab_sashimi;
	private Sushi eel_nigiri;
	private int belt_size;
	private Plate.Color color = Plate.Color.RED;
	private String type = "Tuna";
	private int setPosition = 0;
	private double setPrice = 5.00;
	private String nigiriSashimiORRoll = "Nigiri";
	private String[] nOrSOrR = {"Nigiri", "Sashimi", "Roll"};
	private String[] nOrSTypes = {"Tuna", "YellowTail", "Eel", "Crab", "Shrimp"};
	private String[] ings = {"Avocado", "Crab", "Eel", "Rice", "Seaweed", "Shrimp", "Tuna", "Yellowtail"};
	private String ing = "Avocado";
	private double amount;
	private ArrayList<IngredientPortion> ingPortions = new ArrayList<IngredientPortion>();
	private boolean avocadoAdded;
	private boolean crabAdded;
	private boolean eelAdded;
	private boolean riceAdded;
	private boolean seaweedAdded;
	private boolean shrimpAdded;
	private boolean tunaAdded;
	private boolean yellowtailAdded;
	
	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton sashimi_button = new JButton("Make red plate of crab sashimi at position 3");
		sashimi_button.setActionCommand("red_crab_sashimi_at_3");
		sashimi_button.addActionListener(this);
		add(sashimi_button);

		JButton nigiri_button = new JButton("Make blue plate of eel nigiri at position 8");
		nigiri_button.setActionCommand("blue_eel_nigiri_at_8");
		nigiri_button.addActionListener(this);
		add(nigiri_button);

		JButton roll_button = new JButton("Make gold plate of KMP roll at position 5");
		roll_button.setActionCommand("gold_kmp_roll_at_5");
		roll_button.addActionListener(this);
		add(roll_button);
		
		JLabel plateColor = new JLabel("<html>"
				+ "What plate color do you want to put it on?" + "<br>" + ""
				+ "Note: If you choose GOLD plate, you need to:"
				+ "<br>" + "enter a price value ranging from 5 to 15");
		add(plateColor);
		JComboBox plateColorChoice = new JComboBox(Plate.Color.values());
		plateColorChoice.addItemListener(
				new ItemListener() {
					public void itemStateChanged(ItemEvent event) {
						if (event.getStateChange() == ItemEvent.SELECTED)
							color = Plate.Color.values()[plateColorChoice.getSelectedIndex()];
					}
				});
		add(plateColorChoice);
		
		JLabel platePositionLabel = new JLabel("Where do you want to place the plate?");
		add(platePositionLabel);
		JSpinner platePositionSpinner = new JSpinner(new SpinnerNumberModel(0,0,belt_size, 1));
		platePositionSpinner.addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent event) {
						setPosition = (int) ((JSpinner) event.getSource()).getValue();
					}
				});
		add(platePositionSpinner);
		
		JLabel platePriceLabel = new JLabel("If it is a GOLD plate, what price? From 5 to 15.");
		add(platePriceLabel);
		JSpinner platePriceSpinner = new JSpinner(new SpinnerNumberModel(5,5,15,0.01));
		platePriceSpinner.addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent event) {
						setPrice = (double)((JSpinner)event.getSource()).getValue();
					}
				});
		add(platePriceSpinner);
		
		JLabel nigiriOrSashimiLabel = new JLabel("<html>" + "Which type of Sushi do you want?" + "<br>"
										+ "Note: If you choose Rolls, you need to enter the" + "<br>"
										+ "ingredents and their portions." + "<br>"
										+ "Will always have 0.1 ounces of Seaweed, can be more.");
		add(nigiriOrSashimiLabel);
		JComboBox nigiriOrSashimiChoice = new JComboBox(nOrSOrR);
		nigiriOrSashimiChoice.addItemListener(
				new ItemListener() {
					public void itemStateChanged(ItemEvent event) {
						if (event.getStateChange() == ItemEvent.SELECTED)
							nigiriSashimiORRoll = nOrSOrR[nigiriOrSashimiChoice.getSelectedIndex()];
					}
				});
		add(nigiriOrSashimiChoice);
		
		JLabel nigiriOrSashimiType = new JLabel("What type of nigiri/Sashimi you want to make?");
		add(nigiriOrSashimiType);
		JComboBox nigiriOrSashimiTypeChoice = new JComboBox(nOrSTypes);
		nigiriOrSashimiTypeChoice.addItemListener(
				new ItemListener() {
					public void itemStateChanged(ItemEvent event) {
						if (event.getStateChange() == ItemEvent.SELECTED)
							type = nOrSTypes[nigiriOrSashimiTypeChoice.getSelectedIndex()];
					}
				});
		add(nigiriOrSashimiTypeChoice);
		
		JLabel ingredients = new JLabel("<html>" + "If you choose Roll, what are the ingredients?"
									+ "<br>" + "Chose the Ingredent, chose the amount and click Add ONCE!" +
									"<br>" + "You can only add the same ingredient once before cleared.");
		add(ingredients);
		JComboBox ingredientChoice = new JComboBox(ings);
		ingredientChoice.addItemListener(
				new ItemListener() {
					public void itemStateChanged(ItemEvent event) {
						if (event.getStateChange() == ItemEvent.SELECTED)
							ing = ings[ingredientChoice.getSelectedIndex()];
					}
				});
		add(ingredientChoice);
		
		JLabel ingrAmountLabel = new JLabel("How much of this ingredient?");
		add(ingrAmountLabel);
		JSpinner ingrAmount = new JSpinner(new SpinnerNumberModel(0.0,0.0,1.5,0.1));
		ingrAmount.addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent event) {
						amount = (double)((JSpinner)event.getSource()).getValue();
					}
				});
		add(ingrAmount);
		
		JButton addIngr = new JButton("ADD");
		addIngr.setActionCommand("Add The Ingredient");
		addIngr.addActionListener(this);
		add(addIngr);
		
		JButton clearIngredients = new JButton("Clear ingredents");
		clearIngredients.setActionCommand("Clear");
		clearIngredients.addActionListener(this);
		add(clearIngredients);
		
		JButton makeTheSushi = new JButton("Make the Sushi");
		makeTheSushi.setActionCommand("Form a new Sushi");
		makeTheSushi.addActionListener(this);
		add(makeTheSushi);
		
		avocadoAdded = false;
		crabAdded = false;
		eelAdded = false;
		seaweedAdded = false;
		shrimpAdded = false;
		tunaAdded = false;
		yellowtailAdded = false;
		
		kmp_roll = new Roll("KMP Roll", new IngredientPortion[] {new EelPortion(1.0), new AvocadoPortion(0.5), new SeaweedPortion(0.2)});
		crab_sashimi = new Sashimi(Sashimi.SashimiType.CRAB);
		eel_nigiri = new Nigiri(Nigiri.NigiriType.EEL);
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}
	
	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "red_crab_sashimi_at_3":
			makeRedPlateRequest(crab_sashimi, 3);
			ingPortions.clear();
			break;
		case "blue_eel_nigiri_at_8":
			makeBluePlateRequest(eel_nigiri, 8);
			ingPortions.clear();
			break;
		case "gold_kmp_roll_at_5":
			makeGoldPlateRequest(kmp_roll, 5, 5.00);
			ingPortions.clear();
			break;
		case "Clear":
			ingPortions.clear();
			JOptionPane.showMessageDialog(null, "The ingredient list has been cleared.");
			avocadoAdded = false;
			crabAdded = false;
			eelAdded = false;
			seaweedAdded = false;
			shrimpAdded = false;
			tunaAdded = false;
			yellowtailAdded = false;
			break;
		case "Add The Ingredient":
			IngredientPortion thisIngrPortion = null;
			try {
				switch(ing) {
				case "Avocado":
					if (avocadoAdded) {
						JOptionPane.showMessageDialog(null, "Avocado has already been added!!");
						break;
					}
					thisIngrPortion = new AvocadoPortion(amount);
					ingPortions.add(thisIngrPortion);
					JOptionPane.showMessageDialog(null, thisIngrPortion.getAmount() + " ounces of " + thisIngrPortion.getName() + " is added!!");
					avocadoAdded = true;
					break;
				case "Crab":
					if (crabAdded) {
						JOptionPane.showMessageDialog(null, "Crab has already been added!!");
						break;
					}
					thisIngrPortion = new CrabPortion(amount);
					ingPortions.add(thisIngrPortion);
					JOptionPane.showMessageDialog(null, thisIngrPortion.getAmount() + " ounces of " + thisIngrPortion.getName() + " is added!!");
					crabAdded = true;
					break;
				case "Eel":
					if (eelAdded) {
						JOptionPane.showMessageDialog(null, "Eel has already been added!!");
						break;
					}
					thisIngrPortion = new EelPortion(amount);
					ingPortions.add(thisIngrPortion);
					JOptionPane.showMessageDialog(null, thisIngrPortion.getAmount() + " ounces of " + thisIngrPortion.getName() + " is added!!");
					eelAdded = true;
					break;
				case "Rice":
					if (riceAdded) {
						JOptionPane.showMessageDialog(null, "Rice has already been added!!");
						break;
					}
					thisIngrPortion = new RicePortion(amount);
					ingPortions.add(thisIngrPortion);
					JOptionPane.showMessageDialog(null, thisIngrPortion.getAmount() + " ounces of " + thisIngrPortion.getName() + " is added!!");
					riceAdded = true;
					break;
				case "Seaweed":
					if (seaweedAdded) {
						JOptionPane.showMessageDialog(null, "Seaweed has already been added!!");
						break;
					}
					thisIngrPortion = new SeaweedPortion(amount);
					ingPortions.add(thisIngrPortion);
					JOptionPane.showMessageDialog(null, thisIngrPortion.getAmount() + " ounces of " + thisIngrPortion.getName() + " is added!!");
					seaweedAdded = true;
					break;
				case "Shrimp":
					if (shrimpAdded) {
						JOptionPane.showMessageDialog(null, "Shrimp has already been added!!");
						break;
					}
					thisIngrPortion = new ShrimpPortion(amount);
					ingPortions.add(thisIngrPortion);
					JOptionPane.showMessageDialog(null, thisIngrPortion.getAmount() + " ounces of " + thisIngrPortion.getName() + " is added!!");
					shrimpAdded = true;
					break;
				case "Tuna":
					if (tunaAdded) {
						JOptionPane.showMessageDialog(null, "Tuna has already been added!!");
						break;
					}
					thisIngrPortion = new TunaPortion(amount);
					ingPortions.add(thisIngrPortion);
					JOptionPane.showMessageDialog(null, thisIngrPortion.getAmount() + " ounces of " + thisIngrPortion.getName() + " is added!!");
					tunaAdded = true;
					break;
				case "Yellowtail":
					if (yellowtailAdded) {
						JOptionPane.showMessageDialog(null, "Yellowtail has already been added!!");
						break;
					}
					thisIngrPortion = new YellowtailPortion(amount);
					ingPortions.add(thisIngrPortion);
					JOptionPane.showMessageDialog(null, thisIngrPortion.getAmount() + " ounces of " + thisIngrPortion.getName() + " is added!!");
					yellowtailAdded = true;
					break;
				}
			} catch (RuntimeException exception) {
				JOptionPane.showMessageDialog(null, exception.getMessage());
			}
			break;
		case "Form a new Sushi":
			switch (nigiriSashimiORRoll) {
			case "Nigiri":
				switch (type){
				case "Tuna":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), setPosition, setPrice);
						break;
					}
					break;
				case "YellowTail":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.YELLOWTAIL), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.YELLOWTAIL), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.YELLOWTAIL), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.YELLOWTAIL), setPosition, setPrice);
						break;
					}
					break;
				case "Eel":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.EEL), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.EEL), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.EEL), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.EEL), setPosition, setPrice);
						break;
					}
					break;
				case "Crab":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), setPosition, setPrice);
						break;
					}
					break;
				case "Shrimp":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), setPosition, setPrice);
						break;
					}
					break;
				}
				break;
			case "Sashimi":
				switch (type){
				case "Tuna":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), setPosition, setPrice);
						break;
					}
					break;
				case "YellowTail":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.YELLOWTAIL), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.YELLOWTAIL), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.YELLOWTAIL), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.YELLOWTAIL), setPosition, setPrice);
						break;
					}
					break;
				case "Eel":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.EEL), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.EEL), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.EEL), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.EEL), setPosition, setPrice);
						break;
					}
					break;
				case "Crab":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), setPosition, setPrice);
						break;
					}
					break;
				case "Shrimp":
					switch (color) {
					case RED:
						makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), setPosition);
						break;
					case BLUE:
						makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), setPosition);
						break;
					case GREEN:
						makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), setPosition);
						break;
					case GOLD:
						makeGoldPlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), setPosition, setPrice);
						break;
					}
					break;
				}
				break;
			case "Roll":
				if (ingPortions.size() == 0) {
					JOptionPane.showMessageDialog(null, "You need to add ingredients to form a roll!!!");
					break;
				}
				IngredientPortion[] ingrs =new IngredientPortion[ingPortions.size()];
				ingrs = ingPortions.toArray(ingrs);
				switch(color) {
				case RED:
					makeRedPlateRequest(new Roll("Custome Roll", ingrs), setPosition);
					break;
				case BLUE:
					makeBluePlateRequest(new Roll("Custome Roll", ingrs), setPosition);
					break;
				case GREEN:
					makeGreenPlateRequest(new Roll("Custome Roll", ingrs), setPosition);
					break;
				case GOLD:
					makeGoldPlateRequest(new Roll("Custome Roll", ingrs), setPosition, setPrice);
					break;
				}
				break;
			}
			ingPortions.clear();
			JOptionPane.showMessageDialog(null, "The ingredient list has been cleared.");
			avocadoAdded = false;
			crabAdded = false;
			eelAdded = false;
			seaweedAdded = false;
			shrimpAdded = false;
			tunaAdded = false;
			yellowtailAdded = false;
			break;
		}
	}
}
