import java.util.ArrayList;

public class Report {
	static ArrayList<Book> booksToCheck = null;
	static ArrayList<Kurs> correspondingKurs = null;
	static ArrayList<BachelorClass> correspondingClass = null;
	static ArrayList<Book> booksDiscarded = null;
	static ArrayList<Kurs> correspondingKursDiscarded = null;
	static ArrayList<BachelorClass> correspondingClassDiscarded = null;
	
	public static void init()
	{
		booksToCheck = new ArrayList<Book>();
		correspondingKurs = new ArrayList<Kurs>();
		correspondingClass = new ArrayList<BachelorClass>();
		booksDiscarded = new ArrayList<Book>();
		correspondingKursDiscarded = new ArrayList<Kurs>();
		correspondingClassDiscarded = new ArrayList<BachelorClass>();
	}
	
	public static void addBookToCheck(Book book, Kurs kurs, BachelorClass bclass)
	{
		booksToCheck.add(book);
		correspondingKurs.add(kurs);
		correspondingClass.add(bclass);
	}
	
	public static void addDiscardedBook(Book book, Kurs kurs, BachelorClass bclass)
	{
		booksDiscarded.add(book);
		correspondingKursDiscarded.add(kurs);
		correspondingClassDiscarded.add(bclass);
	}
	
	public static void reportBooksToCheck()
	{
		System.out.println("BOOKS TO CHECK:");
		for (int i=0; i<booksToCheck.size(); i++)
		{
			Book book = booksToCheck.get(i);
			Kurs kurs = correspondingKurs.get(i);
			BachelorClass bclass = correspondingClass.get(i);
			System.out.println("Class: "+bclass.getTitle(false));
			System.out.println("\tCourse: "+kurs.getTitle());
			System.out.println("\t\tBook: "+book.getLine());
			System.out.println("\t\tAuthor: "+book.getAuthors());
			System.out.println("\t\tTitle: "+book.getTitle());
			
		}
	}
	
	public static void reportDiscardedBooks()
	{
		System.out.println("DISCARDED BOOKS:");
		for (int i=0; i<booksDiscarded.size(); i++)
		{
			Book book = booksDiscarded.get(i);
			Kurs kurs = correspondingKursDiscarded.get(i);
			BachelorClass bclass = correspondingClassDiscarded.get(i);
			System.out.println("Class: "+bclass.getTitle(false));
			System.out.println("\tCourse: "+kurs.getTitle());
			System.out.println("\t\tBook: "+book.getLine());
			System.out.println("\t\tAuthor: "+book.getAuthors());
			System.out.println("\t\tTitle: "+book.getTitle());
			
		}
	}
}
