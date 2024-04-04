package com.example.capstone_project_01;

// region Import Android 모음
import androidx.appcompat.app.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.os.*;
// endregion

public class Form_Main extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_main);

        // region Button ID값 저장
        Button exam_menu = findViewById(R.id.Exam_Menu_Button);
        Button user_menu = findViewById(R.id.User_Menu_Button);
        // endregion

        // region Button Event : region form_menu_01로 이동
        exam_menu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_Menu_01.class);
                startActivity(intent);
            }
        });
        // endregion

        // region Button Event : region form_menu_02로 이동
        user_menu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_Menu_02.class);
                startActivity(intent);
            }
        });
        // endregion
    }
}