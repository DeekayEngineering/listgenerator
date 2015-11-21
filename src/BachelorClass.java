import java.util.ArrayList;
import java.util.HashMap;

public class BachelorClass {

	String title = null;
	HashMap<Integer, Semester> semester = null;
	String language = "ENG";
	
	public void readUrl(String url)
	{
		System.out.println("Analysing Class "+url);
		String responseBody = Functions.get(url);
		
		int h1_pos = responseBody.indexOf("<h1>")+4;
		title = Functions.removeHtml(responseBody.substring(h1_pos, responseBody.indexOf("</h1>", h1_pos))).trim();
		System.out.println(title.toUpperCase());
		
		ArrayList<String> sStringEndings = new ArrayList<String>();
		String sStringStart = "/(NO|EN)/om-studier-og-kurs/bachelor/([a-zA-Z0-9%/(/)/-]*)/";
		
		sStringEndings.add("Programme-Content");
		sStringEndings.add("studieplan-heltid");
		sStringEndings.add("Studieplan-heltid");
		sStringEndings.add("Studieplan%20-heltid");
		sStringEndings.add("Studieplan%20-%20heltid");

		int count = 0;
		String content_link = null;
		while (count < sStringEndings.size() && content_link == null)
		{
			content_link = Functions.subStringRegEx(responseBody, sStringStart+sStringEndings.get(count), sStringEndings.get(count));
			count++;
		}
		
		if (content_link == null)
		{
			content_link = url;
		}
		
		//System.out.println(content_link);
		readCourses(content_link);
	}
	
