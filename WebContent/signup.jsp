<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>ReWear – Sign Up</title>
  <link rel="stylesheet" href="css/style.css">
  <script src="js/validation.js"></script>
</head>
<body>
  <%@ include file="components/navbar.jsp" %>

  <div class="container form-container">
    <h2>Create an Account</h2>
    <form action="signup" method="post">
      <label>Name:</label>
      <input type="text" name="name" required />

      <label>Email:</label>
      <input type="email" name="email" required />

      <label>Password:</label>
      <input type="password" name="password" required />

      <label>
        <input type="checkbox" name="autoLogin" /> Keep me logged in
      </label>

      <button type="submit">Sign Up</button>

      <div class="error">
        <%= request.getAttribute("error") != null 
             ? request.getAttribute("error") : "" %>
      </div>

      <p>Already have an account? <a href="login.jsp">Log In</a></p>
    </form>
  </div>

  <%@ include file="components/footer.jsp" %>
</body>
</html>
