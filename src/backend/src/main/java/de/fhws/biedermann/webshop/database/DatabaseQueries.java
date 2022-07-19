package de.fhws.biedermann.webshop.database;

import de.fhws.biedermann.webshop.models.User;
import de.fhws.biedermann.webshop.models.modelsdb.ArticleDB;
import de.fhws.biedermann.webshop.models.modelsdb.CommentDB;

public class DatabaseQueries {

    public static String[] deleteDatabase= new String[]{"DROP TABLE address;", "DROP TABLE article_version;",
            "DROP TABLE comment;", "DROP TABLE sales_order;", "DROP TABLE sales_order_article_version", "DROP TABLE session;",
            "DROP TABLE shopping_cart;", "DROP TABLE user;", "DROP TABLE wish_list;"};
    //missing: picture, article, brand, coupon

    public static String[] createDatabase = new String[]{
           "CREATE TABLE article_version(\n" +
                   "    id INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                   "    quantity INTEGER,\n" +
                   "    gb_size INTEGER,\n" +
                   "    color TEXT,\n" +
                   "    article_id INTEGER,\n" +
                   "    FOREIGN KEY(article_id) REFERENCES article(id)\n" +
                   ");",
            "CREATE TABLE user(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                    "    e_mail TEXT,\n" +
                    "    firstname TEXT,\n" +
                    "    lastname TEXT,\n" +
                    "    password TEXT,\n" +
                    "    newsletter INTEGER,\n" +
                    "    salutation TEXT,\n" +
                    "    title TEXT,\n" +
                    "    profile_picture TEXT,\n" +
                    "    real_user INTEGER, description TEXT\n" +
                    ");",
            "CREATE TABLE address(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    street_house_number TEXT,\n" +
                    "    postcode TEXT,\n" +
                    "    address_supplement TEXT,\n" +
                    "    city TEXT,\n" +
                    "    country TEXT,\n" +
                    "    name TEXT,\n" +
                    "    delivery_instruction TEXT,\n" +
                    "    user_id INTEGER,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id)\n" +
                    ");",
            "CREATE TABLE session(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    key Text,\n" +
                    "    ip_address Text,\n" +
                    "    user_id INTEGER,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id)\n" +
                    ");",
            "CREATE TABLE wish_list(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    quantity INTEGER,\n" +
                    "    user_id INTEGER,\n" +
                    "    article_version_id INTEGER,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id),\n" +
                    "    FOREIGN KEY (article_version_id) REFERENCES article_version(id)\n" +
                    ");",
            "CREATE TABLE shopping_cart(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    quantity INTEGER,\n" +
                    "    user_id INTEGER,\n" +
                    "    article_version_id INTEGER,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id),\n" +
                    "    FOREIGN KEY (article_version_id) REFERENCES article_version(id)\n" +
                    ");",
            "CREATE TABLE comment(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    comment_text TEXT,\n" +
                    "    article_id INTEGER,\n" +
                    "    user_id INTEGER,\n" +
                    "    FOREIGN KEY (article_id) REFERENCES article(id),\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id)\n" +
                    ");",
            "CREATE TABLE sales_order (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    order_date TEXT,\n" +
                    "    amount REAL,\n" +
                    "    iban TEXT,\n" +
                    "    bic TEXT,\n" +
                    "    account_owner TEXT,\n" +
                    "    user_id INTEGER,\n" +
                    "    address_id INTEGER,\n" +
                    "    coupon_code,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id),\n" +
                    "    FOREIGN KEY (address_id) REFERENCES address(id),\n" +
                    "    FOREIGN KEY (coupon_code) REFERENCES coupon(code)\n" +
                    ");",
            "CREATE TABLE sales_order_article_version(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    quantity INTEGER,\n" +
                    "    sales_order_id INTEGER,\n" +
                    "    article_version_id INTEGER,\n" +
                    "    FOREIGN KEY (sales_order_id) REFERENCES sales_order(id),\n" +
                    "    FOREIGN KEY (article_version_id) REFERENCES article_version(id)\n" +
                    ");"
    };

    public static String[] deleteDatabaseInProduction= new String[]{"DROP TABLE address;", "DROP TABLE article_version;",
            "DROP TABLE comment;", "DROP TABLE sales_order;", "DROP TABLE sales_order_article_version",
            "DROP TABLE shopping_cart;", "DROP TABLE wish_list;"};
    //missing: picture, article, brand, coupon, user, session

