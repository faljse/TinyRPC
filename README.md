# TinyRPC
Minimal RPC lib/codegen for serial communication between arduino and host   
Could need some work (support additional datatypes, etc)

# How

## Create an Interface (e.g. a robot)
```java
public interface IBender {
    @TinyRPCMethod(id=42)
    public short bend(@ParamName("force") short force);
}
```

## Generate arduino code
```bash
java -jar ArduinoCodeGen.jar /path/to/arduinoproject/
```

yields 2 files:   
```dispatch.h:``` contains arduino code that reads serial interface and dispatches rpc methods  
```IBender.h:``` header file containing prototypes (```void bend(int16_t force);```)  

## Implement arduino functionality
Create IBender.cpp, include IBender.h and start coding

## Call arduino methods from java
```java
IBender bender = (IBender) TinyRpcHandler.createHandler(IBender.class, CommPortIdentifier.getPortIdentifier("COM4"));
bender.bend(99);
```

# Supported Data types
* Integer (int32_t)
* Short (int16_t)
* Byte (int8_t)
* String (char*)
