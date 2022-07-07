let modal = $('#myModal')

// If the modal is shown
modal.on('show.bs.modal', function (event) {
    // Find the button
    let button = $(event.relatedTarget)

    // From button select data-* objects, extract those values into variables
    let productId = button.data('product-id')
    let productName = button.data('product-name')

    // Inject values into modal
    var modal = $(this)
    modal.find('#productName').text(productName)
    modal.find('#productId').val(productId.toString())

    // Autofocus submit button
    $('submitButton').focus()
})