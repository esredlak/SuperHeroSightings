$(document).ready( function() {
  $(".editBtn").on("click" , goToEditEntity);

  $(".deleteBtn").on("click" , goToDeleteEntity);

  $("#sightingDateFilter").change(filterSightingsByDate);

  $('#showAllSightings').on("click" , clearSightingFilter);
})

function goToEditEntity() {
  var id = $('#entityId').val();
  var url = "edit/" + id;
  window.location.href = url;
}

function goToDeleteEntity() {
  var id = $('#entityId').val();
  var url = "delete/" + id;
  window.location.href = url;
}

function filterSightingsByDate(){
  //here this refers to the #sightingDateFilter that changed
  var dateToFilterBy = $(this).val();
  //console.log(dateToFilterBy, 'change');
  //use sightingRow class to select all sightings on page
  const sightings = $('.sightingRow');
  //iterate through that collection pulling out each sighting
  sightings.each( function(){
    //here this refers to each object in sightings
    var sightingDate = $(this).find('.hiddenSightingDate').val();
    //then compare that sighting's hiddenSightingDate.val()
    //if the two match, then show them
    //else hide the entire sightingWrapper
    if (sightingDate == dateToFilterBy){
      $(this).show();
      //console.log(sightingDate , dateToFilterBy , 'matchy');
    } else {
      $(this).hide();
      //console.log(sightingDate , dateToFilterBy , 'no matchy');
    }
  })
}

function clearSightingFilter(){
  const sightings = $('.sightingRow');
  sightings.show();
  event.preventDefault()
}
