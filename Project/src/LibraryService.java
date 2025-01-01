import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LibraryService {
    private static final Logger logger = LoggerFactory.getLogger(LibraryService.class);

    private final BookDao bookDao;
    private int bookCounter;

    @Value("${library.maxBooks}")
    private int maxBooks;

    @Value("${library.maxBooksPerAuthor}")
    private int maxBooksPerAuthor;

    @Value("${library.minYear}")
    private int minYear;

    public LibraryService(BookDao bookDao) {
        this.bookDao = bookDao;
        this.bookCounter = bookDao.getAll().size();
    }

    public void addBook(String author, LocalDate releaseDate, String name, String link) {
        logger.info("Adding a book: {}", name);

        long authorBookCount = bookDao.getAll().stream()
                                      .filter(book -> book.getAuthor().equals(author))
                                      .count();
        if (authorBookCount >= maxBooksPerAuthor) {
            throw new IllegalStateException("Author " + author + " cannot have more than " + maxBooksPerAuthor + " books.");
        }
        if (bookCounter >= maxBooks) {
            throw new IllegalStateException("Cannot add more than " + maxBooks + " books.");
        }
        if (releaseDate.getYear() < minYear) {
            throw new IllegalArgumentException("Book year cannot be less than " + minYear);
        }

        EBook book = new EBook(author, releaseDate, name, link);
        book.setID(bookCounter++);
        try {
            bookDao.save(book);
            logger.info("Successfully added book: {}", name);
        } catch (Exception e) {
            logger.error("Failed to add book: {}", name, e);
            throw new RuntimeException("Failed to save the book.", e);
        }
    }

    public void editBook(int id, String author, LocalDate releaseDate, String name, String link) {
        if (!bookDao.exists(id)) {
            throw new IllegalArgumentException("Book with ID " + id + " does not exist.");
        }
        EBook book = new EBook(author, releaseDate, name, link);
        book.setID(id);
        bookDao.update(book);
        logger.info("Edited book with ID: {}", id);
    }

    public void deleteBook(int id) {
        if (!bookDao.exists(id)) {
            throw new IllegalArgumentException("Book with ID " + id + " does not exist.");
        }
        bookDao.delete(id);
        logger.info("Deleted book with ID: {}", id);
    }

    public String showBooks() {
        StringBuilder output = new StringBuilder();
        for (EBook book : bookDao.getAll()) {
            output.append(boo
