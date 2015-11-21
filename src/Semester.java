import java.util.ArrayList;

public class Semester {
	BachelorClass bclass = null;
	ArrayList<Kurs> courses = null;
	int semesterNumber = 0;
	int year = 0;
	String season = "";
	
	public Semester(int year, String season, BachelorClass bclass)
	{
		this.courses = new ArrayList<Kurs>();
		this.year = year;
		this.season = season;
		this.bclass = bclass;
		this.semesterNumber = (year-1)*2+1;
		if (season.equalsIgnoreCase("spring")) this.semesterNumber++;
	}
	
	public void addKurs(Kurs course)
	{
		courses.add(course);
		System.out.println("Semester "+semesterNumber+": Kurs "+course.getTitle()+" added");
	}
	
	public ArrayList<Kurs> getCourses()
	{
		return courses;
	}
	
	public int getSemester()
	{
		return semesterNumber;
	}
	
	public String getStartYear()
	{
		int currentYear = 2015;
		int startYear = 0;
		if (semesterNumber == 1 || semesterNumber == 2) startYear = currentYear - 0; 
		if (semesterNumber == 3 || semesterNumber == 4) startYear = currentYear - 1; 
		if (semesterNumber == 5 || semesterNumber == 6) startYear = currentYear - 2;
		
		return startYear+"-"+(startYear+2);
	}
	
	public String getPrefix()
	{
		if (season.equals("autmn"))
		{
			return "h";
		}
		else
		{
			return "v";
		}
	}
	
	public int getYear()
	{
		return year;
	}
	
	public String getSeason()
	{
		return season;
	}
}
