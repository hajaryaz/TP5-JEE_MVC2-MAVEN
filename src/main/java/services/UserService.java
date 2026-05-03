package services;

import model.Utilisateur;

public interface UserService {
    Utilisateur login(String login, String password);
}
