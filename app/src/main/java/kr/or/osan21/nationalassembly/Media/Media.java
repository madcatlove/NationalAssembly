package kr.or.osan21.nationalassembly.Media;

/**
 * Created by madcat on 12/30/15.
 */
public class Media {

    private Integer num;
    private String title;
    private String content;
    private String regdate;
    private String media_image; // 미디어 대표 이미지

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

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getMedia_image() {
        return media_image;
    }

    public void setMedia_image(String media_image) {
        this.media_image = media_image;
    }

    @Override
    public String toString() {
        return "Media{" +
                "num=" + num +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regdate='" + regdate + '\'' +
                ", media_image='" + media_image + '\'' +
                '}';
    }
}
