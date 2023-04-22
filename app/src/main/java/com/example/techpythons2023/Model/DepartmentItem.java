package com.example.techpythons2023.Model;

public class DepartmentItem {
    String Depname;
    String Facname;

    public DepartmentItem() {
    }

    public DepartmentItem( String Depname,String Facname) {
        this.Depname = Depname;
        this.Facname = Facname;

    }

    public String getFacname() {
        return Facname;
    }

    public void setFacname(String Facname) {
        this.Facname = Facname;
    }

    public String getDepname() {
        return Depname;
    }

    public void setDepname(String Depname) {
        this.Depname = Depname;
    }
}
