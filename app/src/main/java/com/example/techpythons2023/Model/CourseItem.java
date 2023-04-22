package com.example.techpythons2023.Model;

public class CourseItem {
    String Depname;
    String Cosname;

    public CourseItem() {
    }

    public CourseItem( String Depname,String Cosname) {
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