    public static String[] createDatabaseInProduction = new String[]{
            "CREATE TABLE article_version(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                    "    quantity INTEGER,\n" +
                    "    gb_size INTEGER,\n" +
                    "    color TEXT,\n" +
                    "    article_id INTEGER,\n" +
                    "    FOREIGN KEY(article_id) REFERENCES article(id)\n" +
                    ");",
            "CREATE TABLE address(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    street_house_number TEXT,\n" +
                    "    postcode TEXT,\n" +
                    "    address_supplement TEXT,\n" +
                    "    city TEXT,\n" +
                    "    country TEXT,\n" +
                    "    name TEXT,\n" +
                    "    delivery_instruction TEXT,\n" +
                    "    user_id INTEGER,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id)\n" +
                    ");",
            "CREATE TABLE wish_list(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    quantity INTEGER,\n" +
                    "    user_id INTEGER,\n" +
                    "    article_version_id INTEGER,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id),\n" +
                    "    FOREIGN KEY (article_version_id) REFERENCES article_version(id)\n" +
                    ");",
            "CREATE TABLE shopping_cart(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    quantity INTEGER,\n" +
                    "    user_id INTEGER,\n" +
                    "    article_version_id INTEGER,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id),\n" +
                    "    FOREIGN KEY (article_version_id) REFERENCES article_version(id)\n" +
                    ");",
            "CREATE TABLE comment(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    comment_text TEXT,\n" +
                    "    article_id INTEGER,\n" +
                    "    user_id INTEGER,\n" +
                    "    FOREIGN KEY (article_id) REFERENCES article(id),\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id)\n" +
                    ");",
            "CREATE TABLE sales_order (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    order_date TEXT,\n" +
                    "    amount REAL,\n" +
                    "    iban TEXT,\n" +
                    "    bic TEXT,\n" +
                    "    account_owner TEXT,\n" +
                    "    user_id INTEGER,\n" +
                    "    address_id INTEGER,\n" +
                    "    coupon_code,\n" +
                    "    FOREIGN KEY (user_id) REFERENCES user(id),\n" +
                    "    FOREIGN KEY (address_id) REFERENCES address(id),\n" +
                    "    FOREIGN KEY (coupon_code) REFERENCES coupon(code)\n" +
                    ");",
            "CREATE TABLE sales_order_article_version(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    quantity INTEGER,\n" +
                    "    sales_order_id INTEGER,\n" +
                    "    article_version_id INTEGER,\n" +
                    "    FOREIGN KEY (sales_order_id) REFERENCES sales_order(id),\n" +
                    "    FOREIGN KEY (article_version_id) REFERENCES article_version(id)\n" +
                    ");"
    };

    public static String[] brands = new String[]{"Samsung", "Apple", "Xiaomi","Sony"};

    public static User[] users = new User[]{new User(1, "Test1@test.de", "test", "test", false, "", "", "", "Herzlichen Glückwunsch, du hast den DAU gefunden!", "123456789"),
    new User(2, "Test2@User.de", "test", "test", false, "", "", "", "Herzlichen Glückwunsch, du hast den 2. DAU gefunden!", "test123456789"),
            new User(3, "Test3@User.de", "test", "test", false, "", "", "", "Herzlichen Glückwunsch, du hast den 3. DAU gefunden!", "0112358132134"),
    new User(4, "dummy@user.com", "Dummy", "User", false, "","","","Herzlichen Glückwunsch, du hast den Dummy User gefunden!","MyPasswordIsSafe"),
            new User(5, "admin", "Bad", "Admin", false, "","","","Herzlichen Glückwunsch, du hast den \"schlechten Admin\" gefunden!","admin"),
            new User(6, "Walter.Schmitt@gegglemail.com", "Walter", "Schmitt", false, "","","","Hallo ich bin der Walter","Xv96!54PoUaCvL"),
            new User(7, "Zufall.Rainer@fmx.de", "Rainer", "Zufall", false, "","","","Was ein Zufall, dass wir uns hier sehen :)","VBU846?ZcN!"),
            new User(8, "XXDestroyerXX@mab.de", "Leon", "Münch", false, "","","","...","Przu8964!!BcV"),};

    public static User[] bidermannUser = new User[]{new User(9, "biedermann", "Admin", "Admin", false, "", "", "", "Hallo Herr Biedermann", "Cv3!56PQ76mLh7?")};

