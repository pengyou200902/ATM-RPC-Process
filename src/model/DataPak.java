package model;

import java.io.Serializable;

public class DataPak implements Serializable {
    private String className;//表示类名或接口名
    private  String methodName;  // 表示方法名
    private Class[] paramTypes;//表示方法参数类型
    private Object[] params;//表示方法参数值/如果方法正常执行，则result为方法返回值，如果方法抛出异常，则result为该异常
    private Object result;

    public DataPak(){}

    public DataPak(String className, String methodName, Class[] paramTypes, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    public String getClassName() {
        return className;

    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public Object getResult() {
        return result;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
