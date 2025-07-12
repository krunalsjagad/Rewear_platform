<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>ReWear – Add Item</title>
  <link rel="stylesheet" href="css/style.css">
  <script src="js/validation.js"></script>
</head>
<body>
  <%@ include file="components/navbar.jsp" %>

  <div class="container">
    <h2>List a New Item</h2>
    <form action="add-item" method="post" enctype="multipart/form-data">
      <label>Title:</label>
      <input type="text" name="title" required />

      <label>Description:</label>
      <textarea name="description" rows="3"></textarea>

      <label>Category:</label>
      <select name="category" required>
        <option>Men</option>
        <option>Women</option>
        <option>Kids</option>
        <option>Unisex</option>
      </select>

      <label>Type:</label>
      <input type="text" name="type" placeholder="e.g. Shirt, Pants" required/>

      <label>Size:</label>
      <select name="size" required>
        <option>XS</option><option>S</option><option>M</option>
        <option>L</option><option>XL</option><option>XXL</option>
      </select>

      <label>Condition:</label>
      <select name="condition" required>
        <option>New</option><option>Like New</option>
        <option>Gently Used</option><option>Used</option>
        <option>Old but Usable</option>
      </select>

      <label>Images (max 5):</label>
      <input type="file" name="images" accept="image/*" multiple />

      <button type="submit">Submit</button>
    </form>
  </div>

  <%@ include file="components/footer.jsp" %>
</body>
</html>
