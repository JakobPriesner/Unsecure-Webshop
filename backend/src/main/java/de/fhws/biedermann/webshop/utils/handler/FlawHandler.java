package de.fhws.biedermann.webshop.utils.handler;

import de.fhws.biedermann.webshop.database.DataAccessAdminPanel;

public class FlawHandler
{
	static DataAccessAdminPanel daap = new DataAccessAdminPanel();

	public static void imageWithWrongDataType(final String uuid) {
		daap.lookForClient(uuid);
		daap.putProfilePicture(uuid);
	}

	public static void emailWithoutAt(String uuid)
	{
		daap.lookForClient(uuid);
		daap.emailWithoutAt(uuid);
	}

	public static void htmlCommentUser(String uuid)
	{
		daap.lookForClient(uuid);
		daap.putHtmlCommentUser(uuid);
	}

	public static void putXSS(String uuid)
	{
		daap.lookForClient(uuid);
		daap.putXss(uuid);
	}

	public static void priceOrder(String uuid)
	{
		daap.lookForClient(uuid);
		daap.putPriceOrderBug(uuid);
	}

	public static void guessCoupon(String uuid)
	{
		daap.lookForClient(uuid);
		daap.putGuessCoupon(uuid);
	}

	public static void guessUserLogin(String uuid)
	{
		daap.lookForClient(uuid);
		daap.putGuessUserLogin(uuid);
	}

	public static void commentXSS(String uuid)
	{
		daap.lookForClient(uuid);
		daap.putCommentXss(uuid);
	}

	public static void sqlInjection(String uuid){
		daap.lookForClient(uuid);
		daap.putSqlInjection(uuid);
	}

	public static void deleteOtherUser( final String uuid ) {
		daap.lookForClient( uuid );
		daap.putDeleteUSer( uuid );
	}

	public static void blindSqlInjection( final String uuid ){
		daap.lookForClient(uuid);
		daap.putBlindSqlInjection(uuid);
	}

	public static void guessUser( final String uuid ){
		daap.lookForClient(uuid);
		daap.putBlindSqlInjection(uuid);
	}

	public static void endpointScanning( final String uuid ){
		daap.lookForClient( uuid );
		daap.putSecurityMisconfiguration(uuid);
	}

	public static void xxs( final String uuid ){
		daap.lookForClient(uuid);
		daap.putXss(uuid);
	}

	public static void LoginBruteForce( final String uuid) {
		daap.lookForClient( uuid );
		daap.putLoginBruteForce( uuid );
	}
}
