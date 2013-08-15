// ----------------------------------------------
// related lists

function linkLists(list1, list2, url) {
    $(list1).change(function () {
        var options = [];
        $.getJSON(url, function (result) {
            for (var i = 0; i < result.length; i++) {
                options.push('<option value="',
                    result[i].key, '">',
                    result[i].value, '</option>');
            }
            $(list2).html(options.join(''));
        });
    });
}


// ----------------------------------------------
// ajax form

function createSpan ( errorBlock, fieldName, fieldId ) {
    var span = document.createElement ( "span" ) ;
    span.innerHTML = errorBlock ;
    var spanId = fieldName + "_error" ;
    span.setAttribute ( "id", spanId ) ;
    span.setAttribute ( "style", "display: inline-block;" ) ;
    if ( $ ( "#" + spanId ) != null ) {
        $ ( fieldId ).parent ( ).children ( ).remove ( "#" + spanId ) ;
    }
    return span ;
}

function insertErrors (errors, fieldName, fieldId) {
    var errorBlock = "<ul style='list-style: none; padding-left: 0px;'>";
    errors.forEach(function (error) {
        errorBlock += "<li class='has-error'>" + error + "</li>" ;
    });
    errorBlock += "</ul>";
    var span = createSpan ( errorBlock, fieldName, fieldId ) ;
    $(fieldId).parent().append(span);
}

function submitForm(id) {
    $.ajax({
        url:  $("#"+id).attr("action"),
        type: $("#"+id).attr("method"),
        data: $("#"+id).serialize(),
        error: function(data) {
            var json = window.JSON.parse(data.responseText);
            json.forEach(function (field) {
                var fieldName = field.field;
                var errors = new Array(field.error);
                var fieldId = "#"+fieldName;
                insertErrors ( errors, fieldName, fieldId ) ;
                $(fieldId).addClass("has-error");
                $(fieldId+"_field").addClass("has-error");
                for(s in $(fieldId).parent.siblings) {
                    s.addClass("has-error");
                }
            });
        },
        success : function (data) {
            window.location.replace(data);
        }
    });
}