    public static ArticleDB[] articles = new ArticleDB[]{new ArticleDB("Galaxy Z Fold3", 1393.00, "Android 11.0, ONE UI 3.1.1, KNOX 3.7", "01.01.2022", "Dynamic AMOLED", "2.208x1.768", 100, 25, 1),
    new ArticleDB("Galaxy S22 Ultra", 1449.00, "Android 12, One UI 4.1, Knox 3.8", "05.12.2021", "Dynamic AMOLED, Quad HD+", "3.088x1.440", 90, 30, 1),
    new ArticleDB("Galaxy 21 Ultra", 1279.87, "Android 11.0, OneUI 3.1, KNOX 3.7", "28.02.2019", "Dynamic AMOLED, Quad HD+", "3.200x1.440", 5, 1, 1),
    new ArticleDB("Galaxy Z Flip3", 799.00,"Android 11.0, ONE UI 3.1.1, KNOX 3.7", "17.04.2020", "Super AMOLED", "2.640x1.080", 25, 7, 1),
    new ArticleDB("Galaxy Xcover", 499.00, "Android 10 AOS", "06.08.2017", "LCD-TFT", "2.340x1.080", 2,1,1),
    new ArticleDB("Iphone 13 Pro", 1749.00, "ios 15", "17.09.2021", "Super Retina XDR Display mit ProMotion", "2.778x1.284", 16,4,2),
    new ArticleDB("Iphone 12 Pro", 1379.00, "ios 14", "17.08.2020", "Super Retina XDR, OLED", "1.856x884", 5,1,2),
    new ArticleDB("Iphone 11", 529.99, "ios 13", "08.06.2019", "Liquid Retina HD, IPS, True Tone Display", "1.792x828", 4, 1, 2),
    new ArticleDB("Iphone SE NE", 489.95, "ios 13", "06.06.2018", "Retina HD Display", "1.334x750", 6, 2, 2),
    new ArticleDB("Redmi Note 11", 219.00, "Android 11, MIUI 13", "18.09.2019", "AMOLED FHD+", "2.400x1.080", 2, 1, 3),
    new ArticleDB("Redmi Note 9 Pro", 189.00, "Android 10 + MIUI 11", "04.11.2022", "IPS", "2.400x1.080", 5, 2, 3),
    new ArticleDB("Redmi 9A", 107.99, "Android 10 + MIUI 11", "31.12.2020", "DotDrop-Display", "1.600x720", 4, 1, 3),
    new ArticleDB("Poco X4 Pro", 399.00, "Android 11, MIUI 13", "21.10.2021", "AMOLED, FHD+", "2.400x1.080", 4, 1, 3),
    new ArticleDB("Xperia 10 III", 399.00, "Android 11", "16.06.2018", "CinemaWide Full HD+ OLED Display", "2.520x1.080", 3, 1, 4),
    new ArticleDB("Xperia Pro-I", 199.00, "Android 11", "24.11.2022", "CinemaWide 4K 120Hz HDR OLED-Display", "3.840x1.644", 8,2,4),
    new ArticleDB("Xperia 5 III", 919.00, "Android 12", "05.05.2022", "CinemaWide FHD+ HDR OLED-Display", "2.520x1.080", 5, 1, 4)};

    public static String[] colors = new String[]{"red", "blue", "green", "black", "white"};

    public static String[] gbSizes = new String[]{"128", "256", "512"};

    public static String[] deleteDatabaseAdminPanel = new String[]{"DROP TABLE ranking;","DROP TABLE session;"};

    public static String[] createDatabaseAdminPanel = new String[]{"CREATE TABLE ranking(\n" +
            "    ip_address TEXT PRIMARY KEY,\n" +
            "    sql_injection INTEGER DEFAULT 0,\n" +
            "    blind_sql_injection INTEGER DEFAULT 0,\n" +
            "    email_without_at INTEGER DEFAULT 0,\n" +
            "    xss INTEGER DEFAULT 0,\n" +
            "    profile_picture INTEGER DEFAULT 0,\n" +
            "    html_comment_user INTEGER DEFAULT 0,\n" +
            "    price_order_bug INTEGER DEFAULT 0,\n" +
            "    guess_user_login INTEGER DEFAULT 0,\n" +
            "    guess_coupon INTEGER DEFAULT 0,\n" +
            "    delete_user INTEGER DEFAULT 0,\n" +
            "    comment_xss INTEGER DEFAULT 0,\n" +
            "    login_brute_force INTEGER DEFAULT 0,\n" +
            "    hash_user INTEGER DEFAULT 0,\n" +
            "    security_misconfiguration INTEGER DEFAULT 0\n" +
            ");\n",
            "CREATE TABLE session(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    key TEXT,\n" +
                    "    admin_username TEXT,\n" +
                    "    FOREIGN KEY (admin_username) REFERENCES admin(username)\n" +
                    ")"
    };

