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

public class Form_Exam_02 extends AppCompatActivity
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
        setContentView(R.layout.form_exam_02);

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
        final AlertDialog.Builder build_1 = new AlertDialog.Builder(Form_Exam_02.this);
        final AlertDialog.Builder build_2 = new AlertDialog.Builder(Form_Exam_02.this);
        final AlertDialog.Builder build_3 = new AlertDialog.Builder(Form_Exam_02.this);
        // endregion

        // region Database 사용
        SQLiteDatabase database = connect.getReadableDatabase();
        final Cursor cursor     = database.rawQuery("SELECT QuizName, QuizNum1, QuizNum2, QuizNum3, QuizNum4 FROM ExamSubject " +
                                                    "WHERE QuizId >= 101 AND QuizId <= 200", null);
        // endregion

        // region Database INSERT : 반드시 1번만 적용하고 주석처리할 것
        database.execSQL("INSERT INTO ExamSubject VALUES(101," +
                "'1. 단위 테스트(Unit Test)와 관련한 설명으로 틀린 것은? [20점]'," +
                "'①  모듈 내부의 구조를 구체적으로 볼 수 있는 구조적 테스트를 주로 시행한다.'," +
                "'②  테스트할 모듈을 호출하는 모듈도 있고, 테스트할 모듈이 호출하는 모듈도 있다.'," +
                "'③  구현 단계에서 각 모듈의 개발을 완료한 후 개발자가 명세서의 내용대로 정확히 구현되었는지 테스트한다.'," +
                "'④  필요 데이터를 인자를 통해 넘겨주고, 테스트 완료 후 그 결과값을 받는 역할을 하는 가상의 모듈을 테스트 스텁(Stub)이라고 한다.', 4);");

        database.execSQL("INSERT INTO ExamSubject VALUES(102," +
                "'2. 소프트웨어 공학에서 워크스루에 대한 설명으로 틀린 것은?'," +
                "'①  인스펙션(Inspection)과 동일한 의미를 가진다.'," +
                "'②  단순한 테스트 케이스를 이용하여 프로덕트를 수작업으로 수행해 보는 것이다.'," +
                "'③  사용사례를 확장하여 명세하거나 설계 다이어그램, 원시코드, 테스트 케이스 등에 적용할 수 있다.'," +
                "'④  복잡한 알고리즘 또는 반복, 실시간 동작, 병행 처리와 같은 기능이나 동작을 이해하려고 할 때 유용하다.', 1);");

        database.execSQL("INSERT INTO ExamSubject VALUES(103," +
                "'3. 동시에 소스를 수정하는 것을 방지하며 다른 방향으로 진행된 개발 결과를 합치거나 변경 내용을 추적할 수 있는 소프트웨어 버전 관리 도구는? [20점]'," +
                "'①  RPC(Remote Procedure Call)'," +
                "'②  RVS(Relative Version System)'," +
                "'③  RCS(Revision Control System)'," +
                "'④  RTS(Reliable Transfer System', 3);");

        database.execSQL("INSERT INTO ExamSubject VALUES(104," +
                "'4. DRM(Digital Rights Management)과 관련한 설명으로 틀린 것은? [20점]'," +
                "'①  디지털 미지털의 생명 주기 동안 발생하는 사용 권한 관리, 유통 단계를 관리하는 기술로도 볼 수 있다.'," +
                "'②  클리어링(Clearing House)는 사용자에게 컨텐츠 라이센스를 발급하고 권한을 부여해주는 시스템을 말한다.'," +
                "'③  원본을 안전하게 유통하기 위한 전자적 보안은 고려하지 않기 때문에 불법 유통과 복제의 방지는 불가능하다.'," +
                "'④  디지털 컨텐츠와 디바이스 사용을 제한하기 위해 하드웨어 제조업자, 저작권자, 출판업자 등이 사용할 수 있는 접근 제어 기술을 의미한다.', 3);");

        database.execSQL("INSERT INTO ExamSubject VALUES(105," +
                "'5. 위험 모니터링의 의미로 옳은 것은? [20점]'," +
                "'①  위험을 이해하는 것'," +
                "'②  위험 발생 후 즉시 조치하는 것'," +
                "'③  첫 번째 조치로 위험을 피할 수 있도록 하는 것'," +
                "'④  위험 요소 징후들에 대하여 계속적으로 인지하는 것', 4);");
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
                else if(iden_number == 5 && quiz_number4.isChecked()) // 문제 5번 정답 : 4번
                {
                    build_1.setMessage("5번 문제 : 정답입니다. 다음 문제로 이동하세요.");
                    build_1.setNeutralButton("Yes", null);
                    build_1.setTitle("알림");
                    build_1.create().show();

                    iden_button.setClickable(false); // 정답일 경우 Button 비활성화
                    score = score + 20; // score + 20
                    count = count + 1;  // count + 1
                }
                else if(iden_number == 5 && (quiz_number1.isChecked() || quiz_number2.isChecked() || quiz_number3.isChecked()))
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
                                    Form_Exam_02.this.finish(); // 해당 Form 나가기
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