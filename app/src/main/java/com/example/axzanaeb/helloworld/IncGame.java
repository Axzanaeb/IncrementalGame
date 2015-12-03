package com.example.axzanaeb.helloworld;

/**
 * Created by axzanaeb on 12/3/15.
 */
public class IncGame {
    int villagers = 0, farmProg, lumbProg, stoneProg, goldProg;
    int villagers_breed_rate = 6; //      p/min

    public void addVillager() {
        villagers++;
    }

    public int getVillagers() {
        return villagers;
    }

    public int getVillagers_breed_rate() {
        return villagers_breed_rate;
    }

    public void setVillagersDistribution(int farmProg, int lumbProg, int stoneProg, int goldProg) {
        this.farmProg=farmProg;
        this.lumbProg=lumbProg;
        this.stoneProg=stoneProg;
        this.goldProg=goldProg;
    }
}
