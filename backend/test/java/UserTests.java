import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class UserTests
{
	@Test
	void getUser() throws IOException {
		HttpURLConnection con = TestHelper.doRequest("http://localhost:8080/api/user", TestHelper.getSession(), "GET");
		BufferedReader in = new BufferedReader(
			new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		Assertions.assertEquals(content, "test");
	}

	@Test
	void testFlaws() throws IOException
	{
		int status = TestHelper.doRequest("http://localhost:8080/api/flaws").getResponseCode();
		Assertions.assertEquals(status, 200);
	}

	@Test
	void testLogin() throws IOException
	{
		HttpURLConnection request;
		request = TestHelper.doRequest("http://localhost:8080/api/user/login");
		Assertions.assertEquals(request.getResponseCode(), 400);
		request = TestHelper.doRequest("http://localhost:8080/api/user/login?"
			+ "mail=Test1@test.de&"
			+ "password=123456789");
		Assertions.assertEquals(request.getResponseCode(), 200);
	}

	@Test
	void testLoginCookie()
	{
		Assertions.assertNotNull(TestHelper.getSession());
	}

	@Test
	void testLogout() throws IOException
	{
		HttpURLConnection request;
		request = TestHelper.doRequest("http://localhost:8080/api/user/logout/", TestHelper.getSession(), "POST");
		Assertions.assertEquals(request.getResponseCode(), 200);
		request = TestHelper.doRequest("http://localhost:8080/api/user/logout/", null, "POST");
		Assertions.assertEquals(request.getResponseCode(), 401);
	}

	@Test
	void testNewsletter() throws IOException
	{
		HttpURLConnection request;
		request = TestHelper.doRequest("http://localhost:8080/api/user/newsletter", TestHelper.getSession(), "POST");
		Assertions.assertEquals(request.getResponseCode(), 204);
		request = TestHelper.doRequest("http://localhost:8080/api/user/newsletter", TestHelper.getSession(), "DELETE");
		Assertions.assertEquals(request.getResponseCode(), 204);
		request = TestHelper.doRequest("http://localhost:8080/api/user/newsletter", null, "POST");
		Assertions.assertEquals(request.getResponseCode(), 401);
		request = TestHelper.doRequest("http://localhost:8080/api/user/newsletter",null, "DELETE");
		Assertions.assertEquals(request.getResponseCode(), 401);
	}
}
