// src/servlet/LogoutServlet.java
package servlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Cookie cookie = new Cookie("authToken", "");
    cookie.setMaxAge(0);
    cookie.setPath("/");
    resp.addCookie(cookie);
    resp.sendRedirect("login.jsp");
  }
}
