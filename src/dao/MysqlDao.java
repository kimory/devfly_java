package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Aeroport;
import model.Vol;

public class MysqlDao {
	private String datasource = "jdbc:mysql://localhost:3306/DEVFLY";
	private String user = "greta";
	private String password = "gretatest";

	// TODO : à CORRIGER (durée...) et optimiser
	// retourne les vols "programmés" sous forme d'une liste
	public List<Vol> getAllVolsProgrammes() throws SQLException {
		List<Vol> vols = new ArrayList<>();
		// on se connecte à la BDD
		Connection connection = DriverManager.getConnection(datasource, user,
				password);
		// on crée et exécute une requête préparée pour récupérer les informations
		// sur le vol et ses employés
		String sql1 = "SELECT V.numvol, V.lieudep, V.lieuarriv, V.dateheuredep,"
				+ "V.dateheurearrivee, V.tarif, T.pilote, T.copilote,"
				+ "T.hotesse_steward1, T.hotesse_steward2, T.hotesse_steward3 FROM vol V INNER JOIN travailler T ON V.numvol = T.vol";
		PreparedStatement stmt1 = connection.prepareStatement(sql1);
		ResultSet result1 = stmt1.executeQuery();
		while (result1.next()) {
			String id = result1.getString("numvol");
			String villeDepart = result1.getString("lieudep");
			String villeArrivee = result1.getString("lieuarriv");
			Date dateHeureDepart = result1.getDate("dateheuredep");
			Date dateHeureArrivee = result1.getDate("dateheurearrivee");
			
			// pour calculer la durée du vol, on récupère d'abord le nombre de millisecondes
			// écoulées entre le 1er janvier 1970 et les dates concernées (timestamp)
			long departMillisecondes = dateHeureDepart.getTime();
			long arriveeMillisecondes = dateHeureArrivee.getTime();
			long dureeMillisecondes = arriveeMillisecondes - departMillisecondes;
//			System.out.println(dateHeureDepart);
//			System.out.println(dateHeureArrivee);
//			System.out.println(departMillisecondes);
//			System.out.println(arriveeMillisecondes);
//			System.out.println(dureeMillisecondes);
			
			int duree = (int) (dureeMillisecondes / 60000); // en minutes
			
			float tarif = result1.getFloat("tarif");
			String codePilote = result1.getString("pilote");
			String codeCopilote = result1.getString("copilote");
			String codeHotesseSt1 = result1.getString("hotesse_steward1");
			String codeHotesseSt2 = result1.getString("hotesse_steward2");
			String codeHotesseSt3 = result1.getString("hotesse_steward3");
			
			// on récupère les éléments sur l'aéroport de départ
			String sql2 = "SELECT codeaeroport, pays FROM destination WHERE ville = ?";
			PreparedStatement stmt2 = connection.prepareStatement(sql2);
			// on valorise le paramètre
			stmt2.setString(1, villeDepart);
			ResultSet result2 = stmt2.executeQuery();
			// on initialise les variables en dehors de la boucle
			String codeDepart = null;
			String paysDepart = null;
			while (result2.next()) {
				codeDepart = result2.getString("codeaeroport");
				paysDepart = result2.getString("pays");
			}
			
			// on récupère les éléments sur l'aéroport de destination (même requête)
			PreparedStatement stmt3 = connection.prepareStatement(sql2);
			// on valorise le paramètre
			stmt3.setString(1, villeArrivee);
			ResultSet result3 = stmt3.executeQuery();
			// on initialise les variables en dehors de la boucle
			String codeArrivee = null;
			String paysArrivee = null;
			while (result3.next()) {
				codeArrivee = result3.getString("codeaeroport");
				paysArrivee = result3.getString("pays");
			}
			
			// on crée les objet "Aeroport" de départ et d'arrivée avec les informations récupérées
			Aeroport aeroportDepart = new Aeroport(codeDepart, villeDepart, paysDepart);
			Aeroport aeroportArrivee = new Aeroport(codeArrivee, villeArrivee, paysArrivee);
			
			// on crée un objet Vol avec les informations récupérées
			Vol v = new Vol(id, aeroportDepart, aeroportArrivee, dateHeureDepart, duree, tarif, codePilote, codeCopilote, codeHotesseSt1, codeHotesseSt2, codeHotesseSt3);
			// on l'ajoute à la liste
			vols.add(v);
		}
		connection.close();
		return vols;
	}

	// retourne les aéroports sous forme d'une liste
	public List<Aeroport> getAllAeroports() throws SQLException {
		List<Aeroport> aeroports = new ArrayList<>();
		// on se connecte à la BDD
		Connection connection = DriverManager.getConnection(datasource, user,
				password);
		// on crée et exécute une requête préparée
		String sql = "SELECT * FROM destination";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			String code = result.getString("codeaeroport");
			String ville = result.getString("ville");
			String pays = result.getString("pays");
			// on crée un objet aéroport avec les informations récupérées
			Aeroport a = new Aeroport(code, ville, pays);
			// on l'ajoute à la liste
			aeroports.add(a);
		}
		connection.close();
		return aeroports;
	}
	
	// TODO : à optimiser ?
	// ajoute un nouvel aéroport en base
	public int addNewAeroport(Aeroport a) throws SQLException{
		// on se connecte à la BDD
		Connection connection = DriverManager.getConnection(datasource, user,
				password);
		// on regarde si le code aéroport existe déjà en base, avec une requête préparée
		String sql1 = "SELECT * from destination WHERE codeaeroport = ?";
		PreparedStatement stmt1 = connection.prepareStatement(sql1);
		// on valorise le paramètre
		stmt1.setString(1, a.getCodeAeroport());
		ResultSet result1 = stmt1.executeQuery();
		// si l'aéroport existe déjà, on sort de la fonction et on renvoie "2".
		if (result1.next()) {
			String id = result1.getString("codeaeroport");
			if(id.equals(a.getCodeAeroport())){
				return 2;
			}
		}
		
		// s'il n'existe pas déjà, on crée et exécute une requête
		// préparée pour insérer l'aéroport
		String sql2 = "INSERT INTO destination values(?, ?, ?)";
		PreparedStatement stmt2 = connection.prepareStatement(sql2);
		stmt2.setString(1, a.getCodeAeroport());
		stmt2.setString(2, a.getVille());
		stmt2.setString(3, a.getPays());
		int result2 = stmt2.executeUpdate(); // renvoie le nb d'enregistrements impactés
		connection.close();
		return result2; // doit renvoyer "1"
	}
}
