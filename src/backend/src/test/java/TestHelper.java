import com.owlike.genson.Genson;
import de.fhws.biedermann.webshop.models.Article;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestHelper
{
	public static HttpURLConnection doRequest(String url_param) {return doRequest(url_param, null, "GET", null);}

	public static HttpURLConnection doRequest(String url_param, String session, String method) {return doRequest(url_param, session, method, null);}

	public static HttpURLConnection doRequest(String url_param, String session, String method, String json)
	{
		try
		{
			URL url = new URL(url_param);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("uuid", "a0q327p50382750");
			if (session != null) con.setRequestProperty("sessionid", session);
			if (json != null) {
				con.addRequestProperty("Content-Type", "application/json");
				con.setDoOutput(true);
				con.setRequestMethod(method);
				OutputStream os = con.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
				osw.write(json);
				osw.flush();
				osw.close();
				os.close();  //don't forget to close the OutputStream
			}

			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getSession() {
		try
		{
			HttpURLConnection con = doRequest("http://localhost:8080/api/user/login?mail=mail1@mail.com&password=test");
			String cookiesHeader = TestHelper.getBody(con);
			return cookiesHeader.replace("\n", "");
		} catch (Exception e) {
			return null;
		}
	}

	public static String getBody(HttpURLConnection con) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\n");
			}
			br.close();
			return sb.toString();
		}
		catch (IOException e)
		{
			return null;
		}
	}
}
