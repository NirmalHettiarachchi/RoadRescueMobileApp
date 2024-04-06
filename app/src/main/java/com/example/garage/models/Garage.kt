package com.example.garage.models

class Garage {
    private var _garageId:String=""
    private var _garageFirstName:String=""
    private var _garageLastName:String=""
    private var _garageContactNumber:String=""
    private var _garageStatus=0
    private var _garageEmail:String=""
    private var _garageRating:Double=0.0
    private var _type:String=""

    constructor()
    constructor(
        _garageId: String,
        _garageFirstName: String,
        _garageLastName: String,
        _garageContactNumber: String,
        _garageStatus: Int,
        _garageEmail: String,
        _garageRating: Double,
        _type: String,
    ) {
        this._garageId = _garageId
        this._garageFirstName = _garageFirstName
        this._garageLastName = _garageLastName
        this._garageContactNumber = _garageContactNumber
        this._garageStatus = _garageStatus
        this._garageEmail = _garageEmail
        this._garageRating = _garageRating
        this._type = _type
    }


    fun getGarageId():String{
        return this._garageId
    }

    fun setGarageId(garageId:String){
        this._garageId=garageId
    }

    fun getGarageFirstName():String{
        return this._garageFirstName
    }

    fun setGarageFirstName (garageFirstName:String){
        this._garageFirstName =garageFirstName
    }

    fun getGarageLastName():String{
        return this._garageLastName
    }

    fun setGarageLastName(garageLastName:String){
        this._garageLastName=garageLastName
    }

    fun getGarageContactNumber():String{
        return this._garageContactNumber
    }

    fun setGarageContactNumber(contactNumber:String){
        this._garageContactNumber=contactNumber
    }

    fun getGarageStatus():Int{
        return this._garageStatus
    }

    fun setGarageStatus(status:Int){
        this._garageStatus=status
    }

    fun getGarageEmail():String{
        return this._garageEmail
    }

    fun setGarageEmail(email:String){
        this._garageEmail=email
    }

    fun getGarageRating():Double{
        return this._garageRating
    }

    fun setGarageRating(garageRate:Double){
        this._garageRating=garageRate
    }

    fun getGarageType():String{
        return this._type
    }

    fun setGarageType(type:String){
        this._type=type
    }
}


// type eken mokkda kiyawenne
// garage ownwerge name eka database danna
//