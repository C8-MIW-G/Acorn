// CONFIRMATION MODAL
let modal = $('#productAddModal')

// Before the modal is shown
modal.on('show.bs.modal', function (event) {
    // Find the button and extract its values
    let button = $(event.relatedTarget)
    let productId = button.data('product-id')
    let productName = button.data('product-name')
    // Inject values into modal
    modal.find('#productName').text(productName)
    modal.find('#productId').val(productId.toString())
})

// After the modal is shown
modal.on('shown.bs.modal', function () {
    modal.find('#submitButton').focus();
}) 
