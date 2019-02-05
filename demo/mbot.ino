#include <MeBuzzer.h>
#include <MeConfig.h>
#include <MeDCMotor.h>
#include <MeLEDMatrix.h>
#include <MeLEDMatrixData.h>
#include <MeLineFollower.h>
#include <MeMbotDCMotor.h>
#include <MeMCore.h>


#include "dispatch.h"


Dispatch p;


MeBuzzer buzzer;
MeDCMotor motorL(M1);
MeDCMotor motorR(M2);
MeLineFollower line(PORT_2);
MeUltrasonicSensor ultr(PORT_3);
MeLEDMatrix ledMx(PORT_4);

void setup() {
  Serial.begin(9600);
  motorL.run(0);
  motorR.run(0);
  ledMx.setBrightness(6);
  ledMx.setColorIndex(1);
  ledMx.showClock(2,3,4);
}

void loop() {
  p.dispatch();
}

void  IRobot::motorLeft(int16_t speed){
  motorL.run(-speed);
}

void  IRobot::motorRight(int16_t speed){
  motorR.run(speed);
}

int16_t  IRobot::getLineRight(){
  return line.readSensor1();
}

int16_t  IRobot::getLineLeft(){
  return line.readSensor2();
}

int16_t  IRobot::getSonarCm(){
  return ultr.distanceCm(50);
}


void IRobot::drawString(){
  ledMx.reset(0x01);
  ledMx.drawStr(10,10,"A");
}


