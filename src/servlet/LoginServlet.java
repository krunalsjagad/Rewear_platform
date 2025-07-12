package servlet;

import dao.UserDAO;
import model.User;
import utils.JWTUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

        String email    = req.getParameter("email");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember"); // optional checkbox

        Optional<User> optUser = userDAO.validateUser(email, password);
        if (optUser.isEmpty()) {
            req.setAttribute("error", "Invalid email or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        User user = optUser.get();
        String token = JWTUtil.generateToken(
            user.getId(), user.getEmail(), user.getRole()
        );

        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // If “Remember Me” checked, extend cookie beyond default expiry
        if ("on".equals(remember)) {
            cookie.setMaxAge(7 * 24 * 3600); // 7 days
        } else {
            cookie.setMaxAge((int)(JWTUtil.EXPIRY_MS / 1000));
        }
        resp.addCookie(cookie);

        resp.sendRedirect("dashboard.jsp");
    }
}
