package servlet;

import dao.ItemDAO;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/browse")
public class BrowseServlet extends HttpServlet {
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Read query parameters (may be null or empty)
        String keyword   = req.getParameter("search");
        String category  = req.getParameter("category");
        String type      = req.getParameter("type");
        String size      = req.getParameter("size");
        String condition = req.getParameter("condition");
        String sort      = req.getParameter("sort");

        // Default values
        if (category == null)  category  = "All";
        if (type == null)      type      = "All";
        if (size == null)      size      = "All";
        if (condition == null) condition = "All";
        if (sort == null)      sort      = "recent";

        // Fetch filtered items
        List<Item> items = itemDAO.searchApprovedItems(
            keyword, category, type, size, condition, sort
        );

        // Pass back to JSP
        req.setAttribute("items", items);
        req.setAttribute("search", keyword);
        req.setAttribute("selCategory", category);
        req.setAttribute("selType", type);
        req.setAttribute("selSize", size);
        req.setAttribute("selCondition", condition);
        req.setAttribute("selSort", sort);

        req.getRequestDispatcher("browse.jsp").forward(req, resp);
    }
}
