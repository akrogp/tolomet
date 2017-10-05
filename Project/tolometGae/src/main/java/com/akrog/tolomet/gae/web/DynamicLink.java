package com.akrog.tolomet.gae.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gorka on 5/10/17.
 */

public class DynamicLink extends HttpServlet {
    public DynamicLink() {
        this.esLangs.add("es");
        this.esLangs.add("ca");
        this.esLangs.add("eu");
        this.esLangs.add("gl");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale = req.getLocale();
        resp.setContentType("text/html");

        PrintWriter pw = resp.getWriter();
        beginHtml(pw);
        pw.println(String.format("<h3>%s</h3>", getMessage(locale)));
        pw.println(String.format("<a href='%s'>%s</a>", URL, URL));
        endHtml(pw);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private String getMessage(Locale locale) {
        String lang = locale.getLanguage().toLowerCase();
        if( this.esLangs.contains(lang) )
            return "Para poder ver este enlace necesitas usar Tolomet";
        return "To see this content you need to install Tolomet";
    }

    private void beginHtml(PrintWriter pw) {
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Tolomet</title>");
        pw.println("</head>");
        pw.println("<body>");
    }

    private void endHtml(PrintWriter pw) {
        pw.println("</body>");
        pw.println("</html>");
        pw.close();
    }

    private final Set<String> esLangs = new HashSet<>();
    private static final String URL = "https://play.google.com/store/apps/details?id=com.akrog.tolomet";
}
