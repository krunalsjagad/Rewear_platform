package servlet;

import dao.ItemDAO;
import dao.SwapDAO;
import model.Item;
import model.Swap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/item-detail")
public class ItemDetailServlet extends HttpServlet {
    private final ItemDAO itemDAO = new ItemDAO();
    private final SwapDAO swapDAO = new SwapDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
           throws ServletException, IOException {

        int itemId = Integer.parseInt(req.getParameter("id"));
        Optional<Item> opt = itemDAO.getItemById(itemId);
        if (opt.isEmpty()) {
            resp.sendRedirect("browse.jsp?error=notfound");
            return;
        }

        Item item = opt.get();
        req.setAttribute("item", item);

        // If user logged in, fetch their swaps for display
        Object uidObj = req.getAttribute("userId");
        if (uidObj != null) {
            int userId = (int) uidObj;
            List<Swap> swaps = swapDAO.getSwapsByUser(userId);
            req.setAttribute("userSwaps", swaps);
        }

        // Error/success flags
        req.setAttribute("success", req.getParameter("success"));
        req.setAttribute("error", req.getParameter("error"));

        req.getRequestDispatcher("item-detail.jsp").forward(req, resp);
    }
}
