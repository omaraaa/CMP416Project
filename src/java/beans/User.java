/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Orders;
import db.OrdersPK;
import db.Packages;
import db.Posts;
import db.Users;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.Part;

/**
 *
 * @author User
 */
@ManagedBean
@ApplicationScoped
public class User {

    boolean login = false;
    boolean failed = false;
    boolean ufailed = false;
    boolean efailed = false;

    String iusername = "";
    String ipass = "";
    String iemail = "";

    Users loggeduser = null;
    
    String viewUserName = "";
    Users viewUser = null;

    /**
     * Creates a new instance of User
     */
    public User() {
    }

    public int getCredits() {
        return loggeduser.getCredits();
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

    public boolean isUfailed() {
        return ufailed;
    }
    
    
    public String getIusername() {
        if(loggeduser != null)
            return loggeduser.getUname();
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
        
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(iusername.getBytes(StandardCharsets.UTF_8));
            byte[] b = md.digest(ipass.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< b.length ;i++){
               sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
            }
            String hashedpass = sb.toString();
            ipass = hashedpass;    
        } catch (NoSuchAlgorithmException  e) {
            e.printStackTrace();
        }
        
        Query q = em.createQuery("select a from Users a where a.uname = :uname and a.pass = :pass");
        q.setParameter("uname", iusername);
        q.setParameter("pass", ipass);
        try {
            loggeduser = (Users) q.getSingleResult();
        } catch (NoResultException e) {
            failed = true;
            return null;
        }
        em.getTransaction().begin();
        
        if (loggeduser.getCredits() < 500) {
            Date d = loggeduser.getLogindate();
            
            long days = (Date.from(Instant.now()).getTime() - d.getTime()) / (1000 * 60 * 60 * 24);
            if (days > 1) {
                loggeduser.setCredits(Integer.max(loggeduser.getCredits() + 100, 500));
            }
        }
        loggeduser.setLogindate(Date.from(Instant.now()));
        em.getTransaction().commit();
        iusername = "";
        ipass = "";
        login = true;
        return "index";
    }

    public String register() {
        ufailed = false;
        efailed = false;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
        EntityManager em = emf.createEntityManager();

        Query q = em.createNamedQuery("Users.findByUname");
        q.setParameter("uname", iusername);
        try {
            q.getSingleResult();
            ufailed = true;

        } catch (NoResultException e) {

        }

        q = em.createNamedQuery("Users.findByEmail");
        q.setParameter("email", iemail);
        try {
            q.getSingleResult();
            efailed = true;

        } catch (NoResultException e) {

        }

        if (ufailed || efailed) {
            return null;
        }

        Users u = new Users();
        u.setUname(iusername);
        u.setEmail(iemail);
        
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(iusername.getBytes(StandardCharsets.UTF_8));
            byte[] b = md.digest(ipass.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< b.length ;i++){
               sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
            }
            String hashedpass = sb.toString();
            u.setPass(hashedpass);    
        } catch (NoSuchAlgorithmException  e) {
            e.printStackTrace();
        }
        
        u.setJoindate(Date.from(Instant.now()));
        u.setLogindate(u.getJoindate());
        u.setCredits(500);

        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();

        return "login";
    }

    public String checkLogin() {
        if (login) {
            return null;
        }

        return "welcome";
    }

    public String logout() {
        login = false;

        return "welcome";
    }

    String title = null;
    String ptext = null;
    String ltext = null;

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
        if (loggeduser.getCredits() < 10) {
            return "credits";
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Posts p = new Posts();
        p.setUid(loggeduser);
        p.setTitle(title);
        p.setRnd(0);
        if (ptext != null) {
            p.setTxt(ptext);
            ptext = null;
        }
        if (ltext != null) {
            if (!ltext.matches("https?://.*")) {
                ltext = "http://" + ltext;
            }
            p.setLnk(ltext);
            ltext = null;
        }
        title = null;
        loggeduser.setCredits(loggeduser.getCredits() - 10);
        em.persist(p);
        em.getTransaction().commit();
        return "back";
    }

    public String getLtext() {
        return ltext;
    }

    public void setLtext(String ltext) {
        this.ltext = ltext;
    }
    
    public List<Packages> getPackages() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
        EntityManager em = emf.createEntityManager();
        
        Query q = em.createNamedQuery("Packages.findAll");
        List l = q.getResultList();
        return l;
    }
    
    int cpack=0;

    public int getCpack() {
        return cpack;
    }

    public void setCpack(int cpack) {
        this.cpack = cpack;
    }
    
    public String buy() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
        EntityManager em = emf.createEntityManager();
        
        Query q = em.createNamedQuery("Packages.findByPkid");
        Packages p = (Packages) q.getSingleResult();

        Orders order = new Orders(cpack, loggeduser.getUid());
        order.setOdate(Date.from(Instant.now()));
        order.setPaymentmethod("credit");;
        order.setPackages(p);
        order.setUsers(loggeduser);
        
        em.getTransaction().begin();
        loggeduser.setCredits(loggeduser.getCredits() + p.getCno());
        em.persist(order);
        em.getTransaction().commit();
        
        return "index";
    }

    public List<Posts> getViewUserPosts() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
        EntityManager em = emf.createEntityManager();
        
        Query q = em.createQuery("SELECT p FROM Posts p, Users u WHERE p.uid.uid = u.uid");
           
        return q.getResultList();
    }

    public String getViewUserName() {
        return viewUserName;
    }

    public void setViewUserName(String viewUserName) {
        this.viewUserName = viewUserName;
    }

    public Users getViewUser() {
        if(viewUser == null){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
            EntityManager em = emf.createEntityManager();

            Query q = em.createNamedQuery("Users.findByUname");
            viewUser = (Users) q.getSingleResult();
        }
        
        return viewUser;
    }
    
    int viewpid = 0;
    public String viewPost(int pid) {
        viewpid = pid;
        return "post";
    }

    public Users getLoggeduser() {
        return loggeduser;
    }
    
}
