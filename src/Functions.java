import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Functions {
	
	static CloseableHttpClient httpClient = null;
	
	public static int strpos(String haystack, String needle)
	{
		return strpos(haystack, needle, 0);
	}
	
	public static int strpos(String haystack, String needle, int offset)
	{
		if (offset > 0)
		{
			haystack = haystack.substring(offset);
		}
		Pattern pattern = Pattern.compile(needle);
		Matcher matcher = pattern.matcher(haystack);
		if (matcher.find())
		{
			return offset+matcher.start();
		}
		return 0;
	}
	
	public static String get(String url)
	{
		if (httpClient == null)
		{
			initHttpClient();
		}
		
        try {
            HttpGet httpget = new HttpGet(url);

            //System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            
            String responseBody = httpClient.execute(httpget, responseHandler);
            return responseBody;
        }
        catch (ClientProtocolException e)
        {
        	// TODO
        	return "";
        }
        catch (IOException e)
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public static void initHttpClient()
	{
		httpClient = HttpClients.createDefault();
	}
	
	public static String subStringRegEx(String haystack, String needleStart, String needleEnd)
	{
		return subStringRegEx(haystack, needleStart, needleEnd, 0);
	}
	
	public static String subStringRegEx(String haystack, String needleStart, String needleEnd, int offset)
	{
        int startpos = Functions.strpos(haystack, needleStart, offset);
        //System.out.println(startpos);
        if (startpos > 0)
        {
            int endpos = Functions.strpos(haystack, needleEnd, startpos)+needleEnd.length();
            return haystack.substring(startpos, endpos);
        }
        else
        {
        	offset = -1;
        	return null;
        }
	}
	
	public static String removeHtml(String html)
	{
		String retVal = html;
		retVal = retVal.replace("\n", "");
		while (retVal.contains("<"))
		{
			int start = retVal.indexOf("<");
			int end = retVal.indexOf(">",start);
			String before = "";
			String after = "";
			if (start > 0)
			{
				before = retVal.substring(0, start);
			}
			if (end > 0)
			{
				after = retVal.substring(end+1);
			}
			retVal = before+after;
		}
		retVal = retVal.replace("&#160;", "");
		retVal = retVal.replace("&#8203;", "");
		return retVal;
	}
	
	public static boolean isInteger(String txt)
	{
		try
		{
			Integer.parseInt(txt);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
}
