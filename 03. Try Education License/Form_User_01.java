package com.example.capstone_project_01;

// region Import Android 모음
import androidx.appcompat.app.*;

import android.annotation.SuppressLint;
import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.widget.*;
import android.view.*;
import android.os.*;
// endregion

public class Form_User_01 extends AppCompatActivity
{
    // region 멤버필드 : 초기화
    DBConnect connect = new DBConnect(this); // DBConnect 객체
    String quizname = "";
    String quiznum1 = "";
    String quiznum2 = "";
    String quiznum3 = "";
    String quiznum4 = "";
    int quizanswer  = 0;

    String temp = "";   // 임시변수
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_user_01);

        // region EditText + Button Control 선언
        final EditText user_name    = findViewById(R.id.User_Name);     // 문제 내용
        final EditText user_number1 = findViewById(R.id.User_Number01); // 문제 번호 : 1번
        final EditText user_number2 = findViewById(R.id.User_Number02); // 문제 번호 : 2번
        final EditText user_number3 = findViewById(R.id.User_Number03); // 문제 번호 : 3번
        final EditText user_number4 = findViewById(R.id.User_Number04); // 문제 번호 : 4번
        final EditText user_answer  = findViewById(R.id.User_Answer);   // 문제 번호 : 정답

        final Button new_button  = findViewById(R.id.New_Button);    // 버튼 : 새 문서
        final Button save_button = findViewById(R.id.Save_Button);   // 버튼 : 저장하기
        // endregion

        // region Dialog Control 선언
        final AlertDialog.Builder build_1 = new AlertDialog.Builder(Form_User_01.this); // New_Button 관련
        final AlertDialog.Builder build_2 = new AlertDialog.Builder(Form_User_01.this); // Save_BUtton 관련
        // endregion

        // SQLiteDatabase database = connect.getWritableDatabase();
        // database.execSQL("DELETE FROM UserSubject");
        // database.execSQL("DELETE FROM Custom");

        // region New_Button : Click Event
        new_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                build_1.setMessage("현재 내용을 지울까요?");
                build_1.setTitle("알림");

                // region Yes : PositiveButton Click Event
                build_1.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        user_name.setText("");
                        user_number1.setText("");
                        user_number2.setText("");
                        user_number3.setText("");
                        user_number4.setText("");
                        user_answer.setText("");
                    }
                });
                // endregion

                // region No : NegativeButton Click Event
                build_1.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });
                build_1.create().show();
                // endregion
            }
        });
        // endregion

        // region Save_Button : Click Event
        save_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SQLiteDatabase database = connect.getWritableDatabase();

                // region 입력한 정보 변수에 저장
                try
                {
                    // region EditText에 입력한 값 변수에 저장
                    quizname = user_name.getText().toString();     // 문제 내용
                    quiznum1 = user_number1.getText().toString();  // 1번
                    quiznum2 = user_number2.getText().toString();  // 2번
                    quiznum3 = user_number3.getText().toString();  // 3번
                    quiznum4 = user_number4.getText().toString();  // 4번

                    temp = user_answer.getText().toString().trim(); // 정답 번호 -> Error
                    quizanswer = Integer.parseInt(temp);
                    // endregion
                }
                catch(NumberFormatException ignored)
                {

                }
                // endregion

                // region DB Insert + 예외처리
                if(quizname.equals("") || quiznum1.equals("") || quiznum2.equals("")
                                       || quiznum3.equals("") || quiznum4.equals("") || temp.equals(""))
                {
                    // 예외처리 : 값을 입력하지 않았을 때
                    build_2.setMessage("입력하지 않은 부분이 있어 문제를 저장할 수 없습니다.");
                    build_2.setNeutralButton("Yes", null);
                    build_2.setTitle("알림");
                    build_2.create().show();
                }
                else if(quizanswer > 4 || quizanswer < 0)
                {
                    // 예외처리 : 올바른 답의 번호를 입력하지 않았을 때
                    build_2.setMessage("올바른 답의 번호를 입력해 주세요. 번호는 1~4의 값만 유효합니다.");
                    build_2.setNeutralButton("Yes", null);
                    build_2.setTitle("알림");
                    build_2.create().show();
                }
                else
                {
                    // DB Insert
                    @SuppressLint("DefaultLocale")
                    String sql = String.format("INSERT INTO UserSubject VALUES(null, '%s', '%s', '%s', '%s', '%s', %d)",
                            quizname, quiznum1, quiznum2, quiznum3, quiznum4, quizanswer);
                    database.execSQL(sql);

                    build_2.setMessage("문제를 성공적으로 저장했습니다.");
                    build_2.setNeutralButton("Yes", null);
                    build_2.setTitle("알림");
                    build_2.create().show();
                }
                // endregion
            }
        });
        // endregion
    }


}