package com.example.techpythons2023.Model;

public class Lecturemoduleitem {
    String Lecmodid;
    String Modname;
    String Cosname;
    String Lecemail;

    public Lecturemoduleitem() {
    }

    public Lecturemoduleitem(String Lecmodid, String Modname,String Cosname, String Lecemail) {
        this.Modname = Modname;
        this.Cosname = Cosname;
        this.Lecemail = Lecemail;
        this.Lecmodid = Lecmodid;
    }

    public String getLecmodid() {
        return Lecmodid;
    }

    public void setLecmodid(String Lecmodid) {
        this.Lecmodid = Lecmodid;
    }

    public String getModname() {
        return Modname;
    }

    public void setModname(String Modname) {
        this.Modname = Modname;
    }


    public String getCosname() {
        return Cosname;
    }

    public void setCosname(String Cosname) {
        this.Cosname = Cosname;
    }




    public String getLecemail() {
        return Lecemail;
    }

    public void setLecemail(String Lecemail) {
        this.Lecemail = Lecemail;
    }


}
