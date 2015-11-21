
public class Filter {
	static boolean filterSpring = true;
	static boolean filterAutmn = true;
	static int filterYear = 0;
	
	public static void setFilterSpring(boolean value)
	{
		filterSpring = value;
	}
	
	public static void setFilterAutmn(boolean value)
	{
		filterAutmn = value;
	}
	
	public static boolean getFilterSpring()
	{
		return filterSpring;
	}
	
	public static boolean getFilterAutmn()
	{
		return filterAutmn;
	}
	
	public static void setFilterYear(int year)
	{
		filterYear = year;
	}
	
	public static int getFilterYear()
	{
		return filterYear;
	}
	
	public static String getFilterTitle()
	{
		String title = "";
		if (filterSpring) title = title+"vår";
		if (filterAutmn)
		{
			if (filterSpring) title = title+"/";
			title = title+"høsten";
		}
		title = title + " "+filterYear;
		return title;
	}
}
