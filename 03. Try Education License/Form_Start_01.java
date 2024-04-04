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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static android.os.StrictMode.setThreadPolicy;
// endregion

public class Form_Start_01 extends AppCompatActivity
{
    // region 멤버필드 : 초기화
    DBConnect connect = new DBConnect(this); // DBConnect 객체
    String cname      = ""; // ID
    String cpassword  = ""; // PW

    // 소켓 통신 관련
    Socket socket = null;
    String server = "10.101.74.154";   // 서버 IP
    int    port   = 9000; // PORT

    PrintWriter streamOut   = null; // 서버 송신 Stream
    BufferedReader streamIn = null; // 서버 수신 Stream
    // endregion

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_start_01);

        // region EditText + Button Control 선언
        final EditText start_id = findViewById(R.id.Start_ID);   // ID
        final EditText start_pw = findViewById(R.id.Start_PW); // PW

        Button main_menu = findViewById(R.id.Main_Menu_Button); // 로그인
        Button regi_menu = findViewById(R.id.Regi_Menu_Button); // 회원가입
        // endregion

        // region Dialog Control 선언
        final AlertDialog.Builder build_1 = new AlertDialog.Builder(Form_Start_01.this); // 로그인 관련 Dialog
        // endregion

        // region Button Event : form_menu_01로 이동
        main_menu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                SQLiteDatabase database = connect.getReadableDatabase(); // SELECT

                // region 입력한 정보 변수에 저장
                try
                {
                    cname = start_id.getText().toString();       // ID
                    cpassword  = start_pw.getText().toString();  // PW
                }
                catch(NumberFormatException ignored)
                {

                }
                // endregion

                // region DB Select : ID 확인 부분에서 사용
                @SuppressLint("DefaultLocale")
                String temp = String.format("SELECT ifnull(max(cname), '') cname, ifnull(max(cpassword), '') cpassword " +
                                            "FROM Custom WHERE cname = '%s'", cname);
                // 검색된 데이터가 있을 때 : cname 출력
                // 검색된 데이터가 없을 때 : '' 출력
                Cursor cursor = database.rawQuery(temp, null);

                cursor.moveToNext();
                String idcheck = cursor.getString(0);
                String pwcheck = cursor.getString(1);
                // endregion

                // region SELECT + 예외처리
                if(cname.equals("") || cpassword.equals(""))
                {
                    // 예외처리 : ID 및 PW가 공백일 때
                    build_1.setMessage("로그인할 수 없습니다. ID 및 PW를 입력해주세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                else if(!cname.equals(idcheck) || !cpassword.equals(pwcheck))
                {
                    // 예외처리 : ID 및 PW가 다를 때
                    build_1.setMessage("로그인할 수 없습니다. ID 또는 PW를 확인해주세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), Form_Main.class);
                    startActivity(intent);
                    // endregion
                }
                // endregion
            }
        });
        // endregion

        // region Button Event : form_start_02로 이동
        regi_menu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_Start_02.class);
                startActivity(intent);
            }
        });
        // endregion
    }
}