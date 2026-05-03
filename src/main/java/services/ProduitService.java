package services;

import model.Produit;
import java.util.List;

public interface ProduitService {
    void addProduit(Produit p);
    void deleteProduit(Long id);
    List<Produit> getAllProduits();
    Produit getProduit(Long id);
    void updateProduit(Produit p);
}
