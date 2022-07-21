let deleteModal = $('#deleteModal')

deleteModal.on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget);
    deleteModal.find('.nameSpan').text(button.data('name'));
    deleteModal.find('#deleteLink').attr('href', '/pantry/' + button.data('id') + '/delete');
})
