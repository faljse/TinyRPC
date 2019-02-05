package info.faljse.tinyrpc.demo;

import info.faljse.tinyrpc.codegen.ParamName;
import info.faljse.tinyrpc.codegen.TinyRPCMethod;

public interface IRobot {

    @TinyRPCMethod(id=5)
    public short getSonarCm();

    @TinyRPCMethod(id=6)
    public short getLineLeft();

    @TinyRPCMethod(id=7)
    public short getLineRight();

    @TinyRPCMethod(id=8)
    public void motorLeft(@ParamName("speed") short speed);

    @TinyRPCMethod(id=9)
    public void motorRight(@ParamName("speed") short speed);

    @TinyRPCMethod(id=10)
    public void drawString();
}
