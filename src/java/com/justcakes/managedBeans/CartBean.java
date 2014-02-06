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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.ws.WebServiceRef;
import serv.Exception_Exception;
import serv.MyBankserv_Service;

/**
 *
 * @author ZEN
 */
@ManagedBean
@SessionScoped
public class CartBean {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/Banque-WebService/MyBankserv.wsdl")
    private MyBankserv_Service service;

    Properties prop;
    Context ctx;
    ProduitFacadeRemote produitRemote;
    CommandeFacadeRemote commandeRemote;
    LigneCommandeFacadeRemote ligneCommandeRemote;
    UserFacadeRemote userRemote;
    private Collection<Produit> listCakes;
    private List<CartItem> panier=new ArrayList<CartItem>();
    private double prixTotal=0;
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
        for(CartItem ci : panier){
            LigneCommande lc=new LigneCommande();
            lc.setIdProduit(ci.getProduit());
            lc.setQte(ci.getQuantite());
            lc.setIdCommade(commande);
            lignesCommande.add(lc);
        }
        commande.setLigneCommandeCollection(lignesCommande);
        commandeRemote.create(commande);
    }
    public CartBean() {
    }
    public void addToCart(CartItem cartItem){
        panier.add(cartItem);
    }
    public Collection<Produit> getListCakes() {
        return listCakes;
    }

    public void setListCakes(Collection<Produit> listCakes) {
        this.listCakes = listCakes;
    }

    public List<CartItem> getPanier() {
        return panier;
    }

    public void setPanier(List<CartItem> panier) {
        this.panier = panier;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }
    
    public void addToPrixTotal(double prix){
        this.prixTotal=this.prixTotal+prix;
    }
   

   public void removeFomCart(CartItem p){
       this.prixTotal=this.prixTotal-p.getQuantite()*p.getProduit().getPrix();
       panier.remove(p);
   }

    private String verifSolde(float montant, int num) throws Exception_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        serv.MyBankserv port = service.getMyBankservPort();
        return port.verifSolde(montant, num);
    }
   
   
   
}
