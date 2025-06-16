package app;

import interfaces.MessageSender;
import model.*;
import service.*;
import notification.ConsoleSender;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Création des services de base
        AuthService authService = new AuthService(); // Gère les utilisateurs (employés et admin)
        ServiceRegistry serviceRegistry = new ServiceRegistry(); // Registre de tous les services disponibles
        MessageSender sender = new ConsoleSender(); // Permet d'envoyer les notifications
        NotificationCenter center = new NotificationCenter(); // Centre de notification (Observable)
        // Boucle principale du programme
        while (true) {
            System.out.println("\n--- MENU ---\n1. Connexion\n2. Quitter");
            int choix = Integer.parseInt(scanner.nextLine());

            if (choix == 1) {
                // Authentification de l'utilisateur
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Mot de passe: ");
                String password = scanner.nextLine();
                User user = authService.login(email, password);

                // Si l'utilisateur est un administrateur
                if (user instanceof Admin admin) {
                    while (true) {
                        System.out.println("\n-- Menu Admin --");
                        System.out.println("1. Ajouter un employé");
                        System.out.println("2. Ajouter un service");
                        System.out.println("3. Envoyer un message");
                        System.out.println("4. Gérer les demandes");
                        System.out.println("5. Déconnexion");

                        int action = Integer.parseInt(scanner.nextLine());

                        if (action == 1) {
                            // Ajouter un nouvel employé
                            System.out.print("Email employé: ");
                            String em = scanner.nextLine();
                            System.out.print("Mot de passe: ");
                            String pw = scanner.nextLine();
                            boolean ok = authService.registerEmployee(em, pw);
                            System.out.println(ok ? "Employé ajouté." : "Déjà existant.");

                        } else if (action == 2) {
                            // Ajouter un nouveau service
                            System.out.print("Nom du service: ");
                            serviceRegistry.addService(new Service(scanner.nextLine()));

                        } else if (action == 3) {
                            // Envoyer un message à tous les employés abonnés à un service
                            System.out.print("Message à envoyer: ");
                            String msg = scanner.nextLine();

                            System.out.println("Choisir un service:");
                            List<Service> services = serviceRegistry.getServices();
                            for (int i = 0; i < services.size(); i++) {
                                System.out.println((i + 1) + ". " + services.get(i).getName());
                            }
                            int idx = Integer.parseInt(scanner.nextLine()) - 1;
                            Service targetService = services.get(idx);

                            for (User u : authService.getUsers().values()) {
                                if (u instanceof Employee e && e.getSubscribedServices().contains(targetService)) {
                                    // Message personnalisé à chaque employé
                                    String personalized = "Salut " + e.getEmail() + ". Message pour service [" + targetService.getName() + "]: " + msg;
                                    sender.send(e, personalized);
                                }
                            }

                        } else if (action == 4) {
                            // Gérer les demandes d'abonnement des employés
                            for (User u : authService.getUsers().values()) {
                                if (u instanceof Employee e) {
                                    for (Service s : new ArrayList<>(e.getPendingRequests())) {
                                        System.out.println("Demande: " + e.getEmail() + " -> " + s.getName());
                                        System.out.println("1. Accepter\n2. Refuser");
                                        int resp = Integer.parseInt(scanner.nextLine());
                                        if (resp == 1) {
                                            e.subscribe(s);
                                            System.out.println("Abonnement validé.");
                                        } else {
                                            e.getPendingRequests().remove(s);
                                            System.out.println("Demande refusée.");
                                        }
                                    }
                                }
                            }
                        } else break; // Déconnexion admin
                    }
                }
                // Si l'utilisateur est un employé
                else if (user instanceof Employee employee) {
                    center.addObserver(employee); // L'employé devient observer des notifications
                    while (true) {
                        System.out.println("\n-- Menu Employé --");
                        System.out.println("1. S’abonner à un service");
                        System.out.println("2. Voir mes abonnements");
                        System.out.println("3. Me désabonner d’un service");
                        System.out.println("4. Voir mes notifications");
                        System.out.println("5. Déconnexion");

                        int action = Integer.parseInt(scanner.nextLine());

                        if (action == 1) {
                            // Liste des services disponibles
                            List<Service> services = serviceRegistry.getServices();
                            for (int i = 0; i < services.size(); i++) {
                                System.out.println((i + 1) + ". " + services.get(i).getName());
                            }
                            int idx = Integer.parseInt(scanner.nextLine()) - 1;
                            Service selected = services.get(idx);

                            if (employee.getSubscribedServices().contains(selected)) {
                                System.out.println("Vous êtes déjà abonné à ce service.");
                            } else {
                                employee.requestSubscription(selected);
                                System.out.println("Demande d’abonnement envoyée.");
                            }

                        } else if (action == 2) {
                            // Affichage des abonnements de l'employé
                            System.out.println("Vos abonnements :");
                            for (Service s : employee.getSubscribedServices()) {
                                System.out.println("- " + s.getName());
                            }

                        } else if (action == 3) {
                            // Désabonnement d’un service
                            List<Service> subs = employee.getSubscribedServices();
                            if (subs.isEmpty()) {
                                System.out.println("Aucun service à désabonner.");
                            } else {
                                for (int i = 0; i < subs.size(); i++) {
                                    System.out.println((i + 1) + ". " + subs.get(i).getName());
                                }
                                int idx = Integer.parseInt(scanner.nextLine()) - 1;
                                Service selected = subs.get(idx);
                                employee.unsubscribe(selected);
                                System.out.println("Désabonnement effectué.");
                            }

                        } else if (action == 4) {
                            // Affichage des notifications reçues
                            System.out.println("Vos notifications :");
                            for (String n : employee.getNotifications()) {
                                System.out.println("- " + n);
                            }

                        } else break; // Déconnexion employé
                    }

                    center.removeObserver(employee); // Retirer l'observer après déconnexion
                }

                // Si les identifiants sont invalides
                else {
                    System.out.println("Identifiants invalides.");
                }

            } else break; // Quitter le programme
        }
    }
}
