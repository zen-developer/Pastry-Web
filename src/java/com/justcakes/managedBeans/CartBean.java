/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.justcakes.managedBeans;

import DAO.CommandeFacadeRemote;
import DAO.LigneCommandeFacadeRemote;
import DAO.ProduitFacadeRemote;
import DAO.UserFacadeRemote;
import entities.Commande;
import entities.LigneCommande;
import entities.Produit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
    ProduitFacadeRemote produitRemote;
    CommandeFacadeRemote commandeRemote;
    LigneCommandeFacadeRemote ligneCommandeRemote;
    UserFacadeRemote userRemote;
    private Collection<Produit> listCakes;
    private List<Produit> panier=new ArrayList<Produit>();;
    @PostConstruct
    public void init(){
        try {
            
            prop = new Properties();
            prop.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
            Context ctx = new InitialContext(prop);
            produitRemote=(ProduitFacadeRemote) ctx.lookup("java:global/Pastry-EJB/ProduitFacade!DAO.ProduitFacadeRemote");
            commandeRemote=(CommandeFacadeRemote) ctx.lookup("java:global/Pastry-EJB/CommandeFacade!DAO.CommandeFacadeRemote");
            ligneCommandeRemote=(LigneCommandeFacadeRemote) ctx.lookup("java:global/Pastry-EJB/LigneCommandeFacade!DAO.LigneCommandeFacadeRemote");
            userRemote=(UserFacadeRemote) ctx.lookup("java:global/Pastry-EJB/UserFacade!DAO.UserFacadeRemote");
            listCakes=produitRemote.findAllProduits();
        } catch (NamingException ex) {
            Logger.getLogger(CakeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void validateCart(){
        Commande commande=new Commande(null);
        commande.setIdUser(userRemote.find(1));
        commande.setEtat("en cours");
        commande.setDate(new Date());
        Collection<LigneCommande> lignesCommande=new ArrayList<>();
        for(Produit p : panier){
            LigneCommande lc=new LigneCommande();
            lc.setIdProduit(p);
            lc.setQte(1);
            lc.setIdCommade(commande);
            lignesCommande.add(lc);
        }
        commande.setLigneCommandeCollection(lignesCommande);
        commandeRemote.create(commande);
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

   public void removeFomCart(Produit p){
       panier.remove(p);
   }
}
