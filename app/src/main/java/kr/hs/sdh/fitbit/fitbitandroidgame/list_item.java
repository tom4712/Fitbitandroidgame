package kr.hs.sdh.fitbit.fitbitandroidgame;

/**
 * Created by Resten on 2018-02-21.
 */

public class list_item {
    private String value;
    private String date;


    public list_item(String value, String date){
        this.value = value;
        this.date = date;


    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }



    public void setValue(String value) {
        this.value = value;
    }
}
