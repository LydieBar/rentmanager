package com.epf.rentmanager.modeles;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Client {

    private long id;
    private String prenom;
    private String nom;
    private String email;
    private LocalDate naissance;

    public Client() {
    }




    public long getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    public Client(long id) {
        this.id = id;
    }

    public Client(long id, String prenom, String nom, String email, LocalDate naissance) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.naissance = naissance;
    }

    public Client(String prenom, String nom, String email, LocalDate naissance) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.naissance = naissance;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
    public static boolean isLegal(Client client) {
        return client.getNaissance().until(LocalDate.now(), ChronoUnit.YEARS) >= 18;
    }

}
