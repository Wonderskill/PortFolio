package com.example.capstone_project_01;

// region Import Android 모음
import android.content.*;
import android.database.sqlite.*;
// endregion

public class DBConnect extends SQLiteOpenHelper
{
    // region 멤버필드
    public static final int version = 50; // Setting Database Version : DB를 수정할 때마다 version을 바꿀 것
    // endregion

    // region Setting Database
    public DBConnect(Context context)
    {
        super(context, "2022-2nd", null, version); // Setting Database
    }
    // endregion

    // region CREATE Table
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL("CREATE TABLE ExamSubject(QuizId INTEGER PRIMARY KEY," +
                                                  "QuizName TEXT NOT NULL," +
                                                  "QuizNum1 TEXT NOT NULL," +
                                                  "QuizNum2 TEXT NOT NULL," +
                                                  "QuizNum3 TEXT NOT NULL," +
                                                  "QuizNum4 TEXT NOT NULL," +
                                                  "QuizAnswer INTEGER NOT NULL)");

        database.execSQL("CREATE TABLE UserSubject(UserQuizId INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                  "UserQuizName TEXT NOT NULL," +
                                                  "UserQuizNum1 TEXT NOT NULL," +
                                                  "UserQuizNum2 TEXT NOT NULL," +
                                                  "UserQuizNum3 TEXT NOT NULL," +
                                                  "UserQuizNum4 TEXT NOT NULL," +
                                                  "UserQuizAnswer INTEGER NOT NULL)");

        database.execSQL("CREATE TABLE Custom(cid INTEGER PRIMARY KEY AUTOINCREMENT, cname TEXT NOT NULL, cpassword TEXT NOT NULL)");
    }
    // endregion

    // region DROP Table
    @Override
    public void onUpgrade(SQLiteDatabase database, int old_version, int new_version)
    {
        if(new_version == 45)
        {
            database.execSQL("DROP TABLE IF EXISTS ExamSubject");
            database.execSQL("DROP TABLE IF EXISTS UserSubject");
            database.execSQL("DROP TABLE IF EXISTS Custom");
            onCreate(database);
        }
    }
    // endregion
}
