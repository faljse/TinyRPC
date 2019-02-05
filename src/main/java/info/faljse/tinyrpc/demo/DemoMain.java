package info.faljse.tinyrpc.demo;

import info.faljse.tinyrpc.TinyRpcHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import purejavacomm.CommPortIdentifier;
import purejavacomm.NoSuchPortException;
import purejavacomm.PortInUseException;
import purejavacomm.UnsupportedCommOperationException;

import java.io.IOException;

public class DemoMain {
    private final static Logger logger = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String [] args){
        try {
            IRobot mbot = (IRobot) TinyRpcHandler.createHandler(IRobot.class, CommPortIdentifier.getPortIdentifier("COM4"));
            mbot.motorLeft((short)0);
            mbot.motorRight((short)0);
            mbot.drawString();
            while(true){
                short dist=mbot.getSonarCm();
                if(dist==0)
                    dist=100;
                if(dist<10)
                    dist=0;
                mbot.motorLeft((short) (dist*4));
                mbot.motorRight((short) (dist*4));
                String s="";
                s+=dist +" "+mbot.getLineLeft()+" "+mbot.getLineRight();
                System.out.println(s);
            }
        } catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException e) {
            logger.warn("eee", e);
        }
    }
}
