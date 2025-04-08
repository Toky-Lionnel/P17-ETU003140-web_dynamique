<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="SO.*"%>
<%@page import="OO.*"%>

<% 
    List<Prevision> previsions = (List<Prevision>)request.getAttribute("previsions");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h3>Insertion des depenses</h3>
    <form name="depense" method="post" action="insert_depense">
        <label for="prevision">Libelle de prevision : </label>
        <select name="prevision" id="prevision">
            <option>Prevision</option>
            <%
            if (previsions != null && !previsions.isEmpty()) { 
            for(Prevision p : previsions) { %>
                <option value="<%= p.getId() %>"><%= p.getLibelle() %></option>
            <% }}else { %>
                <option disabled>Aucune prevision disponible</option>
            <% } %> 
        </select>
        <p>
            Montant : <input type="number" name="montant">
        </p>
        <input type="submit" value="Entrer">
    </form>
</html>