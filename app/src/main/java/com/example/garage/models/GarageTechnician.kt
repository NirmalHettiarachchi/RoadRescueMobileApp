package com.example.garage.models

class GarageTechnician {
    private var _techId:String=""
    private var _techFirstName: String = ""
    private var _techLastName: String = ""
    private var _techContactNumber = ""
    private var _techExpertiseAreas: List<String> = emptyList()
    private var _techStatus: Int = 0





    constructor(
        _techFirstName: String,
        _techLastName: String,
        _techContactNumber: String,
        _techExpertiseAreas: List<String>,
        _techStatus: Int,
    ) {
        this._techFirstName = _techFirstName
        this._techLastName = _techLastName
        this._techContactNumber = _techContactNumber
        this._techExpertiseAreas = _techExpertiseAreas
        this._techStatus = _techStatus
    }

    constructor(
        _techId: String,
        _techFirstName: String,
        _techLastName: String,
        _techContactNumber: String,
        _techExpertiseAreas: List<String>,
        _techStatus: Int,
    ) {
        this._techId = _techId
        this._techFirstName = _techFirstName
        this._techLastName = _techLastName
        this._techContactNumber = _techContactNumber
        this._techExpertiseAreas = _techExpertiseAreas
        this._techStatus = _techStatus
    }

    constructor()


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

    fun getTechStatus(): Int {
        return this._techStatus
    }

    fun setTechStatus(status: Int) {
        this._techStatus = status
    }

    fun getTechContactNumber(): String {
        return this._techContactNumber
    }

    fun setTechContactNumber(contactNumber: String) {
        this._techContactNumber = contactNumber
    }

    fun getTechExpertiseAreas(): List<String> {
        return this._techExpertiseAreas
    }

    fun setTechExpertiseAreas(techExpertiseAreas: List<String>) {
        this._techExpertiseAreas
    }
}


data class Tech(val techName: String, val techStatus: String)
