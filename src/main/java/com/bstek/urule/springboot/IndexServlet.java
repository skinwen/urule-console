package com.bstek.urule.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jacky.gao
 * @since 2016年10月12日
 */
public class IndexServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(IndexServlet.class);
    private static final long serialVersionUID = 9155627652423910928L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("index servlet req {}:", req);
        resp.sendRedirect(req.getContextPath() + "/urule/frame");
    }
}
