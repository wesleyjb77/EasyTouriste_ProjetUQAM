package com.ingwesley.www.easytouriste_true.models;

public class ModelEndroits {
    private String id;
    private String nom;
    private String illustration;
    private String description;
    private String adresse;
    private String telephone;
    private String email;
    private String stars;
    private String prix;
    private String nom_cat;

    public ModelEndroits(String id, String nom, String illustration, String description, String adresse, String telephone, String email, String stars,String prix,String nom_cat) {
        this.id = id;
        this.nom = nom;
        this.illustration = illustration;
        this.description = description;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.stars = stars;
        this.nom_cat=nom_cat;
        this.prix=prix;
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVille() {
        return stars;
    }

    public void setVille(String stars) {
        this.stars = stars;
    }
    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getNom_cat() {
        return nom_cat;
    }

    public void setId_cat(String id_cat) {
        this.nom_cat = nom_cat;
    }
}
