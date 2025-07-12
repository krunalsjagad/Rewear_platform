package servlet;

import dao.ItemDAO;
import dao.SwapDAO;
import dao.UserDAO;
import model.Item;
import model.Swap;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final UserDAO userDAO   = new UserDAO();
    private final ItemDAO itemDAO   = new ItemDAO();
    private final SwapDAO swapDAO   = new SwapDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Retrieve userId set by AuthFilter
        int userId = (int) req.getAttribute("userId");

        // 2. Fetch user profile
        Optional<User> optUser = userDAO.getUserById(userId);
        if (optUser.isEmpty()) {
            resp.sendRedirect("login.jsp");
            return;
        }
        User user = optUser.get();

        // 3. Fetch user’s items and swap history
        List<Item> items = itemDAO.getItemsByUser(userId);
        List<Swap> swaps = swapDAO.getSwapsByUser(userId);

        // 4. Set attributes for JSP
        req.setAttribute("user", user);
        req.setAttribute("items", items);
        req.setAttribute("swaps", swaps);
        req.setAttribute("points", user.getPoints());

        // 5. Forward to dashboard.jsp
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}
