package com.example.xlfbook;

import java.io.Serializable;

public class CostBean implements Serializable {
    public String costTitle;
    public String costDate;
    public String costMoney;

    @Override
    public String toString() {
        return "CostBean{" +
                "costTitle='" + costTitle + '\'' +
                ", costDate='" + costDate + '\'' +
                ", costMoney='" + costMoney + '\'' +
                '}';
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public String getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(String costMoney) {
        this.costMoney = costMoney;
    }

    public String getCostTitle() {
        return costTitle;
    }

    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }
}
