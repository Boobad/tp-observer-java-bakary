// notification/ConsoleSender.java
package notification;

import interfaces.MessageSender;
import model.Employee;

// Envoi simulé de message via console
public class ConsoleSender implements MessageSender {
    public void send(Employee employee, String message) {
        System.out.println("[Email Simulé à " + employee.getEmail() + "] " + message);
        employee.update(message); // Mise à jour immédiate
    }
}
