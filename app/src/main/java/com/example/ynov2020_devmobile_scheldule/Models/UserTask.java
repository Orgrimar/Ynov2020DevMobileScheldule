package com.example.ynov2020_devmobile_scheldule.Models;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

public class UserTask {
    private long id;
    private String titre;
    private Time duree;
    private String desc;
    private Date date;
    private long nbrRappels;
    private boolean recurrence;
    private boolean estFinie;
    private int idEnfant;

    public UserTask() {
        this.id = 0;
        this.titre = "";
        this.duree = new Time(3600);
        this.desc = "Une tache";
        this.date = new Date();
        this.nbrRappels = 1;
        this.estFinie = false;
    }

    public UserTask(String pfTitre) {
        this.id = 0;
        this.titre = (String) pfTitre;
        this.duree = new Time(3600);
        this.desc = "Une description de tâche";
        this.date = new Date();
        this.nbrRappels = 1;
        this.recurrence = false;
        this.estFinie = false;
    }

    public UserTask(String pfTitre, Time pfDuree, String pfDesc, long pfNbrRappels){
        this.id = 0;
        this.titre = pfTitre;
        this.duree = pfDuree;
        this.desc = pfDesc;
        this.date = new Date();
        this.nbrRappels = pfNbrRappels;
        this.recurrence = false;
        this.estFinie = false;
    }

    public UserTask(long pfId, String pfTitre, Time pfDuree, String pfDesc, long pfNbrRappels, boolean pfState){
        this.id = pfId;
        this.titre = pfTitre;
        this.duree = pfDuree;
        this.desc = pfDesc;
        this.date = new Date();
        this.nbrRappels = pfNbrRappels;
        this.recurrence = false;
        this.estFinie = pfState;
    }

    public UserTask(long pfId, String pfTitre, Time pfDuree, String pfDesc, Date pfDate, long pfNbrRappels, boolean pfRecurrence, boolean pfState){
        this.id = pfId;
        this.titre = pfTitre;
        this.duree = pfDuree;
        this.desc = pfDesc;
        this.date = pfDate;
        this.nbrRappels = pfNbrRappels;
        this.recurrence = pfRecurrence;
        this.estFinie = pfState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNbrRappels(long nbrRappels) {
        this.nbrRappels = nbrRappels;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getNbrRappels() {
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

    public void setEstFinie() {
        this.estFinie = !this.estFinie;
    }

    public boolean getEstFinie() {
        return estFinie;
    }
}
