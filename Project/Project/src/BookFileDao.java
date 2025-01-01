import java.io.*;
import java.util.*;

public class BookFileDao implements BookDao{
    private static final String FILE_PATH = "./books.dat";
    private HashMap<Integer, EBook> bookMap = new HashMap<>();

    public BookFileDao() {
        loadBooks();
    }

    public List<EBook> getAll() {
        List<EBook> books = new ArrayList<>(bookMap.values());
        books.sort(Comparator.comparing(EBook::getID));
        return books;
    }

    public void save(EBook book) {
        bookMap.put(book.getID(), book);
        saveBooks();
    }

    public void update(EBook book) {
        if (bookMap.containsKey(book.getID())) {
            bookMap.put(book.getID(), book);
            saveBooks();
        } else {
            throw new IllegalArgumentException("Book with ID " + book.getID() + " not found.");
        }
    }

    public void delete(int id) {
        if (bookMap.remove(id) != null) {
            saveBooks();
        } else {
            throw new IllegalArgumentException("Book with ID " + id + " not found.");
        }
    }

    public EBook get(int id) {
        return bookMap.get(id);
    }

    public void loadBooks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            bookMap = (HashMap<Integer, EBook>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.");
        }
    }

    public void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(bookMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
