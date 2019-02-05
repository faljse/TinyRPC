package info.faljse.tinyrpc.codegen;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class HeaderGen {
    public static String generate(Class clazz){
        String headerFileName=clazz.getSimpleName();
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("// %s.h\n"+
                "#ifndef %s_H\n"+
                "#define %s_H\n",
                headerFileName,
                headerFileName,
                headerFileName));
        sb.append("\n");
        sb.append(String.format("class %s {\n", headerFileName));
        sb.append("\tpublic:\n");


        Method[] methods = clazz.getDeclaredMethods();
        for(Method m:methods) {
            appendMethod(sb, m);
        }
        sb.append("}; \n");
        sb.append(String.format("#endif /* %s_H */", headerFileName));
        return sb.toString();
    }

    private static void appendMethod(StringBuilder sb, Method m){
        Class<?> returnType = m.getReturnType();
        sb.append("\t\t");
        sb.append(Tools.typeToC(returnType));
        sb.append(" ");
        sb.append(m.getName());
        sb.append("(");

        Parameter[] parameters = m.getParameters();
        for(Parameter p:parameters) {
            sb.append(Tools.typeToC(p.getType()));
            sb.append(" ");
            sb.append(p.getAnnotation(ParamName.class).value());
            sb.append(", ");
        }
        if( parameters.length>0)
            sb.delete(sb.length()-2,sb.length()); //remove last ", ";
        sb.append(");");
        sb.append("\n");
    }
}
