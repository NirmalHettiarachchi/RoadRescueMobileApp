package eu.tutorials.roadrescuecustomer.models

data class Profile(
    var name: String,
    var email: String?,
    var phoneNumber: String,
    var numOfServiceRequests: Int
)

class ProfileRepository() {

    private var _profile = Profile(
        "Nirmal Hettiarachchi",
        "nirmalhettiarachchi@gmail.com",
        "+94 768879830",
        2)

    fun getProfile() = _profile

    fun setProfile(name: String, email: String?, phoneNumber: String, numOfServiceRequests: Int) {
        _profile.name = name
        _profile.email = email
        _profile.phoneNumber = phoneNumber
        _profile.numOfServiceRequests = numOfServiceRequests
    }
}