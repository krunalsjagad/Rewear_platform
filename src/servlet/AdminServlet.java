package servlet;

import dao.ItemDAO;
import model.Item;
import utils.PointManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Item> pending = itemDAO.getPendingItems();
        req.setAttribute("pendingItems", pending);

        // Ajax fragment for polling
        if ("1".equals(req.getParameter("ajax"))) {
            req.getRequestDispatcher("components/pending-rows.jsp")
               .forward(req, resp);
            return;
        }

        req.getRequestDispatcher("admin-panel.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int itemId = Integer.parseInt(req.getParameter("itemId"));
        String  action = req.getParameter("action"); // "approve" or "reject"

        // Update status
        itemDAO.updateStatus(itemId, action.equals("approve") ? "Approved" : "Rejected");

        // If approved, award points to uploader
        if ("approve".equals(action)) {
            // fetch item to get owner
            itemDAO.getItemById(itemId).ifPresent(i ->
                PointManager.awardPoints(i.getUserId(), /*points=*/10)
            );
        }

        resp.sendRedirect("admin");
    }
}
