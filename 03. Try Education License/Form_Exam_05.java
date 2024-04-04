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

public class Form_Exam_05 extends AppCompatActivity
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
        setContentView(R.layout.form_exam_05);

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
        final AlertDialog.Builder build_1 = new AlertDialog.Builder(Form_Exam_05.this);
        final AlertDialog.Builder build_2 = new AlertDialog.Builder(Form_Exam_05.this);
        final AlertDialog.Builder build_3 = new AlertDialog.Builder(Form_Exam_05.this);
        // endregion

        // region Database 사용
        SQLiteDatabase database = connect.getReadableDatabase();
        final Cursor cursor     = database.rawQuery("SELECT QuizName, QuizNum1, QuizNum2, QuizNum3, QuizNum4 FROM ExamSubject " +
                "WHERE QuizId >= 401 AND QuizId <= 500", null);
        // endregion

        // region Database INSERT : 반드시 1번만 적용하고 주석처리할 것

        database.execSQL("INSERT INTO ExamSubject VALUES(401," +
                "'1. 취약점 관리를 위한 응용 프로그램의 보안 설정과 가장 거리가 먼 것은? [20점]'," +
                "'①  운영체제의 접근 제한'," +
                "'②  서버 관리실 출입 통제'," +
                "'③  실행 프로세스 권한 설정'," +
                "'④  운영체제의 정보 수집 제한', 2);");

        database.execSQL("INSERT INTO ExamSubject VALUES(402," +
                "'2. 대칭 암호 알고리즘과 비대칭 암호 알고리즘에 대한 설명으로 틀린 것은? [20점]'," +
                "'①  대표적인 대칭키 암호 알고리즘으로는 AES, IDEA 등이 있다.'," +
                "'②  대칭 암호 알고리즘은 비교적 실행 속도가 빠르기 때문에 다양한 암호의 핵심 함수로 사용될 수 있다.'," +
                "'③  비대칭 암호 알고리즘은 자신만의 보관하는 비밀키를 이용하여 인증, 전자서명 등에 적용이 가능하다.'," +
                "'④  대칭 암호 알고리즘은 비밀키 전달을 위한 키 교환이 필요하지 않아 암호화 및 복호화의 속도가 빠르다.', 4);");

        database.execSQL("INSERT INTO ExamSubject VALUES(403," +
                "'3. 악성코드의 유형 중 다른 컴퓨터의 취약점을 이용하여 스스로 전파하거나 메일로 전파되며 스스로를 증식하는 것은? [20점]'," +
                "'①  Worm'," +
                "'②  Adware'," +
                "'③  Rogue Ware'," +
                "'④  Reflaction Attack', 1);");

        database.execSQL("INSERT INTO ExamSubject VALUES(404," +
                "'4. 소프트웨어 개발 방법론 중 애자일(Agile) 방법론의 특징과 가장 거리가 먼 것은? [20점]'," +
                "'①  환경 변화에 대한 즉시 대응'," +
                "'②  프로젝트 상황에 따른 주기적 조정'," +
                "'③  각 단계의 결과가 완전히 확인된 후 다음 단계 진행'," +
                "'④  소프트웨어 개발에 참여하는 구성원들 간의 의사소통 중시', 3);");

        database.execSQL("INSERT INTO ExamSubject VALUES(405," +
                "'5. 소프트웨어 개발 프레임워크와 관련한 설명으로 적절하지 않은 것은? [20점]'," +
                "'①  라이브러리와는 달리 사용자 코드에서 프레임워크를 호출해서 사용하고, 그에 대한 제어도 사용자 코드가 가지는 방식이다.'," +
                "'②  프레임워크의 동작 원리를 그 제어 흐름의 일반적인 프로그램 흐름과 반대로 동작한다고 해서 IoC(Inversion of Cnotrol)이라고 설명하기도 한다.'," +
                "'③  설계 관점에 개발 방식을 패턴화시키기 위한 노력의 결과물인 소프트웨어 디자인 패턴을 반제품 소프트웨어 상태로 집적화시킨 것으로 볼 수 있다.'," +
                "'④  반제품 상태의 제품을 토대로 도메인별로 필요한 서비스 컴포넌트를 사용하여 재사용성 확대와 성능을 보장받을 수 있게 하는 개발 소프트웨어이다.', 1);");
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
                if(iden_number == 1 && quiz_number2.isChecked()) // 문제 1번 정답 : 2번
                {
                    build_1.setMessage("1번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 1 && (quiz_number1.isChecked() || quiz_number3.isChecked() || quiz_number4.isChecked()))
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
                                    Form_Exam_05.this.finish(); // 해당 Form 나가기
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