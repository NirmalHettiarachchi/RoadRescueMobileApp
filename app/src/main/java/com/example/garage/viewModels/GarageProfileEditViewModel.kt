package com.example.garage.viewModels

class GarageProfileEditViewModel (
    private var _checkBoxName:String,
    private  var _isSelected:Boolean
){

    fun getCheckBoxName():String{
        return this._checkBoxName
    }

    fun setCheckBoxName(checkBoxName:String){
       this._checkBoxName=checkBoxName
    }

    fun getIsSelected():Boolean{
        return this._isSelected
    }

    fun setIsSelected(isSelected:Boolean){
        this._isSelected=isSelected
    }

}