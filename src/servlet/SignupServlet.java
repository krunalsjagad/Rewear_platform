package servlet;

import dao.UserDAO;
import model.User;
import utils.JWTUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

        String name       = req.getParameter("name");
        String email      = req.getParameter("email");
        String password   = req.getParameter("password");
        String autoLogin  = req.getParameter("autoLogin"); // "on" if checked

        boolean created = userDAO.createUser(name, email, password);
        if (!created) {
            req.setAttribute("error", "Email already registered");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
            return;
        }

        // If user wants to auto-login, generate JWT and set cookie
        if ("on".equals(autoLogin)) {
            Optional<User> optUser = userDAO.validateUser(email, password);
            if (optUser.isPresent()) {
                User user = optUser.get();
                String token = JWTUtil.generateToken(
                    user.getId(), user.getEmail(), user.getRole()
                );

                Cookie cookie = new Cookie("authToken", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge((int)(JWTUtil.EXPIRY_MS / 1000));
                resp.addCookie(cookie);

                resp.sendRedirect("dashboard.jsp");
                return;
            }
        }

        // Otherwise direct to login page with a flag
        resp.sendRedirect("login.jsp?signup=success");
    }
}
