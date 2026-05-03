package controller.commands;

import model.Produit;
import services.ProduitService;
import services.ProduitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCommand implements Command {

    private ProduitService service = new ProduitServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom   = request.getParameter("nom");
        String desc  = request.getParameter("description");
        String prixS = request.getParameter("prix");
        Double prix  = (prixS == null || prixS.isEmpty()) ? 0.0 : Double.parseDouble(prixS);

        service.addProduit(new Produit(nom, desc, prix));
        response.sendRedirect(request.getContextPath() + "/app?action=list");
        return null;
    }
}
