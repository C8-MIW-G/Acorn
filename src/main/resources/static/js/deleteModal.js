let deleteModal = $('#deleteModal')

deleteModal.on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget)
    let pantryId = button.data('pantry-id')
    let requirementId = button.data('product-id')
    let deleteURL = "/pantry/" + pantryId + "/stock-requirements/" + requirementId + "/delete"
    deleteModal.find('#deleteLink').attr("href", deleteURL)
})
