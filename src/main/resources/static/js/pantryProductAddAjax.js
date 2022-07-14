// When document is completely loaded
$(document).ready(function () {
    // Find the search form submit and input fields and assign them a custom JavaScript function
    $("#searchForm").submit(function (event) {
        event.preventDefault();
    });

    $('#searchInput').on('input', function() {
        ajaxSearchProduct();
    });
});

function ajaxSearchProduct() {
    let keyword = $("#searchInput").val();

    $.ajax({
        type: "POST",
        url: "/ajaxSearchProduct",
        data: JSON.stringify(keyword),
        dataType: "json",
        contentType: "application/json",
        cache: false,
        timeout: 6000,
        success: function (result) {
            console.log("AJAX Success");
            fillTable(result);
        },
        error: function () {
            console.log("AJAX Error");
        }
    });
}

function fillTable(result) {
    let newButtonContainer = document.createElement("div");
    newButtonContainer.id = "buttonContainer";
    newButtonContainer.classList.add("table-container", "scrollbar-thin");

    result.productDefinitionDTOS.forEach(productDefinitionDTO => {
        let newDiv = document.createElement("div");
        let button = document.createElement("button");
        button.classList.add("pantryProductButton");
        button.setAttribute("type", "button");
        button.setAttribute("data-toggle", "modal");
        button.setAttribute("data-target", "productAddModal");
        button.setAttribute("th:product-data-id", productDefinitionDTO.id);
        button.setAttribute("th:data-product-name", productDefinitionDTO.name);
        button.textContent = productDefinitionDTO.name;
        newDiv.append(button);
        newButtonContainer.append(newDiv);
    });

    $("#buttonContainer").replaceWith(newButtonContainer);
}
