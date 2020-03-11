/**
 * Particleground demo
 * @author Jonathan Nicol - @mrjnicol
 */

$(document).ready(function() {
  $('#particles').particleground({
    dotColor: 'rgba(255,255,255,0.22)',
    lineColor: 'rgba(255,255,255,0.1)'
  });
  $('.intro').css({
    'margin-top': -($('.intro').height() / 2)
  });
});