<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>ReWear – Community Clothing Exchange</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <%@ include file="components/navbar.jsp" %>

  <div class="container" style="text-align:center; padding:2rem;">
    <h1>Welcome to ReWear</h1>
    <p>Swap and redeem clothing with your community. Sustainable fashion starts here.</p>
    <button onclick="location.href='signup.jsp'">Sign Up</button>
    <button onclick="location.href='login.jsp'">Log In</button>
  </div>

  <%@ include file="components/footer.jsp" %>
</body>
</html>
