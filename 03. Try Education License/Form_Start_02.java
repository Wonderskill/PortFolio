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

public class Form_Start_02 extends AppCompatActivity
{
    // region 멤버필드 : 초기화
    DBConnect connect = new DBConnect(this); // DBConnect 객체
    String cname      = ""; // ID
    String cpassword1 = ""; // PW
    String cpassword2 = ""; // PW확인
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_start_02);

        // region EditText + Button Control 선언
        final EditText start_id  = findViewById(R.id.Start_ID);   // ID
        final EditText start_pw1 = findViewById(R.id.Start_PW01); // PW
        final EditText start_pw2 = findViewById(R.id.Start_PW02); // PW 확인

        final Button regi_menu = findViewById(R.id.Regi_Menu_Button); // 회원가입
        final Button exit_menu = findViewById(R.id.Exit_Menu_Button); // 나가기
        // endregion

        // region Dialog Control 선언
        final AlertDialog.Builder build_1 = new AlertDialog.Builder(Form_Start_02.this); // 회원가입 관련 Dialog
        final AlertDialog.Builder build_2 = new AlertDialog.Builder(Form_Start_02.this); // 나가기 관련 Dialog
        // endregion

        // region Button Event : 회원가입 기능
        regi_menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SQLiteDatabase w_database = connect.getWritableDatabase(); // INSERT
                SQLiteDatabase r_database = connect.getReadableDatabase(); // SELECT

                // region 입력한 정보 변수에 저장
                try
                {
                    cname      = start_id.getText().toString();   // ID
                    cpassword1 = start_pw1.getText().toString();  // PW
                    cpassword2 = start_pw2.getText().toString();  // PW확인
                }
                catch(NumberFormatException ignored)
                {

                }
                // endregion

                // region DB Select : ID 중복체크 부분에서 사용
                @SuppressLint("DefaultLocale")
                String temp = String.format("SELECT ifnull(max(cname), '') cname FROM Custom WHERE cname = '%s'", cname);
                // 검색된 데이터가 있을 때 : cname 출력
                // 검색된 데이터가 없을 때 : '' 출력
                Cursor cursor = r_database.rawQuery(temp, null);

                cursor.moveToNext();
                String idcheck = cursor.getString(0);
                // endregion

                // region DB Insert + 각종 예외처리
                if(cname.equals("") || cpassword1.equals("") || cpassword2.equals(""))
                {
                    // 예외처리 : 값을 입력하지 않았을 때
                    build_1.setMessage("입력하지 않은 부분이 있어 회원가입을 완료할 수 없습니다.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                else if(cname.equals(idcheck))
                {
                    // 예외처리 : ID 중복체크
                    build_1.setMessage("이미 존재하는 ID입니다. 해당 ID는 사용할 수 없습니다.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                else if(!cpassword1.equals(cpassword2))
                {
                    // 예외처리 : PW와 PW2 입력 값이 다를 때
                    build_1.setMessage("입력한 PW와 PW확인 값이 다르기 때문에 회원가입을 완료할 수 없습니다.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                else
                {
                    @SuppressLint("DefaultLocale")
                    String sql = String.format("INSERT INTO Custom VALUES(null, '%s', '%s')", cname, cpassword1);
                    w_database.execSQL(sql);

                    build_1.setMessage("회원가입을 성공적으로 완료했습니다. 로그인 화면으로 이동합니다.");
                    build_1.setNeutralButton("Exit", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            Form_Start_02.this.finish(); // 해당 Form 나가기
                        }
                    });
                    build_1.create().show();
                }
                // endregion
            }
        });
        // endregion

        // region Button Event : 나가기 기능
        exit_menu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                build_2.setMessage("회원가입을 그만두고 로그인 화면으로 이동할까요?");
                build_2.setTitle("알림");

                // region Dialog Positive Button
                build_2.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Form_Start_02.this.finish(); // 해당 Form 나가기
                    }
                });
                // endregion

                // region Dialog Negative Button
                build_2.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });
                build_2.create().show();
                // endregion
            }
        });
        // endregion
    }
}