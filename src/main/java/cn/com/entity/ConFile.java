package cn.com.entity;

import java.sql.Timestamp;

public class ConFile {
    String filename;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    String link;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    int size;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    Timestamp lastModified;

}