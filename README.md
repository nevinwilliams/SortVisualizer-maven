# yet-another_Sorting_Visualizer
Yet Another Sorting Visualizer made in Processing 4.3.4/java.

Every few years, Timo Bergman's, W0rthy's, Kuvina's or another sorting visualizer demos hit my YT feed, and I feel the urge to 
write my own.

Sucks that I suck at programming or know any modern language very well.

The last sorting sims I did were done in ACSLogo, a Mac version of the turtle graphics language, which made it reasonably easy to do drawings; just
not very fast.  

This last round of sort-mania, I had learned that Processing was a thing that exists, so I gave it a try.

I was well into doing over a dozen sorts and a few display modes, when I learned that ArrayV exists, when Kuvina mentioned it near the end of one of her videos.  Had I known about it ahead of time, I probably would not have done any of this project.

But I'm glad I did.

---
This visualizer saves arrays of progressively sorted items to an ArrayList.  It requires the snap() method to be called at stategic
points in the sort algorithm, that adds a clone of the array to an ArrayList 
as well as some index pointers and their array values in a 2nd ArrayList that are used to draw pointers on the parts of the array that are changing.

After the sorts are done, and the 2 ArrayLists are made, the lists are read and drawn in various display modes.

I chose the size of the sorting arrays to take roughly > 30s, < 60s, at 60 FPS.