    public static String[] deleteRanking = new String[]{"DROP TABLE ranking;"};

    public static String[] createRanking = new String[]{"CREATE TABLE ranking(\n" +
            "    ip_address TEXT PRIMARY KEY,\n" +
            "    sql_injection INTEGER DEFAULT 0,\n" +
            "    blind_sql_injection INTEGER DEFAULT 0,\n" +
            "    email_without_at INTEGER DEFAULT 0,\n" +
            "    xss INTEGER DEFAULT 0,\n" +
            "    profile_picture INTEGER DEFAULT 0,\n" +
            "    html_comment_user INTEGER DEFAULT 0,\n" +
            "    price_order_bug INTEGER DEFAULT 0,\n" +
            "    guess_user_login INTEGER DEFAULT 0,\n" +
            "    guess_coupon INTEGER DEFAULT 0,\n" +
            "    delete_user INTEGER DEFAULT 0,\n" +
            "    comment_xss INTEGER DEFAULT 0,\n" +
            "    login_brute_force INTEGER DEFAULT 0,\n" +
            "    hash_user INTEGER DEFAULT 0,\n" +
            "    security_misconfiguration INTEGER DEFAULT 0\n" +
            ");"
    };

    public static CommentDB[] comments = new CommentDB[]{new CommentDB(1, 6, "geiles Teil!"), new CommentDB(1, 7, "gut diese"), new CommentDB(1, 8, "Bestes Gerät, dass ich je hatte"),
            new CommentDB(2, 6, "Wie der Name schon sagt ULTRA"),new CommentDB(2, 7, "kam leider mit Displayschaden an"),new CommentDB(2, 8, "Unser Sohn findet es super"),
            new CommentDB(3, 6, "Top"),new CommentDB(3, 7, "Schlechter als der Vorgänger"),new CommentDB(3, 8, "is ok"),
            new CommentDB(4, 6, "flippt sich gut <3"),new CommentDB(4, 7, "hab meines Flippi genannt"),new CommentDB(4, 8, "Nicht geeignet für flip cup"), new CommentDB(4, 8, "ich flippe aus wie geil!"),
            new CommentDB(5, 6, "Schlechtes Gerät"),new CommentDB(5, 7, "Naja"),new CommentDB(5, 8, "da nehm ich lieber mein alten Nokia"),
            new CommentDB(6, 6, "Android ist besser"),new CommentDB(6, 7, "geiles Teil!"),new CommentDB(6, 8, "Super"),
            new CommentDB(7, 6, "geiles Teil!"),new CommentDB(7, 7, "Passt"),new CommentDB(7, 8, "absolut genial"),
            new CommentDB(8, 6, "Super Smartphone!"),new CommentDB(8, 7, "!"),new CommentDB(8, 8, "Super Kamera"),
            new CommentDB(9, 6, "hab gleich 10 gekauft"),new CommentDB(9, 7, "gutes Gerät"),new CommentDB(9, 8, "passt scho"),
            new CommentDB(10, 6, "geiles Teil!"),new CommentDB(10, 7, "HAMMER!"),new CommentDB(10, 8, "bestes Redmi"),
            new CommentDB(11, 6, "2. bestes Redmi"),new CommentDB(11, 7, "Besser als Iphone!"),new CommentDB(11, 8, "Hat mein Leben bereichert!"),
            new CommentDB(12, 6, "3. bestes Redmi"),new CommentDB(12, 7, "Meine Oma liebt es"),new CommentDB(12, 8, "Ist das das Internet?"),
            new CommentDB(13, 6, "besser als Poco Domäne!"),new CommentDB(13, 7, "Absolut bestes Handy der Welt"),new CommentDB(13, 8, "Läuft"),
            new CommentDB(14, 6, "Es ist eine Offenbarung!"),new CommentDB(14, 7, "bestes Sony"),new CommentDB(14, 8, "geiles Teilchen!"),
            new CommentDB(15, 6, "Habs nicht gekauft aber gutes Gerät"),new CommentDB(15, 7, "kam kaputt an"),new CommentDB(15, 8, "hatte beim Tiefseetauchen gelich nen Wasserschaden :("),
            new CommentDB(16, 6, "Nicht als Football geeignet"),new CommentDB(16, 7, ":D"),new CommentDB(16, 8, "Super Briefbeschwerer!")
    };
}

