package com.example.intracer.Fr_groups;

public class QRlist implements Comparable<QRlist>{

    public QRlist(String dateft, String datesn) {
        this.DateFT = dateft;
        this.DateSN = datesn;
    }

    String DateFT;
    String DateSN;

    public String getDateSN() {
        return DateSN;
    }

    public void setDateSN(String dateSN) {
        DateSN = dateSN;
    }

    public String getDate() {
        return DateFT;
    }

    public void setDate(String date) {
        DateFT = date;
    }

    @Override
    public int compareTo(QRlist e) {
        return this.getDateSN().compareTo(e.getDateSN());
    }

}
