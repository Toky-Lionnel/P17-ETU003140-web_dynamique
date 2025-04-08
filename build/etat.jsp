<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="SO.*"%>
<%@page import="OO.*"%>

<% 
    List<Prevision> previsions = (List<Prevision>)request.getAttribute("previsions");
    DepenseDAO dep = new DepenseDAO();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h3>Etat</h3>

    <table width="500" border="1">
        <tr>
            <th>Libelle</th>
            <th>Prevision</th>
            <th>Total Depenses</th>
            <th>Reste</th>
        </tr>
        <%
            if (previsions != null && !previsions.isEmpty()) { 
            for(Prevision p : previsions) { %>
        <tr>
            <td><%= p.getLibelle() %></td>
            <td><%= p.getMontant() %></td>
            <td><%= dep.findByIdPrev(p.getId()).getMontant() %></td>
            <td><%= p.getMontant()-(int)dep.findByIdPrev(p.getId()).getMontant() %></td>
        </tr>
        <% }}else { %>
            <p>Aucune prevision disponible</p>
        <% } %>
    </table>
</body>
</html>