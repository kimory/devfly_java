package ui;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLabel;

import model.Vol;
import dao.MysqlDao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class PanelVolsProgrammes extends JPanel {
	private JTable tableVolsProgrammes;
	private JScrollPane scrollPane; // conteneur pour avoir une barre de défilement
	private JLabel labelMessage;
	private MysqlDao dao = new MysqlDao();
	private PanelModifVol panelModifVolProgramme;

	public PanelModifVol getPanelModifVolProgramme() {
		return panelModifVolProgramme;
	}

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public PanelVolsProgrammes() throws SQLException {
		setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		tableVolsProgrammes = new JTable();
		tableVolsProgrammes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// on récupère l'endroit où a eu lieu l'événement (= le clic)
				Point p = e.getPoint();
				// on remplit le formulaire avec les données du tableau
				PanelModifVol.fillInForm(panelModifVolProgramme, p, tableVolsProgrammes);
			}
		});
		// pour que le tableau ne soit pas redimensionné automatiquement à la longueur de la fenêtre :
		tableVolsProgrammes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// pour trier en cliquant sur les en-têtes
		tableVolsProgrammes.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(tableVolsProgrammes);
		
		// pour enlever le fait qu'on puisse déplacer les colonnes :
		tableVolsProgrammes.getTableHeader().setReorderingAllowed(false);
		
		// On récupère les vols programmés :
		List<Vol> volsProgrammes = dao.getAllVolsProgrammes();
		
		// On crée le model avec les bonnes données et on le donne à la JTable
		// On utilise pour cela la méthode statique définie dans Vol
		Vol.TableCreation(volsProgrammes, tableVolsProgrammes);
		
		// Label qui pourra contenir les différents messages à afficher :
		labelMessage = new JLabel("");
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
		labelMessage.setForeground(Color.RED);
		add(labelMessage, BorderLayout.NORTH);
		
		// On dimensionne les colonnes :
		Vol.columnSizeVols(tableVolsProgrammes);
		
		panelModifVolProgramme = new PanelModifVol();
		add(panelModifVolProgramme, BorderLayout.SOUTH);
		
	}

}
