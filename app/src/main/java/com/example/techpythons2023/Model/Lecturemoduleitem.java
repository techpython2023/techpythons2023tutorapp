package com.example.techpythons2023.Model;

public class Lecturemoduleitem {
    String Lecmodid;
    String Modname;
    String Cosname;
    String Depname;
    String Lecemail;
    String Staffnum;

    public Lecturemoduleitem() {
    }

    public Lecturemoduleitem(String Lecmodid, String Modname,String Cosname,String Depname, String Lecemail, String Staffnum) {
        this.Modname = Modname;
        this.Cosname = Cosname;
        this.Depname = Depname;
        this.Lecemail = Lecemail;
        this.Staffnum = Staffnum;
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


    public String getDepname() {
        return Depname;
    }

    public void setDepname(String Depname) {
        this.Depname = Depname;
    }



    public String getLecemail() {
        return Lecemail;
    }

    public void setLecemail(String Lecemail) {
        this.Lecemail = Lecemail;
    }



    public String getStaffnum() {
        return Staffnum;
    }

    public void setStaffnum(String Staffnum) {
        this.Staffnum = Staffnum;
    }
}
