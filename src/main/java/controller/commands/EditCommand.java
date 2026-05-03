package controller.commands;

import services.ProduitService;
import services.ProduitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCommand implements Command {

    private ProduitService service = new ProduitServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        request.setAttribute("produitEdit", service.getProduit(id));
        request.setAttribute("listeProduits", service.getAllProduits());
        return "/WEB-INF/views/index.jsp";
    }
}
