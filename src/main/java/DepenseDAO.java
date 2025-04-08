package SO;

import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import Connexion.*;
import OO.*;

public class DepenseDAO 
{
    private UtilDB connect = new UtilDB();

    public UtilDB getConnect() {
        return connect;
    }

    public void setConnect(UtilDB connect) {
        this.connect = connect;
    }

    public void save(Map<String, Object> values) throws Exception
    {
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (String column : values.keySet()) 
        {
            columns.append(column).append(",");
            placeholders.append("?,");
        }

        // Supprimer la dernière virgule
        columns.setLength(columns.length() - 1);
        placeholders.setLength(placeholders.length() - 1);

        String query = "INSERT INTO Depense" + " (" + columns + ") VALUES (" + placeholders + ")";
        System.out.println(""+query);

        try (Connection connection = connect.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            int index = 1;
            for (Object value : values.values()) 
            {
                stmt.setObject(index++, value);
            }

            stmt.executeUpdate();
            System.out.println("Depense inseree");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }


    public List<Depense> findAll() throws Exception
    {
        List<Depense> list = new ArrayList<>();
        String query = "SELECT * FROM Depense";

        try (Connection connection = connect.connect();
             Statement stmt = connection.createStatement();
             ResultSet rSet = stmt.executeQuery(query)) {

            while (rSet.next()) 
            {
                Depense entity = new Depense();
                entity.setId(rSet.getInt("id"));
                entity.setId_prevision(rSet.getInt("id_prevision"));
                entity.setMontant(rSet.getInt("montant"));
            
            list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
       
        return list;
    }

    
    public Depense findByID(int id)  throws Exception 
    {
        String query = "SELECT * FROM Depense" + " WHERE id = ?";
        Depense entity = null;

        try (Connection connection = connect.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rSet = stmt.executeQuery();

            if (rSet.next()) {
                entity = createEntityFromResultSet(rSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return entity;
    }

    
    public void update(int id, Map<String, Object> updates, Connection connection)  throws Exception 
    {
        if (updates.isEmpty()) return;

        StringBuilder query = new StringBuilder("UPDATE Depense" + " SET ");
        boolean first = true;

        for (String column : updates.keySet()) 
        {
            if (!first) query.append(", ");
            query.append(column).append(" = ?");
            first = false;
        }

        query.append(" WHERE id_prevision = ?");

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {

            int index = 1;
            for (Object value : updates.values()) {
                stmt.setObject(index++, value);
            }
            stmt.setInt(index, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    
    public void remove(int id)  throws Exception 
    {
        String query = "DELETE FROM Depense" + " WHERE id = ?";

        try (Connection connection = connect.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    //  Convertir un ResultSet en objet T
    private Depense createEntityFromResultSet(ResultSet rSet) throws Exception 
    {
        try {
            Depense entity = new Depense(); 

            for (Field field : Depense.class.getDeclaredFields()) 
            {
                if (field.getName().equals("connect")) 
                {
                    continue;
                }
    
                field.setAccessible(true);
                Object value = rSet.getObject(field.getName());
                field.set(entity, value);
            }
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public Depense findByIdPrev(int id_prevision)  throws Exception 
    {
        String query = "SELECT * FROM Depense" + " WHERE id_prevision = ?";
        Depense entity = null;

        try (Connection connection = connect.connect();
            PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id_prevision);
            ResultSet rSet = stmt.executeQuery();

            if (rSet.next()) {
                entity = createEntityFromResultSet(rSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return entity;
    }

    public boolean checkInsert(int id_prevision, int montant, Connection connect) throws Exception
    {
        Prevision prevision = new Prevision();
        boolean ans = false;
        int montant_prev = prevision.findByID(id_prevision).getMontant();
        if (montant_prev >= montant) 
        {
            ans = true;
        }
        return ans;
    }

    public void insert_depense(int montant, int id_prevision) throws Exception
    {
        DepenseDAO depense = new DepenseDAO();
        int montant_depense = depense.findByIdPrev(id_prevision).getMontant();

        try(Connection connection = this.getConnect().connect();) 
        {  
            Map<String, Object> dep = new HashMap<>();
            dep.put("montant", montant_depense+montant);

            boolean check = this.checkInsert(id_prevision, montant, connection);
            if (check) 
            {
                this.update(id_prevision, dep, connection);
            }
            else
            {
               throw new Exception("Prevision depassee");
            }

        }
        catch (Exception e) {
            throw new Exception("TSY MAHAZO MI-DEBITER", e);
        }
    }

}