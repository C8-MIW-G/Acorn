// When document is completely loaded
$(document).ready(function () {
    // Find the search form submit and input fields and assign them a custom JavaScript function
    $("#searchForm").submit(function (event) {
        event.preventDefault();
        ajaxSearchProduct();
    });

    $('#searchInput').on('input', function() {
        ajaxSearchProduct();
    });
});

function ajaxSearchProduct() {
    var searchData = {};
    searchData["keywords"] = $("#searchInput").val();

    $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/ajaxSearchProduct",
            data: JSON.stringify(searchData),
            dataType: 'json',
            cache: false,
            timeout: 6000,
            succes: fillTable(),
            error: function (e) {
                console.log("Error fetching AJAX data.");
                fillTable();
            }
        });
}

function fillTable() {
    const newDiv = document.createElement("div");
    const newContent = document.createTextNode("Hi there and greetings!");
    newDiv.appendChild(newContent);
    $("#buttonContainer").replaceAll(newDiv);
}
