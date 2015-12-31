package kr.or.osan21.nationalassembly.SupportMessage;

import java.util.List;

/**
 * Created by madcat on 12/27/15.
 */

// 건의사항/격려 메시지
public class SupportMessage {

    private Integer num;
    private String title;
    private String content;
    private String username;
    private String regdate;
    private Integer reply_count;

    //optional
    private List<SupportMessageReply> reply;




    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<SupportMessageReply> getReply() {
        return reply;
    }

    public void setReply(List<SupportMessageReply> reply) {
        this.reply = reply;
    }

    public Integer getReply_count() {
        return reply_count;
    }

    public void setReply_count(Integer reply_count) {
        this.reply_count = reply_count;
    }

    @Override
    public String toString() {
        return "SupportMessage{" +
                "num=" + num +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", regdate='" + regdate + '\'' +
                ", reply=" + reply +
                ", reply_count=" + reply_count +
                '}';
    }
}
