import java.util.List;

public interface BookDao {
	public List<EBook> getAll();
	public void save(EBook book);
	public void update(EBook book);
	public void delete(int id);
	public EBook get(int id);
	public void loadBooks();
	public void saveBooks();
}

