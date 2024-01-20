package com.example.garage.viewModels

class GarageTechnician {

    private var _techFirstName: String = ""
    private var _techLastName: String = ""
    private var _techStatus: String = ""
    private var _techContactNumber = ""
    private var _techExpertiseAreas: List<String>? = null

    constructor(
        _techFirstName: String,
        _techLastName: String,
        _techStatus: String,
        _techContactNumber: String,
        _techExpertiseAreas: List<String>?,
    ) {
        this._techFirstName = _techFirstName
        this._techLastName = _techLastName
        this._techStatus = _techStatus
        this._techContactNumber = _techContactNumber
        this._techExpertiseAreas = _techExpertiseAreas
    }




    fun getTechFirstName(): String {
        return this._techFirstName
    }

    fun setTechFirstName(techFirstName: String) {
        this._techFirstName = techFirstName
    }

    fun getTechLastName(): String {
        return this._techLastName
    }

    fun setTechLastName(techLastName: String) {
        this._techLastName = techLastName
    }

    fun getTechStatus(): String {
        return this._techStatus
    }

    fun setTechStatus(status: String) {
        this._techStatus = status
    }

    fun getTechContactNumber(): String {
        return this._techContactNumber
    }

    fun setTechContactNumber(contactNumber: String) {
        this._techContactNumber = contactNumber
    }

    fun getTechExpertiseAreas(): List<String>? {
        return this._techExpertiseAreas
    }

    fun setTechExpertiseAreas(techExpertiseAreas: List<String>?) {
        this._techExpertiseAreas
    }
}