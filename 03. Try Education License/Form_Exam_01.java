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

public class Form_Exam_01 extends AppCompatActivity
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
        setContentView(R.layout.form_exam_01);

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
        final AlertDialog.Builder build_1 = new AlertDialog.Builder(com.example.capstone_project_01.Form_Exam_01.this);
        final AlertDialog.Builder build_2 = new AlertDialog.Builder(com.example.capstone_project_01.Form_Exam_01.this);
        final AlertDialog.Builder build_3 = new AlertDialog.Builder(com.example.capstone_project_01.Form_Exam_01.this);
        // endregion

        // region Database 사용

        // endregion

        // region Database INSERT : 반드시 1번만 적용하고 주석처리할 것
        /*
        database.execSQL("INSERT INTO ExamSubject VALUES(1," +
                "'1. UI의 설계 지침으로 틀린 것은? [20점]'," +
                "'①  이해하기 편하고 쉽게 사용할 수 있는 환경을 제공해야 한다.'," +
                "'②  사용자의 직무, 연령, 성별 등 다양한 계층을 수용해야 한다.'," +
                "'③  주요 기능을 메인 화면에 노출하여 조작이 쉽도록 해야 한다.'," +
                "'④  치명적인 오류에 대한 부정적인 사항은 사용자가 인지할 수 없도록 한다.', 4);");

        database.execSQL("INSERT INTO ExamSubject VALUES(2," +
                "'2. 객체에 대한 설명으로 틀린 것은? [20점]'," +
                "'①  객체의 상태는 속성값에 의해 정의된다.'," +
                "'②  객체는 공통 속성을 공유하는 클래스들의 집합이다.'," +
                "'③  객체는 상태, 동작, 고유 식별자를 가진 모든 것이라 할 수 있다.'," +
                "'④  객체는 필요한 자료 구조와 이에 수행되는 함수들을 가진 하나의 독립된 존재이다.', 2);");

        database.execSQL("INSERT INTO ExamSubject VALUES(3," +
                "'3. 익스트림 프로그래밍에 대한 설명으로 틀린 것은? [20점]'," +
                "'①  대표적인 구조적 방법론 중 하나이다.'," +
                "'②  소규모 개발 조직이 불확실하고 변경이 많은 요구를 접했을 때 적절한 방법이다.'," +
                "'③  구체적인 실천 방법을 정의하고 있으며, 개발 문서 보다는 소스코드에 중점을 둔다.'," +
                "'④  익스트림 프로그래밍을 구동시키는 원리는 상식적인 원리와 경험을 최대한 끌어올리는 것이다.', 1);");

        database.execSQL("INSERT INTO ExamSubject VALUES(4," +
                "'4. 정보공학 방법론에서 데이터베이스 설계의 표현으로 사용하는 모델링 언어는? [20점]'," +
                "'①  Package Diagram'," +
                "'②  Deployment Diagram'," +
                "'③  Entity-Relation Digram'," +
                "'④  State Transition Diagram', 3);");

        database.execSQL("INSERT INTO ExamSubject VALUES(5," +
                "'5. 요구사항 분석에서 비기능적 요구에 대한 설명으로 옳은 것은? [20점]'," +
                "'①  <금융 시스템은 조회, 인출, 입금, 송금의 기능이 있어야 한다.>는 비기능적 요구이다.'," +
                "'②  시스템 구축과 관련된 안전, 보안에 대한 요구사항들은 비기능적 요구에 해당하지 않는다.'," +
                "'③  시스템의 처리량, 반응 시간 등의 성능 요구나 품질 요구는 비기능적 요구에 해당하지 않는다.'," +
                "'④  <차량 대여 시스템이 제공하는 모든 화면이 3초 이내에 사용자에게 보여야 한다.>는 비기능적 요구이다.', 1);");

         */
        // endregion

        // region Database SELECT
        SQLiteDatabase database = connect.getReadableDatabase();
        final Cursor cursor     = database.rawQuery("SELECT QuizName, QuizNum1, QuizNum2, QuizNum3, QuizNum4 FROM ExamSubject " +
                "WHERE QuizId >= 1 AND QuizId <= 100", null);

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
                else if(iden_number == 2 && quiz_number2.isChecked()) // 문제 2번 정답 : 2번
                {
                    build_1.setMessage("2번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 2 && (quiz_number1.isChecked() || quiz_number3.isChecked() || quiz_number4.isChecked()))
                {
                    build_1.setMessage("2번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 3번 정답여부 처리
                else if(iden_number == 3 && quiz_number1.isChecked()) // 문제 3번 정답 : 1번
                {
                    build_1.setMessage("3번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 3 && (quiz_number2.isChecked() || quiz_number3.isChecked() || quiz_number4.isChecked()))
                {
                    build_1.setMessage("3번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 4번 정답여부 처리
                else if(iden_number == 4 && quiz_number3.isChecked()) // 문제 4번 정답 : 3번
                {
                    build_1.setMessage("4번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 4 && (quiz_number1.isChecked() || quiz_number2.isChecked() || quiz_number4.isChecked()))
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
                                    com.example.capstone_project_01.Form_Exam_01.this.finish(); // 해당 Form 나가기
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