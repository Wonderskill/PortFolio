#include <SoftwareSerial.h>
#include <DHT.h>

#define DHTPIN A1     // 온습도 센서
#define DHTTYPE DHT11 // 온습도 버전
#define FLAME A0      // 화제감지
#define PRA A3        // 플라즈마 발생기

DHT dht(DHTPIN, DHTTYPE);

int RX = 8;
int TX = 7;

SoftwareSerial BTSerial(RX, TX);    // 블루투스 핀번호

// 버퍼에 데이터를 저장할 때 기록할 위치
byte buffer[1024]; 
int bufferPosition; 
boolean temp = 0;

// 바퀴 핀번호
int in1 = 9;
int in2 = 6;
int in3 = 10;
int in4 = 12;

// 바퀴속도   
int RSpeed = 255;
int LSpeed = 255;  

// 바퀴값 저장할 버퍼 
char data;  
 
void setup() {
  Serial.begin(9600); 
  BTSerial.begin(9600);
  pinMode(FLAME, INPUT);
  pinMode(PRA, OUTPUT);
  
  bufferPosition = 0;   // 버퍼 위치 초기화
}
 
void loop() {
    
    // 센서부분
    int t = dht.readTemperature();
    int F_val = analogRead(FLAME);
    /*
    Serial.print("불꽃 센서 : ");
    Serial.println(F_val);  // 불꽃 센서 출력
    */

    BTSerial.print("현재 온도 : ");
    BTSerial.print(t);        // 현재 온도 출력
    BTSerial.println(" C");

    /*
    // 불꽃 센서 값과 현재 온도를 앱 인벤터에 출력
    BTSerial.print("불꽃 센서 / 현재 온도 : ");
    String SensorData = String(F_val) + " / " + String(t);
    BTSerial.print(SensorData);
    BTSerial.println(" C");
    */
    
    delay(1000); // 딜레이 값을 적게 입력할 시 앱 인벤터 출력 부분에 값이 중복되게 출력될 수 있음
    // Serial.println(SensorData);
   
    if (BTSerial.available()) { // 입력값을 받을경우의 if문
      data = BTSerial.read();
      // Serial.write(BTSerial.read()); // 시리얼 모니터와 동시에 값을 출력할 경우 앱 인벤터 출력 부분이 글자가 깨짐
      // BTSerial.write(Serial.read());

    if (data == 't') { // 직진
      Serial.println("이동 : RC카는 현재 전진 상태입니다.");
      analogWrite(in1, RSpeed);
      analogWrite(in2, 0);
      analogWrite(in3, 0);
      analogWrite(in4, LSpeed); 
    }
    if (data == 'r') { // 우회전
      Serial.println("이동 : RC카는 현재 우회전 상태입니다.");
      analogWrite(in1, 0);
      analogWrite(in2, 0);
      analogWrite(in3, 0);
      analogWrite(in4, LSpeed);
    }
    
    if (data == 'l') { // 좌회전
      Serial.println("이동 : RC카는 현재 좌회전 상태입니다.");
      analogWrite(in1, RSpeed);
      analogWrite(in2, 0);
      analogWrite(in3, 0);
      analogWrite(in4, 0);
    }
    
    if (data == 'b') { // 후진
      Serial.println("이동 : RC카는 현재 후진 상태입니다.");
      analogWrite(in1, 0);
      analogWrite(in2, RSpeed);
      analogWrite(in3, LSpeed);
      analogWrite(in4, 0);
    }
    
    if (data == 's') { // 정지
      Serial.println("이동 : RC카는 현재 정지 상태입니다.");
      analogWrite(in1, 0);
      analogWrite(in2, 0);
      analogWrite(in3, 0);
      analogWrite(in4, 0);
    }
    
    if (data == 'p') { // 플라즈마 발생기 작동
      digitalWrite(PRA, HIGH);
      Serial.println("주의 : 플라즈마를 발생시킵니다!");
    }

    if (data == 'a') { // 플라즈마 발생기 작동 멈춤
      digitalWrite(PRA, LOW);
      Serial.println("주의 : 플라즈마를 멈춥니다!");
    }
  }
  
  // 대기모드
  /*
  if(F_val < 0){
      Serial.println("주의 : 불꽃이 감지되었습니다!");  // 불꽃이 감지될 경우
      Serial.println("주의 : 플라즈마를 자동으로 발생시킵니다!");     // 불꽃이 감지될 경우
      Serial.println("--------------------------------");
    } else{
      Serial.println("알림 : 불꽃이 감지되지 않았습니다."); // 평상 시
      Serial.println("--------------------------------");  
      digitalWrite(PRA, LOW);
}

  // 블루투스 논버전(두이노 명령에 따름)
  if(Serial.available()) {
    int value = Serial.read();
    switch(value) {
      case '1' :
        Serial.println("이동 : RC카는 현재 전진 상태입니다.");
        digitalWrite(in1, HIGH);   // 모터가 전진
        digitalWrite(in2, LOW);
        digitalWrite(in3, LOW);
        digitalWrite(in4, HIGH);
        break;
        
      case '2' :
        Serial.println("이동 : RC카는 현재 후진 상태입니다.");
        digitalWrite(in1, LOW);   // 모터가 후진
        digitalWrite(in2, HIGH);
        digitalWrite(in3, HIGH);
        digitalWrite(in4, LOW);
        break;

       case '3' :
        Serial.println("이동 : RC카는 현재 죄회전 상태입니다.");
        digitalWrite(in1, HIGH);
        digitalWrite(in2, LOW);
        break;

      case '0' :
        Serial.println("이동 : RC카는 현재 정지 상태입니다.");
        digitalWrite(in1, HIGH);   // 모터가 정지
        digitalWrite(in2, HIGH);
        digitalWrite(in3, HIGH);
        digitalWrite(in4, HIGH);
        digitalWrite(PRA,LOW);
        break;  

      case '5':
        digitalWrite(PRA, HIGH);
        Serial.println("주의 : 플라즈마를 발생시킵니다!");
        break;

      case '6':
        digitalWrite(PRA, LOW);
        Serial.println("주의 : 플라즈마를 멈춥니다!");
        break;
      
      default : // 예외 처리
        Serial.println("알림 : 잘못된 입력값입니다.");
        break;
    }
  }
*/
















}
