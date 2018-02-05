package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.valueOf;

/**
 * Created by tom47 on 2018-01-18.
 */

public class DBhelper extends SQLiteOpenHelper {


    private SQLiteDatabase mDb;

    private SQLiteStatement insertStmt;

    private static final String DATABASE_CREATE ="CREATE TABLE COIN (ID INTEGER PRIMARY KEY AUTOINCREMENT,MONEY INTEGER,TRUE INTEGER)";

    public static final int DATABASE_VERSION = 1;

    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);

        Log.d("asd", "coindb만들음");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void insert(String COIN) {

        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO COIN VALUES(null, '" +COIN+"', '1');");
        Log.d("asd","DB에"+COIN);
        db.close();
    }

    public String getResult() {

        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COIN where id='1'", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(1);
            Log.d("asd","결과 "+result);
        }
        return result;
    }

    public void update(int money) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE COIN SET Money=" + money + " WHERE id='1';");
        Log.d("asd","구매완료");
        db.close();
    }

    public int checkfirst() {

        Log.d("asd","비교중");
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM COIN where id='1'", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(2);
            Log.d("asd",result);
        }
        if(result.equals("1")) {
            Log.d("asd","비교 1");
            return 1;
        }else{
            Log.d("asd","비교 0");
            return 0;
        }
    }

    public void buyitem(int price){
        Fragmentmain frgm = new Fragmentmain();
        int value;
        SQLiteDatabase db = getReadableDatabase();
        String DBback = "";
        int DBbackint;
        String result = "";
        Cursor cursor = db.rawQuery("SELECT * FROM COIN where id='1'", null);

        while (cursor.moveToNext()) {
            DBback += cursor.getString(1);
            Log.d("asd",result);
        }
        DBbackint = Integer.parseInt(DBback);

        value =  DBbackint - price;

        update(value);


    }
}
