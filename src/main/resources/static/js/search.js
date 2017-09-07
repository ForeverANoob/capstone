$(document).ready(function() {
  $("#searchBar").focus();

  function doSearch() {
    var searchTerm = $("#searchBar").val();
    window.location.href = window.location.origin + "/search/" + searchTerm;
  }

  $("#searchButton").click(doSearch);
  $("#searchBar").keydown(function(e) {
    if (e.keyCode == 13) {
      doSearch();
    }
  });

  /* TODO: load search results via the REST api */

});
