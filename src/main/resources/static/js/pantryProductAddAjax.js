// When document is completely loaded
$(document).ready(function () {
    // Find the search form submit and input fields and assign them a custom JavaScript function
    $("#searchForm").submit(function (event) {
        event.preventDefault();
        topProduct.focus();
    });

    $('#searchInput').on('input', function() {
        ajaxSearchProduct();
        selectFirstProduct();
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
            selectFirstProduct();
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

    let counter = 0;
    result.productDefinitionDTOS.forEach(productDefinitionDTO => {
        let newDiv = document.createElement("div");
        let button = document.createElement("button");
        button.classList.add("pantryProductButton");

        if (counter === 0) {
            button.id = "topProduct"
        }

        button.type = "button";
        button.dataset.toggle = "modal";
        button.dataset.target = "#productAddModal";
        button.setAttribute("data-product-id", productDefinitionDTO.id);
        button.setAttribute("data-product-name", productDefinitionDTO.name);
        button.textContent = productDefinitionDTO.name;
        newDiv.append(button);
        newButtonContainer.append(newDiv);

        counter++;
    });

    $("#buttonContainer").replaceWith(newButtonContainer);
}

let topProduct;
function selectFirstProduct() {
    topProduct =  $("#topProduct");
}
