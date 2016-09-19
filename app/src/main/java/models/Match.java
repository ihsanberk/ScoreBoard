package models;

/**
 * Created by ihsanberk on 13.9.2016.
 */
public class Match {
    String pOneName="Oyuncu A";
    String pTwoName="Oyuncu C";
    int handCount=0;
    int pOneScore=0;
    int pTwoScore=0;
    String pOneAverage="0.000";
    String pTwoAverage="0.000";
    int pOneMax=0;
    int pTwoMax=0;


    public Match() {
    }

    public String getpOneName() {
        return pOneName;
    }

    public void setpOneName(String pOneName) {
        this.pOneName = pOneName;
    }

    public String getpTwoName() {
        return pTwoName;
    }

    public void setpTwoName(String pTwoName) {
        this.pTwoName = pTwoName;
    }

    public int getHandCount() {
        return handCount;
    }

    public void setHandCount(int handCount) {
        this.handCount = handCount;
    }

    public int getpOneScore() {
        return pOneScore;
    }

    public void setpOneScore(int pOneScore) {
        this.pOneScore = pOneScore;
    }

    public int getpTwoScore() {
        return pTwoScore;
    }

    public void setpTwoScore(int pTwoScore) {
        this.pTwoScore = pTwoScore;
    }

    public String getpOneAverage() {
        return pOneAverage;
    }

    public void setpOneAverage(String pOneAverage) {
        this.pOneAverage = pOneAverage;
    }

    public String getpTwoAverage() {
        return pTwoAverage;
    }

    public void setpTwoAverage(String pTwoAverage) {
        this.pTwoAverage = pTwoAverage;
    }

    public int getpOneMax() {
        return pOneMax;
    }

    public void setpOneMax(int pOneMax) {
        this.pOneMax = pOneMax;
    }

    public int getpTwoMax() {
        return pTwoMax;
    }

    public void setpTwoMax(int pTwoMax) {
        this.pTwoMax = pTwoMax;
    }
}
