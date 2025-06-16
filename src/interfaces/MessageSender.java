package interfaces;

import model.Employee;

// Interface pour l'envoi de messages
public interface MessageSender {
    void send(Employee employee, String message); // Envoyer un message à un employé
}