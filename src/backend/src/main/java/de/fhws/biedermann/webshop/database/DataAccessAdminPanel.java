package de.fhws.biedermann.webshop.database;

import de.fhws.biedermann.webshop.models.RankingRow;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessAdminPanel {

    private Connection createConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Path currentRelativePath = FileSystems.getDefault( ).getPath( "" );
            c = DriverManager.getConnection( String.format( "jdbc:sqlite:%s/src/backend/src/main/java/de/fhws/biedermann/webshop/database/adminPanel.db", currentRelativePath.toAbsolutePath() ) );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Can't create Connection!");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found!");
        }
        return c;
    }

    public void lookForClient(String ipAddress){
        if(!this.checkClientExist(ipAddress)){
            this.postClient(ipAddress);
        }
    }

    public void resetDatabase(){
        this.deleteDatabase();;
        this.createDatabase();
    }

    public void resetRanking(){
        this.deleteRanking();;
        this.createRanking();
    }

    private void deleteRanking(){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for (String sql : DatabaseQueries.deleteRanking) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createRanking(){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for (String sql : DatabaseQueries.createRanking) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> checkForNewFindings(String ipAddress){
        ArrayList<String> findings = new ArrayList<>();
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="SELECT sql_injection, blind_sql_injection, email_without_at, xss, profile_picture, html_comment_user, price_order_bug, " +
                    "guess_user_login, guess_coupon, delete_user, comment_xss, login_brute_force, hash_user, security_misconfiguration FROM ranking WHERE ip_address='"+ipAddress+"';";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                ResultSetMetaData rsmd = rs.getMetaData();
                for(int i=1; i<=13;i++){
                    if(rs.getInt(i)==1){
                        findings.add(rsmd.getColumnName(i));
                    }
                }
            }
            if(findings.size()!=0){
                String sql2="UPDATE ranking SET ";
                for(int j=0; j<findings.size();j++){
                    sql2 = sql2+findings.get(j)+"=2, ";
                }
                sql2=sql2.substring(0, sql2.length()-2);
                sql2=sql2+" WHERE ip_address='"+ipAddress+"';";
                stmt.execute(sql2);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findings;
    }

    public List<RankingRow> getRanking(){
        List<RankingRow> ranking = new ArrayList<>();
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="SELECT *, SUM(sql_injection+blind_sql_injection+email_without_at+xss+profile_picture+html_comment_user+price_order_bug+guess_user_login+guess_coupon+delete_user+comment_xss+login_brute_force+hash_user+security_misconfiguration) as sum FROM ranking GROUP BY ip_address ORDER BY sum DESC;";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                ranking.add( new RankingRow(rs.getString("ip_address"), rs.getInt("sql_injection"), rs.getInt("blind_sql_injection"), rs.getInt("email_without_at"), rs.getInt("xss"), rs.getInt("profile_picture"),
                        rs.getInt("html_comment_user"), rs.getInt("price_order_bug"), rs.getInt("guess_user_login"), rs.getInt("guess_coupon"),
                        rs.getInt("delete_user"), rs.getInt("comment_xss"), rs.getInt("login_brute_force"), rs.getInt("hash_user"), rs.getInt("security_misconfiguration"), rs.getInt("sum")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ranking;
    }

    public boolean login(String username, String password){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="SELECT password FROM admin WHERE username='"+username+"';";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                DataAccessShopDatabase dasd = new DataAccessShopDatabase();
                if(dasd.encryptPasswordRealUser(password).equals(rs.getString("password"))){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteSession(final String session){
        try (Connection con = this.createConnection();
            Statement stmt = con.createStatement()){
            String sql="DELETE FROM session WHERE key='"+session+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putSqlInjection(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET sql_injection=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putBlindSqlInjection(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET blind_sql_injection=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void emailWithoutAt(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET email_without_at=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putXss(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET xss=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putProfilePicture(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET profile_picture=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putHtmlCommentUser(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET html_comment_user=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putPriceOrderBug(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET price_order_bug=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putGuessUserLogin(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET guess_user_login=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putGuessCoupon(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET guess_coupon=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putDeleteUSer(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET delete_user=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putCommentXss(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET comment_xss=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putLoginBruteForce(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET login_brute_force=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putHashUser(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET hash_user=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putSecurityMisconfiguration(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="UPDATE ranking SET security_misconfiguration=1 WHERE ip_address='"+ipAddress+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void postSession(String session, String username){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="INSERT INTO session(key, admin_username) VALUES('"+session+"','"+username+"');";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkSession(String session){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="SELECT key FROM session WHERE key='"+session+"';";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next() && rs.getString("key").equals(session)){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void postClient(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "INSERT INTO ranking(ip_address) VALUES('"+ipAddress+"');";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkClientExist(String ipAddress){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="SELECT ip_address FROM ranking WHERE ip_address='"+ipAddress+"';";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next() && rs.getString("ip_address").equals(ipAddress)){
                return true;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void deleteDatabase(){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for (String sql : DatabaseQueries.deleteDatabaseAdminPanel) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDatabase(){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for (String sql : DatabaseQueries.createDatabaseAdminPanel) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
