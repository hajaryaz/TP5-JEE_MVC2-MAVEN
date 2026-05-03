package services;

import dao.ProduitDAO;
import dao.ProduitDAOImpl;
import model.Produit;
import java.util.List;

public class ProduitServiceImpl implements ProduitService {

    private ProduitDAO dao = new ProduitDAOImpl();

    @Override
    public void addProduit(Produit p) {
        dao.addProduit(p);
    }

    @Override
    public void deleteProduit(Long id) {
        dao.deleteProduit(id);
    }

    @Override
    public List<Produit> getAllProduits() {
        return dao.getAllProduits();
    }

    @Override
    public Produit getProduit(Long id) {
        return dao.getProduitById(id);
    }

    @Override
    public void updateProduit(Produit p) {
        dao.updateProduit(p);
    }
}
