#!/usr/bin/praat

form input
	sentence File
endform

threshold = 0
for_modifier = 100;

extension$="wav"

Read from file... 'file$'.'extension$'

sound = selected ("Sound")

t_start = Get starting time
t_end = Get finishing time

To Pitch... 0 75 300
Rename... pitch
select sound
To Intensity... 75 0.001
Rename... intensity

for n from (t_start * for_modifier) to (t_end * for_modifier)	
	x = 'n' / 100

	select Intensity intensity
	i = Get value at time... x Cubic
	select Pitch pitch
	p = Get value at time... x Hertz Linear

	if (i != undefined and p != undefined) and i > threshold		
		echo 'x'
		echo 'p'
	endif
endfor

