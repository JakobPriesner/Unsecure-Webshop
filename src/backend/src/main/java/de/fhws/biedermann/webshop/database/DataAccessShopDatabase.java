package de.fhws.biedermann.webshop.database;

import com.google.common.hash.Hashing;
import de.fhws.biedermann.webshop.models.*;
import de.fhws.biedermann.webshop.models.modelsdb.ArticleDB;
import de.fhws.biedermann.webshop.models.modelsdb.CommentDB;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataAccessShopDatabase {

    private Connection createConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Path currentRelativePath = FileSystems.getDefault( ).getPath( "" );
            c = DriverManager.getConnection( String.format( "jdbc:sqlite:%s/src/backend/src/main/java/de/fhws/biedermann/webshop/database/shopDatabase.db", currentRelativePath.toAbsolutePath() ) );
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void resetDatabase() {
        this.deleteDatabase();
        this.createDatabase();
    }

    public void resetDatabaseInProduction() {
        this.deleteDatabaseInProduction();
        this.createDatabaseInProduction();
    }

    private void deleteDatabaseInProduction() {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            for (String sql : DatabaseQueries.deleteDatabaseInProduction) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDatabaseInProduction() {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for (String sql : DatabaseQueries.createDatabaseInProduction) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.postArticleVersions();
        this.postComments();
    }

    private void createDatabase() {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for (String sql : DatabaseQueries.createDatabase) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.postDummyUsers();
        this.postRealUser();
        this.postArticleVersions();
        this.postComments();
    }

    private void postComments(){
        for( CommentDB comment : DatabaseQueries.comments){
            this.postCommentDB(comment);
        }
    }

    private void deleteDatabase() {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            for (String sql : DatabaseQueries.deleteDatabase) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Commentary postCommentary(Commentary comment, int articleId, String sessionId) {
        int userId = getUserId( sessionId );
        if(this.checkForInjection(comment.getCommentText()) || this.checkForInjection(comment.getFirstName()) ||
                this.checkForInjection(comment.getLastName())|| this.checkForInjection(comment.getProfilePicture())){
           return new Commentary();
        }
        Commentary commentaryRet = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){

            String sql = "INSERT INTO comment(comment_text, article_id, user_id) " +
                "VALUES('" + comment.getCommentText() + "', " + articleId + ", " + userId + ");";
            stmt.execute(sql);
            int commentId = stmt.getGeneratedKeys().getInt(1);
            commentaryRet = this.getCommentary(commentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentaryRet;
    }

    public Commentary getCommentary(int id) {
        Commentary commentary = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT * FROM comment INNER JOIN user on user.id=comment.user_id WHERE comment.id=" + id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                commentary = new Commentary(rs.getInt(1), rs.getString("comment_text"), rs.getInt("user_id"),
                    rs.getString("firstname"), rs.getString("lastname"), rs.getString("profile_picture"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentary;
    }

    public User postUser( User user) {
        if(this.checkForInjection(user.getDescription()) || this.checkForInjection(user.getMail())|| this.checkForInjection(user.getFirstName())|| this.checkForInjection(user.getLastName())
                || this.checkForInjection(user.getSalutation())|| this.checkForInjection(user.getTitle()) || this.checkForInjection(user.getProfilePicture())){
            return null;
        }
        int newsletter = user.isNewsletter() ? 1 : 0;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){

            String sql = "INSERT INTO User(e_mail, firstname, lastname, password,real_user) " +
                "VALUES('" + user.getMail() + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + this.encryptPasswordRealUser(user.getPassword()) + "'," + 1 + ");";
            stmt.execute(sql);
            int userId = stmt.getGeneratedKeys().getInt(1);
            user.setId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Address putAddress( Address address, int userId) {
        if(this.checkForInjection(address.getName()) || this.checkForInjection(address.getCountry()) || this.checkForInjection(address.getAddress())||
                this.checkForInjection(address.getAddress2())|| this.checkForInjection(address.getZipCode())|| this.checkForInjection(address.getCity())||
                this.checkForInjection(address.getDeliveryInstructions())){
            return null;
        }
        Address addressRet = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "UPDATE address " +
                " SET street_house_number='" + address.getAddress() + "', postcode='" + address.getZipCode() + "', address_supplement='" + address.getAddress2() + "', city='" + address.getCity() + "', country='" + address.getCountry()
                + "', name='" + address.getName() + "', delivery_instruction='" + address.getDeliveryInstructions() + "' WHERE id=" + address.getId() +" AND "+"user_id="+userId+";";
            stmt.execute(sql);
            addressRet = this.getAddress(address.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return addressRet;
    }

    public Address postAddress(Address address, int userId) {
        if(this.checkForInjection(address.getName()) || this.checkForInjection(address.getCountry()) || this.checkForInjection(address.getAddress())||
                this.checkForInjection(address.getAddress2())|| this.checkForInjection(address.getZipCode())|| this.checkForInjection(address.getCity())||
                this.checkForInjection(address.getDeliveryInstructions())){
            return null;
        }
        Address addressRet = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            String sql = "INSERT INTO address(street_house_number, postcode, address_supplement, city, country, name, delivery_instruction, user_id)" +
                "VALUES('" + address.getAddress() + "', '" + address.getZipCode() + "', '" + address.getAddress2() + "', '" + address.getCity() + "', '" + address.getCountry()
                + "', '" + address.getName() + "', '" + address.getDeliveryInstructions() + "', '" + userId+ "');";
            stmt.execute(sql);
            int addressId = stmt.getGeneratedKeys().getInt(1);
            addressRet = this.getAddress(addressId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressRet;
    }

    public void postNewsletter(int userId) {

        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            String sql = "UPDATE user SET newsletter=1 WHERE id=" + userId + ";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNewsletter(int userId) {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){

            String sql = "UPDATE user SET newsletter=0 WHERE id=" + userId + ";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAddress(int addressId, int userId) {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){

            String sql = "UPDATE address SET user_id=-1 WHERE id=" + addressId + "AND"+"user_id="+userId+";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWishList(int userId) {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "DELETE FROM wish_list WHERE user_id=" + userId + ";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWishListItem(int wishListItemId) {

        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "DELETE FROM wish_list WHERE id=" + wishListItemId + ";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteShoppingCartItem(String session,int shoppingCartItemId) {
        if(this.getUserId(session) != this.getUserIdFromShoppingCartItem(shoppingCartItemId)){
            return;
        }
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "DELETE FROM shopping_cart WHERE id=" + shoppingCartItemId + ";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User putUser(User user, int userId) {
        if(this.checkForInjection(user.getDescription()) || this.checkForInjection(user.getMail())|| this.checkForInjection(user.getFirstName())|| this.checkForInjection(user.getLastName())
                || this.checkForInjection(user.getSalutation())|| this.checkForInjection(user.getTitle()) || this.checkForInjection(user.getProfilePicture())){
            return null;
        }
        User userRet = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "UPDATE user SET firstname='" + user.getFirstName() + "', lastname='" + user.getLastName() + "', title='" + user.getTitle() + "', " +
                "salutation='" + user.getSalutation() + "', e_mail='" + user.getMail() + "', profile_picture='" + user.getProfilePicture() + "', description='" + user.getDescription() + "' " + "WHERE id=" + userId + ";";
            stmt.execute(sql);
            userRet = this.getUserInformation(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRet;
    }

    public void putPassword(String password, int userId) {
        String hash = this.encryptPasswordRealUser(password);
        if (!this.isRealUser(userId)) {
            return;
        }
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "UPDATE user SET password='" + hash + "' WHERE id=" + userId + ";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArticleVersion postWishListItem(ArticleVersion articleVersion, int userId) {
        int articleVersionId = this.findArticleVersionId(articleVersion.getArticleNumber(), articleVersion.getGbSize(), articleVersion.getColor());
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            String sql = "INSERT INTO wish_list(quantity, user_id, article_version_id) " +
                "VALUES(" + articleVersion.getQuantity() + ", " + userId + ", " + articleVersionId + ");";
            stmt.execute(sql);
            articleVersion.setId(stmt.getGeneratedKeys().getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articleVersion;
    }

    public ArticleVersion postShoppingCartItem(ArticleVersion articleVersion, int userId) {
        int articleVersionId = this.findArticleVersionId(articleVersion.getArticleNumber(), articleVersion.getGbSize(), articleVersion.getColor());
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="";
            int quantity=this.checkShoppingCartItemExist(userId, articleVersionId);
            if(quantity!=0){
                int quantitySum=quantity+articleVersion.getQuantity();
                sql="UPDATE shopping_cart SET quantity="+quantitySum+" WHERE user_id="+userId+" AND article_version_id="+articleVersionId+";";
            }
            else{
                sql = "INSERT INTO shopping_cart(quantity, user_id, article_version_id) " +
                        "VALUES(" + articleVersion.getQuantity() + ", " + userId + ", " + articleVersionId + ");";
            }
            stmt.execute(sql);
            articleVersion.setId(stmt.getGeneratedKeys().getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articleVersion;
    }

    public ArticleVersion putWishListItem(ArticleVersion articleVersion) {
        int articleVersionId = this.findArticleVersionId(articleVersion.getArticleNumber(), articleVersion.getGbSize(), articleVersion.getColor());
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "UPDATE wish_list SET quantity=" + articleVersion.getQuantity() + ", article_version_id=" + articleVersionId + " WHERE id=" + articleVersion.getId() + ";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.getArticleVersion(articleVersion.getId());
    }

    public ArticleVersion putShoppingCartItem(ArticleVersion articleVersion) {
        int articleVersionId = this.findArticleVersionId(articleVersion.getArticleNumber(), articleVersion.getGbSize(), articleVersion.getColor());
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "UPDATE shopping_cart SET quantity=" + articleVersion.getQuantity() + ", article_version_id=" + articleVersionId + " WHERE id=" + articleVersion.getId() + ";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.getArticleVersion(articleVersion.getId());
    }

    public User getUserInformation(int userId) {
        User user = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id, e_mail, firstname, lastname, newsletter, salutation, title, profile_picture, description FROM user WHERE id=" + userId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                user = new User(rs.getInt("id"), rs.getString("e_mail"), rs.getString("firstname"), rs.getString("lastname"), rs.getBoolean("newsletter"), rs.getString("salutation"),
                    rs.getString("title"), rs.getString("profile_picture"), rs.getString("description"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public UserVulnerability getUserInformationVulnerability(int userId) {
        UserVulnerability user = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id, e_mail, firstname, lastname, newsletter, salutation, title, profile_picture, description FROM user WHERE id=" + userId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                user = new UserVulnerability(rs.getInt("id"), rs.getString("e_mail"), rs.getString("firstname"), rs.getString("lastname"), rs.getBoolean("newsletter"), rs.getString("salutation"),
                        rs.getString("title"), rs.getString("profile_picture"), rs.getString("description"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Address getAddress(int addressId) {
        Address address = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            String sql = "SELECT id, name, street_house_number, address_supplement, postcode, city, country, delivery_instruction FROM address WHERE id=" + addressId;
            ResultSet rs = stmt.executeQuery(sql);
            address = new Address(addressId, rs.getString("name"), rs.getString("country"), rs.getString("street_house_number"), rs.getString("address_supplement"), rs.getString("postcode"), rs.getString("city"), rs.getString("delivery_instruction"));
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    public Payment getPayment(int orderId) {
        Payment payment = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT iban, bic, account_owner FROM sales_order WHERE id=" + orderId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            payment = new Payment(rs.getString("iban"), rs.getString("bic"), rs.getString("account_owner"));
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }

    public Order postOrder(Order order, int userId, boolean clean) {
        if(this.checkForInjection(order.getPayment().getBic()) || this.checkForInjection(order.getPayment().getIban()) || this.checkForInjection(order.getPayment().getAccountHolder())){
            return null;
        }
        int orderId = -1;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "INSERT INTO sales_order(order_date, amount, iban, bic, account_owner, user_id, address_id) " +
                "VALUES('" + order.getDate() + "', " + order.getAmount() + ", '" + order.getPayment().getIban() + "', '" + order.getPayment().getBic() + "', '" + order.getPayment().getAccountHolder() +
                "', " + userId + ", " + order.getAddress().getId() + ");";
            stmt.execute(sql);
            orderId = stmt.getGeneratedKeys().getInt(1);
            order.setOrderNumber(orderId);
            for (ArticleVersion articleVersion : order.getSpecifiedItems()) {
                this.postOrderItem(articleVersion, orderId);
            }
            this.deleteShoppingCart(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (clean) {
            this.deleteShoppingCart(userId);
        }
        return order;
    }

    public void postPayment(int OrderId, Payment payment) {
        if(this.checkForInjection(payment.getBic()) || this.checkForInjection(payment.getIban()) || this.checkForInjection(payment.getAccountHolder())){
            return;
        }
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            String sql = "INSERT INTO sales_order(iban, bic, account_owner) VALUES('" + payment.getIban() + "', '" + payment.getBic() + "','" + payment.getAccountHolder() + "');";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteShoppingCart(int userId) {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            String sql = "DELETE FROM shopping_cart WHERE user_id=" + userId + ";";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Article getArticle(int articleId) {
        Article article = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT article.id, model_name, price, operating_system, release_date, screen, resolution, valuation_sum, number_of_valuation, name FROM article INNER JOIN brand ON brand.id=article.brand_id WHERE article.id=" + articleId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                article = new Article(rs.getInt("id"), rs.getString("model_name"), rs.getDouble("price"), Math.round(rs.getInt("valuation_sum") / rs.getInt("number_of_valuation")), rs.getString("operating_system"),
                    rs.getString("release_date"), rs.getString("screen"), rs.getString("resolution"), rs.getString("name"), this.getCommentaries(articleId), this.getPictureIds(articleId));
            } else {
                return null;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }

    public Coupon getCoupon(String couponName) {
        if(this.checkForInjection(couponName)){
          return null;
        }
        Coupon coupon = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT * FROM coupon WHERE code='" + couponName + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                coupon = new Coupon(rs.getString("code"), rs.getDouble("discount_percent"), rs.getBoolean("active"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coupon;
    }

    public List<ArticleVersion> getWishlist(int userId) {
        ArrayList<ArticleVersion> articleVersionList = new ArrayList<>();
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id, quantity, article_version_id FROM wish_list WHERE user_Id=" + userId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ArticleVersion articleVersion = this.getArticleVersion(rs.getInt("article_version_id"));
                articleVersion.setId(rs.getInt("id"));
                articleVersion.setQuantity(rs.getInt("quantity"));
                articleVersionList.add(articleVersion);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articleVersionList;
    }

    public List<ArticleVersion> getShoppingCart(int userId) {
        ArrayList<ArticleVersion> articleVersionList = new ArrayList<>();
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id, quantity, article_version_id FROM shopping_cart WHERE user_id=" + userId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ArticleVersion articleVersion = this.getArticleVersion(rs.getInt("article_version_id"));
                articleVersion.setId(rs.getInt("id"));
                articleVersion.setQuantity(rs.getInt("quantity"));
                articleVersionList.add(articleVersion);

            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articleVersionList;
    }

    public Order getOrder(int orderId) {
        Order order = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT * FROM sales_order WHERE id=" + orderId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                order = new Order(orderId, null, this.getCoupon(rs.getString("coupon_code")), this.getAddress(rs.getInt("address_id")), this.getPayment(orderId), rs.getString("order_date"), rs.getDouble("amount"));
            }
            List<ArticleVersion> articles =this.getOrderItems(orderId);
            order.setSpecifiedItems(articles);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public AuthorizationType checkAuthData(String email, String password) {
        if(this.checkForInjection(email)){
            return AuthorizationType.FALSE_USER;
        }
        String hash = "";
        String rightPassword = "";
        int userId;
        AuthorizationType auth = AuthorizationType.FALSE_USER;
        AuthorizationType validPassword;
        if (this.isRealUser(this.findUserId(email))) {
            hash = this.encryptPasswordRealUser(password);
            validPassword = AuthorizationType.AUTHORIZED_USER;
        } else {
            validPassword = email.equals( "dummy@user.com" ) ? AuthorizationType.AUTHORIZED_USER : AuthorizationType.AUTHORIZED_DUMMY_USER;
            hash = this.encryptPasswordDummyUser(password);
        }
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT password FROM user WHERE e_mail='" + email + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                rightPassword = rs.getString("password");
                auth = AuthorizationType.FALSE_PASSWORD;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (hash.equals(rightPassword)) {
            return validPassword;
        } else {
            return auth;
        }
    }

    public int getUserId(String sessionKey) {
        int userid = -1;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT user.id FROM user INNER JOIN session ON user.id = session.user_id WHERE session.key='" + sessionKey + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                userid = rs.getInt("id");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userid;
    }

    public List<Order> getOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id FROM sales_order WHERE user_id=" + userId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                orders.add(this.getOrder(rs.getInt("id")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Article> getArticles(int page, String search) {
        if(this.checkForInjection(search)){
            return new ArrayList<>();
        }
        if(page==0){
            page=1;
        }
        List<Article> articles = new ArrayList<>();
        int toId = page * 9;
        int fromId = toId - 8;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            if (search.equals("")) {
                while (fromId <= toId) {
                    Article article = this.getArticle(fromId);
                    if (article != null) {
                        articles.add(article);
                    }
                    fromId++;
                }
            }
            else{
                String sql = "Select DISTINCT article.id AS articleId FROM article INNER JOIN brand ON article.brand_id = brand.id WHERE brand.name LIKE'%" + search + "%' OR article.model_name LIKE '%"+search+"%';";
                ResultSet rs = stmt.executeQuery(sql);
                for (int i = 1; i < (page - 1) * 9; i++) {
                    rs.next();
                }
                while (rs.next() && fromId < toId) {
                    articles.add(this.getArticle(rs.getInt("articleId")));
                    fromId++;
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public String getPicture(int id) {
        String base = "";
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT picture FROM picture WHERE id=" + id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                base = rs.getString("picture");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return base;
    }

    public List<Address> getAddresses(int userId) {
        List<Address> addresses = new ArrayList<Address>();
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id FROM address WHERE user_id=" + userId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                addresses.add(this.getAddress(rs.getInt("id")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public void postSession(String session, String email, String ipAddress) {
        int userId = this.findUserId(email);
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "INSERT INTO session(key, ip_address, user_id) VALUES('" + session + "','" + ipAddress + "', " + userId + ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSession(String session){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="DELETE FROM session WHERE key='"+session+"';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPassword(int userId){
        String password="";
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="SELECT password FROM user WHERE id="+userId+";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                password=rs.getString("password");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public boolean getNewsletter(int userId){
        boolean newsletter = false;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            String sql="SELECT newsletter FROM user WHERE id="+userId+";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                newsletter = rs.getBoolean("newsletter");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsletter;
    }

    public String encryptPasswordRealUser(String password) {
        String hash = Hashing.sha512().hashString(password, StandardCharsets.UTF_8).toString();
        return hash;
    }

    private String encryptPasswordDummyUser(String password) {
        String hash = Hashing.sha1().hashString(password, StandardCharsets.UTF_8).toString();
        return hash;
    }

    private boolean isRealUser(int userId) {
        boolean real = false;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT real_user FROM user WHERE id=" + userId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                real = rs.getBoolean("real_user");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return real;
    }

    private int findArticleVersionId(int articleId, int gbSize, String color) {
        int articleVersionId = -1;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id FROM article_version WHERE article_id=" + articleId + " AND gb_size=" + gbSize + " AND color='" + color + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                articleVersionId = rs.getInt("id");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articleVersionId;
    }

    private void postOrderItem(ArticleVersion articleVersion, int orderId) {
        int articleVersionId = this.findArticleVersionId(articleVersion.getArticleNumber(), articleVersion.getGbSize(), articleVersion.getColor());
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()) {
            String sql = "INSERT INTO sales_order_article_version(quantity, sales_order_id, article_version_id) " +
                "VALUES(" + articleVersion.getQuantity() + ", " + orderId + ", " + articleVersionId + ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getPictureIds(int articleId) {
        List<Integer> pictureIds = new ArrayList<>();
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id FROM picture WHERE article_id=" + articleId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                pictureIds.add(rs.getInt("id"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pictureIds;
    }

    private List<Commentary> getCommentaries(int articleId) {
        List<Commentary> comments = new ArrayList<>();
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT comment.id AS commentId, comment.comment_text, user.firstname, user.lastname, user.id AS userId, user.profile_picture FROM comment INNER JOIN user ON comment.user_id = user.id " +
                "WHERE article_id=" + articleId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                comments.add(new Commentary(rs.getInt("commentId"), rs.getString("comment_text"), rs.getInt("userId"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("profile_picture")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    private int findUserId(String email) {
        int userId = -1;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id FROM user WHERE e_mail='" + email + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                userId = rs.getInt("id");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    private ArticleVersion getArticleVersion(int articleVersionId) {
        ArticleVersion articleVersion = null;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT * FROM article_version WHERE id=" + articleVersionId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            Article article = this.getArticle(rs.getInt("article_id"));
            if (rs.next()) {
                articleVersion = new ArticleVersion(-1, rs.getInt("article_id"), rs.getInt("quantity"), rs.getInt("gb_size"), rs.getString("color"), article.getModelName(), article.getAmount(), this.getPictureIds(article.getArticleNumber()).get(0));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articleVersion;
    }

    private List<ArticleVersion> getOrderItems(int orderId) {
        ArrayList<ArticleVersion> articleVersionList = new ArrayList<>();
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql = "SELECT id, article_version_id, quantity FROM sales_order_article_version WHERE sales_order_id=" + orderId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ArticleVersion articleVersion = this.getArticleVersion(rs.getInt("article_version_id"));
                articleVersion.setQuantity(rs.getInt("quantity"));
                articleVersion.setId(rs.getInt("id"));
                articleVersionList.add(articleVersion);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articleVersionList;
    }

    private void postBrands() {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for (String sql : DatabaseQueries.brands) {
                stmt.execute("INSERT INTO brand(name) VALUES('" + sql + "');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void postDummyUsers() {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for (User u : DatabaseQueries.users) {
                stmt.execute("INSERT INTO user(e_mail, firstname, lastname, password, newsletter, salutation, title, profile_picture, real_user, description) " +
                    "VALUES('" + u.getMail() + "', '" + u.getFirstName() + "', '" + u.getLastName() + "', '" + this.encryptPasswordDummyUser(u.getPassword()) + "', " + u.isNewsletter() + ",'" +
                    u.getSalutation() + "', '" + u.getTitle() + "', '" + u.getProfilePicture() + "', " + false + ",'" + u.getDescription() + "');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void postRealUser() {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for (User u : DatabaseQueries.bidermannUser) {
                stmt.execute("INSERT INTO user(e_mail, firstname, lastname, password, newsletter, salutation, title, profile_picture, real_user, description) " +
                        "VALUES('" + u.getMail() + "', '" + u.getFirstName() + "', '" + u.getLastName() + "', '" + this.encryptPasswordRealUser(u.getPassword()) + "', " + u.isNewsletter() + ",'" +
                        u.getSalutation() + "', '" + u.getTitle() + "', '" + u.getProfilePicture() + "', " + true + ",'" + u.getDescription() + "');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void postArticles() {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            for ( ArticleDB article : DatabaseQueries.articles) {
                stmt.execute("INSERT INTO article(model_name, price, operating_system, release_date, screen, resolution, valuation_sum, number_of_valuation, brand_id) " +
                    "VALUES('" + article.getModelName() + "', " + article.getPrice() + ", '" + article.getOperatingSystem() + "', '" + article.getReleaseDate() + "', '" + article.getScreen() + "','" +
                    article.getResolution() + "', " + article.getValuation_sum() + ", " + article.getNumber_of_valuation() + ", " + article.getBrand_id() + ");");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void postArticleVersions() {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            ArrayList<Integer> articleIds = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("SELECT id FROM article");
            while (rs.next()) {  //because ResultSet resets after new execute of an SQL statement
                articleIds.add(rs.getInt("id"));
            }
            for (int id : articleIds) {
                for (String color : DatabaseQueries.colors) {
                    for (String gb : DatabaseQueries.gbSizes) {
                        stmt.execute("INSERT INTO article_version(quantity, gb_size, color, article_id) VALUES(999, '" + gb + "', '" + color + "', " + id + ");");
                    }
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkForInjection(String sql){
        if(sql ==null){
            return false;
        }
        else if(sql.contains(" DROP ") || sql.contains(" drop ") || sql.contains(" UNION ") || sql.contains(" union ")||
                sql.contains("'UNION ") || sql.contains("'union ")){ //Check for Drop Tables
            return true;
        }
        else if(sql.contains("SLEEP(") || sql.contains("BENCHMARK(") || sql.contains("SLEEP (") || sql.contains("BENCHMARK (") || sql.contains("; WAIT FOR DELAY") || sql.contains(";WAIT FOR DELAY") || sql.contains("; WAIT FOR TIME") || sql.contains(";WAIT FOR TIME")){ //Check for Timeout Attacks
            return true;
        }
        return false;
    }

    public boolean sessionExists(String session) {
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM session WHERE key='" + session+ "';");
            boolean exists = rs.next();
            rs.close();
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void postCommentDB(CommentDB comment){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
           stmt.execute("INSERT INTO comment(comment_text, article_id, user_id) VALUES('"+comment.getText()+"', "+comment.getArticleId()+","+comment.getUserId()+");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUserIdFromShoppingCartItem(int shoppingCartItemId){
        int id=-1;
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="SELECT user_id AS id FROM shopping_cart WHERE id="+shoppingCartItemId+";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                id = rs.getInt("id");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private int checkShoppingCartItemExist(int userId, int articleVersionId){
        try (Connection con = this.createConnection();
             Statement stmt = con.createStatement()){
            String sql="SELECT quantity FROM shopping_cart WHERE user_id="+userId+" AND article_version_id="+articleVersionId+";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}