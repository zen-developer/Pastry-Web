/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.justcakes.managedBeans;

import DAO.ProduitFacadeRemote;
import entities.Produit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ZEN
 */
@ManagedBean
@SessionScoped
public class CartBean {

    Properties prop;
    Context ctx;
    ProduitFacadeRemote pfr;
    private Collection<Produit> listCakes;
    private List<Produit> panier=new ArrayList<Produit>();;
    @PostConstruct
    public void init(){
        try {
            
            prop = new Properties();
            prop.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
            Context ctx = new InitialContext(prop);
            pfr=(ProduitFacadeRemote) ctx.lookup("java:global/Pastry-EJB/ProduitFacade!DAO.ProduitFacadeRemote");
            listCakes=pfr.findAllProduits();
        } catch (NamingException ex) {
            Logger.getLogger(CakeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public CartBean() {
    }
    public void addToCart(Produit produit){
        panier.add(produit);
    }
    public Collection<Produit> getListCakes() {
        return listCakes;
    }

    public void setListCakes(Collection<Produit> listCakes) {
        this.listCakes = listCakes;
    }

    public List<Produit> getPanier() {
        return panier;
    }

    public void setPanier(List<Produit> panier) {
        this.panier = panier;
    }

   
    
}
