package cn.com.entity;


import java.sql.Timestamp;

public class Contract {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;


    public ConFile getFile() {
        return file;
    }

    public void setFile(ConFile file) {
        this.file = file;
    }

    ConFile file;
}
