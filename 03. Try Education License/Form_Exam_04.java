package com.example.capstone_project_01;

// region Import Android 모음
import androidx.appcompat.app.*;

import android.database.sqlite.*;
import android.database.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.os.*;
// endregion

public class Form_Exam_04 extends AppCompatActivity
{
    // region 멤버필드 : 초기화
    DBConnect connect = new DBConnect(this); // DBConnect 객체

    int prev_number = 1; // Use prev_button
    int iden_number = 1; // Use iden_button
    int next_number = 1; // Use next_button

    int score = 0; // 정답 점수
    int count = 0; // 정답 개수
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_exam_04);

        // region TextView + CheckBox Control 선언
        final TextView quiz_name    = findViewById(R.id.Quiz_Name);     // 문제 내용
        final CheckBox quiz_number1 = findViewById(R.id.Quiz_Number01); // 문제 번호 : 1번
        final CheckBox quiz_number2 = findViewById(R.id.Quiz_Number02); // 문제 번호 : 2번
        final CheckBox quiz_number3 = findViewById(R.id.Quiz_Number03); // 문제 번호 : 3번
        final CheckBox quiz_number4 = findViewById(R.id.Quiz_Number04); // 문제 번호 : 4번
        // endregion

        // region ImageButton Control 선언
        final ImageButton prev_button = findViewById(R.id.Prev_Button); // 이전 버튼
        final ImageButton iden_button = findViewById(R.id.Iden_Button); // 확인 버튼
        final ImageButton next_button = findViewById(R.id.Next_Button); // 다음 버튼
        // endregion

        // region Dialog 선언
        final AlertDialog.Builder build_1 = new AlertDialog.Builder(Form_Exam_04.this);
        final AlertDialog.Builder build_2 = new AlertDialog.Builder(Form_Exam_04.this);
        final AlertDialog.Builder build_3 = new AlertDialog.Builder(Form_Exam_04.this);
        // endregion

        // region Database 사용
        SQLiteDatabase database = connect.getReadableDatabase();
        final Cursor cursor     = database.rawQuery("SELECT QuizName, QuizNum1, QuizNum2, QuizNum3, QuizNum4 FROM ExamSubject " +
                "WHERE QuizId >= 301 AND QuizId <= 400", null);
        // endregion

        // region Database INSERT : 반드시 1번만 적용하고 주석처리할 것
        database.execSQL("INSERT INTO ExamSubject VALUES(301," +
                "'1. C언어에서 문자열 처리 함수의 서식과 그 기능의 연결로 틀린 것은? [20점]'," +
                "'①  strlen(s) - s의 길이를 구한다.'," +
                "'②  strrev(s) - s를 거꾸로 변환한다.'," +
                "'③  strcpy(s1, s2) - s2를 s1에 복사한다.'," +
                "'④  strcmp(s1, s2) - s1과 s2를 연결한다.', 4);");

        database.execSQL("INSERT INTO ExamSubject VALUES(302," +
                "'2. UDP 프로토콜의 특징이 아닌 것은? [20점]'," +
                "'①  비연결형 서비스를 제공한다.'," +
                "'②  단순한 헤더 구조로 오버헤드가 작다.'," +
                "'③  TCP와 같이 트랜스포트 계층에 존재한다.'," +
                "'④  주로 주소를 지정하고, 경로를 설정하는 기능을 한다.', 4);");

        database.execSQL("INSERT INTO ExamSubject VALUES(303," +
                "'3. UNIX 운영체제에 관한 특징으로 틀린 것은? [20점]'," +
                "'①  트리 구조의 파일 시스템을 갖는다.'," +
                "'②  이식성이 높으며 장치 간의 호환성이 높다.'," +
                "'③  Multi-User는 지원하지만 Multi-Tasking은 지워하지 않는다.'," +
                "'④  하나 이상의 작업에 대하여 백그라운드에서 수행이 가능하다.', 3);");

        database.execSQL("INSERT INTO ExamSubject VALUES(304," +
                "'4. IP 프로토콜에서 사용하는 필드와 해당 필드에 대한 설명으로 틀린 것은? [20점]'," +
                "'①  Time To Live는 송신 호스트가 패킷을 전송하기 전 네트워크에서 생존할 수 있는 시간을 지정한 것이다.'," +
                "'②  Packet Length는 IP 헤더를 제외한 패킷 전체의 길이를 나타내며 최대 크기는 (2^32 - 1)비트이다.'," +
                "'③  Header Length는 IP 프로토콜의 헤더 길이를 32비트 워드 단위로 표시한다.'," +
                "'④  Version Number는 IP 프로토콜의 버전 번호를 나타낸다.', 2);");

        database.execSQL("INSERT INTO ExamSubject VALUES(305," +
                "'5. 다음 중 Myers가 구분한 응집도(Cohesion)의 정도에서 가장 낮은 응집도를 갖는 단계는? [20점]'," +
                "'①  우연적 응집도(Coincidental Cohesion)'," +
                "'②  순차적 응집도(Sequential Cohesion)'," +
                "'③  기능적 응집도(Funcitonal Cohesion)'," +
                "'④  시간적 응집도(Temporal Cohesion)', 1);");
         // endregion

        // region Database SELECT
        cursor.moveToNext();
        quiz_name.setText(cursor.getString(0));    // 0번째 : 문제 내용
        quiz_number1.setText(cursor.getString(1)); // 1번째 : 문제 번호 : 1번
        quiz_number2.setText(cursor.getString(2)); // 2번째 : 문제 번호 : 2번
        quiz_number3.setText(cursor.getString(3)); // 3번째 : 문제 번호 : 3번
        quiz_number4.setText(cursor.getString(4)); // 4번째 : 문제 번호 : 4번
        // endregion

        // region CheckBox : Click Event
        quiz_number1.setOnClickListener(new CheckBox.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // CheckBox Check 해제
                quiz_number2.setChecked(false);
                quiz_number3.setChecked(false);
                quiz_number4.setChecked(false);
            }
        });

        quiz_number2.setOnClickListener(new CheckBox.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // CheckBox Check 해제
                quiz_number1.setChecked(false);
                quiz_number3.setChecked(false);
                quiz_number4.setChecked(false);
            }
        });

        quiz_number3.setOnClickListener(new CheckBox.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // CheckBox Check 해제
                quiz_number1.setChecked(false);
                quiz_number2.setChecked(false);
                quiz_number4.setChecked(false);
            }
        });

        quiz_number4.setOnClickListener(new CheckBox.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // CheckBox Check 해제
                quiz_number1.setChecked(false);
                quiz_number2.setChecked(false);
                quiz_number3.setChecked(false);
            }
        });
        // endregion

        // region Prev_Button : Click Event
        prev_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // region 문제 2번 → 문제 1번
                if(prev_number == 1)
                {
                    cursor.moveToPosition(prev_number - 1);    // Move To 문제 1번
                    quiz_name.setText(cursor.getString(0));    // 0번째 : 문제 내용
                    quiz_number1.setText(cursor.getString(1)); // 1번째 : 문제 번호 : 1번
                    quiz_number2.setText(cursor.getString(2)); // 2번째 : 문제 번호 : 2번
                    quiz_number3.setText(cursor.getString(3)); // 3번째 : 문제 번호 : 3번
                    quiz_number4.setText(cursor.getString(4)); // 4번째 : 문제 번호 : 4번

                    // CheckBox Check 해제
                    quiz_number1.setChecked(false);
                    quiz_number2.setChecked(false);
                    quiz_number3.setChecked(false);
                    quiz_number4.setChecked(false);

                    iden_button.setClickable(true);
                    prev_number--;   // prev_number - 1
                    iden_number--;   // iden_number - 1
                    next_number = 1; // next_button 정상 작동을 위한 목적
                }
                // endregion

                // region 문제 3번 → 문제 2번
                else if(prev_number == 2)
                {
                    cursor.moveToPosition(prev_number - 1);    // Move To 문제 1번
                    quiz_name.setText(cursor.getString(0));    // 0번째 : 문제 내용
                    quiz_number1.setText(cursor.getString(1)); // 1번째 : 문제 번호 : 1번
                    quiz_number2.setText(cursor.getString(2)); // 2번째 : 문제 번호 : 2번
                    quiz_number3.setText(cursor.getString(3)); // 3번째 : 문제 번호 : 3번
                    quiz_number4.setText(cursor.getString(4)); // 4번째 : 문제 번호 : 4번

                    // CheckBox Check 해제
                    quiz_number1.setChecked(false);
                    quiz_number2.setChecked(false);
                    quiz_number3.setChecked(false);
                    quiz_number4.setChecked(false);

                    iden_button.setClickable(true);
                    prev_number--;   // prev_number - 1
                    iden_number--;   // iden_number - 1
                    next_number = 2; // next_button 정상 작동을 위한 목적
                }
                // endregion

                // region 문제 4번 → 문제 3번
                else if(prev_number == 3)
                {
                    cursor.moveToPosition(prev_number - 1);    // Move To 문제 1번
                    quiz_name.setText(cursor.getString(0));    // 0번째 : 문제 내용
                    quiz_number1.setText(cursor.getString(1)); // 1번째 : 문제 번호 : 1번
                    quiz_number2.setText(cursor.getString(2)); // 2번째 : 문제 번호 : 2번
                    quiz_number3.setText(cursor.getString(3)); // 3번째 : 문제 번호 : 3번
                    quiz_number4.setText(cursor.getString(4)); // 4번째 : 문제 번호 : 4번

                    // CheckBox Check 해제
                    quiz_number1.setChecked(false);
                    quiz_number2.setChecked(false);
                    quiz_number3.setChecked(false);
                    quiz_number4.setChecked(false);

                    iden_button.setClickable(true);
                    prev_number--;   // prev_number - 1
                    iden_number--;   // iden_number - 1
                    next_number = 3; // next_button 정상 작동을 위한 목적
                }
                // endregion

                // region 문제 5번 → 문제 4번
                else if(prev_number == 4)
                {
                    cursor.moveToPosition(prev_number - 1);    // Move To 문제 1번
                    quiz_name.setText(cursor.getString(0));    // 0번째 : 문제 내용
                    quiz_number1.setText(cursor.getString(1)); // 1번째 : 문제 번호 : 1번
                    quiz_number2.setText(cursor.getString(2)); // 2번째 : 문제 번호 : 2번
                    quiz_number3.setText(cursor.getString(3)); // 3번째 : 문제 번호 : 3번
                    quiz_number4.setText(cursor.getString(4)); // 4번째 : 문제 번호 : 4번

                    // CheckBox Check 해제
                    quiz_number1.setChecked(false);
                    quiz_number2.setChecked(false);
                    quiz_number3.setChecked(false);
                    quiz_number4.setChecked(false);

                    iden_button.setClickable(true);
                    prev_number--;   // prev_number - 1
                    iden_number--;   // iden_number - 1
                    next_number = 4; // next_button 정상 작동을 위한 목적
                }
                // endregion

                // region 예외처리 : 문제 1번일 경우
                else
                {
                    build_1.setMessage("현재 1번째 문제입니다. 이전 문제로 이동할 수 없습니다.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion
            }
        });
        // endregion

        // region Iden_Button : Click Event
        iden_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // region 문제 1번 정답여부 처리
                if(iden_number == 1 && quiz_number4.isChecked()) // 문제 1번 정답 : 4번
                {
                    build_1.setMessage("1번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 1 && (quiz_number1.isChecked() || quiz_number2.isChecked() || quiz_number3.isChecked()))
                {
                    build_1.setMessage("1번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 2번 정답여부 처리
                else if(iden_number == 2 && quiz_number4.isChecked()) // 문제 2번 정답 : 4번
                {
                    build_1.setMessage("2번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 2 && (quiz_number1.isChecked() || quiz_number2.isChecked() || quiz_number3.isChecked()))
                {
                    build_1.setMessage("2번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 3번 정답여부 처리
                else if(iden_number == 3 && quiz_number3.isChecked()) // 문제 3번 정답 : 3번
                {
                    build_1.setMessage("3번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 3 && (quiz_number1.isChecked() || quiz_number2.isChecked() || quiz_number4.isChecked()))
                {
                    build_1.setMessage("3번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 4번 정답여부 처리
                else if(iden_number == 4 && quiz_number2.isChecked()) // 문제 4번 정답 : 2번
                {
                    build_1.setMessage("4번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 4 && (quiz_number1.isChecked() || quiz_number3.isChecked() || quiz_number4.isChecked()))
                {
                    build_1.setMessage("4번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 5번 정답여부 처리
                else if(iden_number == 5 && quiz_number1.isChecked()) // 문제 5번 정답 : 1번
                {
                    build_1.setMessage("5번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 5 && (quiz_number2.isChecked() || quiz_number3.isChecked() || quiz_number4.isChecked()))
                {
                    build_1.setMessage("5번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 예외처리 : 답을 선택하지 않았을 경우
                else if(!quiz_number1.isChecked() && !quiz_number2.isChecked() && !quiz_number3.isChecked() && !quiz_number4.isChecked())
                {
                    build_1.setMessage("답을 선택하지 않았습니다. 답을 선택해주세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion
            }
        });
        // endregion

        // region Next_Button : Click Event
        next_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // region 문제 1번 → 문제 2번
                if(next_number == 1)
                {
                    cursor.moveToPosition(next_number);        // Move To 문제 2번
                    quiz_name.setText(cursor.getString(0));    // 0번째 : 문제 내용
                    quiz_number1.setText(cursor.getString(1)); // 1번째 : 문제 번호 : 1번
                    quiz_number2.setText(cursor.getString(2)); // 2번째 : 문제 번호 : 2번
                    quiz_number3.setText(cursor.getString(3)); // 3번째 : 문제 번호 : 3번
                    quiz_number4.setText(cursor.getString(4)); // 4번째 : 문제 번호 : 4번

                    // CheckBox Check 해제
                    quiz_number1.setChecked(false);
                    quiz_number2.setChecked(false);
                    quiz_number3.setChecked(false);
                    quiz_number4.setChecked(false);

                    iden_button.setClickable(true); // Iden_Button Click Event에서 비활성화된 부분을 다시 활성화
                    prev_number = 1; // prev_button 정상 작동을 위한 목적
                    iden_number++;   // iden_number + 1
                    next_number++;   // next_number + 1
                }
                // endregion

                // region 문제 2번 → 문제 3번
                else if(next_number == 2)
                {
                    cursor.moveToPosition(next_number);        // Move To 문제 3번
                    quiz_name.setText(cursor.getString(0));    // 0번째 : 문제 내용
                    quiz_number1.setText(cursor.getString(1)); // 1번째 : 문제 번호 : 1번
                    quiz_number2.setText(cursor.getString(2)); // 2번째 : 문제 번호 : 2번
                    quiz_number3.setText(cursor.getString(3)); // 3번째 : 문제 번호 : 3번
                    quiz_number4.setText(cursor.getString(4)); // 4번째 : 문제 번호 : 4번

                    // CheckBox Check 해제
                    quiz_number1.setChecked(false);
                    quiz_number2.setChecked(false);
                    quiz_number3.setChecked(false);
                    quiz_number4.setChecked(false);

                    iden_button.setClickable(true); // Iden_Button Click Event에서 비활성화된 부분을 다시 활성화
                    prev_number = 2; // prev_button 정상 작동을 위한 목적
                    iden_number++;   // iden_number + 1
                    next_number++;   // next_number + 1
                }
                // endregion

                // region 문제 3번 → 문제 4번
                else if(next_number == 3)
                {
                    cursor.moveToPosition(next_number);        // Move To 문제 4번
                    quiz_name.setText(cursor.getString(0));    // 0번째 : 문제 내용
                    quiz_number1.setText(cursor.getString(1)); // 1번째 : 문제 번호 : 1번
                    quiz_number2.setText(cursor.getString(2)); // 2번째 : 문제 번호 : 2번
                    quiz_number3.setText(cursor.getString(3)); // 3번째 : 문제 번호 : 3번
                    quiz_number4.setText(cursor.getString(4)); // 4번째 : 문제 번호 : 4번

                    // CheckBox Check 해제
                    quiz_number1.setChecked(false);
                    quiz_number2.setChecked(false);
                    quiz_number3.setChecked(false);
                    quiz_number4.setChecked(false);

                    iden_button.setClickable(true); // Iden_Button Click Event에서 비활성화된 부분을 다시 활성화
                    prev_number = 3; // prev_button 정상 작동을 위한 목적
                    iden_number++;   // iden_number + 1
                    next_number++;   // next_number + 1
                }
                // endregion

                // region 문제 4번 → 문제 5번
                else if(next_number == 4)
                {
                    cursor.moveToPosition(next_number);        // Move To 문제 5번
                    quiz_name.setText(cursor.getString(0));    // 0번째 : 문제 내용
                    quiz_number1.setText(cursor.getString(1)); // 1번째 : 문제 번호 : 1번
                    quiz_number2.setText(cursor.getString(2)); // 2번째 : 문제 번호 : 2번
                    quiz_number3.setText(cursor.getString(3)); // 3번째 : 문제 번호 : 3번
                    quiz_number4.setText(cursor.getString(4)); // 4번째 : 문제 번호 : 4번

                    // CheckBox Check 해제
                    quiz_number1.setChecked(false);
                    quiz_number2.setChecked(false);
                    quiz_number3.setChecked(false);
                    quiz_number4.setChecked(false);

                    iden_button.setClickable(true); // Iden_Button Click Event에서 비활성화된 부분을 다시 활성화
                    prev_number = 4; // prev_button 정상 작동을 위한 목적
                    iden_number++;   // iden_number + 1
                    next_number++;   // next_number + 1
                }
                // endregion

                // region 예외처리 : 문제 5번일 경우
                else
                {
                    build_2.setMessage("현재 마지막 문제입니다. 결과를 출력할까요?");
                    build_2.setTitle("알림");

                    // region Yes : PositiveButton Click Event
                    build_2.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            build_3.setMessage("당신의 채점 결과는 다음과 같습니다.\n" +
                                    "→ 맞춘 전체 개수 : " + count + "\n" +
                                    "→ 맞춘 전체 점수 : " + score);
                            build_3.setTitle("알림");

                            // region Exit : NeutralButton Click Event
                            build_3.setNeutralButton("Exit", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    Form_Exam_04.this.finish(); // 해당 Form 나가기
                                }
                            });
                            build_3.create().show();
                            // endregion
                        }
                    });
                    // endregion

                    // region No : NegativeButton Click Event
                    build_2.setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    });
                    build_2.create().show();
                    // endregion
                }
                // endregion
            }
        });
        // endregion

        // Close Code
        database.close();
    }
}