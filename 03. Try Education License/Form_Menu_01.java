package com.example.capstone_project_01;

// region Import Android 모음
import androidx.appcompat.app.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.os.*;
// endregion

public class Form_Menu_01 extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_menu_01);

        // region Button ID값 저장
        Button subject_01 = findViewById(R.id.Subject01_Button);
        Button subject_02 = findViewById(R.id.Subject02_Button);
        Button subject_03 = findViewById(R.id.Subject03_Button);
        Button subject_04 = findViewById(R.id.Subject04_Button);
        Button subject_05 = findViewById(R.id.Subject05_Button);
        // endregion

        // region Button Event : region form_exam_01로 이동
        subject_01.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_Exam_01.class);
                startActivity(intent);
            }
        });
        // endregion

        // region Button Event : region form_exam_02로 이동
        subject_02.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_Exam_02.class);
                startActivity(intent);
            }
        });
        // endregion

        // region Button Event : region form_menu_03로 이동
        subject_03.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_Exam_03.class);
                startActivity(intent);
            }
        });
        // endregion

        // region Button Event : region form_menu_04로 이동
        subject_04.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_Exam_04.class);
                startActivity(intent);
            }
        });
        // endregion

        // region Button Event : region form_menu_05로 이동
        subject_05.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Form_Exam_05.class);
                startActivity(intent);
            }
        });
        // endregion
    }
}