package OO;

import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import Connexion.*;
import SO.*;

public class Prevision
{
    private UtilDB connect = new UtilDB();
    private int id;
    private String libelle;
    private int montant;

    public Prevision(){}
    
    public Prevision(String libelle, int montant) 
    {
        this.libelle = libelle;
        this.montant = montant;
    }

    public UtilDB getConnect() {
        return connect;
    }

    public void setConnect(UtilDB connect) {
        this.connect = connect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
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

        String query = "INSERT INTO Prevision" + " (" + columns + ") VALUES (" + placeholders + ")";
        System.out.println(""+query);

        try (Connection connection = connect.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            int index = 1;
            for (Object value : values.values()) 
            {
                stmt.setObject(index++, value);
            }

            stmt.executeUpdate();
            System.out.println("Previsions inserees");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }


    public List<Prevision> findAll() throws Exception
    {
        List<Prevision> list = new ArrayList<>();
        String query = "SELECT * FROM Prevision";

        try (Connection connection = connect.connect();
             Statement stmt = connection.createStatement();
             ResultSet rSet = stmt.executeQuery(query)) {

            while (rSet.next()) 
            {
                Prevision entity = new Prevision();
                entity.setId(rSet.getInt("id"));
                entity.setLibelle(rSet.getString("libelle"));
                entity.setMontant(rSet.getInt("montant"));
            
            list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
       
        return list;
    }

    
    public Prevision findByID(int id)  throws Exception 
    {
        String query = "SELECT * FROM Prevision" + " WHERE id = ?";
        Prevision entity = null;

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

    
    public void update(int id, Map<String, Object> updates)  throws Exception 
    {
        if (updates.isEmpty()) return;

        StringBuilder query = new StringBuilder("UPDATE Prevision" + " SET ");
        boolean first = true;

        for (String column : updates.keySet()) 
        {
            if (!first) query.append(", ");
            query.append(column).append(" = ?");
            first = false;
        }

        query.append(" WHERE id = ?");

        try (Connection connection = connect.connect();
             PreparedStatement stmt = connection.prepareStatement(query.toString())) {

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
        String query = "DELETE FROM Prevision" + " WHERE id = ?";

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
    private Prevision createEntityFromResultSet(ResultSet rSet) throws Exception 
    {
        try {
            Prevision entity = new Prevision(); 

            for (Field field : Prevision.class.getDeclaredFields()) 
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

    
    
}