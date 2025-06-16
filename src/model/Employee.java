// model/Employee.java
package model;

import interfaces.Observer;

import java.util.*;

// Classe représentant un employé qui observe les notifications
public class Employee extends User implements Observer {
    private List<Service> subscribedServices = new ArrayList<>(); // Abonnements confirmés
    private List<Service> pendingRequests = new ArrayList<>(); // Abonnements en attente
    private List<String> notifications = new ArrayList<>(); // Notifications reçues

    public Employee(String email, String password) {
        super(email, password);
    }

    // Abonnement confirmé à un service
    public void subscribe(Service service) {
        if (!subscribedServices.contains(service)) {
            subscribedServices.add(service);
        }
        pendingRequests.remove(service); // Retirer des demandes en attente
    }

    public void unsubscribe(Service service) {
        subscribedServices.remove(service);
    }

    // Demander l’abonnement à un service
    public void requestSubscription(Service service) {
        if (!pendingRequests.contains(service) && !subscribedServices.contains(service)) {
            pendingRequests.add(service);
        }
    }

    public List<Service> getSubscribedServices() {
        return subscribedServices;
    }

    public List<Service> getPendingRequests() {
        return pendingRequests;
    }

    // Réception d’une notification
    @Override
    public void update(String message) {
        notifications.add(message);
        System.out.println("[Console] " + message);
    }

    public List<String> getNotifications() {
        return notifications;
    }
}
