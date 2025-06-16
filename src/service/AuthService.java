package service;

import model.*;

import java.util.*;

// Service d’authentification et d’enregistrement des utilisateurs
public class AuthService {
    private Map<String, User> users = new HashMap<>();

    public AuthService() {
        // Compte administrateur par défaut
        users.put("booba@gmail.com", new Admin("booba@gmail.com", "admin123"));
    }

    // Inscription d’un nouvel employé
    public boolean registerEmployee(String email, String password) {
        if (users.containsKey(email)) return false;
        users.put(email, new Employee(email, password));
        return true;
    }

    // Connexion d’un utilisateur
    public User login(String email, String password) {
        if (users.containsKey(email) && users.get(email).getPassword().equals(password)) {
            return users.get(email);
        }
        return null;
    }

    public Map<String, User> getUsers() {
        return users;
    }
}