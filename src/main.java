import java.io.IOException;
import java.util.ArrayList;



public class main {
	
	public static void main(String[] args) throws IOException {

		Report.init();
		Filter.setFilterAutmn(false);
		Filter.setFilterSpring(true);
		Filter.setFilterYear(2016);
		
		int testMode = 2;
		
		if (testMode < 2)
		{
			ArrayList<BachelorClass> bclasses = null;
			if (testMode == 1)
			{
				BachelorClass bclass = new BachelorClass();
				//bclass.readUrl("https://at.bi.no/NO/Pages/programme-information/2015-2016/ProBac_Markedsf%C3%B8ringsledelse_L%C3%A6ringsm%C3%A5l.aspx");
				bclass.createTextContent();
				bclasses = new ArrayList<BachelorClass>();
				bclasses.add(bclass);
			}
			else
			{
				Bachelor bachelor = new Bachelor();
				bclasses = bachelor.getClasses();
			}
			Exporter exporter = new Exporter();
			exporter.exportRtf(bclasses);
			//exporter.exportOdt(bclasses);
		}
		else if (testMode == 2)
		{
			Book testbook = new Book();
			testbook.parse("Langfeldt, Sverre F. 2012. Oppgavesamling i rettslære med løsningsveiledninger : med bl.a. eksamensoppgaver i rettslære på revisorstudiet, på siviløkonomstudiet og på bachelorstudiene 2003-2012. 11. utg. Focus");
		}
		
		System.out.println("");
		Report.reportBooksToCheck();
		Report.reportDiscardedBooks();

	  /*File dir = new File("csv");
	  File[] directoryListing = dir.listFiles();
	  if (directoryListing != null) {
	    for (File child : directoryListing) {
			String CurSrc = child.getName().substring(0, child.getName().indexOf("."));
			System.out.println(CurSrc);
			ArrayList<String> list = getListFromFile("csv/"+CurSrc+".csv");
			//String site = "https://at.bi.no/EN/programme-information/master-of-science/class-of-2015-2017/msc-in-financial-economics-2015-2017";
			//ArrayList<String> list = getListFromWebsite(site,"1. semester");
	
		    HashMap<String,Kurs> kursList = new HashMap<String, Kurs>();
		    for (String item : list)
			{
				Kurs kurs = KursLookup.lookup(item);
				kursList.put(item, kurs);
			}
	
		    // not yet sorted
		    ArrayList<Kurs> kurslistsorted = sortList(kursList.values());
		    writeHtml(CurSrc+".html", kurslistsorted);
	    }
	  } else {
	    // Handle the case where dir is not really a directory.
	    // Checking dir.isDirectory() above would not be sufficient
	    // to avoid race conditions with another process that deletes
	    // directories.
	  }*/
	  
		/*String[] src = {*//* Class 2014 - 2016 */
						/*"https://at.bi.no/EN/programme-information/master-of-science/class-of-2014-2016/msc-in-financial-economics-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/class-of-2014-2016/msc-in-leadership-and-organisational-psychology-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/class-of-2014-2016/msc-in-strategic-marketing-management-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/class-of-2014-2016/internship-for-msc-in-leadership-and-organizational-psychology-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/class-of-2014-2016/internship-for-msc-in-strategic-marketing-management-2014-2016",*/
						/* Class 2015 - 2017 */
						/*"https://at.bi.no/EN/programme-information/master-of-science/class-of-2015-2017/msc-in-financial-economics-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/class-of-2015-2017/msc-in-leadership-and-organisational-psychology-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/class-of-2015-2017/msc-in-strategic-marketing-management-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/class-of-2015-2017/internship-for-msc-in-leadership-and-organizational-psychology-2015-2017",*/
						/* Business 2014 - 2016 */
						/*"https://at.bi.no/EN/programme-information/master-of-science/business-2014-2016/msc-in-business-business-law-tax-and-accounting-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2014-2016/msc-in-business-economics-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2014-2016/msc-in-business-finance-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2014-2016/msc-in-business-international-business-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2014-2016/msc-in-business-leadership-and-change-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2014-2016/msc-in-business-logistics-operations-and-supply-chain-management-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2014-2016/msc-in-business-marketing-2014-2016",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2014-2016/msc-in-business-strategy-2014-2016",*/
						/* Business 2015 - 2017 */
						/*"https://at.bi.no/EN/programme-information/master-of-science/business-2015-2017/msc-in-business-business-law-tax-and-accounting-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2015-2017/msc-in-business-economics-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2015-2017/msc-in-business-finance-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2015-2017/msc-in-business-international-business-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2015-2017/msc-in-business-leadership-and-change-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2015-2017/msc-in-business-logistics-operations-and-supply-chain-management-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2015-2017/msc-in-business-marketing-2015-2017",
						"https://at.bi.no/EN/programme-information/master-of-science/business-2015-2017/msc-in-business-strategy-2015-2017"*/
						/* Addition kurses */
						/*"https://at.bi.no/NO/om-studier-og-kurs/master-of-science/regnskap-og-revisjon/master-i-regnskap-og-revisjon-2015-2017",
						"https://at.bi.no/NO/om-studier-og-kurs/master-of-science/regnskap-og-revisjon/master-i-regnskap-og-revisjon-2014-2016"};*/
		
		/*for (int i=0; i<src.length; i++)
		{
			//ArrayList<String> list = getListFromFile(CurSrc+".csv");
			String site = src[i];
			WebReader myReader = new WebReader(site, false);
			ArrayList<String> list = myReader.getList();
			
			//System.exit(0);
	
		    HashMap<String,Kurs> kursList = new HashMap<String, Kurs>();
		    for (String item : list)
			{
				Kurs kurs = KursLookup.lookup(item);
				kursList.put(item, kurs);
			}
	
		    // not yet sorted
		    ArrayList<Kurs> kurslistsorted = sortList(kursList.values());
		    writeHtml(myReader.getTitle(), kurslistsorted);
		    
		    //System.exit(0);
		}*/
	}
	
