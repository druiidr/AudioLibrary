import java.time.LocalDate;

public class EBook {
    private String author;
    private LocalDate releaseDate;
    private String name;
    private int ID;
    private String link;

    public EBook(String author, LocalDate releaseDate, String name, String link) {
        this.author = author;
        this.releaseDate = releaseDate;
        this.name = name;
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EBook ebook = (EBook) o;
        return ID == ebook.ID;
    }

    @Override
    public String toString() {
        return "EBook{" +
                "author='" + author + '\'' +
                ", releaseDate=" + releaseDate +
                ", name='" + name + '\'' +
                ", ID=" + ID +
                ", link='" + link + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }
}