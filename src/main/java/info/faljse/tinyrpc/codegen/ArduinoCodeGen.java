package info.faljse.tinyrpc.codegen;

import info.faljse.tinyrpc.demo.IRobot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ArduinoCodeGen {
    private final static Logger logger = LoggerFactory.getLogger(ArduinoCodeGen.class);

    public static void main(String[] args){
        Class c= IRobot.class;
        String name=c.getSimpleName();
        if(args.length==0){
            System.out.println("req. path param");
            System.exit(0);
        }
        Path path=Paths.get(args[0]);
        new File(path.toString()).mkdirs();
        try {
            Files.write(path.resolve(name+".h"), HeaderGen.generate(c).getBytes(StandardCharsets.US_ASCII));
            Files.write(path.resolve("dispatch.h"), DispatchGen.generate(c).getBytes(StandardCharsets.US_ASCII));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
