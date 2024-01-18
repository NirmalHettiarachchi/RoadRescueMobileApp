const submitLogin = (e, formData) => {
    console.log("customer support login");
    e.preventDefault();

    const phone = formData?.phone?.value;
    const otp = formData?.opt?.value;

    if (!phone) {
        alert("Please enter phone number");
        return;
    } else if (!otp || otp.length < 6) {
        alert("Please enter otp");
        return;
    }
    // Confirm OTP first
    if (otp.length >= 6) {
        console.log("test 1");
        const data = {
            mobileNo: phone,
            option: "CONFIRM_OTP",
            otp: otp
        }

        $.ajax({
            url: API_URL + "/RoadRescue/otp",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (res) {
                console.log("test 2")
                if (res.status == 200) {
                    console.log("test 3");
                    if (res.code === "00") {
                        console.log("test 4");
                        // Success
                        const loginData = {
                            mobileNo: phone,
                            option: "LOGIN"
                        };
                        // Login and get token
                        console.log("Login and get token");

                        if (randomOTP==otp){
                            alert("Successfully login");
                            window.location.href = "../../../../src/Pages/Csupport/dashboard.html";
                        }else {
                            alert("OTP is incorrect..!");
                            window.location.reload();
                        }


                        /*$.ajax({
                            url: API_URL + "/RoadRescue/customerSupport",
                            method: "POST",
                            contentType: "application/json",
                            data: JSON.stringify(loginData),
                            success: function (res) {
                                console.log("536544");
                                if (res.status == 200) {
                                    console.log("customer Servlet ok ");
                                    alert(res.message);
                                    localStorage.setItem("token", res?.data?.token);
                                    localStorage.setItem("customerSupporter", JSON.stringify(res?.data?.customerSupporter));
                                    window.location.href = "../../../Pages/Csupport/dashboard.html";
                                } else {
                                    alert(res.data);
                                    window.location.reload();
                                }
                            }, error: function (ob, textStatus, error) {
                                alert(textStatus);
                            }
                        });*/
                    } else {
                        console.log("otp error");
                        alert(res.message);
                        if (res.code !== "03") {
                            window.location.reload();
                        }
                    }
                } else {
                    console.log("otp ekat kalin error eka  error");
                    alert(res.data);
                    window.location.reload();
                }
            }, error: function (ob, textStatus, error) {
                alert(textStatus);
            }
        });
    }
};