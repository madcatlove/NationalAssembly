package kr.or.osan21.nationalassembly.CloudMessage;

/**
 * Created by madcat on 12/27/15.
 */
public class CloudMessage {

    private Integer num;
    private String gcm_token;
    private String regdate;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getGcm_token() {
        return gcm_token;
    }

    public void setGcm_token(String gcm_token) {
        this.gcm_token = gcm_token;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
}
