package models;

/**
 * Created by ihsanberk on 14.9.2016.
 */
public class ListItem {
    int id=0;
    Match match = new Match();
    String date="00.00.00.00.00";

    public ListItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