	private void readCourses(String url)
	{
		System.out.println("Analysing courses...");
		
		if (!url.startsWith("https://at.bi.no"))
		{
			url = "https://at.bi.no"+url;
		}
		String responseBody = Functions.get(url);
		String searchKey = "Course\n  code";
		String searchKeyYear = "year";
		
		if (!responseBody.contains("Course"))
		{
			searchKey = "Kurskode";
			searchKeyYear = "år";
			language = "NO";
		}
		else
		{
			//System.out.println(responseBody);
		}
		//System.out.println(searchKey);
		int tableEnd = 0;
		int tableStart = responseBody.indexOf(searchKey);

		semester = new HashMap<Integer, Semester>();
		
		while (tableStart > 0)
		{
			String preText = responseBody.substring(tableEnd, tableStart);
			int offset = tableStart;
			tableEnd = Functions.strpos(responseBody, "</table>", tableStart);

			//System.out.println(preText);
			if (preText.contains("<h3") || responseBody.contains("Liste over valgkurs"))
			{
				/* Get year */
				int year = 0;
				if (responseBody.contains("Liste over valgkurs"))
				{
					year = 1;
				}
				else
				{
					//System.out.println(preText);
					int h3_pos = preText.lastIndexOf("<h3");
					String yearTextTmp = Functions.removeHtml(preText.substring(h3_pos, preText.indexOf("</h3>", h3_pos)).replace("\"", ""));
					//System.out.println(yearTextTmp);
					int yearStart = Functions.strpos(yearTextTmp, "([0-9]|First|Second|Third)+");
					String yearText = yearTextTmp.substring(yearStart, yearTextTmp.indexOf(searchKeyYear)).replace(".","").trim();
					//System.out.println("yearText: "+yearText);
					try
					{
						year = Integer.parseInt(yearText);
					}
					catch(NumberFormatException e)
					{
						if (yearText.equalsIgnoreCase("first")) year = 1;
						if (yearText.equalsIgnoreCase("second")) year = 2;
						if (yearText.equalsIgnoreCase("third")) year = 3;
					}
					if (year == 0)
					{
						if (preText.contains("1. "+searchKeyYear)) year = 1;
						if (preText.contains("2. "+searchKeyYear)) year = 2;
						if (preText.contains("3. "+searchKeyYear)) year = 3;
						if (preText.contains("First "+searchKeyYear)) year = 1;
						if (preText.contains("Second "+searchKeyYear)) year = 2;
						if (preText.contains("Third "+searchKeyYear)) year = 3;
					}
				}

				/* Loop through tables */
				while (offset < tableEnd && offset > 0)
				{
					int trStart = Functions.strpos(responseBody, "<tr", offset);
					if (trStart < tableEnd && trStart != 0)
					{
						/* Coursecode */
						int tdS = responseBody.indexOf("<td", trStart);
						int tdE = responseBody.indexOf("</td>", tdS);
						String coursecode = responseBody.substring((responseBody.indexOf(">", tdS)+1), tdE);
						tdS = responseBody.indexOf("<td", tdE);
						tdE = responseBody.indexOf("</td>", tdS);
						String coursename = Functions.removeHtml(responseBody.substring((responseBody.indexOf(">", tdS)+1), tdE).replace("\n", ""));
						tdS = responseBody.indexOf("<td", tdE);
						tdE = responseBody.indexOf("</td>", tdS);
						String credit = Functions.removeHtml(responseBody.substring((responseBody.indexOf(">", tdS)+1), tdE));
						tdS = responseBody.indexOf("<td", tdE);
						tdE = responseBody.indexOf("</td>", tdS);
						String autmn = Functions.removeHtml(responseBody.substring((responseBody.indexOf(">", tdS)+1), tdE));
						tdS = responseBody.indexOf("<td", tdE);
						tdE = responseBody.indexOf("</td>", tdS);
						String spring = Functions.removeHtml(responseBody.substring((responseBody.indexOf(">", tdS)+1), tdE));
						
						//if (autmn != "X") autmn = "";
						//if (spring != "X") spring = "";
						
						if (coursecode.contains("http"))
						{
							int tmp = coursecode.indexOf("http");
							coursecode = coursecode.substring(tmp, coursecode.indexOf("\"", tmp)).replace("&#58;",":");
							
							Kurs kurs = null;
							if ((autmn.equals("X") && Filter.getFilterAutmn()) || (spring.equals("X") && Filter.getFilterSpring()))
							{
								kurs = new Kurs();
								kurs.readUrl(coursecode, this);
							}
							
							int curSemester = (year-1)*2+1;
							
							if (autmn.equals("X") && Filter.getFilterAutmn())
							{
								Semester courseSemester = null;
								if (semester.containsKey(new Integer(curSemester)))
								{
									courseSemester = semester.get(new Integer(curSemester));
								}
								else
								{
									courseSemester = new Semester(year, "autmn", this);
								}
								courseSemester.addKurs(kurs);
								semester.put(new Integer(curSemester), courseSemester);
							}
							if (spring.equals("X") && Filter.getFilterSpring())
							{
								Semester courseSemester = null;
								if (semester.containsKey(new Integer(curSemester+1)))
								{
									courseSemester = semester.get(new Integer(curSemester+1));
								}
								else
								{
									courseSemester = new Semester(year, "spring", this);
								}
								courseSemester.addKurs(kurs);
								semester.put(new Integer(curSemester+1), courseSemester);
							}
						}
						else
						{
							coursecode = "";
						}
						
						System.out.println("\t"+coursename+", "+credit+", "+autmn+", "+spring+", "+coursecode);
						
						offset = tdE;
					}
					else
					{
						/* Last tr found */
						offset = tableEnd;
					}
				}
			}
			else
			{
				/* Table has no semester header */
				offset = tableEnd;
			}
			tableStart = responseBody.indexOf(searchKey, offset);
		}
	}
	
	public HashMap<Integer, Semester> getSemester()
	{
		return semester;
	}
	
	public String getTitle(boolean addBachelor)
	{
		String retTitle = "";
		if (title.equalsIgnoreCase("VALGKURS"))
		{
			retTitle = title + " for 2. og 3. år (electives)";
		}
		else
		{
			if (addBachelor == true)
			{
				if (language.equalsIgnoreCase("no"))
				{
					retTitle = "Bachelor i ";
				}
				else
				{
					retTitle = "Bachelor of ";
				}
			}
			retTitle = retTitle + title.replace(" (Engelsk)", "");
		}
		return retTitle;
	}
	
	public void createTextContent()
	{
		title = "test class";
		semester = new HashMap<Integer, Semester>();
		language = "NO";
		for (int s=0; s<3; s++)
		{
			Semester curSemester = new Semester(s+1, "autmn", this);
			for (int i=0; i<3;i++)
			{
				Kurs kurs = new Kurs();
				kurs.createTestContent(i);
				curSemester.addKurs(kurs);
			}
			semester.put(new Integer(s+1), curSemester);
		}
	}
	
	public String getLanguage()
	{
		return language;
	}
}
