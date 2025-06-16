package interfaces;


// Interface pour les observateurs qui réagiront aux notifications
public interface Observer {
    void update(String message); // Réagir à une mise à jour
}