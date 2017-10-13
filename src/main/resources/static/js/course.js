$(document).ready(function() {

  $('#importPplsoftButton').click(function(){
    $.ajax({
      url: '/api/upload_pplsoft/'+ courseYear +'/'+ courseCode,
      type: 'POST',
      data: new FormData($('#pplsoftFileForm')[0]),
      // Tell jQuery not to process data or worry about content-type
      // You *must* include these options!
      cache: false,
      contentType: false,
      processData: false,
    }).done(function() {
      $("#importPplsoftModal").modal("toggle");
    });
  });

  $("input[name$='assType']").click(function() {
    $("div.newass_options").hide();
    $("#newass_options_" + $(this).val()).show();
  });

  $("#setCoordinatorButton").click(function() {
    $.ajax({
      url: '/api/set_coord/'+ courseYear +'/'+ courseCode +'/'+ $("#coordinatorId").val(),
      type: 'POST',
      data: new FormData($('#pplsoftFileForm')[0]),
      // Tell jQuery not to process data or worry about content-type
      // You *must* include these options!
      cache: false,
      contentType: false,
      processData: false,
    }).done(function(response) {
      if (response == "success") {
        console.log("--0 hijab420");
        $("#setCoordinatorModal").modal("toggle");
      } else {

        console.log("hkjsadf");
        $("#couldntSetCoordinator").stop();
        $("#couldntSetCoordinator").show();
        $("#couldntSetCoordinator").delay(969).fadeOut(420);
      }
    });
    
  });

  function update_check_marks_and_header() {
    var col_sel = "";
    for (var aId in visibleCols ) {
      var checked = "";
      if (visibleCols[aId][0]) {
        checked = "checked";
      }
      col_sel += "<input id=\"markcol-" + aId + "\" class=\"colselector\" type=\"checkbox\" " + checked + ">" + visibleCols[aId][1] + "</input>";

      $("#header_row").append("<th class=\"markcol-" + aId + "\">" + visibleCols[aId][1] + "</th>");
    }
    $("#column_selection").html(col_sel);
  }

  update_check_marks_and_header(); // initial check-mark update. should also be called when.... not sure when else but w/e.

  function update_column(aId) {
      $.post(
        "/api/get_assessment/"+courseCode+"/"+courseYear+"/"+aId,

      ).done(function(data) {
        // TODO: parse the JSON list, inserting new columns into each existing
        // row. change negative marks into appropriate text version of the info
        // they are representing (-1 -> exempt, -2 -> absent, anything else?)

        /* ---- Updating the header ---- */
        var aid_search_done = false;
        $("#header_row").children().each(function(i) {
          console.log("----", $(this).attr("class"));
          if (!aid_search_done && !!$(this).attr("class")) {
            var thisAssId = parseInt( $(this).attr("class").substring(("markcol-").length) );
            console.log("at least once");
            if (thisAssId >= aId) {
              console.log("at least once");
              aid_search_done = true;
              if (thisAssId > aId) {
                $(this).before("<th class=\"markcol-" + aId + "\">" + visibleCols[aId][1] + "</th>");
              }
            }
          }
        });
        if (!aid_search_done) {
            $("#header_row").append("<th class=\"markcol-" + aId + "\">" + visibleCols[aId][1] + "</th>");
        }

        /* ---- Updating the mark columns ---- */
        for (stunum in data) {
          if (!$("#markrow-"+stunum).length) { // if there is no markrow for this student number
            var stunum_found = false;
            $("#marktable-body").children().each(function(i) { // search through and insert in correct location
              if (!stunum_found) {
                if ($(this).attr("id").substring(("markrow-").length) > stunum) {
                  $(this).before("<tr id=\"markrow-" + stunum + "\"><td>" + stunum.toUpperCase() + "</td></tr>");
                  stunum_found = true;
                }
              }
            });
            if (!stunum_found) {
              $("#marktable-body").append("<tr id=\"markrow-" + stunum + "\"><td>" + stunum.toUpperCase() + "</td></tr>");
            }
          }
          var col_found = false;
          $("#markrow-" + stunum).children().each(function(i) {
            if (!col_found && !!$(this).attr("class")) {
              var thisAssId = parseInt( $(this).attr("class").substring(("markcol-").length) );
              if (thisAssId >= aId) {
                col_found = true;

                if (thisAssId > aId) {
                  $(this).before("<td class=\"markcol-" + aId + "\">" + data[stunum] + "</td>");
                } else {
                  $(this).html("<td class=\"markcol-" + aId + "\">" + data[stunum] + "</td>");
                }
              }
            }
          });

          if (!col_found) {
            $("#markrow-" + stunum).append("<td class=\"markcol-" + aId + "\">" + data[stunum] + "</td>");
          }
        }
      }).fail(function() {
        console.log("Couldn't get assessment mark table! #RIP");
      });
  }

  for (var aId in visibleCols ) {
    update_column(aId);
  }

  $(".colselector").click(function() {
    var aId = (this.id).substring(("markcol-").length);
    var justMadeVisible = visibleCols[aId][0] = !visibleCols[aId][0];
    if (justMadeVisible) {
      update_column(aId);
    } else {
      $('.' + this.id).remove();
    }
  });
});
