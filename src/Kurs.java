import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

public class Kurs implements Comparable {
	ArrayList<Book> r_books = null;
	ArrayList<Book> c_books = null;
	String courseTitle = null;
	String courseId = null;
	String language = "ENG";
	
	public void readUrl(String url, BachelorClass bclass)
	{
		//System.out.println(url);
		String responseBody = Functions.get(url);
		//System.out.println(responseBody);
		
		String[] parts = responseBody.split("\n");
		String mode = "";
		
		r_books = new ArrayList<Book>();
		c_books = new ArrayList<Book>();
		
		for (String line : parts)
		{
			String title = "";
			if (line.startsWith("<title>"))
			{
				String header = line.substring(7, line.indexOf("</title>"));
				courseId = header.substring(Functions.strpos(header, "[A-ZÅÆØ]{3,4}[\\s]+[0-9]{4}"));
			}
			else if (line.startsWith("<b>") || line.startsWith("<p>"))
			{
				title = Functions.removeHtml(line).trim();
				
				if (title.contains(courseId))
				{
					courseTitle = title.replace(courseId, "").trim();
					System.out.println("\t"+courseTitle);
				}
				else
				{
					switch (title.toLowerCase())
					{
					case "bøker:":
						language = "no";
					case "books:":
						if (mode == "compulsory")
						{
							mode = "compulsory-books";
						}
						else if (mode == "recommended")
						{
							mode = "recommended-books";
						}
						break;
					case "obligatorisk litteratur":
						language = "no";
					case "compulsory reading":
						mode = "compulsory";
						break;
					case "anbefalt litteratur":
						language = "no";
					case "recommended reading":
						mode = "recommended";
						break;
					default:
						mode = "";
					}
				}
			}
			else
			{
				switch (mode)
				{
				case "recommended-books":
				case "compulsory-books":
					String sBook = Functions.removeHtml(line).trim();
					if (!sBook.isEmpty() && 
							!sBook.startsWith("- Kap") && 
							!sBook.startsWith("- Appendix") &&
							!(sBook.contains(" side ") && sBook.contains(" av ")))
					{
						Book book = new Book();
						if (!book.parse(sBook))
						{
							Report.addBookToCheck(book, this, bclass);
						}
						
						if (mode == "recommended-books")
						{
							//System.out.println("\tr\t- "+book);
							r_books.add(book);
						}
						else
						{
							//System.out.println("\tc\t- "+book);
							c_books.add(book);
						}
					}
					else
					{
						if (!sBook.isEmpty())
						{
							Book book = new Book();
							if (!book.parse(sBook))
							{
								Report.addDiscardedBook(book, this, bclass);
							}
						}
					}
					break;
				}
			
			}
		}
	}
	
	public String getTitle()
	{
		return courseTitle;
	}
	
	public String getId()
	{
		return courseId;
	}
	
	public ArrayList<Book> getRecommendedBooks()
	{
		return r_books;
	}
	
	public ArrayList<Book> getCompulsoryBooks()
	{
		return c_books;
	}
	
	public String getLanguage()
	{
		return language;
	}
	
	public void createTestContent(int number)
	{
		if (number == 0) courseTitle = "Der Fuchs";
		if (number == 1) courseTitle = "Zeppelin";
		if (number == 2) courseTitle = "Arachnophobie";
		courseId = "TES 1234";
		r_books = new ArrayList<Book>();
		c_books = new ArrayList<Book>();
		for (int i =0; i<3; i++)
		{
			Book nBook = new Book();
			nBook.createTestContent(i);
			r_books.add(nBook);
			c_books.add(nBook);
		}
		if (number == 2) language = "NO";
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
        return this.courseTitle.compareTo(arg0.toString());
	}
    @Override
    public String toString() {
        return this.courseTitle;
    }}
