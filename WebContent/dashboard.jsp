<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item, model.Swap, model.User" %>
<!DOCTYPE html>
<html>
<head>
  <title>ReWear – Dashboard</title>
  <link rel="stylesheet" href="css/style.css">
  <script src="js/dashboard.js"></script>
</head>
<body>
  <%@ include file="components/navbar.jsp" %>

  <div class="container">
    <%
      User user = (User) request.getAttribute("user");
      int points = (Integer) request.getAttribute("points");
      List<Item> items = (List<Item>) request.getAttribute("items");
      List<Swap> swaps = (List<Swap>) request.getAttribute("swaps");
      String added = request.getParameter("added");
    %>

    <h2>Welcome, <%= user.getName() %>!</h2>
    <p>Your Points: <strong><%= points %></strong></p>
    <% if ("success".equals(added)) { %>
      <div class="alert success">Item submitted! Awaiting approval.</div>
    <% } %>

    <div class="tabs">
      <button id="tab-items">My Listings</button>
      <button id="tab-swaps">My Swaps</button>
    </div>

    <div id="items-section">
      <h3>My Listings</h3>
      <ul>
        <% for (Item it : items) { %>
          <li>
            <a href="item-detail?id=<%= it.getId() %>">
              <%= it.getTitle() %> (<%= it.getStatus() %>)
            </a>
          </li>
        <% } %>
      </ul>
    </div>

    <div id="swaps-section" style="display:none">
      <h3>My Swap Requests</h3>
      <ul>
        <% for (Swap s : swaps) { %>
          <li>Request #<%= s.getId() %> - Status: <%= s.getStatus() %></li>
        <% } %>
      </ul>
    </div>
  </div>

  <%@ include file="components/footer.jsp" %>
</body>
</html>
