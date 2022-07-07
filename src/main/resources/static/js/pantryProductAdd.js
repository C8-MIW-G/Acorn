let modal = $('#myModal')

// If the modal is shown
modal.on('show.bs.modal', function (event) {
    // Find the button
    let button = $(event.relatedTarget)

    // From button select data-* objects, extract those values into variables
    let productId = button.data('product-id')
    let productName = button.data('product-name')

    // Inject values into modal
    modal.find('#productName').text(productName)
    modal.find('#productId').val(productId.toString())
})

modal.on('shown.bs.modal', function () {
    // Autofocus submit button
    modal.find('#submitButton').focus();
})