package ui;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import dao.MysqlDao;
import model.Aeroport;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PanelModifAeroport extends JPanel {
	private JTextField textFieldCode;
	private JTextField textFieldVille;
	private JTextField textFieldPays;
	private JButton btnMettreAJour;
	private JButton btnReinitialiser;
	private JButton btnSupprimer;
	private MysqlDao dao = new MysqlDao();

	public JTextField getTextFieldCode() {
		return textFieldCode;
	}

	public JTextField getTextFieldVille() {
		return textFieldVille;
	}

	public JTextField getTextFieldPays() {
		return textFieldPays;
	}

	public JButton getBtnMettreAJour() {
		return btnMettreAJour;
	}

	public JButton getBtnReinitialiser() {
		return btnReinitialiser;
	}

	public JButton getBtnSupprimer() {
		return btnSupprimer;
	}

	/**
	 * Create the panel.
	 */
	public PanelModifAeroport() {
		// on joue sur les dimensions de la grille pour positionner les éléments :
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 46, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		// Label qui pourra contenir les différents messages à afficher :
		final JLabel lblMessage = new JLabel("");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setForeground(Color.RED);
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.gridwidth = 3;
		gbc_lblMessage.anchor = GridBagConstraints.EAST;
		gbc_lblMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessage.gridx = 1;
		gbc_lblMessage.gridy = 1;
		add(lblMessage, gbc_lblMessage);
		
		// Les différents éléments du formulaires :
		
		JLabel lblCode = new JLabel("code AITA");
		lblCode.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblCode.gridx = 1;
		gbc_lblCode.gridy = 2;
		add(lblCode, gbc_lblCode);
		
		textFieldCode = new JTextField();
		textFieldCode.setEditable(false); // on empêche la modification de l'id
		GridBagConstraints gbc_textFieldCode = new GridBagConstraints();
		gbc_textFieldCode.gridwidth = 2;
		gbc_textFieldCode.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCode.gridx = 2;
		gbc_textFieldCode.gridy = 2;
		add(textFieldCode, gbc_textFieldCode);
		textFieldCode.setColumns(10);
		
		JLabel lblVille = new JLabel("ville");
		lblVille.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblVille = new GridBagConstraints();
		gbc_lblVille.insets = new Insets(0, 0, 5, 5);
		gbc_lblVille.gridx = 1;
		gbc_lblVille.gridy = 3;
		add(lblVille, gbc_lblVille);
		
		textFieldVille = new JTextField();
		GridBagConstraints gbc_textFieldVille = new GridBagConstraints();
		gbc_textFieldVille.gridwidth = 2;
		gbc_textFieldVille.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldVille.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldVille.gridx = 2;
		gbc_textFieldVille.gridy = 3;
		add(textFieldVille, gbc_textFieldVille);
		textFieldVille.setColumns(10);
		
		JLabel lblPays = new JLabel("pays");
		lblPays.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblPays = new GridBagConstraints();
		gbc_lblPays.insets = new Insets(0, 0, 5, 5);
		gbc_lblPays.gridx = 1;
		gbc_lblPays.gridy = 4;
		add(lblPays, gbc_lblPays);
		
		textFieldPays = new JTextField();
		GridBagConstraints gbc_textFieldPays = new GridBagConstraints();
		gbc_textFieldPays.gridwidth = 2;
		gbc_textFieldPays.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPays.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPays.gridx = 2;
		gbc_textFieldPays.gridy = 4;
		add(textFieldPays, gbc_textFieldPays);
		textFieldPays.setColumns(10);
		
		btnMettreAJour = new JButton("mettre à jour");
		btnMettreAJour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String codeAita = getTextFieldCode().getText();
				String ville = getTextFieldVille().getText();
				String pays = getTextFieldPays().getText();
				
				Aeroport a = new Aeroport(codeAita, ville, pays);
				try {
					if(dao.updateAeroport(a)){ // si la mise à jour s'est bien passée
						// On affiche un message :
						lblMessage.setText("La mise à jour a bien été effectuée !");
						
						// On vide les champs texte :
						getTextFieldCode().setText("");
						getTextFieldVille().setText("");
						getTextFieldPays().setText("");
						
						// on recharge la liste des aéroports pour que la mise à jour apparaisse
						List<Aeroport> aeroports = dao.getAllAeroports();
						
						// on récupère la frame principale
						FenetrePrincipale frame = (FenetrePrincipale) SwingUtilities.getRoot(PanelModifAeroport.this);
						// on récupère la JTable
						JTable table = frame.getPanelAeroports().getTableAeroports();
						// On crée le model avec les bonnes données et on le donne à la JTable
						// On utilise pour cela la méthode statique définie dans Aeroport
						Aeroport.TableCreation(aeroports, table);
						
						// on va également recharger la liste des villes proposées dans le formulaire de création d'un vol
						String[]villes = frame.getPanelNouveauVol().getVillesProposees();
						// On insère les villes dans les comboBox
						JComboBox comboBoxDepart = frame.getPanelNouveauVol().getComboBoxVilleDeDepart();
						JComboBox comboBoxArrivee = frame.getPanelNouveauVol().getComboBoxVilleDarrivee();
						frame.getPanelNouveauVol().comboBoxCreation(villes, comboBoxDepart);
						frame.getPanelNouveauVol().comboBoxCreation(villes, comboBoxArrivee);
					}else{
						// En cas d'erreur, on affiche un message (cause probable = saisie ds le formulaire
						// sans avoir sélectionné un aéroport, donc pas de code AITA...)
						lblMessage.setText("<html><p>La mise à jour n'a pas pu être effectuée !<br>"
								+ "Veuillez sélectionnez un aéroport ci-dessus<br>"
								+ "et renouvelez l'opération.</p></html>");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		});
		GridBagConstraints gbc_btnMettreAJour = new GridBagConstraints();
		gbc_btnMettreAJour.insets = new Insets(0, 0, 5, 5);
		gbc_btnMettreAJour.gridx = 2;
		gbc_btnMettreAJour.gridy = 5;
		add(btnMettreAJour, gbc_btnMettreAJour);
		
		btnReinitialiser = new JButton("réinitialiser");
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// au clic sur "réinitialiser", on réinitalise les champs à leur valeur initiale
				// On récupère le code de l'aéroport en cours de modification
				String codeAita = getTextFieldCode().getText();
				// On récupère les données de l'aéroport correspondant :
				try {
					Aeroport a = dao.getAeroportByCode(codeAita);
					// On réinitialise les champs ville et pays :
					getTextFieldVille().setText(a.getVille());
					getTextFieldPays().setText(a.getPays());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
		gbc_btnAnnuler.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnnuler.gridx = 3;
		gbc_btnAnnuler.gridy = 5;
		add(btnReinitialiser, gbc_btnAnnuler);
		
		btnSupprimer = new JButton("supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSupprimer.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSupprimer.setBackground(Color.PINK);
		btnSupprimer.setForeground(Color.RED);
		GridBagConstraints gbc_btnSupprimer = new GridBagConstraints();
		gbc_btnSupprimer.insets = new Insets(0, 0, 0, 5);
		gbc_btnSupprimer.gridx = 2;
		gbc_btnSupprimer.gridy = 6;
		add(btnSupprimer, gbc_btnSupprimer);

	}

}
