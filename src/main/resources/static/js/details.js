$(document).ready(function() {
  hideEditAndDelete();
  $("input").setAttribute(readonly);
})

function hideEditAndDelete(){
  var editBtns = $('.editBtnWrapper');
  var deleteBtns = $('.deleteBtnWrapper');

  editBtns.attr("hidden" , true);
  deleteBtns.attr("hidden" , true);
}
