package controller.commands;

import model.Produit;
import services.ProduitService;
import services.ProduitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateCommand implements Command {

    private ProduitService service = new ProduitServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Produit p = new Produit(
            request.getParameter("nom"),
            request.getParameter("description"),
            Double.parseDouble(request.getParameter("prix"))
        );
        p.setIdProduit(Long.parseLong(request.getParameter("idProduit")));
        service.updateProduit(p);

        response.sendRedirect(request.getContextPath() + "/app?action=list");
        return null;
    }
}
