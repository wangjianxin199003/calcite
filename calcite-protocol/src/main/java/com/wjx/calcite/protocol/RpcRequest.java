package com.wjx.calcite.protocol;


import java.util.Arrays;

public class RpcRequest {
    private int interfaceId;

    private String interfaceName;

    private String invokeMethodName;

    private Object[] args;

    public int getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(int interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInvokeMethodName() {
        return invokeMethodName;
    }

    public void setInvokeMethodName(String invokeMethodName) {
        this.invokeMethodName = invokeMethodName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RpcRequest that = (RpcRequest) o;

        if (interfaceId != that.interfaceId) return false;
        if (invokeMethodName != null ? !invokeMethodName.equals(that.invokeMethodName) : that.invokeMethodName != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        int result = (int) (interfaceId ^ (interfaceId >>> 32));
        result = 31 * result + (invokeMethodName != null ? invokeMethodName.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "interfaceId=" + interfaceId +
                ", invokeMethodName='" + invokeMethodName + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
