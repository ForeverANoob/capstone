$(document).ready(function() {
  function update_mark_columns() {
    var col_sel = "";
    for (var aId in visibleCols ) {
      var checked = "";
      if (visibleCols[aId][0]) {
        checked = "checked";
        console.log(checked);
      }
      col_sel += "<input id=\"markcol-" + aId + "\" class=\"colselector\" type=\"checkbox\" " + checked + ">" + visibleCols[aId][1] + "</input>";
    }
    $("#column_selection").html(col_sel);
  }

  update_mark_columns();

  $(".colselector").click(function() {
    var aId = (this.id).substring(("markcol-").length);
    var justMadeVisible = visibleCols[aId][0] = !visibleCols[aId][0];
    if (justMadeVisible) {
      $.post(
        "/api/get_assessment/"+courseCode+"/"+courseYear+"/"+aId,

      ).done(function(data) {
        // TODO: parse the JSON list, inserting new columns into each existing
        // row. change negative marks into appropriate text version of the info
        // they are representing (-1 -> exempt, -2 -> absent, anything else?)
        console.log(data);
      }).fail(function() {
        console.log("Couldn't get assessment mark table! #RIP");
      });
    } else {
      $('.' + this.id).remove();
    }
  });
});
