package com.example.techpythons2023.Model;


public class TarequestItem {
    String Lecemail;
    String Lecmodcos;
    String Minmark;
    String Minqual;
    String Modname;
    String Reason;
    String Requestid;
    String Status;


    public TarequestItem() {
    }

    public TarequestItem(String Lecemail, String Lecmodcos,String Minmark, String Minqual, String Modname, String Reason, String Requestid, String Status) {
        this.Lecemail = Lecemail;
        this.Lecmodcos = Lecmodcos;
        this.Minmark = Minmark;
        this.Minqual = Minqual;
        this.Modname = Modname;
        this.Reason = Reason;
        this.Requestid = Requestid;
        this.Status = Status;
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


    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }


    public String getRequestid() {
        return Requestid;
    }

    public void setRequestid(String Requestid) {
        this.Requestid = Requestid;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }



}
