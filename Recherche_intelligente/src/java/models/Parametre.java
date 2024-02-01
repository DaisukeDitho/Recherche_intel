/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 *
 * @author pc
 */
public class Parametre {
    private String cle;
    private String valeur;
    private int ordre;

    public String getCle() {
        return cle;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }
    
    public Parametre(String cle,String valeur,int ordre)
    {
        this.setCle(cle);
        this.setValeur(valeur);
        this.setOrdre(ordre);
    }
    
    public static void trier_asc(Vector<Parametre> param)
    {
        Collections.sort(param, new Comparator<Parametre>() {
            @Override
            public int compare(Parametre p1, Parametre p2) {
                return Integer.compare(p1.getOrdre(), p2.getOrdre());
            }
        });
    }
}
