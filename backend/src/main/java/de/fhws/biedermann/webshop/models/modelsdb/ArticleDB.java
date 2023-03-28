package de.fhws.biedermann.webshop.models.modelsdb;

public class ArticleDB {


    private String modelName;
    private double price;
    private String operatingSystem;
    private String releaseDate;
    private String screen;
    private String resolution;
    private int valuation_sum;
    private int number_of_valuation;
    private int brand_id;

    public ArticleDB(String modelName, double price, String operatingSystem, String releaseDate, String screen, String resolution, int valuation_sum, int number_of_valuation, int brand_id) {

        this.modelName = modelName;
        this.price = price;
        this.operatingSystem = operatingSystem;
        this.releaseDate = releaseDate;
        this.screen = screen;
        this.resolution = resolution;
        this.valuation_sum = valuation_sum;
        this.number_of_valuation = number_of_valuation;
        this.brand_id = brand_id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getValuation_sum() {
        return valuation_sum;
    }

    public void setValuation_sum(int valuation_sum) {
        this.valuation_sum = valuation_sum;
    }

    public int getNumber_of_valuation() {
        return number_of_valuation;
    }

    public void setNumber_of_valuation(int number_of_valuation) {
        this.number_of_valuation = number_of_valuation;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
