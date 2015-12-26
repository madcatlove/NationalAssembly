package kr.or.osan21.nationalassembly.SupportMessage;

/**
 * Created by madcat on 12/27/15.
 */

// 댓글
public class SupportMessageReply {

    private Integer num;
    private Integer mid; // SupportMessage 부모 아이디
    private String content;
    private String username;
    private String regdate;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    @Override
    public String toString() {
        return "SupportMessageReply{" +
                "num=" + num +
                ", mid=" + mid +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", regdate='" + regdate + '\'' +
                '}';
    }
}
