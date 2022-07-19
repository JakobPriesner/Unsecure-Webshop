package de.fhws.biedermann.webshop.utils.logic;

import de.fhws.biedermann.webshop.database.DataAccessAdminPanel;
import de.fhws.biedermann.webshop.database.DataAccessShopDatabase;
import de.fhws.biedermann.webshop.models.RankingRow;

import javax.annotation.Nullable;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;

public class AdminLogic
{

    private static AdminLogic INSTANCE;

    private final DataAccessAdminPanel dataAccessAdminPanel;
    private final DataAccessShopDatabase dataAccessShopDatabase;
    private int level;

    private AdminLogic( final int level ) {
        this.dataAccessAdminPanel = new DataAccessAdminPanel();
        this.dataAccessShopDatabase = new DataAccessShopDatabase();
        this.level = level;
    }

    public static AdminLogic getInstance( )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new AdminLogic( 1 );
        }

        return INSTANCE;
    }

    public String login( final String username, final String password ){
        String sessionId = AuthenticationLogic.createSessionId();
        dataAccessAdminPanel.postSession( sessionId, username );
        if ( !dataAccessAdminPanel.login( username, password ) ) {
            throw new NotAuthorizedException( "" );
        }
        dataAccessShopDatabase.postSession(sessionId, "biedermann", "Admin");
        return sessionId;
    }

    public Nullable logout( final String session ){
        authorizeRequest( session );
        dataAccessAdminPanel.deleteSession( session );
        return null;
    }

    public List<RankingRow> getRanking( final String session ){
        authorizeRequest( session );
        return dataAccessAdminPanel.getRanking();
    }

    public Nullable resetDatabaseShop ( final String session ){
        authorizeRequest( session );
        dataAccessShopDatabase.resetDatabaseInProduction();
        return null;
    }

    public int getLevel( ){
        return level;
    }

    public Nullable setLevel( int levelModel, final String session ){
        authorizeRequest( session );
        if ( levelModel < 1 || levelModel > 3) throw new BadRequestException( );
        level = levelModel;
        return null;
    }

    public Nullable resetDatabaseRanking( final String session ){
        authorizeRequest( session );
        dataAccessAdminPanel.resetRanking();
        return null;
    }

    private void authorizeRequest( final String session ) {
        if ( !dataAccessAdminPanel.checkSession(session) ) {
            throw new ForbiddenException( );
        }
    }
}
