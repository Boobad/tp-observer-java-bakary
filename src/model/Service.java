package model;

// Classe représentant un service auquel un employé peut s’abonner
public class Service {
    private String name;

    public Service(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    // Redéfinition pour vérifier si deux services sont les mêmes via leur nom
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Service service = (Service) obj;
        return name.equals(service.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}