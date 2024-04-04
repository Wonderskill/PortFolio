package com.example.capstone_project_01;

// region Import Android 모음
import androidx.appcompat.app.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.os.*;
// endregion

public class Form_Menu_02 extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_menu_02);

        // region Button ID값 저장
        Button insert_menu = findViewById(R.id.Insert_Menu_Button);
        Button print_menu  = findViewById(R.id.Print_Menu_Button);
        // endregion

        // region Button Event : region form_user_01로 이동
        insert_menu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_User_01.class);
                startActivity(intent);
            }
        });
        // endregion

        // region Button Event : region form_user_02로 이동
        print_menu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_User_02.class);
                startActivity(intent);
            }
        });
        // endregion
    }
}