package ex3;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int PAGE_SIZE = 3;

    public static void main(String[] args) {
        FileRepository fileRepository = new FileRepository();
        PlayerService playerService = new PlayerService(fileRepository);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- PNU Sports Football Game ---");
            System.out.println("1. Add Player");
            System.out.println("2. Delete Player");
            System.out.println("3. Get Player by ID");
            System.out.println("4. List All Players");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addPlayer(playerService, scanner);
                    break;

                case 2:
                    deletePlayer(playerService, scanner);
                    break;

                case 3:
                    getPlayerById(playerService, scanner);
                    break;

                case 4:
                    listPlayersWithPagination(playerService, scanner);
                    break;

                case 5:
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void addPlayer(PlayerService playerService, Scanner scanner) {
        System.out.print("Enter short name: ");
        String name = scanner.nextLine();
        System.out.print("Enter height in cm: ");
        int height = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter nationality: ");
        String nationality = scanner.nextLine();
        System.out.print("Enter club: ");
        String club = scanner.nextLine();
        System.out.print("Enter overall rating: ");
        int overall = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter player positions (comma-separated): ");
        String[] positions = scanner.nextLine().split(",");
        Player player = new Player(name, height, nationality, club, overall, positions);
        playerService.addPlayer(player); // ID is automatically assigned
        System.out.println("Player successfully added with ID: " + player.getSofifaId());
    }

    private static void deletePlayer(PlayerService playerService, Scanner scanner) {
        System.out.print("Enter player ID to delete: ");
        int deleteId = scanner.nextInt();
        boolean deleted = playerService.deletePlayer(deleteId);
        if (deleted) {
            System.out.println("Player with ID " + deleteId + " has been deleted.");
        } else {
            System.out.println("Player not found.");
        }
    }

    private static void getPlayerById(PlayerService playerService, Scanner scanner) {
        System.out.print("Enter player ID to retrieve: ");
        int retrieveId = scanner.nextInt();
        Player retrievedPlayer = playerService.getPlayer(retrieveId);
        if (retrievedPlayer != null) {
            System.out.println("Player found: " + retrievedPlayer);
        } else {
            System.out.println("Player not found.");
        }
    }

    private static void listPlayersWithPagination(PlayerService playerService, Scanner scanner) {
        System.out.println("\nChoose sorting option:");
        System.out.println("1. Sort by ID");
        System.out.println("2. Sort by Highest Overall");
        System.out.print("Choose an option: ");

        int sortOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sortBy = (sortOption == 2) ? "overall" : "id";

        int currentPage = 1;
        List<Player> players;

        while (true) {
            players = playerService.getAllPlayers(currentPage, PAGE_SIZE, sortBy);

            if (players.isEmpty()) {
                System.out.println("No players found for the specified page.");
                break;
            }

            System.out.println("\n--- Player List (Page " + currentPage + ") ---");
            for (Player player : players) {
                System.out.println(player);
            }

            System.out.println("\nOptions:");
            System.out.println("N - Next Page");
            if (currentPage > 1) {
                System.out.println("B - Previous Page");
            }
            System.out.println("Q - Quit to Main Menu");

            System.out.print("Choose an option: ");
            String option = scanner.nextLine().trim().toUpperCase();

            if (option.equals("N")) {
                currentPage++;
            } else if (option.equals("B") && currentPage > 1) {
                currentPage--;
            } else if (option.equals("Q")) {
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
