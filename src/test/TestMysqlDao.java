package test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Aeroport;
import model.Vol;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dao.MysqlDao;

@RunWith(JUnit4.class)
//TODO : à décommenter si nécessaire
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMysqlDao {
	
	@Test
	public void getAllAeroports() throws SQLException{
		MysqlDao dao = new MysqlDao();
		List<Aeroport>aeroports = dao.getAllAeroports();
		// on peut tester que le nombre d'aéroports retournés correspond bien
		// au nombre d'aéroports en base (à réajuster au fur et à mesure) :
		Assert.assertEquals(40, aeroports.size());
		// on teste que le premier élément de la liste est bien une instance d'Aeroport
		Assert.assertTrue(aeroports.get(0) instanceof Aeroport);
	}
	
	@Test
	public void addNewAeroport() throws SQLException{
		MysqlDao dao = new MysqlDao();
		// à remodifier à chaque test :
		Aeroport aeroportTest = new Aeroport("SXB", "Strasbourg", "France");
		int result = dao.addNewAeroport(aeroportTest);
		Assert.assertEquals(1, result); // une seule ligne doit être impactée
		//Aeroport a = dao.getAeroportById(41);
		//Assert.assertEquals(aeroportTest, a);
	}
	
	@Test
	public void getAllVolsProgrammes() throws SQLException{
		MysqlDao dao = new MysqlDao();
		List<Vol>vols = dao.getAllVolsProgrammes();
		// on peut tester que le nombre de vols retournés correspond bien
		// au nombre de vols en base (à réajuster au fur et à mesure) :
		Assert.assertEquals(9, vols.size());
		// on teste que le premier élément de la liste est bien une instance de Vol
		Assert.assertTrue(vols.get(0) instanceof Vol);
	}
	
	@Test
	public void getAllVolsEnAttente() throws SQLException{
		MysqlDao dao = new MysqlDao();
		List<Vol>vols = dao.getAllVolsEnAttente();
		// on peut tester que le nombre de vols retournés correspond bien
		// au nombre de vols en base (à réajuster au fur et à mesure) :
		Assert.assertEquals(4, vols.size());
		// on teste que le premier élément de la liste est bien une instance de Vol
		Assert.assertTrue(vols.get(0) instanceof Vol);
	}
	
	@Test
	public void addNewVol() throws SQLException{
		MysqlDao dao = new MysqlDao();
		
		// On transforme les chaînes de caractères en dates selon le motif.
		// On déclare les dates à l'extérieur du try / catch
		Date dateDepart = null;
		Date dateArrivee = null;
		
		try {
			dateDepart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-03-24 02:20:00");
			dateArrivee = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-03-24 18:34:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Aeroport aeroportTest1 = new Aeroport("CMN", "Casablanca", "Maroc");
		Aeroport aeroportTest2 = new Aeroport("HNL", "Honolulu", "États-Unis");
		
		// (On peut le modifier avant de relancer un test) :
		Vol volTest = new Vol(aeroportTest1, aeroportTest2, dateDepart, dateArrivee, 1540, 974F);
		
		int result = dao.addNewVol(volTest);
		Assert.assertEquals(1, result); // une seule ligne doit être impactée
		//Vol v = dao.getVolById(5);
		//Assert.assertEquals(volTest, v);
	}
}
