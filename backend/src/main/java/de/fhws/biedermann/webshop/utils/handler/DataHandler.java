package de.fhws.biedermann.webshop.utils.handler;

import de.fhws.biedermann.webshop.database.DataAccessShopDatabase;
import de.fhws.biedermann.webshop.models.*;
import de.fhws.biedermann.webshop.utils.SecurityBreachDetection;

import javax.annotation.Nullable;
import java.util.List;

public class DataHandler
{
	static DataAccessShopDatabase Database = new DataAccessShopDatabase();

	public static List<Article> getArticles(int page, String search, boolean specifications)
	{
		// toDo: without specifications
		return Database.getArticles(page, search);
	}

	public static Article getArticle(int id)
	{
		return Database.getArticle(id);
	}

	public static List<ArticleVersion> getCartItems(String session)
	{
		return Database.getShoppingCart(Database.getUserId(session));
	}

	public static Nullable createCartItem(ArticleVersion articleVersion, String session)
	{
		Database.postShoppingCartItem(articleVersion, Database.getUserId(session));  //get user id from
		return null;
	}

	public static ArticleVersion modifyCartItem(int id, ArticleVersion articleVersion)
	{
		Database.putShoppingCartItem(articleVersion);
		return articleVersion;
	}

	public static Nullable deleteCartItem(String session, int id)
	{
		Database.deleteShoppingCartItem(session, id);
		return null;
	}

	public static Nullable createContact( Contact contact)
	{
		//TODO detect attack
		return null;
	}

	public static Coupon getCoupon(String name)
	{
		return Database.getCoupon(name);
	}

	public static String getPicture(int id)
	{
		return Database.getPicture(id);
	}

	public static List<Order> getOrders( final String session )
	{
		return Database.getOrders( Database.getUserId(session) );
	}

	public static Order getOrder( final int id )
	{
		return Database.getOrder(id);
	}

	public static int createOrder( final Order order, final String session, final boolean cleanup )
	{
		return Database.postOrder(order, Database.getUserId(session), cleanup).getOrderNumber();
	}

	public static User getUser(String session)
	{
		return Database.getUserInformation(Database.getUserId(session));
	}

	public static User getUserById( final int id )
	{
		return Database.getUserInformation(id);
	}

	public static int createUser( final User user )
	{
		Database.postUser(user);
		return 1;
	}

	public static Nullable modifyUser( final String session, final User user )
	{
		Database.putUser(user, Database.getUserId(session));
		return null;
	}

	public static Nullable deleteUser( final String session )
	{
		// Database.deleteUser(Database.getUserId(session));
		return null;
	}

	public static Nullable deleteUserById( final String session, final long userId, final String ipOfRequest ) {
		if (!SecurityBreachDetection.matchSessionToUserId( session, userId )) {
			FlawHandler.deleteOtherUser( ipOfRequest );
		}
		return null;
	}

	public static Payment getUserPayment( final String session )
	{
		return Database.getPayment(Database.getUserId(session));
	}

	public static int createUserPayment( final String session, final Payment payment )
	{
		Database.postPayment(Database.getUserId(session), payment);
		return 1;
	}

	public static Address getUserAddress(String session, int addressID)
	{
		return Database.getAddress(addressID);
	}

	public static List<Address> getAllUserAddresses(String session)
	{
		return Database.getAddresses(Database.getUserId(session));
	}

	public static Address createAddress(String session, Address address)
	{
		return Database.postAddress(address, Database.getUserId(session));
	}

	public static Address modifyAddress(Address address, String session)
	{
		return Database.putAddress(address, Database.getUserId(session));
	}

	public static Nullable deleteAddress(String session, int addressID)
	{
		Database.deleteAddress(addressID, Database.getUserId(session));
		return null;
	}

	public static String getUserMail(String session)
	{
		return Database.getUserInformation(Database.getUserId(session)).getMail();
	}

	public static Nullable createUserMail(String session, String mail)
	{
		// Database.createUserMail(id);
		return null;
	}

	public static boolean checkNewsletter(String session)	{
		return Database.getNewsletter(Database.getUserId(session));
	}

	public static Nullable turnOnNewsletter(String session)
	{
		Database.postNewsletter(Database.getUserId(session));
		return null;
	}

	public static Nullable turnOffNewsletter(String session)
	{
		Database.deleteNewsletter(Database.getUserId(session));
		return null;
	}

	public static List<ArticleVersion> getWishlist(String session)
	{
		return Database.getWishlist(Database.getUserId(session));
	}

	public static Nullable createWishlistItem(ArticleVersion articleVersion, String session)
	{
		Database.postWishListItem(articleVersion, Database.getUserId(session));
		return null;
	}

	public static Nullable modifyWhishlistItem(ArticleVersion articleVersion, String session, int id)
	{
		Database.putWishListItem(articleVersion);
		return null;
	}

	public static Nullable deleteWishlist(String session)
	{
		Database.deleteWishList(Database.getUserId(session));
		return null;
	}

	public static Nullable deleteWishlistItem(int id)
	{
		Database.deleteWishListItem(id);
		return null;
	}


}
