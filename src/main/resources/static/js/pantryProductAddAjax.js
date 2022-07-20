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

    for (let i = 0; i < result.productDefinitionDTOS.length; i++) {
        let newDiv = document.createElement("div");
        let button = document.createElement("button");
        button.classList.add("pantryProductButton");
        button.type = "button";
        button.dataset.toggle = "modal";
        button.dataset.target = "#productAddModal";
        button.setAttribute("data-product-id", result.productDefinitionDTOS[i].id);
        button.setAttribute("data-product-name", result.productDefinitionDTOS[i].name);
        button.textContent = result.productDefinitionDTOS[i].name;
        newDiv.append(button);
        newButtonContainer.append(newDiv);

        if (i === 0) {
            button.id = "topProduct"
        }
    }

    $("#buttonContainer").replaceWith(newButtonContainer);
}

let topProduct;
function selectFirstProduct() {
    topProduct =  $("#topProduct");
}
