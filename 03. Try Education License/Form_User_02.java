package com.example.capstone_project_01;

// region Import Android 모음
import androidx.appcompat.app.*;

import android.annotation.SuppressLint;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.*;
import android.view.*;
import android.os.*;
// endregion

public class Form_User_02 extends AppCompatActivity
{
    // region 멤버필드 : 초기화
    DBConnect connect = new DBConnect(this); // DBConnect 객체

    String[] result;    // 저장된 데이터를 담을 배열

    int userquizid = 0;
    int count = 0;      // 저장된 데이터 개수
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_user_02);

        // region ListView Control 선언
        final ListView user_list = findViewById(R.id.User_List);    // 생성된 문제 ListView
        // endregion

        // region Dialog Control 선언
        final AlertDialog.Builder build_1 = new AlertDialog.Builder(Form_User_02.this); // user_list 관련
        // endregion

        // region Database 사용 및 count, result 설정
        final SQLiteDatabase database = connect.getReadableDatabase();
        final Cursor cursor     = database.rawQuery("SELECT UserQuizid, UserQuizName FROM UserSubject", null);
        count  = cursor.getCount();
        result = new String[count];
        // endregion

        // region 저장된 데이터 개수만큼 result[]에 저장
        for(int i = 0; i < count; i++)
        {
            cursor.moveToNext();
            String quiz_id = cursor.getString(0);
            String quiz_name = cursor.getString(1);
            result[i] = quiz_id + ". " + quiz_name;
        }
        // endregion

        // region ListView 적용
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, result); // this, 출력모양, 배열

        user_list.setAdapter(adapter);  // ListView에 저장
        // endregion

        // region ListView : Click Event
        user_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // region Datebase 사용
                @SuppressLint("DefaultLocale")
                // String sql = String.format("SELECT QuizName FROM ExamSubject WHERE QuizId = %d + 1", i); // TEST CODE
                String sql = String.format("SELECT UserQuizName, UserQuizNum1, UserQuizNum2, UserQuizNum3, UserQuizNum4 " +
                                           "FROM UserSubject WHERE UserQuizId = %d + 1", i);
                Cursor r_cursor = database.rawQuery(sql, null);
                // endregion

                // region SELECT
                r_cursor.moveToNext();
                String userquiz_name = r_cursor.getString(0);
                String userquiz_num1 = r_cursor.getString(1);
                String userquiz_num2 = r_cursor.getString(2);
                String userquiz_num3 = r_cursor.getString(3);
                String userquiz_num4 = r_cursor.getString(4);
                // endregion

                // region SELECT 결과 Dialog 출력
                build_1.setNeutralButton("Yes", null);
                build_1.setMessage(userquiz_name + "\n\n\n" + "①\t" + userquiz_num1 + "\n" + "②\t" + userquiz_num2 + "\n"
                                                            + "③\t" + userquiz_num3 + "\n" + "④\t" + userquiz_num4);
                build_1.create().show();
                // endregion
            }
        });
        // endregion
    }
}