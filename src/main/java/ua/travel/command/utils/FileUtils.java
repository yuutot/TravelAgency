package ua.travel.command.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Save the file to the specified directory and generate a random name.
 */
public class FileUtils {

    private static final String PATH = "/home/yuuto/IdeaProjects/TravelAgency/src/main/webapp/photo";

    /**
     * @param part
     * @return random name with file extension
     */
    private static String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return nextFileName() + content.substring(content.lastIndexOf('.')).trim().replace("\"", "");

            }
        }
        return null;
    }

    /**
     * Save the file to the specified directory
     *
     * @return file name
     * @throws ServletException
     * @throws IOException
     */
    public static String loadFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Part filePart = request.getPart("photo");
        String fileName = getFileName(filePart);
        OutputStream out = new FileOutputStream(
                new File(PATH + File.separator + fileName));

        InputStream filecontent = filePart.getInputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while ((read = filecontent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        return fileName;
    }

    /**
     * @return generated random string
     */
    private static String nextFileName() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }
}
