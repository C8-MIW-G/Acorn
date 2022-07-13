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
            url: "/ajaxSearchProduct",
            data: JSON.stringify(searchData),
            dataType: 'json',
            contentType: "application/json",
            cache: false,
            timeout: 6000,
            succes: fillTable(),
            error: function (e) {
                console.log("Error fetching AJAX data.");
            }
        });
}

function fillTable() {
    const newButtonContainer = document.createElement("div");
    newButtonContainer.classList.add("table-container", "scrollbar-thin")

    const newContent = document.createTextNode("Hi there and greetings!");
    newButtonContainer.appendChild(newContent);

    $("#buttonContainer").replaceWith(newButtonContainer);
}
