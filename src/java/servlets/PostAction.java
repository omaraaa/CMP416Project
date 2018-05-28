/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.Posts;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(name = "PostAction", urlPatterns = {"/PostAction"})
public class PostAction extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        int uid = Integer.parseInt(request.getParameter("user"));
        int postid = Integer.parseInt(request.getParameter("postid"));
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
        EntityManager em = emf.createEntityManager();
        
        if(action.equals("fav")) {
            Query q = em.createNativeQuery("insert into favs (uid,pid) values (:uid, :pid)");
            q.setParameter("uid", uid);
            q.setParameter("pid", postid);
            q.executeUpdate();
        }
        em.getTransaction().begin();
        
        if(action.equals("multi")) {
            Query q = em.createNamedQuery("Posts.findByPid");
            Posts p = (Posts) q.getSingleResult();
            p.setRnd(p.getRnd()+1);
        }
        
        if(action.equals("rem")) {
            Query q = em.createNamedQuery("Posts.findByPid");
            Posts p = (Posts) q.getSingleResult();
            p.setRnd(p.getRnd()-1);
        }
        em.getTransaction().commit();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
