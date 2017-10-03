$(document).ready(function() l
    function update_mark_columns() {
      for (i = 0; i < columnVisibility.length; i++) {
        $("#column_selection:nth-child("+i+")").prop("checked", columnVisibility[i]);
      }
    }
});
