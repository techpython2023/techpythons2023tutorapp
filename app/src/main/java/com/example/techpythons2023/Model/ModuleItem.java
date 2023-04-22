package com.example.techpythons2023.Model;

public class ModuleItem {
    String Modname;
    String Cosname;

    public ModuleItem() {
    }

    public ModuleItem( String Modname,String Cosname) {
        this.Modname = Modname;
        this.Cosname = Cosname;
    }

    public String getCosname() {
        return Cosname;
    }

    public void setCosname(String Cosname) {
        this.Cosname = Cosname;
    }

    public String getModname() {
        return Modname;
    }

    public void setModname(String Depname) {
        this.Modname = Modname;
    }
}
