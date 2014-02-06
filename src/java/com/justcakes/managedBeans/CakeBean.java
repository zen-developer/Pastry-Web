
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
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Zen
 */
@ManagedBean
@RequestScoped
public class CakeBean {
Properties prop;
Context ctx ;
ProduitFacadeRemote pfr;
    private Collection<Produit> listCakes;
    private List<CartItem> listCartItem=new ArrayList<CartItem>();
    public CakeBean() {
    }
    @PostConstruct
    private void init(){
    try {
        prop = new Properties();
        prop.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        Context ctx = new InitialContext(prop);
        pfr=(ProduitFacadeRemote) ctx.lookup("java:global/Pastry-EJB/ProduitFacade!DAO.ProduitFacadeRemote");
        listCakes=pfr.findAllProduits();
        for(Produit p: listCakes){
            CartItem c=new CartItem();
            c.setProduit(p);
            c.setQuantite(1);
            listCartItem.add(c);
        }
    } catch (NamingException ex) {
        Logger.getLogger(CakeBean.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    public String test(String nn){
        return pfr.sayHello(nn);
    }

    public ProduitFacadeRemote getPfr() {
        return pfr;
    }

    public void setPfr(ProduitFacadeRemote pfr) {
        this.pfr = pfr;
    }

    public Collection<Produit> getListCakes() {
        return listCakes;
    }

    public void setListCakes(Collection<Produit> listCakes) {
        this.listCakes = listCakes;
    }

    public List<CartItem> getListCartItem() {
        return listCartItem;
    }

    public void setListCartItem(List<CartItem> listCartItem) {
        this.listCartItem = listCartItem;
    }
    
    
}
