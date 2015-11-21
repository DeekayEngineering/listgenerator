
public class Book {

	String author;
	String year;
	String title;
	String info;
	String line;
	
	public boolean parse(String line)
	{
		//System.out.println("\t\t"+line);
		this.line = line;
		
		int start = 0;
		int lastSpace = 0;
		int part = 1; 
		if (line.endsWith(".")) 
		{
			line = line.substring(0, line.length()-1);
		}
		
		boolean retValue = true;
		
		try
		{
			boolean containedNumber = false;
			for (int i = 1; i < line.length() && part < 4; i++)
			{
				if (Functions.isInteger(""+line.charAt(i)))
				{
					containedNumber = true;
				}
				if (line.charAt(i) == '.' && line.charAt(i+1) == ' ')
				{
					boolean numberFollows = false;
					if ((i < (line.length()-2) && part == 1 && Functions.isInteger(""+line.charAt(i+2))))
					{
						numberFollows = true;
					}
					if ((line.charAt(i-1) != '.' && line.charAt(i-2) != ' ') || numberFollows == true || containedNumber == true)
					{
						String lastWord = line.substring(lastSpace+1, i);
						//System.out.println(lastWord);
						if ((!lastWord.equals("al") &&
								!lastWord.equals("ed") && 
								!lastWord.equals("nr") &&
								!lastWord.equals("bl.a")
								) || numberFollows == true)
						{
							//System.out.println(lastWord);
							String splitPart = line.substring(start, i);
							//System.out.println(splitPart);
							if (part == 1)
							{
								author = splitPart;
							}
							else if (part == 2)
							{
								year = splitPart;
							}
							else if (part == 3)
							{
								title = splitPart;
							}
							part++;
							start = i+2;
							containedNumber = false;
						}
					}
				}
				else if (line.charAt(i) == ' ' || line.charAt(i) == ']' || line.charAt(i) == '[' || line.charAt(i) == ')' || line.charAt(i) == '(' || line.charAt(i) == ',')
				{
					lastSpace = i;
				}
			}
			if (part <= 3)
			{
				title = line.substring(start, line.length());
				if (!title.contains("dictionary") && !title.contains("ordbok"))
				{
					retValue = false;
				}
			}
			else
			{
				info = line.substring(start, line.length());
			}
		}
		catch (StringIndexOutOfBoundsException e)
		{
			System.out.println(line);
			System.exit(1);
		}
		System.out.println("\t\tAuthors: "+author);
		System.out.println("\t\tYear: "+year);
		System.out.println("\t\tTitle: "+title);
		System.out.println("\t\tInfo: "+info);
		
		return retValue;
	}
	
	public String getTitle()
	{
		if (title != null)
		{
			return title.replace("&amp;","&").replace("&quote;", "\"");
		}
		else
		{
			return title;
		}
	}
	
	public String getAuthors()
	{
		if (author != null)
		{
			return author.replace("&amp;","&").replace("&quote;", "\"");
		}
		else
		{
			return author;
		}
	}
	
	public void createTestContent(int number)
	{
		title = "test title "+number;
		author = "test author";
	}
	
	public String getLine()
	{
		return line;
	}
}
