package com.cxl.life.image;

/**
 * 图片实体
 * Created by Nereo on 2015/4/7.
 */
public class Image {
    public String path;//路径
    public String name;//名称
    public long time;

    public Image(String path, String name, long time){
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
