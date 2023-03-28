package de.fhws.biedermann.webshop.models;

public class Nletter implements IModel {
    String email;

    public Nletter(){

    }

    public Nletter(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format( "Nletter( email: %s )\n", email );
    }
}
