package app;

import interfaces.MessageSender;
import model.*;
import service.*;
import notification.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService();
        ServiceRegistry serviceRegistry = new ServiceRegistry();
        MessageSender sender = new ConsoleSender();
        NotificationCenter center = new NotificationCenter();
        NotificationService notificationService = new NotificationService(sender);

        while (true) {
            System.out.println("\n--- MENU ---\n1. Connexion\n2. Quitter");
            int choix = Integer.parseInt(scanner.nextLine());

            if (choix == 1) {
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Mot de passe: ");
                String password = scanner.nextLine();
                User user = authService.login(email, password);

                if (user instanceof Admin admin) {
                    while (true) {
                        System.out.println("\n-- Menu Admin --\n1. Ajouter un employé\n2. Ajouter un service\n3. Envoyer un message\n4. Gérer les demandes\n5. Déconnexion");
                        int action = Integer.parseInt(scanner.nextLine());

                        if (action == 1) {
                            System.out.print("Email employé: ");
                            String em = scanner.nextLine();
                            System.out.print("Mot de passe: ");
                            String pw = scanner.nextLine();
                            boolean ok = authService.registerEmployee(em, pw);
                            System.out.println(ok ? "Employé ajouté." : "Déjà existant.");
                        } else if (action == 2) {
                            System.out.print("Nom du service: ");
                            serviceRegistry.addService(new Service(scanner.nextLine()));
                        } else if (action == 3) {
                            System.out.print("Message à envoyer: ");
                            String msg = scanner.nextLine();
                            System.out.println("Choisir un service:");
                            List<Service> services = serviceRegistry.getServices();
                            for (int i = 0; i < services.size(); i++) {
                                System.out.println((i + 1) + ". " + services.get(i).getName());
                            }
                            int idx = Integer.parseInt(scanner.nextLine()) - 1;
                            Service targetService = services.get(idx);
                            List<Employee> employees = new ArrayList<>();
                            for (User u : authService.getUsers().values()) {
                                if (u instanceof Employee e) employees.add(e);
                            }
                            notificationService.notifyEmployees(employees, targetService, "Message pour service [" + targetService.getName() + "]: " + msg);
                        } else if (action == 4) {
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
                        } else break;
                    }
                } else if (user instanceof Employee employee) {
                    center.addObserver(employee);
                    while (true) {
                        System.out.println("\n-- Menu Employé --\n1. S’abonner à un service\n2. Voir mes abonnements\n3. Me désabonner d’un service\n4. Voir mes notifications\n5. Confirmer une notification\n6. Déconnexion");
                        int action = Integer.parseInt(scanner.nextLine());

                        if (action == 1) {
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
                            System.out.println("Vos abonnements :");
                            for (Service s : employee.getSubscribedServices()) {
                                System.out.println("- " + s.getName());
                            }
                        } else if (action == 3) {
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
                            System.out.println("Vos notifications :");
                            for (String n : employee.getNotifications()) {
                                System.out.println("- " + n);
                            }
                        } else if (action == 5) {
                            List<String> notes = employee.getNotifications();
                            for (int i = 0; i < notes.size(); i++) {
                                System.out.println((i + 1) + ". " + notes.get(i));
                            }
                            System.out.print("Numéro de la notification à confirmer: ");
                            int confirmIndex = Integer.parseInt(scanner.nextLine()) - 1;
                            employee.confirmNotification(confirmIndex);
                        } else break;
                    }
                    center.removeObserver(employee);
                } else {
                    System.out.println("Identifiants invalides.");
                }
            } else break;
        }
    }
}
