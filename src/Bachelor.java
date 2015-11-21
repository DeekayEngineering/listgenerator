import java.util.ArrayList;
import org.apache.http.client.HttpClient;

public class Bachelor {
	HttpClient client = null;
	ArrayList<BachelorClass> classes = null;

	public Bachelor()
	{
		System.out.println("Reading classes...");
		String responseBody = Functions.get("https://at.bi.no/NO/om-studier-og-kurs/bachelor");

		classes = new ArrayList<BachelorClass>();
		int offset = 0;
        while (offset >= 0)
        {
            int startpos = Functions.strpos(responseBody, "https://at.bi.no/(NO|EN)/Pages/programme-information/", offset);
            //System.out.println(startpos);
            if (startpos > 0)
            {
                int endpos = Functions.strpos(responseBody, ".aspx", startpos)+5;
                String classLink = responseBody.substring(startpos, endpos);
                
                BachelorClass bClass = new BachelorClass();
                bClass.readUrl(classLink);
                classes.add(bClass);
                
                //System.out.println(classLink);
                offset = endpos;
                //break;
            }
            else
            {
            	offset = -1;
            }
        }
        
        /* valgkurs */
        int startpos = Functions.strpos(responseBody, "/(NO|EN)/om-studier-og-kurs/bachelor/valgkurs/elective", 0);
        int endpos = Functions.strpos(responseBody, "\"", startpos);
        String classLink = "https://at.bi.no"+responseBody.substring(startpos, endpos);
        System.out.println(classLink);
        BachelorClass bClass = new BachelorClass();
        bClass.readUrl(classLink);
        classes.add(bClass);
        
	}
	
	public ArrayList<BachelorClass> getClasses()
	{
		return classes;
	}
}
