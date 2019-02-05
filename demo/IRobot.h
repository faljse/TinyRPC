// IRobot.h
#ifndef IRobot_H
#define IRobot_H

class IRobot {
	public:
		int16_t getSonarCm();
		int16_t getLineLeft();
		int16_t getLineRight();
		void motorRight(int16_t arg0);
		void motorLeft(int16_t arg0);
		void drawString();
}; 
#endif /* IRobot_H */