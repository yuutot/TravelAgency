package ua.travel.command.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by yuuto on 6/5/17.
 */
public class FileUtils {

    private static String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return nextFileName() + content.substring(content.lastIndexOf('.')).trim().replace("\"", "");

            }
        }
        return null;
    }

    public static String loadFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Part filePart = request.getPart("photo");
        String path = "/home/yuuto/IdeaProjects/TravelAgency/src/main/webapp/img";
        String fileName = getFileName(filePart);
        OutputStream out = new FileOutputStream(
                new File(path + File.separator + fileName));

        InputStream filecontent = filePart.getInputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while ((read = filecontent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        return fileName;
    }

    private static String nextFileName() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }
}
