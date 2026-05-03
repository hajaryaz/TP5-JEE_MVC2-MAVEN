package dao;

import model.Produit;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProduitDAOImpl implements ProduitDAO {

    @Override
    public void addProduit(Produit p) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de l'ajout du produit", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteProduit(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Produit p = em.find(Produit.class, id);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de la suppression du produit", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Produit getProduitById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Produit.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Produit> getAllProduits() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Produit> q = em.createQuery("SELECT p FROM Produit p", Produit.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void updateProduit(Produit p) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de la mise à jour du produit", e);
        } finally {
            em.close();
        }
    }
}
