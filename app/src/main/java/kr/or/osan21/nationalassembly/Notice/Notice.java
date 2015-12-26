package kr.or.osan21.nationalassembly.Notice;

/**
 * Created by madcat on 12/27/15.
 */
public class Notice {
    private Integer num;
    private String title;
    private String content;
    private String name;
    private String regdate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }


    @Override
    public String toString() {
        return "Notice{" +
                "num=" + num +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", regdate='" + regdate + '\'' +
                '}';
    }
}
