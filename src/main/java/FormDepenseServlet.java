import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Connexion.*;
import OO.*;
import SO.*;

public class FormDepenseServlet extends HttpServlet 
{
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
    {
        try {
            // Depense e = new Depense();
            // String id_prevision = req.getParameter("id");
            // if (id_prevision != null) 
            // {
            //     int id = Integer.parseInt(id_prevision);
            //     Prevision prevision = e.findByID(id);
            //     req.setAttribute("prevision", prevision);
            // }

            Prevision prevision = new Prevision();
            List<Prevision> previsions = prevision.findAll();            
            req.setAttribute("previsions", previsions);  

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        } 

        RequestDispatcher dispat = req.getRequestDispatcher("form_depense.jsp");
        dispat.forward(req,res);
    }
}