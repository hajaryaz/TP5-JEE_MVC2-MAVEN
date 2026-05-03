package controller.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command interface — each action is a separate class.
 * The FrontController maps action names to Command implementations.
 * Returns the view path to forward to (null = redirect already done).
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