	/*private static ArrayList<String> getListFromFile(String filename) throws IOException
	{
		BufferedReader br = null;
		String line;
		String cvsSplitBy = ";";
		ArrayList<String> list = new ArrayList<String>();
		br = new BufferedReader(new FileReader(filename));

		while ((line = br.readLine()) != null) {
			if (line.contains(" "))
			{
				String[] colomn = line.split(cvsSplitBy);
				String id = colomn[0];
				list.add(id);
			}
		}

		return list;
	}
	
	private static ArrayList<Kurs> sortList(Collection<Kurs> collection)
	{
	    ArrayList<Kurs> kurslistsorted = new ArrayList<Kurs>(collection);

	    Collections.sort(kurslistsorted, new Comparator<Kurs>() {

	        public int compare(Kurs k1, Kurs k2) {
	            return k1.getId().compareTo(k2.getId());
	        }
	    });
	    
	    return kurslistsorted;
		
	}*/
	
	/*private static void writeHtml(String title, ArrayList<Kurs> kursList) throws IOException
	{
		String filename = title.replace(" ", "-");
		filename = filename.replace("(", "");
		filename = filename.replace(")", "");
		filename = filename.replace(".", "");
		filename = filename.replace(":", "");
		String pdfFilename = filename + ".pdf";
		filename = filename + ".html";
		//System.out.println(filename);

	    BufferedWriter out = null;
		try  
		{
			File f = new File(filename);
			if(f.exists() && !f.isDirectory())
			{
				f.delete();
			}
			
		    FileWriter fstream = new FileWriter(filename, true); //true tells to append data.

		    out = new BufferedWriter(fstream);
			out.write("<p><font color=\"#ff0000\" size=\"6\" style=\"font-family:arial\">"+title+"<BR></font></p>");
			for (Kurs kurs : kursList)
			{
				String tableBorder = "";//";border-top-style:solid; border-top-width:1px; border-top-color:#ff0000";
				out.write("<p><font color=\"#ff0000\" size=\"5\" style=\"font-family:arial" + tableBorder + "\">"+kurs.getId()+" "+kurs.getTitle()+"</font></p>");
				
				
				ArrayList<Book> books = kurs.getRecommendedBooks();
				if (books.size()>0)
				{
					String subhead = "Obligatorisk litteratur";
					if (kurs.getLanguage().equalsIgnoreCase("eng"))
					{
						subhead = "Recommended reading";
					}
	
					out.write("<p><font color=\"#000000\" size=\"4\" style=\"font-family:arial\"><b>"+subhead+"</b></font></p>");
					
					out.write("<p>");
					for (Book book : books)
					{
						out.write("<font color=\"#000000\" size=\"3\" style=\"font-family:arial\">");
						if (book.getAuthors() != null)
						{
							out.write(book.getAuthors() + "<br>");
						}
					    out.write(book.getTitle()+"</font><br><br>");
					}
					out.write("</p>");
				}
	
				
				books = kurs.getCompulsoryBooks();
				if (books.size()>0)
				{
					String subhead = "Anbefalt litteratur";
					if (kurs.getLanguage().equalsIgnoreCase("eng"))
					{
						subhead = "Compulsory reading";
					}
	
					out.write("<p><font color=\"#000000\" size=\"4\" style=\"font-family:arial\"><b>"+subhead+"</b></font></p>");
					
					out.write("<p>");
					for (Book book : kurs.getCompulsoryBooks())
					{
						out.write("<font color=\"#000000\" size=\"3\" style=\"font-family:arial\">");
						if (book.getAuthors() != null)
						{
							out.write(book.getAuthors() + "<br>");
						}
					    out.write(book.getTitle()+"</font><br><br>");
					}
					out.write("</p>");
				}
			}
		}
		catch (IOException e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
		finally
		{
		    if(out != null) {
		        out.close();
		    }
		}
		
	}*/
}
