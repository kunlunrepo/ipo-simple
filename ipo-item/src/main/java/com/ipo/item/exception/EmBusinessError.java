package com.ipo.item.exception;

/**
 * description : 异常枚举
 *
 * @author kunlunrepo
 * date :  2023-07-21 11:57
 */
public enum EmBusinessError implements CommonError{

    PARAMETER_VALIDATION_ERROR(10001, "参数不合法");

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;

    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
