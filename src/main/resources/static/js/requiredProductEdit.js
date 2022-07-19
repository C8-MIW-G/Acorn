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

// COUNTER FIELD IN MODAL
// Updating the value inside the number value
$('.btn-number').click(function(e){
    e.preventDefault();
    inputName = $(this).attr('data-input');
    type      = $(this).attr('data-type');
    var input = $("input[name='" + inputName + "']");
    var currentVal = parseInt(input.val());

    if (!isNaN(currentVal)) {
        if(type == 'minus') {
            if(currentVal > input.attr('min')) {
                input.val(currentVal - 1).change();
            }
            if(parseInt(input.val()) == input.attr('min')) {
                $(this).attr('disabled', true);
            }
        } else if(type == 'plus') {
            if(currentVal < input.attr('max')) {
                input.val(currentVal + 1).change();
            }
            if(parseInt(input.val()) == input.attr('max')) {
                $(this).attr('disabled', true);
            }
        }
    } else {
        input.val(0);
    }
});

$('.input-number').focusin(function(){
    $(this).data('oldValue', $(this).val());
});

// Prevent
$('.input-number').change(function() {
    minValue =  parseInt($(this).attr('min'));
    maxValue =  parseInt($(this).attr('max'));
    valueCurrent = parseInt($(this).val());
    name = $(this).attr('name');

    if(valueCurrent >= minValue) {
        $(".btn-number[data-type='minus'][data-input='"+name+"']").removeAttr('disabled')
    } else {
        $(this).val($(this).data('oldValue'));
    }
    if(valueCurrent <= maxValue) {
        $(".btn-number[data-type='plus'][data-input='"+name+"']").removeAttr('disabled')
    } else {
        $(this).val($(this).data('oldValue'));
    }
});

// Allow only numbers in the input field
$(".input-number").keydown(function (e) {
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
        e.preventDefault();
    }
});


