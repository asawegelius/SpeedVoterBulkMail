<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page import="se.wegelius.svbulkmailclient.model.Mail"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="se.wegelius.svbulkmailclient.client.MailClient"%>
<!DOCTYPE html>
<html> 
    <head>
        <meta charset="utf-8">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="" />
        <meta name="author" content=""/>
        <link rel="icon" href="assets/img/favicon.ico"/>

        <title>SV Bulk Mail</title>

        <!--  CSS -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet"/>

        <link href="assets/css/bootstrap-theme.min.css" rel="stylesheet">

        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css"/>
        <link rel="stylesheet" href="assets/css/src/docs.css" type="text/css" >
        <link rel="stylesheet" href="assets/css/src/docs.css" type="text/css"/>
        <link rel="stylesheet" href="assets/css/src/pygments-manni.css" type="text/css"/>

        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <link href="assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet"/>

        <!-- Custom styles for this template -->
        <link href="assets/css/sticky-footer-navbar.css" rel="stylesheet"/>


        <link href="assets/css/main.css" rel="stylesheet"/>


        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

        <link href="assets/css/docs.min.min.css" rel="stylesheet">

    </head>

    <body onload="drawTable(allMails)">
        <%
            Mail newMail = null;
            MailClient client = new MailClient();
            String jsonMails = client.getJson().getEntity(String.class);
            List<Mail> mails = new Gson().fromJson(jsonMails, new TypeToken<List<Mail>>() {
            }.getType());
        %>
        <!-- Fixed navbar -->
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">SV Bulk Mail</a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Home</a></li>
                        <li><a href="#about">About</a></li>
                        <li><a href="#contact">Contact</a></li>

                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </nav>

        <!-- Begin page content -->
        <div class="container">

            <div id="dialog-form" title="Create new mail">
                <p class="validateTips">
                    All form fields are required.</p>       
                <form name="create_edit_form" id="create_edit_form" method="POST" action="SVClientServlet">
                    <fieldset>
                        <input type="hidden" name="id" id="id" class="id" value=""/>
                        <label for="email">
                            Email</label>
                        <input type="text" name="email" id="email" value="" class="email text ui-widget-content ui-corner-all" />
                        <label for="message">
                            Message</label>
                        <input  name="message" id="message" value="" class="message text ui-widget-content ui-corner-all" />
                        <input type="hidden" name="content_type" id="content_type" value=""/>
                        <input type="hidden" name="method_type" id="method_type" value=""/>
                    </fieldset>
                </form>
            </div>
            <div id="mails-contain" class="ui-widget">
                <h1> Existing E-mails: </h1>
                
            <form method="POST" action="SVSendMails">
                <button value="submit" class="button">Send all mails </button>
            </form>
                <table id="mails" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th>Id</th>
                            <th>E-mail </th>
                            <th>Message</th>
                            <th> Action</th>
                            <th> Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Mail m : mails) {
                        %>
                        <tr> 
                            <td class="id"><%out.write(Integer.toString(m.getEmailsId()));%></td>
                            <td class="email"><%out.write(m.getEmail());%></td>
                            <td class="message"><%out.write(m.getMessage());%></td>
                            <td class="edit"><span class="edit"><a class="edit action" href="">Edit</a> </span></td>
                            <td class="delete"><span class="delete"><a class="action" href="">Delete</a></span>   </td>
                        </tr>
                        <%}
                        %>
                    </tbody>
                </table>
                    <button id="create-mail" class="button">
                Create new e-mail</button> 

            </div>
        </div>      


        <footer class="footer">
            <div class="container">
                <p class="text-muted">&copy; 2016 Åsa Wegelius</p>
            </div>
        </footer>


        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="assets/js/vendor/jquery.min.js"><\/script>');</script>
        <script src="assets/js/bootstrap.min.js"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="assets/js/ie10-viewport-bug-workaround.js"></script>



        <script src="http://code.jquery.com/jquery-1.8.3.js" type="text/javascript"></script>
        <script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js" type="text/javascript"></script>

        <script>

        $(function () {
            var new_dialog = function (type, row) {
                var dlg = $("#dialog-form").clone();
                id = dlg.find("#id"),
                        email = dlg.find(("#email")),
                        message = dlg.find(("#message")),
                        content_type = dlg.find("#content_type"),
                        method_type = dlg.find("#method_type");
                type = type || 'Create';
                var config = {
                    autoOpen: true,
                    height: 400,
                    width: 350,
                    modal: true,
                    buttons: {
                        "Create an e-mail": save_data,
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
                    config.buttons['Edit e-mail'] = function () {
                        //row.remove();
                        //save_data();
                        content_type.val("application/json");
                        method_type.val("PUT");
                        $("#create_edit_form").submit();
                    };
                }
                dlg.dialog(config);


                function get_data() {
                    var _id = $(row.children().get(0)).text(),
                            _email = $(row.children().get(1)).text(),
                            _message = $(row.children().get(2)).text();
                    id.val(_id);
                    email.val(_email);
                    message.val(_message);
                }



                function save_data() {

                    content_type.val("application/json");
                    method_type.val("POST");
                    $("#create_edit_form").submit();
                    //$("#mails tbody").append("<tr>" + "<td>" + id.val() + "</td>" + "<td>" + email.val() + "</td>" + "<td>" + message.val() + "</td>" + "<td><a href='' class='edit'>Edit</a></td>" + "<td><span class='delete'><a href=''>Delete</a></span></td>" + "</tr>");
                    dlg.dialog("close");
                }
            };



            $(document).on('click', 'span.delete', function () {
                var delid;
                var delemail;
                var delmessage;
                $(this).closest('tr').find('td').fadeOut(1000,
                        function () {
                            if ($(this).attr('class') == "id") {
                                delid = $(this).text();
                            } else if ($(this).attr('class') == "email") {
                                delemail = $(this).text();
                            } else if ($(this).attr('class') == "message") {
                                delmessage = $(this).text();
                            }
                            jQuery.ajax({
                                url: 'http://188.181.85.75/SVBulkMail/rest/mail/plain/delete/' + delid,
                                type: "DELETE",
                                contentType: "application/plain; charset=utf-8",
                                success: function () {
                                },
                                error: function (msg) {
                                }
                            });
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

        </script>

    </body>
</html>
