<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>ReWear – Log In</title>
  <link rel="stylesheet" href="css/style.css">
  <script src="js/validation.js"></script>
</head>
<body>
  <%@ include file="components/navbar.jsp" %>

  <div class="container form-container">
    <h2>Log In</h2>
    <form action="login" method="post">
      <label>Email:</label>
      <input type="email" name="email" required />

      <label>Password:</label>
      <input type="password" name="password" required />

      <label>
        <input type="checkbox" name="remember" /> Remember me
      </label>

      <button type="submit">Log In</button>

      <div class="error">
        <%= request.getAttribute("error") != null 
             ? request.getAttribute("error") : "" %>
      </div>

      <p>Don't have an account? <a href="signup.jsp">Sign Up</a></p>
    </form>
  </div>

  <%@ include file="components/footer.jsp" %>
</body>
</html>
