const API_URL = "http://localhost:8082";
function clickOption(link) {
    console.log("hi")
    var elements = document.querySelectorAll(".sideNavLink");

    // Remove the "active" class from all links
    elements.forEach(function (element) {
        element.classList.remove("active");
    });

    // Add the "active" class to the clicked link
    link.classList.add("active");
}

// supportTicket.html table row click
const tableRows = document.querySelectorAll('tr[data-href]');
tableRows.forEach(row => {
    row.addEventListener('click', () => {
        window.location.href = row.getAttribute('data-href');
    });
});


/*
var ticketId=null;

function loadSupportTickets(){
    console.log("call")
    $.ajax({
        url:API_URL+"/RoadRescue/customerSupport",
        method: "GET",
        success: function (res) {
            if (res.status==200){
                ticketId=res.data.ticketId;
                console.log(ticketId);
                alert("hye");
            }
        }
    });
}

console.log(ticketId);*/
