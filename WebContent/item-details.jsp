<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Item, java.util.List" %>
<%@ page import="model.Swap" %>
<!DOCTYPE html>
<html>
<head>
  <title>ReWear – Item Detail</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <%@ include file="components/navbar.jsp" %>

  <%
    Item item = (Item) request.getAttribute("item");
    List<String> imgs = item.getImagePaths();
    String success = (String) request.getAttribute("success");
    String error   = (String) request.getAttribute("error");
  %>

  <div class="detail-container">
    <h2><%= item.getTitle() %></h2>

    <div class="gallery">
      <% if (imgs.isEmpty()) { %>
        <img src="images/placeholder.png" alt="No image" />
      <% } else {
           for (String path : imgs) { %>
             <img src="<%= path %>" alt="<%= item.getTitle() %>" />
      <%   }
         }
      %>
    </div>

    <p><strong>Description:</strong> <%= item.getDescription() %></p>
    <p><strong>Category:</strong> <%= item.getCategory() %></p>
    <p><strong>Type:</strong> <%= item.getType() %></p>
    <p><strong>Size:</strong> <%= item.getSize() %></p>
    <p><strong>Condition:</strong> <%= item.getCondition() %></p>
    <p><strong>Cost in Points:</strong> <%= item.getPointsCost() %></p>
    <p><strong>Status:</strong> <%= item.getStatus() %></p>

    <div class="messages">
      <% if ("requested".equals(success)) { %>
        <div class="alert success">Swap requested! Check dashboard.</div>
      <% } else if ("notenough".equals(error)) { %>
        <div class="alert error">Not enough points.</div>
      <% } else if ("notfound".equals(error)) { %>
        <div class="alert error">Item not found.</div>
      <% } %>
    </div>

    <%-- Only show request button to non-owners if item is approved --%>
    <%
      Integer userId = (Integer) request.getAttribute("userId");
      if (userId != null && userId != item.getUserId()
          && "Approved".equals(item.getStatus())) {
    %>
      <form action="swap-request" method="post">
        <input type="hidden" name="itemId" value="<%= item.getId() %>" />
        <button type="submit">Request with <%= item.getPointsCost() %> Points</button>
      </form>
    <% } %>

    <a href="browse.jsp">← Back to Browse</a>
  </div>

  <%@ include file="components/footer.jsp" %>
</body>
</html>
