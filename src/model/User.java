package model;

// Classe abstraite représentant un utilisateur
public abstract class User {
    protected String email;
    protected String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}