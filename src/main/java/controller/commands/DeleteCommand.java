package controller.commands;

import services.ProduitService;
import services.ProduitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCommand implements Command {

    private ProduitService service = new ProduitServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        service.deleteProduit(Long.parseLong(request.getParameter("id")));
        response.sendRedirect(request.getContextPath() + "/app?action=list");
        return null;
    }
}
