<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<!DOCTYPE html>
<html>
<head>
  <title>ReWear – Browse Items</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <%@ include file="components/navbar.jsp" %>

  <div class="browse-container">
    <h2>Browse Items</h2>
    <form method="get" action="browse">
      <input type="text" name="search"
             placeholder="Search by title or keyword"
             value="<%= request.getAttribute("search") != null 
                       ? request.getAttribute("search") 
                       : "" %>" />

      <select name="category">
        <option>All</option>
        <option<%= "Men".equals(request.getAttribute("selCategory")) 
                    ? " selected" : "" %>>Men</option>
        <option<%= "Women".equals(request.getAttribute("selCategory")) 
                    ? " selected" : "" %>>Women</option>
        <option<%= "Kids".equals(request.getAttribute("selCategory")) 
                    ? " selected" : "" %>>Kids</option>
        <option<%= "Unisex".equals(request.getAttribute("selCategory")) 
                    ? " selected" : "" %>>Unisex</option>
      </select>

      <select name="type">
        <option>All</option>
        <option<%= "Shirt".equals(request.getAttribute("selType")) 
                    ? " selected" : "" %>>Shirt</option>
        <option<%= "Pants".equals(request.getAttribute("selType")) 
                    ? " selected" : "" %>>Pants</option>
        <option<%= "Jacket".equals(request.getAttribute("selType")) 
                    ? " selected" : "" %>>Jacket</option>
        <!-- add other types as needed -->
      </select>

      <select name="size">
        <option>All</option>
        <option<%= "XS".equals(request.getAttribute("selSize")) ? " selected" : "" %>>XS</option>
        <option<%= "S".equals(request.getAttribute("selSize"))  ? " selected" : "" %>>S</option>
        <option<%= "M".equals(request.getAttribute("selSize"))  ? " selected" : "" %>>M</option>
        <option<%= "L".equals(request.getAttribute("selSize"))  ? " selected" : "" %>>L</option>
        <option<%= "XL".equals(request.getAttribute("selSize")) ? " selected" : "" %>>XL</option>
        <option<%= "XXL".equals(request.getAttribute("selSize"))? " selected" : "" %>>XXL</option>
      </select>

      <select name="condition">
        <option>All</option>
        <option<%= "New".equals(request.getAttribute("selCondition"))      ? " selected" : "" %>>New</option>
        <option<%= "Like New".equals(request.getAttribute("selCondition")) ? " selected" : "" %>>Like New</option>
        <option<%= "Gently Used".equals(request.getAttribute("selCondition"))? " selected" : "" %>>Gently Used</option>
        <option<%= "Used".equals(request.getAttribute("selCondition"))    ? " selected" : "" %>>Used</option>
        <option<%= "Old but Usable".equals(request.getAttribute("selCondition"))? " selected" : "" %>>Old but Usable</option>
      </select>

      <select name="sort">
        <option value="recent"
          <%= "recent".equals(request.getAttribute("selSort")) ? "selected" : "" %>>
          Recently Added
        </option>
        <option value="size"
          <%= "size".equals(request.getAttribute("selSort")) ? "selected" : "" %>>
          Size (A-Z)
        </option>
        <option value="points"
          <%= "points".equals(request.getAttribute("selSort")) ? "selected" : "" %>>
          Points (Low-High)
        </option>
      </select>

      <button type="submit">Apply</button>
    </form>

    <div class="item-grid">
      <%
        List<Item> items = (List<Item>) request.getAttribute("items");
        if (items != null && !items.isEmpty()) {
          for (Item it : items) {
      %>
        <div class="item-card">
          <img src="<%= it.getImagePaths().isEmpty() 
                     ? "images/placeholder.png" 
                     : it.getImagePaths().get(0) %>" 
               alt="<%= it.getTitle() %>" />
          <h3><%= it.getTitle() %></h3>
          <p><strong>Points:</strong> <%= it.getPointsCost() %></p>
          <a href="item-detail.jsp?id=<%= it.getId() %>">
            View Details
          </a>
        </div>
      <%
          }
        } else {
      %>
        <p>No items found matching your criteria.</p>
      <%
        }
      %>
    </div>
  </div>

  <%@ include file="components/footer.jsp" %>
</body>
</html>
