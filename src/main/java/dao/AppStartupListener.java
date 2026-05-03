package dao;

import model.Produit;
import model.Utilisateur;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UtilisateurDAO userDAO = new UtilisateurDAOImpl();
        ProduitDAO produitDAO = new ProduitDAOImpl();

        if (userDAO.findByLogin("admin_hajar") == null)
            userDAO.addUser(new Utilisateur("admin_hajar", "admin123", "ADMIN"));
        if (userDAO.findByLogin("user_hajar") == null)
            userDAO.addUser(new Utilisateur("user_hajar", "user123", "USER"));

        if (produitDAO.getAllProduits().isEmpty()) {
            produitDAO.addProduit(new Produit("PC 1", "Sony Vaio 1", 7000.0));
            produitDAO.addProduit(new Produit("PC 2", "Sony Vaio 2", 6000.0));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil.close();
    }
}
