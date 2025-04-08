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

public class DepenseServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
    {
        try {
            DepenseDAO depenseDAO = new DepenseDAO();
            Map<String, Object> depenses = new HashMap<>();

            String str_id_prevision = req.getParameter("prevision");
            String str_montant = req.getParameter("montant");
            int id_prevision = 0;
            int montant = 0;

            if (str_id_prevision != null && str_montant != null) 
            {
                id_prevision = Integer.parseInt(str_id_prevision);
                montant = Integer.parseInt(str_montant);
            }

            depenses.put("id_prevision", id_prevision);
            depenses.put("montant", montant);

            if (depenseDAO.findByIdPrev(id_prevision) != null) 
            {
                depenseDAO.insert_depense(montant, id_prevision);
            }
            else
            {
                depenseDAO.save(depenses);
            }
            

        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

        RequestDispatcher dispat = req.getRequestDispatcher("form_depense.jsp"); 
        dispat.forward(req,res);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
    {
        try {
            Prevision prevision = new Prevision();
            List<Prevision> previsions = prevision.findAll();            
            req.setAttribute("previsions", previsions);  

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        } 

        RequestDispatcher dispat = req.getRequestDispatcher("etat.jsp");
        dispat.forward(req,res);
    }
}