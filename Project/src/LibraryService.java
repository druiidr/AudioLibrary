import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class LibraryService {
    private static final int MAX_BOOKS = 100;
    private static final int MAX_BOOKS_PER_AUTHOR = 10;
    private static final int MIN_RELEASE_YEAR = 800;

    private BookDao bookDao;
    private int bookCounter = 0;
    private Map<String, Integer> authorBookCount = new HashMap<>();

    public LibraryService() {
        this.bookDao = new BookFileDao();
        this.bookCounter = bookDao.getAll().size();
        initializeAuthorBookCount();
    }

    private void initializeAuthorBookCount() {
        for (EBook book : bookDao.getAll()) {
            authorBookCount.put(book.getAuthor(), 
                authorBookCount.getOrDefault(book.getAuthor(), 0) + 1);
        }
    }

    public void addBook(String author, LocalDate releaseDate, String name, String link) throws IllegalArgumentException {
        if (bookDao.getAll().size() >= MAX_BOOKS) {
            throw new IllegalArgumentException("Maximum number of books (100) reached.");
        }
        if (authorBookCount.getOrDefault(author, 0) >= MAX_BOOKS_PER_AUTHOR) {
            throw new IllegalArgumentException("Maximum books by this author (10) reached.");
        }
        if (releaseDate.getYear() < MIN_RELEASE_YEAR) {
            throw new IllegalArgumentException("Release year must be at least " + MIN_RELEASE_YEAR + ".");
        }
        if (bookDao.getAll().stream().anyMatch(b -> b.getName().equals(name))) {
            throw new IllegalArgumentException("A book with this name already exists.");
        }

        EBook book = new EBook(author, releaseDate, name, link);
        book.setID(bookCounter++);
        bookDao.save(book);
        authorBookCount.put(author, authorBookCount.getOrDefault(author, 0) + 1);
    }

    public void editBook(int id, String author, LocalDate releaseDate, String name, String link) throws IllegalArgumentException {
        if (releaseDate.getYear() < MIN_RELEASE_YEAR) {
            throw new IllegalArgumentException("Release year must be at least " + MIN_RELEASE_YEAR + ".");
        }

        EBook existingBook = bookDao.get(id);
        if (existingBook == null) {
            throw new IllegalArgumentException("Book with ID " + id + " does not exist.");
        }

        // Update author book count if the author is being changed
        if (!existingBook.getAuthor().equals(author)) {
            if (authorBookCount.getOrDefault(author, 0) >= MAX_BOOKS_PER_AUTHOR) {
                throw new IllegalArgumentException("Maximum books by this author (10) reached.");
            }
            authorBookCount.put(existingBook.getAuthor(), authorBookCount.get(existingBook.getAuthor()) - 1);
            authorBookCount.put(author, authorBookCount.getOrDefault(author, 0) + 1);
        }

        EBook book = new EBook(author, releaseDate, name, link);
        book.setID(id);
        bookDao.update(book);
    }

    public void deleteBook(int id) throws IllegalArgumentException {
        EBook book = bookDao.get(id);
        if (book == null) {
            throw new IllegalArgumentException("Book with ID " + id + " does not exist.");
        }
        bookDao.delete(id);
        authorBookCount.put(book.getAuthor(), authorBookCount.get(book.getAuthor()) - 1);
    }

    public EBook getBook(int id) throws IllegalArgumentException {
        EBook book = bookDao.get(id);
        if (book == null) {
            throw new IllegalArgumentException("Book with ID " + id + " does not exist.");
        }
        return book;
    }

    public String showBooks() {
        StringBuilder output = new StringBuilder();
        for (EBook book : bookDao.getAll()) {
            output.append(book.toString()).append("\n");
        }
        return output.toString();
    }
}
