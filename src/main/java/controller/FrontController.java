package controller;

import controller.commands.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MVC2 — Single Front Controller.
 * Every request enters here via /app?action=xxx
 * and is dispatched to the matching Command.
 */
public class FrontController extends HttpServlet {

    // Actions that require ADMIN role
    private static final java.util.Set<String> ADMIN_ACTIONS =
        new java.util.HashSet<>(java.util.Arrays.asList("add", "edit", "update", "delete"));

    // Actions that do NOT require authentication
    private static final java.util.Set<String> PUBLIC_ACTIONS =
        new java.util.HashSet<>(java.util.Arrays.asList("login", "logout"));

    // Command registry — maps action name → Command instance
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init() {
        commands.put("login",   new LoginCommand());
        commands.put("logout",  new LogoutCommand());
        commands.put("list",    new ListCommand());
        commands.put("add",     new AddCommand());
        commands.put("edit",    new EditCommand());
        commands.put("update",  new UpdateCommand());
        commands.put("delete",  new DeleteCommand());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) action = "list";

        // ── Authentication check ──────────────────────────────────────
        if (!PUBLIC_ACTIONS.contains(action)) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                resp.sendRedirect(req.getContextPath() + "/app?action=login");
                return;
            }
        }

        // ── Role check (ADMIN only actions) ──────────────────────────
        if (ADMIN_ACTIONS.contains(action)) {
            HttpSession session = req.getSession(false);
            String role = (session != null) ? (String) session.getAttribute("role") : null;
            if (!"ADMIN".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/accessDenied.jsp");
                return;
            }
        }

        // ── Dispatch to Command ───────────────────────────────────────
        Command command = commands.get(action);
        if (command == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Action inconnue : " + action);
            return;
        }

        String view = command.execute(req, resp);

        // If command returned a view path → forward to JSP (View)
        if (view != null) {
            req.getRequestDispatcher(view).forward(req, resp);
        }
        // else: command already called sendRedirect
    }
}
