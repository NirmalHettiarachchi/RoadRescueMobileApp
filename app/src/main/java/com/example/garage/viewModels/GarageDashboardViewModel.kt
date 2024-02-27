package com.example.garage.viewModels

import java.time.Period

class GarageDashboardViewModel(
    private var _garageName: String,
    private var _date: Period,
    private var _issue: String,
    private var _description: String,
    private var _serviceFee: Double,

    ) {

    fun getGarageName(): String {
        return this._garageName
    }

    fun setGarageName(garage: String) {
        this._garageName = garage
    }

    fun getDate(): Period {
        return this._date
    }

    fun setDate(date: Period) {
        this._date
    }

    fun getStatus(): String {
        return this._issue
    }

    fun setStatus(issue: String) {
        this._issue = issue
    }

    fun getAssignServiceProvider(): String {
        return this._description
    }

    fun setAssignServiceProvider(description: String) {
        this._description = description
    }

    fun getServiceFee(): Double {
        return this._serviceFee
    }

    fun setServiceFee(serviceFee: Double) {
        this._serviceFee
    }


}