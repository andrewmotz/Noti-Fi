package com.example.noti_fiwifinotificitions;

public class NotiFiObject {

    private String SSID;
    private String DESC;

    public NotiFiObject(String SSID, String DESC){
        this.SSID = SSID;
        this.DESC = DESC;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
    }
}
