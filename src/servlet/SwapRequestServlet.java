package servlet;

import dao.ItemDAO;
import dao.SwapDAO;
import model.Item;
import utils.PointManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/swap-request")
public class SwapRequestServlet extends HttpServlet {
    private final ItemDAO itemDAO = new ItemDAO();
    private final SwapDAO swapDAO = new SwapDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
           throws ServletException, IOException {

        int userId = (int) req.getAttribute("userId");
        int itemId = Integer.parseInt(req.getParameter("itemId"));

        // 1) Load item and cost
        Optional<Item> optItem = itemDAO.getItemById(itemId);
        if (optItem.isEmpty()) {
            resp.sendRedirect("item-detail?id=" + itemId + "&error=notfound");
            return;
        }
        Item item = optItem.get();

        // 2) Check points
        if (item.getPointsCost() > (Integer) req.getAttribute("points")) {
            resp.sendRedirect("item-detail?id=" + itemId + "&error=notenough");
            return;
        }

        // 3) Deduct points & create swap record
        boolean deducted = PointManager.deductPoints(userId, item.getPointsCost());
        if (!deducted) {
            resp.sendRedirect("item-detail?id=" + itemId + "&error=txfail");
            return;
        }
        swapDAO.createSwap(itemId, userId);

        resp.sendRedirect("item-detail?id=" + itemId + "&success=requested");
    }
}
