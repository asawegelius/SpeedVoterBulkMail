
$(function () {
    var new_dialog = function (type, row) {
        var dlg = $("#dialog-form").clone();
                email = dlg.find(("#email")),
                message = dlg.find(("#message"));
        type = type || 'Create';
        var config = {
            autoOpen: true,
            height: 400,
            width: 350,
            modal: true,
            buttons: {
                "Create an account": save_data,
                "Cancel": function () {
                    dlg.dialog("close");
                }
            },
            close: function () {
                dlg.remove();
            }
        };
        if (type === 'Edit') {
            config.title = "Edit e-mail";
            get_data();
            delete (config.buttons['Create an e-mail']);
            config.buttons['Edit account'] = function () {
                row.remove();
                save_data();
            };
        }
        dlg.dialog(config);
        
        
        function get_data() {
            var _email = $(row.children().get(0)).text(),
                    _message = $(row.children().get(1)).text();
            email.val(_email);
            message.val(_message);
        }
        
        
        function save_data() {
            $("#mails tbody").append("<tr>"  + "<td>" + email.val() + "</td>" + "<td>" + message.val() + "</td>" + "<td><a href='' class='edit'>Edit</a></td>" + "<td><span class='delete'><a href=''>Delete</a></span></td>" + "</tr>");
            dlg.dialog("close");
        }
    };
    
    
    $(document).on('click', 'span.delete', function () {
        $(this).closest('tr').find('td').fadeOut(1000,
                function () {
                    // alert($(this).text());
                    $(this).parents('tr:first').remove();
                });
        return false;
    });
    
    $(document).on('click', 'td a.edit', function () {
        new_dialog('Edit', $(this).parents('tr'));
        return false;
    });
    
    $("#create-mail").button().click(new_dialog);
});
