package client.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import shared.Unittype;
import client.domain.GameModel;

/**
 * Eine View innerhalb der MainGameView. Zeigt die Stats der aktuell
 * ausgewählten Einheit an.
 */
public class StateView extends JPanel implements Observer {

	private static final long	serialVersionUID	= -753030388566165383L;

	private JLabel				healthLabelStateView;
	private JLabel				damageLabelStateView;
	private JLabel				defenseLabelStateView;
	private JLabel				speedLabelStateView;
	private JLabel				rangeLabelStateView;
	private JLabel				bonusLabelStateView;

	private GameModel			model;

	public StateView(GameModel model) {
		super();
		setSize(new Dimension(350, 100));
		setMinimumSize(new Dimension(300, 100));
		setMaximumSize(new Dimension(400, 100));
		this.model = model;
		model.addGameObserver(this);

		initialize();
	}

	private void initialize() {
		setBorder(null);

		GridBagLayout gbl_ = new GridBagLayout();
		gbl_.columnWidths = new int[] { 0, 30, 60, 30, 125 };
		gbl_.rowHeights = new int[] { 0, 0, 0 };
		gbl_.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 1.0 };
		gbl_.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		setLayout(gbl_);

		initializeOutputElements();

		initializeTestLabels();
	}

	private void initializeTestLabels() {
		JLabel healthText = new JLabel("Health:");
		GridBagConstraints gbc_healthText = new GridBagConstraints();
		gbc_healthText.fill = GridBagConstraints.HORIZONTAL;
		gbc_healthText.insets = new Insets(0, 0, 5, 5);
		gbc_healthText.gridx = 0;
		gbc_healthText.gridy = 0;
		add(healthText, gbc_healthText);

		JLabel speedText = new JLabel("Speed:");
		GridBagConstraints gbc_speedText = new GridBagConstraints();
		gbc_speedText.fill = GridBagConstraints.HORIZONTAL;
		gbc_speedText.insets = new Insets(0, 0, 5, 5);
		gbc_speedText.gridx = 2;
		gbc_speedText.gridy = 0;
		add(speedText, gbc_speedText);

		JLabel damageText = new JLabel("Damage:");
		GridBagConstraints gbc_damageText = new GridBagConstraints();
		gbc_damageText.fill = GridBagConstraints.HORIZONTAL;
		gbc_damageText.insets = new Insets(0, 0, 5, 5);
		gbc_damageText.gridx = 0;
		gbc_damageText.gridy = 1;
		add(damageText, gbc_damageText);

		JLabel rangeText = new JLabel("Range:");
		GridBagConstraints gbc_rangeText = new GridBagConstraints();
		gbc_rangeText.fill = GridBagConstraints.HORIZONTAL;
		gbc_rangeText.insets = new Insets(0, 0, 5, 5);
		gbc_rangeText.gridx = 2;
		gbc_rangeText.gridy = 1;
		add(rangeText, gbc_rangeText);

		JLabel defenseText = new JLabel("Defense:");
		GridBagConstraints gbc_defenseText = new GridBagConstraints();
		gbc_defenseText.fill = GridBagConstraints.HORIZONTAL;
		gbc_defenseText.insets = new Insets(0, 0, 5, 5);
		gbc_defenseText.gridx = 0;
		gbc_defenseText.gridy = 2;
		add(defenseText, gbc_defenseText);

		JLabel bonusText = new JLabel("Bonus:");
		GridBagConstraints gbc_bonusText = new GridBagConstraints();
		gbc_bonusText.insets = new Insets(0, 0, 5, 0);
		gbc_bonusText.anchor = GridBagConstraints.WEST;
		gbc_bonusText.gridx = 4;
		gbc_bonusText.gridy = 0;
		add(bonusText, gbc_bonusText);

		healthText.setLabelFor(healthLabelStateView);
		speedText.setLabelFor(speedLabelStateView);
		damageText.setLabelFor(damageLabelStateView);
		rangeText.setLabelFor(rangeLabelStateView);
		defenseText.setLabelFor(defenseLabelStateView);
		bonusText.setLabelFor(bonusLabelStateView);
	}

	private void initializeOutputElements() {
		healthLabelStateView = new JLabel("");
		GridBagConstraints gbc_healthLabelStateView = new GridBagConstraints();
		gbc_healthLabelStateView.insets = new Insets(0, 0, 5, 5);
		gbc_healthLabelStateView.gridx = 1;
		gbc_healthLabelStateView.gridy = 0;
		add(healthLabelStateView, gbc_healthLabelStateView);

		speedLabelStateView = new JLabel("");
		GridBagConstraints gbc_speedLabelStateView = new GridBagConstraints();
		gbc_speedLabelStateView.insets = new Insets(0, 0, 5, 5);
		gbc_speedLabelStateView.gridx = 3;
		gbc_speedLabelStateView.gridy = 0;
		add(speedLabelStateView, gbc_speedLabelStateView);

		damageLabelStateView = new JLabel("");
		GridBagConstraints gbc_damageLabelStateView = new GridBagConstraints();
		gbc_damageLabelStateView.insets = new Insets(0, 0, 5, 5);
		gbc_damageLabelStateView.gridx = 1;
		gbc_damageLabelStateView.gridy = 1;
		add(damageLabelStateView, gbc_damageLabelStateView);

		rangeLabelStateView = new JLabel("");
		GridBagConstraints gbc_rangeLabelStateView = new GridBagConstraints();
		gbc_rangeLabelStateView.insets = new Insets(0, 0, 5, 5);
		gbc_rangeLabelStateView.gridx = 3;
		gbc_rangeLabelStateView.gridy = 1;
		add(rangeLabelStateView, gbc_rangeLabelStateView);

		defenseLabelStateView = new JLabel("");
		GridBagConstraints gbc_defenseLabelStateView = new GridBagConstraints();
		gbc_defenseLabelStateView.insets = new Insets(0, 0, 5, 5);
		gbc_defenseLabelStateView.gridx = 1;
		gbc_defenseLabelStateView.gridy = 2;
		add(defenseLabelStateView, gbc_defenseLabelStateView);

		JScrollPane bonusScrollPane = new JScrollPane();
		bonusScrollPane.setBorder(null);
		GridBagConstraints gbc_bonusScrollPane = new GridBagConstraints();
		gbc_bonusScrollPane.gridheight = 2;
		gbc_bonusScrollPane.fill = GridBagConstraints.BOTH;
		gbc_bonusScrollPane.gridx = 4;
		gbc_bonusScrollPane.gridy = 1;
		add(bonusScrollPane, gbc_bonusScrollPane);

		bonusLabelStateView = new JLabel("");
		bonusScrollPane.setViewportView(bonusLabelStateView);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		healthLabelStateView.setText(model.getHealthFromSelectedUnit() + "");
		Unittype unittype = model.getUnittypeFromSelectedUnit();
		damageLabelStateView.setText(unittype.getAttackDamage() + "");
		defenseLabelStateView.setText(unittype.getDefence() + "");
		speedLabelStateView.setText(unittype.getSpeed() + "");
		rangeLabelStateView.setText(unittype.getRange() + "");
		String bonus = "";
		for (Entry<Unittype, Integer> entry : unittype.getAdventages()) {
			int value = entry.getValue();
			if (value != 0) {
				bonus += value + "% vs " + entry.getKey() + "\n\r";
			}
		}
		bonusLabelStateView.setText(bonus);
	}
}
