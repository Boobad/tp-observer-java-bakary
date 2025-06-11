// notification/ConsoleSender.java
package notification;

import interfaces.MessageSender;
import model.Employee;

public class ConsoleSender implements MessageSender {

    public ConsoleSender() {
        // constructeur vide
    }

    @Override
    public void send(Employee employee, String message) {
        System.out.println("[Email Simulé à " + employee.getEmail() + "] " + message);
        employee.update(message);
    }
}
