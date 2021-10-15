$(document).ready(function() {
  sizePowerIds();
  sizeIdentityIds();
  sizeIdentities();
  hideEmptyEntityList();

/*
  hideEmptyHeroList();
  hideEmptyVillianList();
  hideEmptyOrganizationList()
*/

  $('.backBtn').on( 'click', goBackAndRefresh );

  $('.editBtn').on('click' , goToEditEntity);

  $('.deleteBtn').on('click' , goToDeleteEntity);


/*
  $('#navButtonSightings').on('click' , goToSightings);
  $('#navButtonIdentities').on('click' , goToIdentities);
  $('#navButtonPowers').on('click' , goToPowers);
  $('#navButtonOrganizations').on('click' , goToOrganizations);
  $('#navButtonLocations').on('click' , goToLocations);
*/
})


function goBackAndRefresh(){
  //need to have this check if referrer was an "add" page and if so
  //return user to applicable list
  window.location = document.referrer;
  return false;
}

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

//gets length of powerIds and sets it's size to equal # items inside
function sizePowerIds(){
  try {
    var powerIds = document.getElementById('powerIds');
    powerIds.size = powerIds.length;
  } catch ( TypeError ){
    console.log("no power ids found to resize")
  }
}

function sizeIdentityIds(){
  try {
    var identityIds = document.getElementById('identityIds');
    identityIds.size = (identityIds.length + identityIds.childElementCount);
  } catch ( TypeError ){
    console.log("no identity ids found to resize")
  }
}

function sizeIdentities(){
  try {
    var identitiesList = document.getElementById('identities');
    identitiesList.size = (identitiesList.length + identitiesList.childElementCount);
  } catch ( TypeError ){
    console.log("no identity list found to resize")
  }

}

function hideEmptyEntityList(){
  var entityList = $('.entityList');
  //console.log(heroList.children().length);
  entityList.each( function(){
    if ($(this).children().length == 1){
      $(this).hide();
    }
  })
}

/*
function hideEmptyHeroList(){
  var heroList = $('.heroList');
  //console.log(heroList.children().length);
  if (heroList.children().length == 1){
    heroList.hide();
  }
}

function hideEmptyVillianList(){
  var villianList = $('.villianList');
  //console.log(villianList.children().length);
  if (villianList.children().length == 1){
    villianList.hide();
  }
}

function hideEmptyOrganizationList(){
  var organizationList = $('.organizationList');
  if (organizationList.children().length == 1) {
    organizationList.hide();
  }
}
*/
