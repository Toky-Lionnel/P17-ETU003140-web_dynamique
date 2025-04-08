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

public class PrevisionServlet extends HttpServlet 
{
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
    {
        try {
            Prevision prevision = new Prevision();
            Map<String, Object> previsions = new HashMap<>();

            String str_libelle = req.getParameter("libelle");
            String str_montant = req.getParameter("montant");
            int montant = 0;
            if (str_libelle != null && str_montant != null) 
            {
                montant = Integer.parseInt(str_montant);
            }

            previsions.put("libelle", str_libelle);
            previsions.put("montant", montant);
           
            prevision.save(previsions);
            

        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        } 

        RequestDispatcher dispat = req.getRequestDispatcher("/");
        dispat.forward(req, res);
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
    {
        
    }
    
}