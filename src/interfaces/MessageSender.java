package interfaces;

import model.Employee;

public interface MessageSender {
    void send(Employee employee, String message);
}