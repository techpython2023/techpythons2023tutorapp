package com.example.techpythons2023.Model;

public class HodCourseItem {
    String Depname;
    String Cosname;

    public HodCourseItem() {
    }

    public HodCourseItem( String Depname,String Cosname) {
        this.Depname = Depname;
        this.Cosname = Cosname;

    }

    public String getCosname() {
        return Cosname;
    }

    public void setCosname(String Cosname) {
        this.Cosname = Cosname;
    }

    public String getDepname() {
        return Depname;
    }

    public void setDepname(String Depname) {
        this.Depname = Depname;
    }
}
