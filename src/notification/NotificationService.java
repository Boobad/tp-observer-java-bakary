package notification;

import interfaces.MessageSender;
import model.Employee;
import model.Service;

import java.util.List;

public class NotificationService {
    private MessageSender sender;

    // Injection du moyen d'envoi (console, email, etc.)
    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    // Envoie le message à tous les employés abonnés au service donné
    public void notifyEmployees(List<Employee> employees, Service service, String message) {
        for (Employee e : employees) {
            if (e.getSubscribedServices().contains(service)) {
                String personalized = "Salut " + e.getEmail() + ", " + message;
                sender.send(e, personalized);
            }
        }
    }
}
