package de.fewe.arquillian.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.fewe.arquillian.DemoLib;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "NumberServlet", urlPatterns = {"/number", "/number/*"})
public class NumberServlet extends HttpServlet {
    private static final long serialVersionUID = 34907831279934994L;
    private static final Logger log = Logger.getLogger(NumberServlet.class.getName());
    private static final String KEY = NumberServlet.class.getName();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final HttpSession session = req.getSession(true);

        if (session.isNew()) {
            log.log(Level.INFO, "New session created: {0}", session.getId());
            session.setAttribute(KEY, new DemoLib());
        }

        final DemoLib myDemoLibBean = (DemoLib) session.getAttribute(KEY);

        resp.setContentType("text/plain");

        final int num = myDemoLibBean.getNum();

        myDemoLibBean.setNum(num + 1);

        session.setAttribute(KEY, myDemoLibBean);

        resp.getWriter().print(num);

        if (req.getParameter("invalidate") != null) {
            log.log(Level.INFO, "INVALIDATED: {0}", session.getId());
            session.invalidate();
        }
    }
}
