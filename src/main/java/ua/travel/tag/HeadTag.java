package ua.travel.tag;

/**
 * Created by yuuto on 6/5/17.
 */

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Custom tag. Write title and name of page.
 */
public class HeadTag extends TagSupport {

    private static final Logger LOGGER = Logger.getLogger(HeadTag.class.getName());

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public int doStartTag() {

        try {
            JspWriter out = pageContext.getOut();
            out.write("<title>" + title + "</title>");
            out.write("<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet'>");
            out.write("<link rel='stylesheet' href='../../font/css/font-awesome.min.css'>");
            out.write("<link rel='stylesheet' href='/css/bootstrap.min.css'>");
            out.write("<link rel='stylesheet' href='/css/style.css'>");
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
        return SKIP_BODY;
    }

}