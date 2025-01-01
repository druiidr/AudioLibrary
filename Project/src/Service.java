
import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class Service {
	private int bookCounter = 0;
	private HashMap<String, EBook> bookMap = new HashMap<String, EBook>();
	
	public void addBook(EBook b) {
		b.setID(bookCounter);
		bookCounter++;
		bookMap.put(b.getName(), b);
	}
	
	public void deleteBook(String bName) {
		if(bookMap.containsKey(bName)) {
			bookMap.remove(bName);
		}
		else {
			System.out.println("No such book in the library, BAD BOY");
		}
	}
	
	public void editBook(String bName, String name, String author, LocalDate releaseDate, String link) {
		if(bookMap.containsKey(bName)) {
			EBook tempBook = new EBook(author, releaseDate, name, link);
			int tempID = bookMap.get(bName).getID();
			tempBook.setID(tempID);
			bookMap.remove(bName);
			addBook(tempBook);
		}
		else {
			System.out.println("No such book in the library, BAD BOY");
		}
	}
	
	public String showBooks() {
		String output="";
		for (EBook book : bookMap.values()) {
		    output+=book.toString() + " ";
		}
		return output;
	}
}
