package service;

import model.*;

import java.util.*;

public class AuthService {
    private Map<String, User> users = new HashMap<>();

    public AuthService() {
        users.put("booba@gmail.com", new Admin("booba@gmail.com", "elinka123"));
    }

    public boolean registerEmployee(String email, String password) {
        if (users.containsKey(email)) return false;
        users.put(email, new Employee(email, password));
        return true;
    }

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