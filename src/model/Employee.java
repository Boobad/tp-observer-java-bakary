package model;

import interfaces.Observer;

import java.util.*;

public class Employee extends User implements Observer {
    private List<Service> subscribedServices = new ArrayList<>();
    private List<Service> pendingRequests = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();

    public Employee(String email, String password) {
        super(email, password);
    }

    public void subscribe(Service service) {
        if (!subscribedServices.contains(service)) {
            subscribedServices.add(service);
        }
        pendingRequests.remove(service);
    }

    public void unsubscribe(Service service) {
        subscribedServices.remove(service);
    }

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

    @Override
    public void update(String message) {
        notifications.add(message);
        System.out.println("[Console] " + message);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void confirmNotification(int index) {
        if (index >= 0 && index < notifications.size()) {
            String original = notifications.get(index);
            notifications.set(index, original + " [Confirmée]");
            System.out.println("Notification confirmée.");
        } else {
            System.out.println("Index invalide.");
        }
    }
}