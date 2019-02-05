package info.faljse.tinyrpc.codegen;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DispatchGen {
    public static String generate(Class clazz) {
        StringBuilder sb=new StringBuilder();
        String headerFileName="dispatch";
        sb.append(String.format("// %s.h\n"+
                        "#ifndef %s_H\n"+
                        "#define %s_H\n",
                headerFileName,
                headerFileName,
                headerFileName));

        sb.append("#include \"Arduino.h\"\n");
        sb.append(String.format("#include \"%s.h\"\n",clazz.getSimpleName()));

        sb.append("class Dispatch {\n");
        sb.append(String.format("%s rob;\n",clazz.getSimpleName()));
        sb.append("\tpublic: \n");
        sb.append("\tvoid dispatch() {\n");
        sb.append("\t\tbyte fn=readSerial();\n");
        sb.append("\t\tswitch(fn) {\n");
        for(Method m:clazz.getMethods()) {
            TinyRPCMethod annotation = m.getDeclaredAnnotation(TinyRPCMethod.class);
            sb.append(String.format("\t\tcase %d: {\n",annotation.id()));
            sb.append(generateMethodCall(m));
            sb.append("\t\t\tbreak; }\n");
        }
        sb.append("\n");
        sb.append("}\n");
        sb.append("}\n");
        sb.append("\tprivate: \n" +
                "\t\tint readSerial(){\n" +
                "\t\t\twhile (Serial.available() == 0);\n" +
                "\t\t\treturn Serial.read();\n" +
                "\t\t}");
        sb.append("};\n");
        sb.append(String.format("#endif /* %s_H */",headerFileName));
        return sb.toString();
    }

    private static String generateMethodCall(Method m) {
        StringBuilder sb=new StringBuilder();
        Class returnType=m.getReturnType();
        if(returnType.equals(Integer.TYPE)) {
            sb.append("\t\t\tint32_t retVal = ");

        }
        else if(returnType.equals(Short.TYPE)) {
            sb.append("\t\t\tint16_t retVal = ");
        }

        for(int i=0;i<m.getParameters().length;i++) {
            Parameter p=m.getParameters()[i];
            if(p.getType().equals(Short.TYPE)) {
                String typeName=Tools.typeToC(Short.TYPE);
                String varName="p"+i;
                sb.append(String.format("\t\t\t%s %s;\n",typeName, varName));
                sb.append(String.format("\t\t\t%s=readSerial();\n", varName));
                sb.append(String.format("\t\t\t%s<<8;\n", varName));
                sb.append(String.format("\t\t\t%s|=readSerial();\n", varName));
            }
            else if(p.getType().equals(Integer.TYPE)) {
                String typeName=Tools.typeToC(Integer.TYPE);
                String varName="p"+i;
                sb.append(String.format("\t\t\t%s %s;\n",typeName, varName));
                sb.append(String.format("\t\t\t%s=readSerial();\n", varName));
                sb.append(String.format("\t\t\t%s<<8;\n", varName));
                sb.append(String.format("\t\t\t%s|=readSerial();\n", varName));
                sb.append(String.format("\t\t\t%s<<8;\n", varName));
                sb.append(String.format("\t\t\t%s|=readSerial();\n", varName));
                sb.append(String.format("\t\t\t%s<<8;\n", varName));
                sb.append(String.format("\t\t\t%s|=readSerial();\n", varName));
            }
        }
        sb.append(String.format("\t\t\trob.%s",m.getName()));
        sb.append("(");

        for(int i=0;i<m.getParameters().length;i++){
                sb.append(String.format("p%d, ",i));
        }
        if(m.getParameters().length>0)
            sb.delete(sb.length()-2, sb.length());
        sb.append(");\n");


        if(returnType.equals(Integer.TYPE)) {
            sb.append("\t\t\tSerial.write(retVal&0xFF);\n");
            sb.append("\t\t\tSerial.write(retVal>>8&0xFF);\n");
            sb.append("\t\t\tSerial.write(retVal>>16&0xFF);\n");
            sb.append("\t\t\tSerial.write(retVal>>24&0xFF);\n");
        }
        else if(returnType.equals(Short.TYPE)) {
            sb.append("\t\t\tSerial.write(retVal&0xFF);\n");
            sb.append("\t\t\tSerial.write(retVal>>8&0xFF);\n");
        }
        return sb.toString();
    }
}
