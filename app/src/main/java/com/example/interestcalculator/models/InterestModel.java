package com.example.interestcalculator.models;

public class InterestModel{

    String id,currentDate,givenDate,returnDate,principalAmount,durationPeriod,
            interest,interestAmount,interestType,totalAmount,recordName,cityName,remarks,intrimePayment,remianingAmount,fid;

    public InterestModel() {
    }

    public InterestModel(String currentDate, String givenDate,String returnDate,
                         String principalAmount, String durationPeriod,
                         String interest, String interestAmount,
                         String interestType,String totalAmount) {

        this.currentDate = currentDate;
        this.givenDate = givenDate;
        this.returnDate = returnDate;
        this.principalAmount = principalAmount;
        this.durationPeriod = durationPeriod;
        this.interest = interest;
        this.interestAmount = interestAmount;
        this.interestType = interestType;
        this.totalAmount = totalAmount;
    }

    public InterestModel(String currentDate, String givenDate,
                         String returnDate, String principalAmount, String durationPeriod,
                         String interest, String interestAmount, String interestType,
                         String totalAmount, String recordName, String cityName, String remarks) {

        this.currentDate = currentDate;
        this.givenDate = givenDate;
        this.returnDate = returnDate;
        this.principalAmount = principalAmount;
        this.durationPeriod = durationPeriod;
        this.interest = interest;
        this.interestAmount = interestAmount;
        this.interestType = interestType;
        this.totalAmount = totalAmount;
        this.recordName = recordName;
        this.cityName = cityName;
        this.remarks = remarks;
    }

    public InterestModel(String fid,String currentDate, String principalAmount,
                         String durationPeriod, String totalAmount, String intrimePayment,String remarks) {
        this.fid = fid;
        this.currentDate = currentDate;
        this.principalAmount = principalAmount;
        this.durationPeriod = durationPeriod;
        this.totalAmount = totalAmount;
        this.intrimePayment = intrimePayment;
        this.remarks = remarks;
    }


    public String getId() {
        return id;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getGivenDate() {
        return givenDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getPrincipalAmount() {
        return principalAmount;
    }

    public String getDurationPeriod() {
        return durationPeriod;
    }

    public String getInterest() {
        return interest;
    }

    public String getInterestAmount() {
        return interestAmount;
    }

    public String getInterestType() {
        return interestType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getRecordName() {
        return recordName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getIntrimePayment() {
        return intrimePayment;
    }

    public String getRemianingAmount() {
        return remianingAmount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public void setGivenDate(String givenDate) {
        this.givenDate = givenDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setPrincipalAmount(String principalAmount) {
        this.principalAmount = principalAmount;
    }

    public void setDurationPeriod(String durationPeriod) {
        this.durationPeriod = durationPeriod;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setInterestAmount(String interestAmount) {
        this.interestAmount = interestAmount;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setIntrimePayment(String intrimePayment) {
        this.intrimePayment = intrimePayment;
    }

    public void setRemianingAmount(String remianingAmount) {
        this.remianingAmount = remianingAmount;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
