package com.example.techpythons2023.Model;


public class TaopeningItem {
    String Hodemail;
    String Jobid;
    String Lecemail;
    String Lecmodcos;
    String Minmark;
    String Minqual;
    String Modname;
    String Requestid;
    String Status;

    public TaopeningItem() {
    }

    public TaopeningItem( String Hodemai,String Jobid, String Lecemail, String Lecmodcos, String Minmark, String Minqual, String Modname, String Requestid,String Status) {
        this.Hodemail = Hodemai;
        this.Jobid = Jobid;
        this.Lecemail = Lecemail;
        this.Lecmodcos = Lecmodcos;
        this.Minmark = Minmark;
        this.Minqual = Minqual;
        this.Modname = Modname;
        this.Requestid = Requestid;
        this.Status = Status;

    }

    public String getHodemail() {
        return Hodemail;
    }

    public void setHodemail(String Hodemail) {
        this.Hodemail = Hodemail;
    }

    public String getJobid() {
        return Jobid;
    }

    public void setJobid(String Jobid) {
        this.Jobid = Jobid;
    }


    public String getLecemail() {
        return Lecemail;
    }

    public void setLecemail(String Lecemail) {
        this.Lecemail = Lecemail;
    }
    public String getLecmodcos() {
        return Lecmodcos;
    }

    public void setLecmodcos(String Lecmodcos) {
        this.Lecmodcos = Lecmodcos;
    }
    public String getMinmark() {
        return Minmark;
    }

    public void setMinmark(String Minmark) {
        this.Minmark = Minmark;
    }

    public String getMinqual() {
        return Minqual;
    }

    public void setMinqual(String Minqual) {
        this.Minqual = Minqual;
    }

    public String getModname() {
        return Modname;
    }

    public void setModname(String Modname) {
        this.Modname = Modname;
    }

    public String getRequestid() {
        return Requestid;
    }

    public void setRequestid(String Requestid) {
        this.Requestid = Requestid;}
    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}
