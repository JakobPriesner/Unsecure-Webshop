package de.fhws.biedermann.webshop.utils;

import de.fhws.biedermann.webshop.database.DataAccessShopDatabase;
import de.fhws.biedermann.webshop.models.LoginData;
import de.fhws.biedermann.webshop.utils.handler.FlawHandler;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SecurityBreachDetection
{
	static DataAccessShopDatabase db = new DataAccessShopDatabase();
	static Map<String, List<LoginData>> loginTrys = new HashMap<>();
	public static boolean matchSessionToUserId(final String session, final long userId) {
		return db.getUserId( session ) == userId;
	}

	public static boolean isInvalidFileFormat( final String file ) {
		return StringUtils.isNotEmpty( file ) && isNotAnImage( file );
	}

	private static boolean isNotAnImage( String file ) {
		file = file.replaceFirst( "data:image/", "" );
		return ! (file.startsWith( "png" )
			|| file.startsWith( "jpg" )
			|| file.startsWith( "jpeg" )
			|| file.startsWith( "tiff" )
			|| file.startsWith( "gif" )
			|| file.startsWith( "bmp" ));
	}

	public static boolean guessCoupon( final String coupon ) {
		final String lowerCoupon = coupon.toLowerCase().replace( " ", "" );
		final List<String> dummyCoupons = Arrays.asList( "blackfriday", "summersale", "summer", "ausverkauf", "neujahr", "newyear", "winter", "biedermann", "biedisshop" );
		// toDo: look for date (winter, summer)
		return dummyCoupons.stream( ).anyMatch( lowerCoupon::contains );
	}

	public static void detectHtmlCommentUser( final String mail, final String password, final String ip ) {
		if (mail.equals("dummy@user.com") && password.equals("MyPasswordIsSafe")) {
			FlawHandler.htmlCommentUser(ip);
		}
	}

	public static void detectLoginSecurityBreach( final LoginData loginData )
	{
		if( loginTrys.containsKey( loginData.getUuid() ) && loginTrys.get( loginData.getUuid() ).stream().noneMatch(
			loginData::compareTo )) {
			ArrayList<LoginData> loginList = new ArrayList<>( loginTrys.get( loginData.getUuid() ) );
			loginList.add( loginData.setTimestamp( LocalDateTime.now() ) );
			loginTrys.put( loginData.getUuid(), loginList );
		} else {
			loginTrys.put( loginData.getUuid(), List.of( loginData.setTimestamp( LocalDateTime.now() ) ) );
		}

		if( loginTrys.get( loginData.getUuid() ).size() > 10 )
		{
			FlawHandler.LoginBruteForce( loginData.getUuid() );
		}
		loginTrys.put( loginData.getUuid(), loginTrys.get( loginData.getUuid() ).stream( ).filter( byTimeDelta() ).collect(
			Collectors.toList( ) ) ) ;
	}

	private static Predicate<LoginData> byTimeDelta( )
	{
		final LocalDateTime now = LocalDateTime.now();
		return loginData -> loginData.getTimestamp().until( now, ChronoUnit.SECONDS ) < 3;
	}
}
