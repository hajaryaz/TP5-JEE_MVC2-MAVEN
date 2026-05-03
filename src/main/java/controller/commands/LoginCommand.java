package controller.commands;

import model.Utilisateur;
import services.UserService;
import services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {

    private UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            // Show login page
            return "/WEB-INF/views/login.jsp";
        }

        // POST: process login
        String login    = request.getParameter("login");
        String password = request.getParameter("password");
        String error    = null;

        if (login == null || login.trim().isEmpty()) {
            error = "Login est obligatoire";
        } else if (password == null || password.trim().isEmpty()) {
            error = "Mot de passe est obligatoire";
        } else {
            Utilisateur user = userService.login(login, password);
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());
                response.sendRedirect(request.getContextPath() + "/app?action=list");
                return null; // redirect done
            } else {
                error = "Login ou mot de passe incorrect";
            }
        }

        request.setAttribute("error", error);
        return "/WEB-INF/views/login.jsp";
    }
}
