
package com.justcakes.managedBeans;


import DAO.ProduitFacadeRemote;
import entities.Produit;
import java.util.ArrayList;
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
    private List<Produit> listCakes;
    public CakeBean() {
    }
    @PostConstruct
    private void init(){
    try {
        prop = new Properties();
        prop.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        Context ctx = new InitialContext(prop);
        
        pfr=(ProduitFacadeRemote) ctx.lookup("java:global/Pastry-EJB/ProduitFacade!DAO.ProduitFacadeRemote");
        pfr.sayHello("pato");
        System.out.println(pfr.find(3));
       // listCakes=pfr.findAll();
//        Produit a=new Produit(Integer.MIN_VALUE, "Petit pain", "", "cake1", 1, 1, null);
//        Produit b=new Produit(Integer.MIN_VALUE, "Madeleine", "", "cake2", 2, 14, null);
//        Produit c=new Produit(Integer.MIN_VALUE, "Brownies", "", "cake3", 3, 2, null);
//        Produit d=new Produit(Integer.MIN_VALUE, "Cup Cake", "", "cake4", 4, 500, null);
//        Produit e=new Produit(Integer.MIN_VALUE, "Chocolate","", "cake5", 5, 100, null);
//        Produit f=new Produit(Integer.MIN_VALUE, "Ice Cake", "", "cake6", 6, 26, null);
//        Produit g=new Produit(Integer.MIN_VALUE, "Vanila cake", "", "cake7", 7, 40, null);
//        Produit h=new Produit(Integer.MIN_VALUE, "French cake", "", "cake8", 8, 8, null);
//        Produit i=new Produit(Integer.MIN_VALUE, "ITech","", "cake2", 9, 12, null);
//        Produit j=new Produit(Integer.MIN_VALUE, "Milles feuilles", "", "cake1", 10, 15, null);
//        listCakes.add(a);
//        listCakes.add(b);
//        listCakes.add(c);
//        listCakes.add(d);
//        listCakes.add(e);
//        listCakes.add(f);
//        listCakes.add(g);
//        listCakes.add(h);
//        listCakes.add(i);
//        listCakes.add(j);
    } catch (NamingException ex) {
        Logger.getLogger(CakeBean.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    public String test(String nn){
        return pfr.sayHello(nn);
    }
    public List<Produit> getListCakes() {
        return listCakes;
    }

    public void setListCakes(List<Produit> listCakes) {
        this.listCakes = listCakes;
    }
    
}
