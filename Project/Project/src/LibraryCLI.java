import java.time.LocalDate;
import java.util.Scanner;

public class LibraryCLI {
    public static void main(String[] args) {
        LibraryService service = new LibraryService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Show book list\n2. Show single book\n3. Add book\n4. Update book\n5. Delete book\n0. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println(service.showBooks());
                    break;
                case 2:
                    System.out.println("Enter book ID:");
                    int id = scanner.nextInt();
                    System.out.println(service.getBook(id));
                    break;
                case 3:
                    System.out.println("Enter author:");
                    String author = scanner.nextLine();
                    System.out.println("Enter release date (YYYY-MM-DD):");
                    LocalDate date = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter link:");
                    String link = scanner.nextLine();
                    service.addBook(author, date, name, link);
                    break;
                case 4:
                    System.out.println("Enter book ID to update:");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.println("Enter author:");
                    String newAuthor = scanner.nextLine();
                    System.out.println("Enter release date (YYYY-MM-DD):");
                    LocalDate newDate = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter name:");
                    String newName = scanner.nextLine();
                    System.out.println("Enter link:");
                    String newLink = scanner.nextLine();
                    service.editBook(updateId, newAuthor, newDate, newName, newLink);
                    break;
                case 5:
                    System.out.println("Enter book ID to delete:");
                    int deleteId = scanner.nextInt();
                    service.deleteBook(deleteId);
                    break;
                case 0:
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
