package com.example.ynov2020_devmobile_scheldule;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

public class Task {
    private String titre;
    private Time duree;
    private String desc;
    private Date[] recurrence;
    private int nbrRappels;
    private boolean estFinie;

    public Task() {
        this.titre = "Titre";
        this.duree = new Time(1,0,0);
        this.desc = "Une tache";
        this.recurrence = null;
        this.nbrRappels = 1;
        this.estFinie = false;
    }

    public Task(String pfTitre, Time pfDuree,String pfDesc, Date[] pfRecurrence, int pfNbrRappels){
        this.titre = pfTitre;
        this.duree = pfDuree;
        this.desc = pfDesc;
        this.recurrence = pfRecurrence;
        this.nbrRappels = pfNbrRappels;
        this.estFinie = false;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public Date[] getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Date[] recurrence) {
        this.recurrence = recurrence;
    }

    public int getNbrRappels() {
        return nbrRappels;
    }

    public void setNbrRappels(int nbrRappels) {
        this.nbrRappels = nbrRappels;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isEstFinie() {
        return estFinie;
    }

    public void setEstFinie() {
        this.estFinie = !this.estFinie;
    }
}
