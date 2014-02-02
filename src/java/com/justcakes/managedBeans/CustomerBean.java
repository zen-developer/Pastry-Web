/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.justcakes.managedBeans;

import com.justcakes.entites.User;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author etudes
 */
@ManagedBean
@SessionScoped
public class CustomerBean implements Serializable{

    /**
     * Creates a new instance of CustomerBean TEST
     */
    boolean isAuth = false;
    User client;
    String password_verif;
    
    @PostConstruct
    public void init() {
       if (client == null)
           client = new User();
    }
    
    public CustomerBean() {
    }
        
    public String getPassword_verif() {
        return password_verif;
    }

    public void setPassword_verif(String password_verif) {
        this.password_verif = password_verif;
    }

    
    public boolean isIsAuth() {
        return isAuth;
    }

    public void setIsAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User customer) {
        this.client = customer;
    }
    
    public String login(){
        
        // Validate Email
        if(!client.getEmail().contains("@")) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage();  
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Email is not valid.");
            message.setDetail("Email is not valid.");
            context.addMessage("loginForm:Email", message);
            //throw new ValidatorException(message);
            return "";
        }
        else{
            
            // Send "client" to object to remote EJB
            // Get verification results then adjust isAuth 
            // Redirect to "dashboard" if Success
            isAuth= true;
            return "";
        }
        
    }
    
    public String register(){
        // validation
        System.out.println("verification PWD "+client.getPassword()+"/"+password_verif);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        boolean pwd=false, email=false;
        
        // password verif
        if (!client.getPassword().equalsIgnoreCase(password_verif))
        {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Vérification de mot de passe échouée.");
            message.setDetail("Vérification de mot de passe échouée.");
            context.addMessage("regForm:Password", message);
            //throw new ValidatorException(message);
        }
        else
            pwd = true;
        
        // email verif
        if(!client.getEmail().contains("@")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Email is not valid.");
            message.setDetail("Email is not valid.");
            context.addMessage("loginForm:Email", message);
            //throw new ValidatorException(message);
        }
        else
            email = true;
        
        
        if (pwd && email){
            
            /********/
            // Send "client" to object to remote EJB
            /********/
            
            
           return "success_register.xhtml";
        }
        else
            return "";
    }
    
    /*public void getRemoteEjb(){
        Properties prop = new Properties();
        prop.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        Context ctx;
        try {
            ctx = new InitialContext(prop);
        } catch (NamingException ex) {
            Logger.getLogger(CustomerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CategoryFacadeRemote myRemoteEjb = (CategoryFacadeRemote) ctx.lookup("java:global/DSPREP_EJB/CategoryFacade!beans.CategoryFacadeRemote");
        System.out.println("debut");
        System.out.println("message : ");
        myRemoteEjb.create(new Category(Integer.MIN_VALUE, "OK"));
        List<Category> res = myRemoteEjb.findMyCategory("c");
        System.out.println("Found categories : "+res.size());
        if (res.size() > 0){
            for (Category category : res) {
                System.out.println(category.getNom());
                for (Cake ck : category.getCakeCollection()) {
                    System.out.println("- "+ck.getNom());
                }
            }
        }
    }*/
    
}
