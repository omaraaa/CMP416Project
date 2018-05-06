/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Posts;
import db.Textposts;
import db.Users;
import java.time.Instant;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@ManagedBean
@ApplicationScoped
public class User {
    boolean login = false;
    boolean failed = false;
    boolean efailed = false;
    
    String iusername = "";
    String ipass = "";
    String iemail = "";
    
    Users loggeduser = null;
    
    
    
    /**
     * Creates a new instance of User
     */
    public User() {
    }
    
    public void setIusername(String iusername) {
        this.iusername = iusername;
    }

    public void setIpass(String ipass) {
        this.ipass = ipass;
    }

    public boolean isFailed() {
        return failed;
    }

    public boolean isEfailed() {
        return efailed;
    }

    public String getIusername() {
        iusername = "";
        return iusername;
    }

    public String getIpass() {
        ipass = "";
        return ipass;
    }

    public String getIemail() {
        iemail = "";
        return iemail;
    }

    public void setIemail(String iemail) {
        this.iemail = iemail;
    }
    
    public String login() {
        failed = false;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
        EntityManager em = emf.createEntityManager();
        
        Query q = em.createQuery("select a from Users a where a.uname = :uname and a.pass = :pass");
        q.setParameter("uname", iusername);
        q.setParameter("pass", ipass);
        try {
        loggeduser = (Users) q.getSingleResult();
        }
        catch (NoResultException e) {
            failed =  true;
            return null;
        }
        
        iusername = "";
        ipass = "";
        login=true;
        return "index";
    }
    
    public String register() {
        failed = false;
        efailed = false;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
        EntityManager em = emf.createEntityManager();
        
        Query q = em.createNamedQuery("Users.findByUname");
        q.setParameter("uname", iusername);
        try {
            q.getSingleResult();
            failed = true;
            
        } catch (NoResultException e) {
            
        }
        
        q = em.createNamedQuery("Users.findByEmail");
        q.setParameter("email", iemail);
        try {
            q.getSingleResult();
            efailed = true;
            
        } catch (NoResultException e) {
            
        }
        
        if(failed || efailed)
            return null;
        
        Users u = new Users();
        u.setUname(iusername);
        u.setEmail(iemail);
        u.setPass(ipass);
        u.setJoindate(Date.from(Instant.now()));
        u.setCredits(1000);
        
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        
        return "login";
    }
    
    public String checkLogin() {
        if(login) 
            return null;
        
        return "welcome";
    }
    
    public String logout() {
        login=false;
        
        return "welcome";
    }
    
    
    String title = null;
    String ptext = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPtext() {
        return ptext;
    }

    public void setPtext(String ptext) {
        this.ptext = ptext;
    }
    
    
    
    
    public String submit() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Posts p = new Posts();
        p.setUid(loggeduser);
        p.setTitle(title);
        if(ptext != null) {
            
                        
            Textposts tp = new Textposts(p.getPid());
            tp.setText(ptext);
            tp.setPid(p);
            
            
            em.persist(p);
            em.persist(tp);
            
        }
        em.getTransaction().commit();
        return "showPost";
    }
}
