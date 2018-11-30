package net.simplifiedcoding.taskorganizer.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Todo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "finishBy")
    private String finishBy;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "isFinished")
    private boolean isFinished;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFinishBy() {
        return finishBy;
    }

    public void setFinishBy(String finishBy) {
        this.finishBy = finishBy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
