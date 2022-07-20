let modal = $('#editRequiredProductModal')

modal.on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget)
    let productId = button.data('product-id')
    let productName = button.data('product-name')
    let productAmount = button.data('product-amount')

    modal.find('#productId').val(productId.toString())
    modal.find('#productName').text(productName)
    modal.find('#productAmount').val(productAmount)
})
