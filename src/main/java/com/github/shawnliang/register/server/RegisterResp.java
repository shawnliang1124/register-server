package com.github.shawnliang.register.server;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/7/1
 */
public class RegisterResp {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    /**
     * 注册响应状态：SUCCESS、FAILURE
     */
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
