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

public class Form_Exam_03 extends AppCompatActivity
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
        setContentView(R.layout.form_exam_03);

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
        final AlertDialog.Builder build_1 = new AlertDialog.Builder(Form_Exam_03.this);
        final AlertDialog.Builder build_2 = new AlertDialog.Builder(Form_Exam_03.this);
        final AlertDialog.Builder build_3 = new AlertDialog.Builder(Form_Exam_03.this);
        // endregion

        // region Database 사용
        SQLiteDatabase database = connect.getReadableDatabase();
        final Cursor cursor     = database.rawQuery("SELECT QuizName, QuizNum1, QuizNum2, QuizNum3, QuizNum4 FROM ExamSubject " +
                "WHERE QuizId >= 201 AND QuizId <= 300", null);
        // endregion

        // region Database INSERT : 반드시 1번만 적용하고 주석처리할 것
        database.execSQL("INSERT INTO ExamSubject VALUES(201," +
                "'1. 물리적 데이터베이스 구조의 기본 데이터 단위인 저장 레코드의 양식을 설계할 때 고려 사항이 아닌 것은? [20점]'," +
                "'①  접근 빈도'," +
                "'②  데이터 타입'," +
                "'③  트랜잭션 모델링'," +
                "'④  데이터 값의 분포', 3);");

        database.execSQL("INSERT INTO ExamSubject VALUES(202," +
                "'2. 분산 데이터베이스 시스템과 관련한 설명으로 틀린 것은? [20점]'," +
                "'①  데이터베이스가 분산되어 있음을 사용자가 인식할 수 있도록 분산 투명성을 배제해야 한다.'," +
                "'②  물리적으로 분산되어 지역별로 필요한 데이터를 처리할 수 있는 지역 컴퓨터를 분산 처리기라고 한다.'," +
                "'③  분산 데이터베이스 시스템을 위한 통신 네트워크 구조가 데이터 통신에 영향을 주므로 효율적으로 설계해야 한다.'," +
                "'④  물리적으로 분산된 데이터베이스 시스템을 논리적으로 하나의 데이터베이스 시스템처럼 사용할 수 있도록 한 것이다.', 1);");

        database.execSQL("INSERT INTO ExamSubject VALUES(203," +
                "'3. 키의 종류 중 유일성과 최소성을 만족하는 속성 또는 속성들의 집합은? [20점]'," +
                "'①  Test Key'," +
                "'②  Super Key'," +
                "'③  Atomic Key'," +
                "'④  Candidate key', 4);");

        database.execSQL("INSERT INTO ExamSubject VALUES(204," +
                "'4. 데이터베이스의 인덱스와 관련한 설명으로 틀린 것은? [20점]'," +
                "'①  인덱스의 추가, 삭제 명령어는 각각 ADD, DELETE이다.'," +
                "'②  대부분의 데이터베이스에서 테이블을 삭제하면 인덱스도 같이 삭제된다.'," +
                "'③  테이블에 붙여진 색인으로 데이터 검색 시 처리 속도 향상에 도움이 된다.'," +
                "'④  문헌의 색인, 사전과 같이 데이터를 쉽고, 빠르게 찾을 수 있도록 만든 데이터 구조이다.', 1);");

        database.execSQL("INSERT INTO ExamSubject VALUES(205," +
                "'5. 관계 데이터 모델에서 릴레이션(Relation)에 포함되어 있는 튜플(Tuple)의 수를 무엇이라고 하는가? [20점]'," +
                "'①  Degree'," +
                "'②  Attribute'," +
                "'③  Cardinality'," +
                "'④  Cartesian Product', 3);");
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
                if(iden_number == 1 && quiz_number3.isChecked()) // 문제 1번 정답 : 3번
                {
                    build_1.setMessage("1번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 1 && (quiz_number1.isChecked() || quiz_number2.isChecked() || quiz_number4.isChecked()))
                {
                    build_1.setMessage("1번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 2번 정답여부 처리
                else if(iden_number == 2 && quiz_number1.isChecked()) // 문제 2번 정답 : 1번
                {
                    build_1.setMessage("2번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 2 && (quiz_number2.isChecked() || quiz_number3.isChecked() || quiz_number4.isChecked()))
                {
                    build_1.setMessage("2번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 3번 정답여부 처리
                else if(iden_number == 3 && quiz_number4.isChecked()) // 문제 3번 정답 : 4번
                {
                    build_1.setMessage("3번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 3 && (quiz_number1.isChecked() || quiz_number2.isChecked() || quiz_number3.isChecked()))
                {
                    build_1.setMessage("3번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 4번 정답여부 처리
                else if(iden_number == 4 && quiz_number1.isChecked()) // 문제 4번 정답 : 1번
                {
                    build_1.setMessage("4번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 4 && (quiz_number2.isChecked() || quiz_number3.isChecked() || quiz_number4.isChecked()))
                {
                    build_1.setMessage("4번 문제 : 오답입니다. 다시 풀어보세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();
                }
                // endregion

                // region 문제 5번 정답여부 처리
                else if(iden_number == 5 && quiz_number3.isChecked()) // 문제 5번 정답 : 3번
                {
                    build_1.setMessage("5번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 5 && (quiz_number1.isChecked() || quiz_number2.isChecked() || quiz_number4.isChecked()))
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
                                    Form_Exam_03.this.finish(); // 해당 Form 나가기
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