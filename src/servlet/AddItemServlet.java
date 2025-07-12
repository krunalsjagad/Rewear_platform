package servlet;

import dao.ItemDAO;
import model.Item;
import utils.ImageHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add-item")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize       = 5 * 1024 * 1024,
    maxRequestSize    = 25 * 1024 * 1024
)
public class AddItemServlet extends HttpServlet {
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int userId = (int) req.getAttribute("userId");

        // Determine the real folder on the server for saving images
        String uploadDir = getServletContext().getRealPath("/images");

        String title       = req.getParameter("title");
        String description = req.getParameter("description");
        String category    = req.getParameter("category");
        String type        = req.getParameter("type");
        String size        = req.getParameter("size");
        String condition   = req.getParameter("condition");
        int pointsCost     = 10;

        // Save up to 5 uploaded images
        List<String> paths = new ArrayList<>();
        for (Part part : req.getParts()) {
            if ("images".equals(part.getName()) && part.getSize() > 0) {
                // Pass uploadDir into the handler
                String webPath = ImageHandler.save(part, uploadDir);
                paths.add(webPath);
            }
        }

        // Create and persist the item
        Item item = new Item();
        item.setUserId(userId);
        item.setTitle(title);
        item.setDescription(description);
        item.setCategory(category);
        item.setType(type);
        item.setSize(size);
        item.setCondition(condition);
        item.setPointsCost(pointsCost);

        itemDAO.createItem(item).ifPresent(itemId ->
            paths.forEach(p -> itemDAO.addImage(itemId, p))
        );

        resp.sendRedirect("dashboard?added=success");
    }
}
