// initializing datatables plugin and defining the style of the expiration date column
$(document).ready( function () {
    $('#pantryContentsTable').DataTable( {
        "columnDefs": [
            { "orderable": false, "targets": 2 }
        ],
            "aoColumns": [
            null,
            { "sType": "date-uk" },
            null
        ]
    });

} );

jQuery.extend( jQuery.fn.dataTableExt.oSort, {
    "date-uk-pre": function ( a ) {
        let ukDateA = a.split('-');
        return (ukDateA[2] + ukDateA[1] + ukDateA[0]) * 1;
    },

    "date-uk-asc": function ( a, b ) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "date-uk-desc": function ( a, b ) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
} );