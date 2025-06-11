package model;

public class Notification {
    private String content;

    public Notification(String content) {
        this.content = content;
    }

    // Retourne le message personnalisé pour un employé donné
    public String getPersonalizedMessage(String employeeName) {
        return "Salut " + employeeName + ". " + content;
    }
}
