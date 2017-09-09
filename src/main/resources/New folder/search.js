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
  $.ajax({
    type: 'POST',
    url: window.location.origin + '/api/get_search_results',
    data: {searchTerm: $("#searchBar").val()},
    success: function(data) {
      console.log("The REST request worked! The data it got was this:")
      console.log(data)
    }
  });

});
