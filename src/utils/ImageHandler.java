package utils;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public class ImageHandler {

    /**
     * Save an uploaded Part (image) into the given folder.
     * @param part the image part from multipart/form
     * @param uploadDir full server path to the WebContent/images folder
     * @return web-accessible relative path (e.g. "images/uuid.jpg")
     */
    public static String save(Part part, String uploadDir) throws IOException {
        // Ensure folder exists
        Path folder = Paths.get(uploadDir);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        // Build a unique filename with original extension
        String original = Paths.get(part.getSubmittedFileName())
                               .getFileName().toString();
        String ext = original.substring(original.lastIndexOf('.'));
        String fileName = UUID.randomUUID() + ext;

        // Write the file to disk
        Path file = folder.resolve(fileName);
        try (var in = part.getInputStream()) {
            Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
        }

        // Return the relative path used by your JSPs
        return "images/" + fileName;
    }
}
