/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author pc
 */
public class Produit {
    private int id;
    private String designation;
    private Date date_sortie;
    private String model;
    private int qualite;
    private Double prix;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Date getDate_sortie() {
        return date_sortie;
    }

    public void setDate_sortie(Date date_sortie) {
        this.date_sortie = date_sortie;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getQualite() {
        return qualite;
    }

    public void setQualite(int qualite) {
        this.qualite = qualite;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
    
    public Produit(int id,String designation,Date date_sortie,String model,int qualite,Double prix)
    {
        this.setId(id);
        this.setDesignation(designation);
        this.setDate_sortie(date_sortie);
        this.setModel(model);
        this.setQualite(qualite);
        this.setPrix(prix);
    }
    
    public static Vector<Produit> get_all(Connection con)
    {
        Vector<Produit> result=new Vector<Produit>();
        Statement state = null;
        ResultSet res = null;
        try {
            String requete = "select * from v_produit_detail";
            state = con.createStatement();
            res = state.executeQuery(requete);
            while(res.next()){
                result.add(new Produit(res.getInt("id_produit"), res.getString("designation"), res.getDate("date_sortie"), res.getString("model"), res.getInt("qualite"), res.getDouble("prix")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try
            {
                if(res!=null) res.close();
                if(state!=null) state.close();   
            }
            catch(Exception es)
            {
                es.printStackTrace();
            }
        }
        return result;        
    }
    
    public static Vector<String> supprime_mot_insignifiant(String[] mots_bdb)
    {
        Vector<String> result = new Vector<String>();
        for(int i=0;i<mots_bdb.length;i++)
        {
            if(mots_bdb[i].matches(".*\\d+.*")==false)
            {
                result.add(mots_bdb[i]);
                System.out.println(mots_bdb[i]);
            }
        }
        return result;
    }
    public static String[] chiffre(String[] mots_bdb)
    {
        String[] result = null;
        for(int i=0;i<mots_bdb.length;i++)
        {
            if(mots_bdb[i].matches(".*\\d+.*")==true)
            {
                if(mots_bdb[i].contains(","))
                {
                    result = mots_bdb[i].split(",");
                }
                else
                {
                    result = new String[1];
                    result[0] = mots_bdb[i];
                }
                System.out.println("CHIFFRE "+mots_bdb[i]);
            }
        }
        return result;
    }
    public static Boolean read_operation(String mot,Connection con) throws Exception
    {
        String[] mot_separe = new String[0] ;
        if(mot.contains("/")) mot_separe = mot.split("/");
        else if(mot.contains("*")) mot_separe = mot.split("*");
        else if(mot.contains("+")) mot_separe = mot.split("+");
        else if(mot.contains("-")) mot_separe = mot.split("-");
        
        for(int i=0;i<mot_separe.length;i++)
        {
            if(Produit.check_si_nom_colonne2(mot_separe[i], con).equals("")) return false;
        }
        if(mot_separe.length==0) return false;
        return true;
        
    }
    
    public static Parametre check_si_designation(String mot,Connection con)
    {
        Parametre result = null;
        Statement state = null;
        ResultSet res = null;
        try {
            String requete = "select * from v_produit_detail where designation like '%"+mot+"%'";
            
            state = con.createStatement();
            res = state.executeQuery(requete);
            while(res.next()){
                result = new Parametre("designation", "where designation like '%"+mot+"%'", 1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try
            {
                if(res!=null) res.close();
                if(state!=null) state.close();   
            }
            catch(Exception es)
            {
                es.printStackTrace();
            }
        }
        return result;
    }
    public static Parametre check_si_model(String mot,Connection con)
    {
        Parametre result = null;
        Statement state = null;
        ResultSet res = null;
        try {
            String requete = "select * from v_produit_detail where model like '%"+mot+"%'";
            
            state = con.createStatement();
            res = state.executeQuery(requete);
            while(res.next()){
                result = new Parametre("model", "and model like '%"+mot+"%'", 2);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try
            {
                if(res!=null) res.close();
                if(state!=null) state.close();   
            }
            catch(Exception es)
            {
                es.printStackTrace();
            }
        }
        return result;
    }
    public static Parametre check_si_parametre_recherche(String mot,Connection con)
    {
        Parametre result = null;
        Statement state = null;
        ResultSet res = null;
        try {
            String requete = "select * from parametre_recherche where cle='"+mot+"'";
            
            state = con.createStatement();
            res = state.executeQuery(requete);
            while(res.next()){
                result = new Parametre(mot, res.getString("valeur"), res.getInt("ordre"));
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try
            {
                if(res!=null) res.close();
                if(state!=null) state.close();   
            }
            catch(Exception es)
            {
                es.printStackTrace();
            }
        }
        return result;
    }
    public static Parametre check_si_nom_colonne(String mot,Connection con) throws Exception
    {
        Parametre result = null;
        Statement state = null;
        ResultSet res = null;
        ResultSetMetaData metaData = null;
        
            String requete = "select * from v_produit_detail";
            
            state = con.createStatement();
            res = state.executeQuery(requete);
            metaData = res.getMetaData();
            for(int i=1;i<metaData.getColumnCount()+1;i++)
            {
                if(metaData.getColumnName(i).equalsIgnoreCase(mot))
                {
                    result = new Parametre("colonne", mot, -1);
                    return result;
                }
            }
            if(Produit.read_operation(mot, con)==true)
            {
                result = new Parametre("colonne", mot, -1);
                return result;
            }
            return result;
    }
    public static Parametre check_si_nom_colonne2(String mot,Connection con) throws Exception
    {
        Parametre result = null;
        Statement state = null;
        ResultSet res = null;
        ResultSetMetaData metaData = null;
        
            String requete = "select * from v_produit_detail";
            
            state = con.createStatement();
            res = state.executeQuery(requete);
            metaData = res.getMetaData();
            for(int i=1;i<metaData.getColumnCount()+1;i++)
            {
                if(metaData.getColumnName(i).equalsIgnoreCase(mot))
                {
                    result = new Parametre("colonne", mot, -1);
                    return result;
                }
            }
            return result;
    }    
    public static Parametre check_final(String mot,Connection con) throws Exception
    {
        Parametre result = null;
        result = Produit.check_si_designation(mot, con);
        if(result!=null) return result;
        result = Produit.check_si_model(mot, con);
        if(result!=null) return result;
        result = Produit.check_si_parametre_recherche(mot, con);
        if(result!=null) return result;
        result = Produit.check_si_nom_colonne(mot, con);
        
        if(result==null) throw new Exception("Mot inconnu de l'application : "+mot);
        
        return result;
    }
    
    public static Parametre get_by_cle(Vector<Parametre> param,String cle)
    {
        for(int i=0;i<param.size();i++)
        {
            if(param.get(i).getCle().equals(cle))
            {
                return param.get(i);
            }
        }
        return null;
    }
    
    public static String trouve_synonyme_phrase(String phrase,Connection con) throws Exception
    {
        HashMap<String,String> result = new HashMap<String,String>();
        
        String[] mots_de_la_phrase = phrase.split(" ");
        Vector<String> mots_signifiant = Produit.supprime_mot_insignifiant(mots_de_la_phrase);
        
        Vector<Parametre> param = new Vector<Parametre>();
        
        String requete = "select * from v_produit_detail ";
        
        for(int i=0;i<mots_signifiant.size();i++)
        {
            param.add(Produit.check_final(mots_signifiant.get(i), con));
            //System.out.println(Produit.check_final(mots_signifiant.get(i), con).getValeur()+" "+Produit.check_final(mots_signifiant.get(i), con).getCle()+" "+Produit.check_final(mots_signifiant.get(i), con).getOrdre());
        }
        Parametre.trier_asc(param);
        for(int i=0;i<param.size();i++)
        {
            if(param.get(i).getOrdre()>0)
            {
                requete = requete + param.get(i).getValeur() + " ";
            }
            System.out.println(param.get(i).getValeur()+" avec "+param.get(i).getOrdre());
        }
        
        System.out.println(requete);
        
        Parametre colonne = Produit.get_by_cle(param, "colonne");
        if(colonne==null)
        {
            Parametre recherche2 = Produit.get_by_cle(param, "recent");
            Parametre recherche1 = Produit.get_by_cle(param, "ancien");
            if(recherche2==null && recherche1==null)
            {
                throw new Exception("Colonne de recherche introuvable");
            }
            param.add(new Parametre("colonne","ancien",10));
        }
        requete = requete.replace("$", colonne.getValeur());
        
        System.out.println(requete);
        
        String[] chiffre = Produit.chiffre(mots_de_la_phrase);
        if(chiffre!=null && chiffre.length==1)
        {
            requete = requete.replace("#", chiffre[0]);
        }
        else if(chiffre!=null && chiffre.length==2)
        {
            requete = requete.replace("#", chiffre[0]);
            requete = requete.replace("@", chiffre[1]);
        }
        
        System.out.println(requete);
        
        return requete;
    }
    
    public static Vector<Produit> find_requete(String requeteny,Connection con) throws Exception
    {
        Vector<Produit> result = new Vector<Produit>();
        Statement state = null;
        ResultSet res = null;
        try {
            String requete = requeteny;
            
            state = con.createStatement();
            res = state.executeQuery(requete);
            while(res.next()){
                result.add(new Produit(res.getInt("id_produit"), res.getString("designation"), res.getDate("date_sortie"), res.getString("model"), res.getInt("qualite"), res.getDouble("prix")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try
            {
                if(res!=null) res.close();
                if(state!=null) state.close();   
            }
            catch(Exception es)
            {
                es.printStackTrace();
            }
        }
        return result;
    }
}
