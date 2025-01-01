import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class LibraryService {
    private BookDao bookDao;
    private int bookCounter = 0;

    public LibraryService() {
        this.bookDao = new BookFileDao();
        this.bookCounter = bookDao.getAll().size();
    }

    public void addBook(String author, LocalDate releaseDate, String name, String link) {
        EBook book = new EBook(author, releaseDate, name, link);
        book.setID(bookCounter++);
        bookDao.save(book);
    }

    public void editBook(int id, String author, LocalDate releaseDate, String name, String link) {
        EBook book = new EBook(author, releaseDate, name, link);
        book.setID(id);
        bookDao.update(book);
    }

    public void deleteBook(int id) {
        bookDao.delete(id);
    }

    public EBook getBook(int id) {
        return bookDao.get(id);
    }

    public String showBooks() {
        StringBuilder output = new StringBuilder();
        for (EBook book : bookDao.getAll()) {
            output.append(book.toString()).append("\n");
        }
        return output.toString();
    }
}