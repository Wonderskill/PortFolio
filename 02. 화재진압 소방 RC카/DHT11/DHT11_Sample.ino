#include<SoftwareSerial.h>
#include <DHT.h>

#define DHTPIN A1     // 온습도 센서
#define DHTTYPE DHT11 // 온습도 버전
#define FLAME A0      // 

DHT dht(DHTPIN, DHTTYPE);

int RX = 8;
int TX = 7;

SoftwareSerial BTSerial(RX, TX);

void setup() {
  Serial.begin(9600); 
  BTSerial.begin(9600);

}

void loop() {
    int t = dht.readTemperature();
    int F_val = analogRead(FLAME);
  
  if(BTSerial.available()) {
    Serial.write(BTSerial.read());  
  }
  if(Serial.available()) {
    BTSerial.write(Serial.read());
  }

    BTSerial.print("불꽃 센서 / 현재 온도 : ");
    String SensorData = String(F_val) + " / " + String(t);
    BTSerial.print(SensorData);
    BTSerial.println(" C");

  delay(2000);
}
