package com.example.intracer.Fr_chat;


public class msj implements Comparable<msj>{
    String mensaje;
    String hora;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    String Key;
    public String getMensaje() {
        return mensaje;
    }

    public String getHora() {
        return hora;
    }

    public String getSender() {
        return sender;
    }

    String sender;

    public msj(String mensaje, String hora, String sender, String ky) {
        this.mensaje = mensaje;
        this.hora = hora;
        this.sender = sender;
        this.Key = ky;

    }


    @Override
    public int compareTo(msj o) {
        return this.getKey().compareTo(o.getKey());
    }
}
