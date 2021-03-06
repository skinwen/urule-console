/*******************************************************************************
 * Copyright 2017 Bstek
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.bstek.urule.console.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bstek.urule.console.exception.NoPermissionException;

/**
 * @author Jacky.gao
 * @since 2016年5月23日
 */
public class URuleServlet extends HttpServlet {
    private static final long serialVersionUID = -5067484267904906233L;
    private Map<String, ServletHandler> handlerMap = new HashMap<String, ServletHandler>();
    public static final String URL = "/urule";
    private static final Logger logger = LoggerFactory.getLogger(URuleServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("servlet config :{}", config);
        super.init(config);
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        Collection<ServletHandler> handlers = applicationContext.getBeansOfType(ServletHandler.class).values();
        for (ServletHandler handler : handlers) {
            String url = handler.url();
            if (handlerMap.containsKey(url)) {
                throw new RuntimeException("Handler [" + url + "] already exist.");
            }
            handlerMap.put(url, handler);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestHolder.set(req, resp);
        try {
            String path = req.getContextPath() + URL;
            String uri = req.getRequestURI();
            String targetUrl = uri.substring(path.length());
            if (targetUrl.length() < 1) {
                resp.sendRedirect(req.getContextPath() + "/urule/frame");
                return;
            }
            int slashPos = targetUrl.indexOf("/", 1);
            if (slashPos > -1) {
                targetUrl = targetUrl.substring(0, slashPos);
            }
            ServletHandler targetHandler = handlerMap.get(targetUrl);
            if (targetHandler == null) {
                outContent(resp, "Handler [" + targetUrl + "] not exist.");
                return;
            }
            targetHandler.execute(req, resp);
        } catch (Exception ex) {
            Throwable e = getCause(ex);
            if (e instanceof NoPermissionException) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter pw = resp.getWriter();
                pw.write("<h1>Permission denied!</h1>");
                pw.close();
            } else {
                throw new ServletException(ex);
            }
        } finally {
            RequestHolder.reset();
        }
    }

    private void outContent(HttpServletResponse resp, String msg) throws IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        pw.write("<html>");
        pw.write("<header><title>URule Console</title></header>");
        pw.write("<body>");
        pw.write(msg);
        pw.write("</body>");
        pw.write("</html>");
        pw.flush();
        pw.close();
    }

    private Throwable getCause(Throwable e) {
        if (e.getCause() != null) {
            return getCause(e.getCause());
        }
        return e;
    }
}
