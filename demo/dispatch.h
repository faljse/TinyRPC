// dispatch.h
#ifndef dispatch_H
#define dispatch_H
#include "Arduino.h"
#include "IRobot.h"
class Dispatch {
IRobot rob;
	public: 
	void dispatch() {
		byte fn=readSerial();
		switch(fn) {
		case 5: {
			int16_t retVal = 			rob.getSonarCm();
			Serial.write(retVal&0xFF);
			Serial.write(retVal>>8&0xFF);
			break; }
		case 6: {
			int16_t retVal = 			rob.getLineLeft();
			Serial.write(retVal&0xFF);
			Serial.write(retVal>>8&0xFF);
			break; }
		case 7: {
			int16_t retVal = 			rob.getLineRight();
			Serial.write(retVal&0xFF);
			Serial.write(retVal>>8&0xFF);
			break; }
		case 9: {
			int16_t p0;
			p0=readSerial();
			p0<<8;
			p0|=readSerial();
			rob.motorRight(p0);
			break; }
		case 8: {
			int16_t p0;
			p0=readSerial();
			p0<<8;
			p0|=readSerial();
			rob.motorLeft(p0);
			break; }
		case 10: {
			rob.drawString();
			break; }

}
}
	private: 
		int readSerial(){
			while (Serial.available() == 0);
			return Serial.read();
		}};
#endif /* dispatch_H */