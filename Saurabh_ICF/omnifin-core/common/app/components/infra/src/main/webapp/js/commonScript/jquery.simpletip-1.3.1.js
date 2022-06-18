$(document).ready(function() {

			// Select all anchor tag with rel set to tooltip
				$('a[rel=tooltip]')
						.mouseover(function(e) {

							// Grab the title attribute's value and assign it to
							// a variable
								var tip = $(this).attr('id');

								// Remove the title attribute's to avoid the
								// native tooltip from the browser

								// Append the tooltip template and its value
								$(this).append('<div id="tooltip"><div class="tipHeader"></div><div class="tipBody">' + tip + '</div><div class="tipFooter"></div></div>');

								// Show the tooltip with faceIn effect
								$('#tooltip').fadeIn('500');
								$('#tooltip').fadeTo('10', 0.9);

							}).mousemove(function(e) {

							// Keep changing the X and Y axis for the tooltip,
							// thus, the tooltip move along with the mouse
								$('#tooltip').css('top', e.pageY + -100);
								$('#tooltip').css('left', e.pageX + 10);

							}).mouseout(function() {

							// Put back the title attribute's value
								$(this).attr('id',$('.tipBody').html());

								// Remove the appended tooltip template
								$(this).children('div#tooltip').remove();

							});

				$('a[rel=tooltip2]')
						.mouseover(function(e) {

							// Grab the title attribute's value and assign it to
							// a variable
								var id = $(this).attr('id');
								if (document.getElementById(id).value != null) {
									var tip = document.getElementById(id).value;

									// Remove the title attribute's to avoid the
									// native tooltip from the browser

									// Append the tooltip template and its value
									$(this).append('<div id="tooltip2"><div class="tipHeader"></div><div class="tipBody">' + tip + '</div><div class="tipFooter"></div></div>');

									// Show the tooltip with faceIn effect
									$('#tooltip2').fadeIn('500');
									$('#tooltip2').fadeTo('10', 0.9);
									document.getElementById('');
								}

							}).mousemove(function(e) {

							// Keep changing the X and Y axis for the tooltip,
							// thus, the tooltip move along with the mouse
								$('#tooltip2').css('top', e.pageY + 10);
								$('#tooltip2').css('left', e.pageX + 10);

							}).mouseout(function() {

							// Put back the title attribute's value
								$(this).attr($('.tipBody').html());

								// Remove the appended tooltip template
								$(this).children('div#tooltip2').remove();

							});
				
					$('a[rel=tooltip3]').mouseover(function(e) {
						
						//Grab the title attribute's value and assign it to a variable
						var tip = $(this).attr('name');	
						
						//Remove the title attribute's to avoid the native tooltip from the browser

						
						//Append the tooltip template and its value
						$(this).append('<div id="tooltip3"><div class="tipHeader"></div><div class="tipBody">' + tip + '</div><div class="tipFooter"></div></div>');		
								
						//Show the tooltip with faceIn effect
						$('#tooltip3').fadeIn('500');
						$('#tooltip3').fadeTo('10',0.9);
						
					}).mousemove(function(e) {
					
						//Keep changing the X and Y axis for the tooltip, thus, the tooltip move along with the mouse
						$('#tooltip3').css('top', e.pageY + 10 );
						$('#tooltip3').css('left', e.pageX + 10 );
						
					}).mouseout(function() {
					
						//Put back the title attribute's value
						$(this).attr('name',$('.tipBody').html());
					
						//Remove the appended tooltip template
						$(this).children('div#tooltip3').remove();
						
					});

			});