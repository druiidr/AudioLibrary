import java.time.*;

public class EBook{
	private String author;
	private LocalDate releaseDate;
	private String name;
	private int ID;
	private String link;

	public EBook(String author, LocalDate releaseDate, String name, String link) {
		super();
		this.author = author;
		this.releaseDate = releaseDate;
		this.name = name;
		this.link = link;
	}

	public boolean equals(Object o)
	{
		EBook s;
		if (this == o) 
			return true;
		if (o == null) 
			return false;
		if (this.getClass() == o.getClass()) 
		{
			s = (EBook) o; 
			return this.ID == s.ID; 
		}
		return false;
	}

	@Override
	public String toString() {
		return "EBook [author=" + author + ", releaseDate=" + releaseDate + ", name=" + name + ", ID=" + ID + ", link=" + link + "]";
	}

	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setID(int iD) {
		ID = iD;
	}

	
	
}
