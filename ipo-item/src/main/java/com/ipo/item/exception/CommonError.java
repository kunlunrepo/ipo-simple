package com.ipo.item.exception;

/**
 * description : 公共错误
 *
 * @author kunlunrepo
 * date :  2023-07-21 11:53
 */
public interface CommonError {

    public int getErrCode();

    public String getErrMsg();

    public CommonError setErrMsg(String errMsg);
}
