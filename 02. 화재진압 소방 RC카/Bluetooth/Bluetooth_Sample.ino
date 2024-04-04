#include<SoftwareSerial.h>

int RX = 8;
int TX = 7;

SoftwareSerial BTSerial(RX, TX);

void setup() {
  Serial.begin(9600); 
  BTSerial.begin(9600);

}

void loop() {
  if(BTSerial.available()) {
    Serial.write(BTSerial.read());  
  }
  if(Serial.available()) {
    BTSerial.write(Serial.read());
  }
}
