package com.cxl.life.bean;

/**
 * Created by cxl on 2017/6/22.
 * 王者荣耀实体
 */

public class KingGlory {
    private String name;//名称
    private String image;//图片链接
    private String describe;//描述
    private String dialogue;//对白
    private String indexTag;//

    public KingGlory() {
    }

    public KingGlory(String name, String image, String describe) {
        this.name = name;
        this.image = image;
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public String getIndexTag() {
        return indexTag;
    }

    public void setIndexTag(String indexTag) {
        this.indexTag = indexTag;
    }
}
