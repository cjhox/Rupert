package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shared.gameround.Draw;
import client.domain.DrawModel;

/**
 * Die DrawView ist eine View innerhalb der MainGameView. Befindet sich am
 * rechten Rand und beinhaltet die Liste, in welcher die in dieser Phase
 * erfassten Draws angezeigt werden.
 */
public class DrawView extends JPanel {

	private static final long	serialVersionUID	= -5021706161600172483L;
	private JList<Draw>			list;
	private JButton				btnDownDrawView;
	private JButton				btnUpDrawView;
	private JButton				btnDeleteDrawView;

	public DrawView(DrawModel model) {
		setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
		setLayout(new BorderLayout(0, 0));

		list = new JList<Draw>();
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (list.isSelectionEmpty()) {
					disableDrawViewButtons();
				} else {
					enableDrawViewButtons();
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(model);
		add(list, BorderLayout.CENTER);

		initializeButtons(model);
	}

	private void initializeButtons(DrawModel model) {
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		btnUpDrawView = new JButton("Move Up");
		btnUpDrawView.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (model.moveUp(list.getSelectedIndex())) {
					list.setSelectedIndex(list.getSelectedIndex() - 1);
				}
			}
		});
		panel.add(btnUpDrawView);

		btnDownDrawView = new JButton("Move Down");
		btnDownDrawView.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (model.moveDown(list.getSelectedIndex())) {
					list.setSelectedIndex(list.getSelectedIndex() + 1);
				}
			}
		});
		panel.add(btnDownDrawView);

		btnDeleteDrawView = new JButton("Delete");
		btnDeleteDrawView.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (model.delete(list.getSelectedIndex())) {
					list.clearSelection();
				}
			}
		});
		panel.add(btnDeleteDrawView);
	}

	protected void enableDrawViewButtons() {
		btnDownDrawView.setEnabled(true);
		btnUpDrawView.setEnabled(true);
		btnDeleteDrawView.setEnabled(true);

	}

	protected void disableDrawViewButtons() {
		btnDownDrawView.setEnabled(false);
		btnUpDrawView.setEnabled(false);
		btnDeleteDrawView.setEnabled(false);

	}
}
