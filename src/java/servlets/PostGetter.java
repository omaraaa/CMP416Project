/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.Posts;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "PostGetter", urlPatterns = {"/PostGetter"})
public class PostGetter extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int count = Integer.parseInt(request.getParameter("count"));
            int lastid = Integer.parseInt(request.getParameter("lastid"));
         
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CMP416ProjectPU");
            EntityManager em = emf.createEntityManager();

            Query q = em.createQuery("SELECT p FROM Posts p WHERE p.pid > :lastp");
            q.setParameter("lastp", lastid);
            List l = q.getResultList();
        
        for(int i = 0; i < count && i < l.size(); i++) {
            
            Posts p = (Posts) l.get(i);
            out.println("<div id=" +"\""+p.getPid()+"\" class=\"pc\">");
            
            if(p.getLnk()!=null) {
               out.println("<h1 class=\"ui-widget-header\">");
               out.println("<a href="+"\""+p.getLnk()+"\">"+p.getTitle()+"</a>");
               out.println("</h1>");
            }else {
                out.println("<h1 class=\"ui-widget-header\">");
                out.println(p.getTitle());
                out.println("</h1>");
            }
            
            if(p.getTxt()!=null){ 
            out.println("<p class=\"ui-widget-content\">");
            out.println(p.getTxt());
            out.println("</p>");
            }
            out.println("submitted by "+p.getUid().getUname()+"</br>");
            out.println("<input type=\"button\" value=\"Favorite Post\" class=\"fav\"/>");
            out.println("<input type=\"button\" value=\"Dislike\" class=\"rem\"/>");
            out.println("<input type=\"button\" value=\"Like\" class=\"multi\"/>");
            out.println("</div>");
        }
        /*
        <h1 class="ui-widget-header">Post Placeholder Title</h1>
                <p class="ui-widget-content">Test Post Test Post Test Post Test<br/>
                    Post Test Post Test Post Test Post Test Post<br/>
                    Test Post Test Post Test Post Test Post<br/>
                    Test Post Test Post<br/>
                </p>
                <input type="button" value="Favorite Post" id="fav"/>

                <input type="button" value="Remove Post(-1)" id="rem"/>

                <input type="button" value="Mutliply Post(-1)" id="multi"/>
            /* TODO output your page here. You may use following sample code. */
          
        }
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
