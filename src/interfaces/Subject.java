package interfaces;

// Interface représentant un sujet observable
public interface Subject {
    void addObserver(Observer observer); // Ajouter un observateur
    void removeObserver(Observer observer); // Retirer un observateur
    void notifyObservers(String message); // Notifier tous les observateurs
}
