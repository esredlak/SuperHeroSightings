document.addEventListener( "DOMContentLoaded", () => {
   let wrapper = document.querySelector( ".powerDescription" );
   let options = {
      // Options go here
      ellipsis: "\u2026",
      watch: true
   };
   new Dotdotdot( wrapper, options );
});
