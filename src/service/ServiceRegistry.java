package service;

import model.Service;
import java.util.*;

// Registre contenant la liste des services disponibles
public class ServiceRegistry {
    private List<Service> services = new ArrayList<>();

    public void addService(Service service) {
        services.add(service);
    }

    public List<Service> getServices() {
        return services;
    }
}
