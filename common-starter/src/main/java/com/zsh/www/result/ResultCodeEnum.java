package com.zsh.www.result;


public enum ResultCodeEnum {
    /**
     * 基本属性
     */
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    EXCEPTION(-1, "代码异常");

    /**100X开头，权限*/

    /**400X开头，资源未找到*/

    /**
     * 500X开头，错错误
     */


    private int code;

    private String codeMsg;

    private ResultCodeEnum(int code, String codeMsg) {
        this.code = code;
        this.codeMsg = codeMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }
}
