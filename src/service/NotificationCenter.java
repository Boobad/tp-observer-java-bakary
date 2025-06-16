package service;

import interfaces.Observer;
import interfaces.Subject; // anciennement interfaces.Observable

import java.util.*;

// Centre de notification qui prévient tous les observateurs
public class NotificationCenter implements Subject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
}