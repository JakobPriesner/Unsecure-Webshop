import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import de.fhws.biedermann.webshop.models.ArticleVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

public class CartTests
{
	@Test
	void testGetCart() throws IOException
	{
		HttpURLConnection con = TestHelper.doRequest("http://localhost:8080/api/cart/items", TestHelper.getSession(), "GET", null);
		Assertions.assertEquals(con.getResponseCode(), 200);
		List<ArticleVersion> articles = new Genson().deserialize(TestHelper.getBody(con), new GenericType<>(){});
		Assertions.assertNotNull(articles);
	}

	@Test
	void testPostCart() throws IOException
	{
		final int articleNumber = 4;
		ArticleVersion articleVersion = new ArticleVersion(2, articleNumber, 16, 256, "red", "name", 1, 2);
		String json = new Genson().serialize(articleVersion);

		HttpURLConnection con = TestHelper.doRequest("http://localhost:8080/api/cart/items", TestHelper.getSession(), "POST", json);
		Assertions.assertEquals(con.getResponseCode(), 204);
	}
}
