
$(document).ready(function(){
  $("#searchBar").focus();

  /* clicking on search button or pressing enter in the search bar searches */

  // i got that feeling you get when you read a word too often...
  // search
  // search
  // search
  // ever notice how weird that word is? like all words, they are constructs of
  // the human mind, and are thus fallible, imperfect or otherwise different
  // from the theoretical ideals we impose on our most basic mental functions.
  // one day, all of these constructs will be gone from your mind, from mine.
  // we are but flesh and bone, mortal entities with an expiration date before
  // t + 100 years.
  // DEEPressing.

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

})
