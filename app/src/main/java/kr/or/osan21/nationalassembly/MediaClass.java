package kr.or.osan21.nationalassembly;

/**
 * Created by Jeonghoon on 2015-12-29.
 */
public class MediaClass {

    private String title;
    private String content;
    private int photo;

    //생성자들
    MediaClass(String title, String content, int photo) {
        this.title = title;
        this.content = content;
        this.photo = photo;
    }
    MediaClass(String title, String content) {
        this.title = title;
        this.content = content;
    }
    MediaClass() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
