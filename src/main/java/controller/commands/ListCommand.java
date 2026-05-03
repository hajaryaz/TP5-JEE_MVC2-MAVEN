package controller.commands;

import model.Produit;
import services.ProduitService;
import services.ProduitServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListCommand implements Command {

    private ProduitService service = new ProduitServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("idProduit");
        List<Produit> liste = new ArrayList<>();

        if (idParam != null && !idParam.isEmpty()) {
            try {
                Produit found = service.getProduit(Long.parseLong(idParam));
                if (found != null) liste.add(found);
            } catch (NumberFormatException e) {
                liste = service.getAllProduits();
            }
        } else {
            liste = service.getAllProduits();
        }

        request.setAttribute("listeProduits", liste);
        return "/WEB-INF/views/index.jsp";
    }
}
