$(document).ready(function() {

  hideEditAndDelete();

})

function hideEditAndDelete(){
  var editBtns = $('.editBtnWrapper');
  var deleteBtns = $('.deleteBtnWrapper');

  editBtns.attr("hidden" , true);
  deleteBtns.attr("hidden" , true);
}
