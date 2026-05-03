package services;

import dao.UtilisateurDAO;
import dao.UtilisateurDAOImpl;
import model.Utilisateur;

public class UserServiceImpl implements UserService {

    private UtilisateurDAO dao = new UtilisateurDAOImpl();

    @Override
    public Utilisateur login(String login, String password) {
        return dao.findByLoginAndPassword(login, password);
    }
}
