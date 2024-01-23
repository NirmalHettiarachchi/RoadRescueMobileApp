package com.example.garage.viewModels

class GarageTechnician {
    private var _techId:String=""
    private var _techFirstName: String = ""
    private var _techLastName: String = ""
    private var _techStatus: String = ""
    private var _techContactNumber = ""
    private var _techExpertiseAreas: List<String>? = null

    constructor(
        _techId: String,
        _techFirstName: String,
        _techLastName: String,
        _techStatus: String,
        _techContactNumber: String,
        _techExpertiseAreas: List<String>?,
    ) {
        this._techId = _techId
        this._techFirstName = _techFirstName
        this._techLastName = _techLastName
        this._techStatus = _techStatus
        this._techContactNumber = _techContactNumber
        this._techExpertiseAreas = _techExpertiseAreas
    }

    constructor(
        _techId: String,
        _techFirstName: String,
        _techLastName: String,
        _techContactNumber: String,
    ) {
        this._techId = _techId
        this._techFirstName = _techFirstName
        this._techLastName = _techLastName
        this._techContactNumber = _techContactNumber
    }


    fun getTechId(): String {
        return this._techId
    }

    fun setTechId(techId: String) {
        this._techId = techId
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