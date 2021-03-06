package org.simple.auth.showcase.servlet;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.servlet.AbstractAuthorizationRedirect;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class AuthServlet extends AbstractAuthorizationRedirect {

    @Override
    public void onError(Exception authException, HttpServletRequest req, HttpServletResponse resp) {
        log.info("######################## onError() ########################");
        log.info("Message: {}", authException.getMessage());
        log.info("######################## onError() ########################");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
        req.setAttribute("authException", authException);
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
