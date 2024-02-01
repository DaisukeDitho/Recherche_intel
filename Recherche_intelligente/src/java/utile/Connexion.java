package utile;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.Vector;
import models.Produit;
public class Connexion {
    public static Connection conn() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/recherche", "recherche", "recherche");      // connection a postgres
            System.out.println("connecte");
        } catch(Exception e) {
            System.out.println(e);
        }
        return con;
    }  
    public static void main(String[] args) throws Exception {
        try
        {
            Connection conn=Connexion.conn();
            
            /*Vector<Symptome> kk = new Vector<Symptome>();
            kk.add(new Symptome(1));
            kk.add(new Symptome(2));
            kk.add(new Symptome(4));
            kk.add(null);
            Vector<Integer> ss = new Vector<Integer>();
            ss.add(5);
            ss.add(6);
            ss.add(3);*/
            //Maladie.get_maladies(kk, ss, conn);
            //Symptome symp = new Symptome(2, "nom");
            //symp.get_traitement(6, conn);
            /*Medicament med = new Medicament(3, "g", 0.01);
            Vector<Symptome> other_symptome = med.get_symptome_sitrana(1,1,conn);
            Symptome.enleve_symptome(other_symptome, kk, ss);*/
            
            String result = Produit.trouve_synonyme_phrase("meilleur telephone qualite/prix", conn);
            System.out.println("Yess : "+result);
            
            //System.out.println(Medicament.get_total_traitement(Symptome.get_meilleur_combinaison(id_symp, degree,15, conn)));
            
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}