package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.sql.SQLException;

import javax.swing.ImageIcon;


public class FenetrePrincipale extends JFrame {
	private PanelBienvenue panelBienvenue;
	private PanelBoutons panelBoutons;
	private PanelVols panelVols;
	private PanelDeconnexion panelDeconnexion;
	private PanelNouveauVol panelNouveauVol;
	private PanelNouvelleDestination panelNouvelleDestination;
	private PanelDestinations panelDestinations;
	private PanelValiderAnnuler panelValiderAnnuler;

	private JLabel lblLogoGauche;
	private JLabel lblLogoDroite;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetrePrincipale frame = new FenetrePrincipale();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public FenetrePrincipale() throws SQLException { // throws SQLException pour le panelDestination notamment
		// la fenêtre n'est pas redimensionnable
		setResizable(false);
		// on fixe sa position et sa taille :
		setBounds(100, 100, 1000, 350);
		// le processus s'arrête à la fermeture de la fenêtre :
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelBienvenue = new PanelBienvenue();
		// le panelBienvenue est visible par défaut, on l'ajoute au centre du ContentPane
		getContentPane().add(panelBienvenue, BorderLayout.CENTER);

		// par défaut, le logo de la compagnie apparait à gauche et à droite
		lblLogoGauche = new JLabel("");
		lblLogoGauche.setIcon(new ImageIcon(FenetrePrincipale.class.getResource("/img/logo.jpg")));
		getContentPane().add(lblLogoGauche, BorderLayout.WEST);

		lblLogoDroite = new JLabel("");
		lblLogoDroite.setIcon(new ImageIcon(FenetrePrincipale.class.getResource("/img/logo.jpg")));
		getContentPane().add(lblLogoDroite, BorderLayout.EAST);

		// on instancie les panels non visibles au départ :
		panelDeconnexion = new PanelDeconnexion();
		panelNouveauVol = new PanelNouveauVol();
		panelNouvelleDestination = new PanelNouvelleDestination();
		panelDestinations = new PanelDestinations();
		panelValiderAnnuler = new PanelValiderAnnuler();
		panelVols = new PanelVols();
		panelBoutons = new PanelBoutons();
	}

	public PanelBienvenue getPanelBienvenue() {
		return panelBienvenue;
	}

	public PanelBoutons getPanelBoutons() {
		return panelBoutons;
	}

	public PanelVols getPanelVols() {
		return panelVols;
	}

	public JLabel getLblLogoGauche() {
		return lblLogoGauche;
	}

	public JLabel getLblLogoDroite() {
		return lblLogoDroite;
	}

	public PanelDeconnexion getPanelDeconnexion() {
		return panelDeconnexion;
	}

	public PanelNouveauVol getPanelNouveauVol() {
		return panelNouveauVol;
	}

	public PanelNouvelleDestination getPanelNouvelleDestination() {
		return panelNouvelleDestination;
	}

	public PanelValiderAnnuler getPanelValiderAnnuler() {
		return panelValiderAnnuler;
	}
	
	public PanelDestinations getPanelDestinations() {
		return panelDestinations;
	}

	public void hideElements()
	{
		// on nettoie la page en rendant invisible tous les éléments :
		getPanelDeconnexion().setVisible(false);
		getPanelBienvenue().setVisible(false);
		getPanelBoutons().setVisible(false);
		getPanelNouveauVol().setVisible(false);
		getPanelNouvelleDestination().setVisible(false);
		getPanelDestinations().setVisible(false);
		getPanelValiderAnnuler().setVisible(false);
		getPanelVols().setVisible(false);
		getLblLogoDroite().setVisible(false);
		getLblLogoGauche().setVisible(false);				

		// on supprime ces mêmes éléments
		getContentPane().removeAll();
	}

	public void displayVolsProgrammes()
	{
		hideElements();
		// on ajoute le panel de présentation des vols + les boutons
		getContentPane().add(getPanelBoutons(), BorderLayout.NORTH);
		getContentPane().add(getPanelVols(), BorderLayout.CENTER);

		// on rend visible les éléments ajoutés
		// et on fait un repaint pour avoir le nouvel affichage
		getPanelBoutons().setVisible(true);
		getPanelVols().setVisible(true);
		getContentPane().repaint();
	}

	public void displayVolsEnAttente()
	{
		hideElements();
		// on ajoute le panel de présentation des vols + les boutons
		getContentPane().add(getPanelBoutons(), BorderLayout.NORTH);
		getContentPane().add(getPanelVols(), BorderLayout.CENTER);

		// on rend visible les éléments ajoutés
		// et on fait un repaint pour avoir le nouvel affichage
		getPanelBoutons().setVisible(true);
		getPanelVols().setVisible(true);
		getContentPane().repaint();
	}

	public void displayNouveauVol()
	{
		hideElements();
		// on ajoute le panel de création d'un vol + les boutons
		getContentPane().add(getPanelBoutons(), BorderLayout.NORTH);
		getContentPane().add(getPanelNouveauVol(), BorderLayout.CENTER);

		// on rend visible les éléments ajoutés
		// et on fait un repaint pour avoir le nouvel affichage
		getPanelBoutons().setVisible(true);
		getPanelNouveauVol().setVisible(true);
		getContentPane().repaint();
	}

	public void displayNouvelleDestination()
	{
		hideElements();
		// on ajoute le panel de création d'une destination + les boutons
		getContentPane().add(getPanelBoutons(), BorderLayout.NORTH);
		getContentPane().add(getPanelNouvelleDestination(), BorderLayout.CENTER);

		// on rend visible les éléments ajoutés
		// et on fait un repaint pour avoir le nouvel affichage
		getPanelBoutons().setVisible(true);
		getPanelNouvelleDestination().setVisible(true);
		getContentPane().repaint();
	}
	
	public void displayDestinations()
	{
		hideElements();
		// on ajoute le panel des destinations + les boutons
		getContentPane().add(getPanelBoutons(), BorderLayout.NORTH);
		getContentPane().add(getPanelDestinations(), BorderLayout.CENTER);

		// on rend visible les éléments ajoutés
		// et on fait un repaint pour avoir le nouvel affichage
		getPanelBoutons().setVisible(true);
		getPanelDestinations().setVisible(true);
		getContentPane().repaint();
	}

	public void displayDeconnexion()
	{
		hideElements();
		// on ajoute le panel de déconnexion + le PanelBoutons
		getContentPane().add(getPanelBoutons(), BorderLayout.NORTH);
		getContentPane().add(getPanelDeconnexion(), BorderLayout.CENTER);

		// on rend visible les éléments ajoutés
		// et on fait un repaint pour avoir le nouvel affichage
		getPanelBoutons().setVisible(true);
		getPanelDeconnexion().setVisible(true);
		getContentPane().repaint();
	}
}
