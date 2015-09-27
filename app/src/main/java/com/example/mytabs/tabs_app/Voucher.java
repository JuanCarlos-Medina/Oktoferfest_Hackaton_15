package com.example.mytabs.tabs_app;

import java.util.Date;

/**
 * Created by Katharina on 27.09.2015.
 */
public class Voucher {

    int id;
    String shop;
    double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    String unit;
    String received;
    String expires;
    String code;
    boolean isVisible;

    public Voucher(int id, String shop, double amount, String unit, String received, String expires, String code, boolean isVisible) {
        this.id = id;
        this.shop = shop;
        this.amount = amount;
        this.unit = unit;
        this.received = received;
        this.expires = expires;
        this.code = code;
        this.isVisible = isVisible;
        System.out.println("TEST " + this.id);
    }

    public Voucher(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (getId() != ((Voucher)obj).getId()) {
            return false;
        }
        return true;
    }
}
