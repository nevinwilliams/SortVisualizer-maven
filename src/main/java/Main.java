import processing.core.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import processing.sound.*;


public class Main extends PApplet {


    static int swaps = 0;  // put 'swaps++' in sort algorithms when array elements get exchanged.
    static int aas = 0;    // put 'aas++' in sort algos at points where there are Array Accesses.
    static int comps = 0;  // put in 'comps++' where elements are compared < or > or = or 1.
    static int snaps = 0;  // more diagnostic.  uses 'snaps++' instead of snap() to estimate drawing steps.
    static int lines = 0;  // place a ++ in code whereever lines are drawn.  If a triangle, 'lines+=3'.
    static int refresh = 0; // place in code after a background() call.  Counts screen clears.

    static String ptrMode = "W";   // currently 'T'RIANGLE(new) and 'W'EDGE(OG default)

    static int maxArray = 1000; // We're gonna try to use one large array, and only sort/draw some of it.

    static int MIN_MERGE = 32; //  From TimSort.

    static int base = 10; // For Radix Sorts.

    static boolean runOK = false;
    static boolean running = false;

    static String sortName = "";

// colourMode
// 0 = white
// 1 = spectrum

    static int colourMode = 1;  // not used
    static float hueRange = 0;  // not used

    static char displayMode = '0';
    static char pointerMode = '7';
    static boolean soundMode = true;
    static boolean showMenu = true;

// Benchmarking
//static int msTimerStart = 0;  // inits the millisecond timer start variable.
//static int msTimerStop = 0;   // inits the millisecond time stop variable.

// static arrays for benchmarking and troubleshooting.
// they're a pain to make, and these are made, so here they are in case they're needed.
//int[] data700 = { 156, 176, 299, 619, 210, 246, 172, 128, 669, 651, 342, 100, 65, 105, 691, 551, 243, 590, 2, 376, 123, 135, 75, 530, 25, 674, 59, 444, 133, 428, 373, 259, 406, 661, 384, 449, 326, 134, 578, 598, 640, 354, 611, 39, 588, 435, 271, 398, 525, 377, 289, 72, 557, 459, 235, 56, 478, 362, 597, 297, 485, 650, 538, 288, 461, 178, 369, 20, 422, 298, 301, 390, 445, 8, 136, 121, 34, 266, 175, 229, 174, 285, 31, 393, 353, 109, 565, 148, 495, 699, 221, 276, 587, 3, 499, 503, 509, 309, 359, 73, 649, 379, 433, 472, 313, 13, 320, 171, 489, 512, 234, 327, 366, 70, 304, 434, 368, 197, 668, 437, 184, 573, 630, 212, 545, 205, 339, 411, 593, 561, 582, 187, 625, 195, 476, 222, 338, 580, 131, 191, 501, 447, 111, 498, 168, 372, 113, 129, 130, 219, 399, 421, 609, 426, 200, 629, 272, 283, 94, 252, 263, 526, 469, 484, 477, 402, 232, 318, 294, 303, 76, 343, 684, 157, 360, 319, 341, 286, 249, 440, 209, 508, 672, 138, 16, 26, 675, 43, 620, 579, 231, 429, 173, 400, 613, 540, 192, 391, 652, 166, 87, 454, 571, 331, 493, 550, 385, 496, 677, 204, 250, 198, 50, 409, 662, 413, 101, 199, 48, 264, 676, 659, 648, 80, 41, 479, 632, 490, 228, 694, 180, 21, 35, 92, 269, 378, 323, 556, 29, 60, 529, 19, 293, 453, 356, 528, 549, 654, 389, 405, 332, 145, 685, 616, 344, 307, 159, 642, 1, 534, 634, 233, 568, 328, 126, 541, 314, 295, 572, 455, 687, 655, 547, 474, 527, 186, 486, 416, 614, 361, 201, 586, 270, 407, 441, 88, 160, 603, 591, 223, 165, 351, 638, 425, 570, 64, 515, 641, 239, 83, 594, 290, 664, 238, 241, 118, 53, 394, 522, 254, 643, 355, 98, 89, 42, 46, 188, 69, 392, 177, 452, 218, 82, 589, 61, 140, 442, 606, 120, 163, 553, 439, 287, 419, 225, 535, 18, 329, 151, 657, 562, 670, 36, 137, 510, 90, 575, 22, 470, 81, 475, 666, 494, 153, 462, 251, 370, 300, 480, 605, 663, 667, 63, 97, 315, 108, 132, 350, 208, 162, 537, 491, 47, 533, 375, 215, 689, 386, 397, 678, 492, 170, 523, 617, 563, 577, 306, 365, 262, 185, 688, 458, 158, 33, 415, 115, 367, 420, 214, 179, 217, 280, 543, 345, 516, 607, 181, 583, 5, 558, 482, 346, 268, 626, 167, 653, 418, 54, 546, 542, 139, 417, 532, 352, 248, 601, 32, 292, 624, 11, 17, 531, 473, 57, 79, 507, 457, 302, 112, 68, 348, 585, 9, 695, 193, 519, 656, 10, 143, 511, 161, 456, 124, 312, 488, 432, 77, 569, 277, 690, 62, 517, 627, 671, 38, 595, 144, 387, 273, 15, 520, 635, 86, 27, 203, 324, 497, 125, 66, 596, 552, 548, 257, 103, 325, 539, 427, 95, 581, 227, 296, 334, 291, 122, 196, 340, 155, 44, 52, 592, 206, 182, 110, 190, 621, 147, 265, 23, 284, 169, 49, 622, 330, 698, 183, 566, 468, 646, 4, 524, 559, 152, 483, 322, 117, 446, 24, 237, 58, 245, 358, 357, 146, 311, 308, 576, 333, 560, 430, 28, 107, 256, 513, 316, 403, 337, 636, 67, 404, 55, 481, 692, 213, 681, 518, 127, 202, 317, 30, 633, 194, 424, 12, 463, 261, 639, 99, 628, 374, 544, 450, 78, 207, 7, 436, 431, 451, 255, 371, 584, 396, 564, 680, 555, 506, 618, 410, 414, 93, 45, 608, 408, 647, 102, 602, 242, 363, 502, 536, 574, 599, 14, 696, 281, 521, 258, 487, 282, 423, 438, 364, 500, 164, 631, 471, 51, 679, 141, 380, 383, 0, 149, 154, 448, 216, 660, 610, 116, 465, 683, 686, 220, 466, 142, 554, 382, 6, 114, 247, 71, 310, 253, 226, 84, 279, 395, 347, 278, 443, 604, 505, 96, 40, 211, 682, 274, 600, 388, 150, 645, 119, 260, 412, 335, 658, 567, 336, 349, 673, 623, 240, 189, 460, 106, 85, 74, 504, 615, 91, 305, 236, 464, 612, 637, 275, 514, 224, 401, 693, 381, 467, 244, 321, 644, 697, 104, 230, 665, 267, 37};
//int[] data500 = {164, 379, 477, 438, 252, 289, 389, 106, 48, 147, 120, 230, 457, 80, 194, 10, 459, 27, 217, 260, 96, 474, 139, 292, 290, 69, 68, 37, 301, 204, 9, 122, 172, 8, 410, 170, 151, 330, 409, 213, 472, 442, 91, 169, 429, 89, 396, 31, 436, 458, 366, 350, 208, 349, 115, 88, 371, 150, 312, 131, 470, 144, 223, 498, 481, 171, 149, 426, 421, 212, 23, 152, 13, 163, 121, 394, 340, 195, 216, 243, 344, 463, 378, 70, 5, 187, 259, 258, 16, 355, 84, 57, 372, 240, 175, 162, 36, 179, 380, 198, 263, 74, 264, 296, 26, 407, 351, 480, 272, 248, 238, 211, 246, 388, 102, 58, 129, 202, 219, 278, 452, 280, 476, 60, 309, 313, 67, 236, 205, 469, 363, 358, 98, 300, 341, 352, 85, 206, 141, 337, 183, 400, 6, 77, 14, 434, 247, 4, 275, 156, 354, 2, 45, 287, 186, 304, 382, 132, 245, 143, 39, 443, 180, 130, 490, 54, 361, 479, 28, 182, 285, 284, 73, 158, 65, 348, 390, 189, 428, 454, 110, 237, 331, 191, 346, 140, 464, 61, 283, 433, 177, 1, 276, 486, 499, 269, 224, 242, 306, 444, 87, 154, 166, 34, 449, 274, 368, 279, 261, 450, 22, 196, 52, 329, 383, 146, 295, 193, 302, 21, 405, 398, 365, 44, 455, 356, 53, 333, 320, 323, 374, 353, 188, 461, 125, 310, 266, 160, 62, 317, 81, 103, 83, 391, 345, 403, 249, 362, 250, 402, 221, 108, 445, 255, 298, 492, 185, 244, 101, 123, 286, 373, 475, 220, 181, 327, 41, 199, 386, 218, 135, 47, 307, 288, 318, 148, 468, 406, 165, 114, 178, 119, 86, 153, 63, 419, 496, 299, 456, 397, 399, 488, 384, 76, 225, 112, 190, 200, 448, 364, 315, 79, 256, 231, 210, 338, 305, 321, 40, 297, 360, 393, 55, 7, 332, 42, 24, 117, 466, 376, 262, 116, 109, 128, 124, 235, 413, 29, 412, 440, 369, 184, 395, 92, 126, 427, 375, 359, 11, 473, 229, 294, 209, 416, 197, 387, 94, 104, 173, 207, 483, 430, 134, 50, 49, 381, 441, 314, 432, 59, 324, 35, 145, 233, 82, 20, 113, 291, 485, 319, 489, 133, 127, 97, 418, 33, 56, 241, 90, 17, 239, 334, 467, 335, 495, 439, 453, 99, 46, 311, 336, 422, 265, 176, 157, 322, 234, 268, 367, 482, 414, 271, 446, 424, 12, 43, 232, 257, 18, 107, 401, 78, 411, 168, 451, 435, 32, 342, 167, 277, 385, 64, 111, 447, 66, 75, 30, 491, 357, 15, 423, 494, 71, 308, 214, 404, 51, 471, 460, 93, 370, 226, 316, 267, 497, 155, 203, 487, 138, 437, 281, 478, 273, 493, 484, 3, 254, 19, 161, 392, 174, 72, 95, 227, 325, 377, 137, 201, 415, 253, 465, 408, 228, 118, 343, 25, 192, 251, 222, 105, 431, 347, 326, 339, 136, 425, 282, 100, 270, 142, 38, 293, 0, 303, 328, 159, 215, 462, 420, 417};
//int[] data250 = {326, 358, 212, 308, 194, 74, 210, 302, 102, 498, 18, 124, 476, 204, 306, 86, 312, 472, 446, 266, 152, 246, 4, 144, 176, 170, 30, 116, 106, 218, 334, 340, 296, 232, 120, 328, 452, 280, 430, 12, 316, 314, 250, 100, 244, 486, 292, 40, 274, 496, 240, 350, 372, 36, 490, 238, 66, 478, 388, 360, 190, 54, 336, 90, 230, 346, 168, 352, 356, 148, 134, 278, 150, 166, 282, 104, 438, 108, 468, 330, 298, 180, 160, 482, 300, 418, 322, 76, 136, 58, 56, 70, 222, 454, 488, 474, 394, 402, 26, 128, 130, 28, 226, 96, 82, 202, 406, 294, 348, 0, 126, 400, 390, 258, 224, 434, 318, 216, 392, 380, 178, 122, 416, 208, 60, 342, 138, 344, 80, 436, 444, 286, 414, 146, 206, 440, 92, 424, 426, 448, 42, 242, 458, 88, 62, 198, 172, 422, 182, 462, 320, 268, 304, 184, 156, 234, 248, 412, 24, 480, 188, 492, 366, 158, 262, 386, 50, 270, 442, 16, 140, 174, 44, 164, 214, 290, 68, 354, 220, 276, 338, 132, 378, 2, 200, 324, 404, 264, 114, 456, 420, 428, 8, 34, 470, 38, 6, 364, 374, 284, 98, 288, 52, 260, 162, 142, 332, 310, 22, 410, 84, 464, 396, 32, 10, 236, 64, 272, 228, 398, 466, 186, 370, 484, 72, 20, 494, 252, 254, 154, 118, 192, 384, 112, 46, 368, 196, 408, 94, 432, 362, 110, 460, 256, 450, 48, 382, 78, 14, 376};
//int[] data100 = {35, 0, 165, 5, 225, 460, 135, 440, 80, 130, 220, 430, 185, 485, 470, 255, 275, 175, 25, 300, 380, 150, 240, 455, 360, 350, 145, 210, 290, 190, 30, 85, 200, 20, 370, 195, 230, 205, 355, 435, 245, 45, 330, 235, 305, 280, 475, 120, 40, 270, 450, 55, 215, 110, 320, 50, 325, 180, 105, 420, 160, 340, 295, 415, 425, 125, 490, 70, 315, 155, 390, 170, 335, 65, 465, 410, 95, 480, 310, 265, 405, 100, 10, 495, 375, 365, 345, 260, 60, 250, 15, 285, 140, 400, 385, 445, 115, 395, 90, 75};
//int[] data50 = {160, 100, 310, 20, 150, 170, 200, 260, 330, 480, 230, 270, 350, 190, 470, 300, 360, 180, 40, 90, 290, 80, 60, 450, 370, 280, 390, 320, 140, 30, 120, 380, 50, 210, 130, 240, 220, 0, 490, 400, 420, 340, 410, 440, 460, 250, 430, 10, 110, 70};
//int[] data10 = {50, 150, 400, 200, 300, 100, 250, 500, 350, 450};


    // draw() variables.
    static int iter = 0;                 // iteration variable for draw() loop.  frameCount doesn't work with my code.

    static boolean drawArrayDone = false;  // used to do post-sort animation.
    static int iter2 = 0;         // Iteration variable for post array animation.

/* aQueue is a queue of arrays with linked lists.  aQueue is used for the main
 sort array sequences, and populated with the snap() method as often as
 necessary for an interesting animation.
 aQueue is passed the large sort arrays of variable element length with the
 snap() method.  The array should be added to the queue with the .clone() method
 weirdness happens.  The snap() method should be the only method to add to the queue. */


//static  Queue<int[]> aQueue = new LinkedList<>();


//  Should an Array List need to be used, say if access to already drawn array values are needed
//  this is the syntax for defining it:

//      static  ArrayList<int[]> aList = new ArrayList<int[]>();
//  when adding an array to the aList, use the .clone() method or weirdness happens.

/* ptrList is a list of arrays in an ArrayList.  ptrList is used to animate two
 graphical pointers.  It is populated with the snap() method.  Its arrays are
 currently exclusively int[2] in length.  This is not enforced.  The first
 element positions the bottomUp pointer.  The second element places the
 topDown pointer.  It is important that when the queue to aQueue added,
 so too is ptrList, or there will be Trouble.
 It is useful to access past values of the ptr[] array, so it is defined as an ArrayList.
 The snap() method should be the only way to add elements to the ptrList.
 */

    static ArrayList<int[]> ptrList = new ArrayList<int[]>();
    static ArrayList<int[]> aList = new ArrayList<int[]>();
//static ArrayList<int[]> chgList = new ArrayList<int[]>();


//   COLOUR AND DISPLAY VARIABLES

// The HSB colour format is used because it's easier to select colours than RGB.

    int colourOffset = 0;        // where, in the HSB range, does colours start.
    float colourFactor = 1.25F;   // HSB range = aSize * colourFactor
// offset of 1.25 will get rid of most magenta,
// by red shifting.

//*****************************************************SOUND STUFF*****************

SqrOsc snd0;       // sets up two Square Oscillators, one for each floating pointer.
SqrOsc snd1;

Reverb reverb0;   // Applies default reverb effect
Reverb reverb1;    // Adding reverb made the best change.

Env env0;        // Applies an ASR envelope
Env env1;        //  The envelope keeps the tones from playing longer than a few frames.  Sounds ok with reverb.


float aT = (float) (1.0/120);    // Attack Time
float sT = (float) (1.0/120);    // Sustain Time
float sL = 0.01F;      // Sustain Level
float rT = (float) (1.0/120);  // Release Time       (changing any of these really did not affect the sound quality at all)

/*  Some sorts' pointers stick to an element or value for an extended time, causing a long, steady tone.
 The sound code stops playing if there are two consecutive notes in a row.  Some sorts, like QuickSort just have
 short runs of the same value, and these sorts sound worse with the quenching.   This lets one turn the
 quenching on or off on a per-sort basis in the Case statements.  Default to true.*/


    static boolean samePitchQuench = true;


//ARRAY SIZE DEFINITION -- might be less of a deal now with newer scaling drawing.

    // ******************
    static int aSize = 0;    //the MAIN array size for several functions.
// *****************

    float wd = 1280.0F;   // window width
    float ht = 720.0F;   //  window height

    float yR = 0.0F;    // global var to represent the y-Ratio for lines.
    float xR = 0.0F;    // global var to represent the x-Ratio for lines.

// The settings() method must be used if screen height & width are defined with variables.
// I don't know why.

// here begins the start of having multiple sorts in one sketch.
// wish me luck.


    char inputKey = ' ';  // defaults to Display Menu key.

    public static void main(String[] args) {
        PApplet.main(java.lang.invoke.MethodHandles.lookup().lookupClass());

    }

    public void settings() {
        fullScreen(P2D);
        smooth();
    }

    public void setup() {

        wd = width;
        ht = height;
        background(0);


        loop();


        // sets the framerate of the draw() method.

        frameRate(60);


/* This stanza defines the sound effects.  snd0 corresponds to ptr0 sounds.
snd1 corresponds to ptr1 sounds. */

        snd0 = new SqrOsc(this);
        snd1 = new SqrOsc(this);
        env0 = new Env(this);
        env1 = new Env(this);
        reverb0 = new Reverb(this);
        reverb1 = new Reverb(this);

        reverb0.process(snd0);
        reverb1.process(snd1);
        snd0.pan(0.5F);
        snd1.pan(-0.5F);


        showMenu = true;


        // Make Hue range equal to array elements, others to percentages.
        // When lines are drawn, the color can be set to the array value
        // to get a nice spectrum.   The 100s define the SB and Alpha as percentages.

        hueRange = aSize * colourFactor;

        colorMode(HSB, aSize * colourFactor, 10, 10, 10); // changed from 100 to 10; look for magentas; change accordingly.


        // setup is done.
        // DEFAULT STROKECAP (ROUND) = Big performance hit with default renderer.
        // Use SQUARE or PROJECTED instead.

        displayMenuScreen();
        strokeCap(SQUARE);

        // noSmooth();
        yR = ht / (aSize); // Y axis ratio to set line height scale.
        xR = wd / aSize;          // X axis ratio to scale line strokeWeight

        //delay(2000);
        //  msTimerStart = millis();
    }

    public void draw() {

        // main scale and translate calls have to be at start of draw() because docs say they reset each loop

        float scaleFactor = 0.90F;

        scale(scaleFactor, scaleFactor);    // shrinks the drawing within the display screen.
//  translate((((1.0-scaleFactor) * width)/2/scaleFactor), ((1.0-scaleFactor) * height)/2/scaleFactor);  // gives a small border around drawing.
// textSize(((1.0-scaleFactor) * height)/2/scaleFactor)
        translate((float) (((1.0 - scaleFactor) * width) / 2 / scaleFactor), (float) (((1.0 - scaleFactor) * height) / 2 / scaleFactor));  // gives a small border around drawing.


        if (showMenu && !running) {
            displayMenuScreen();
        }

        if (iter < aList.size() && runOK) {

            background(0);

            if (displayMode == '0') {
                drawSpectrum(aList.get(iter));
            }

            if (displayMode == '1') {
                drawSticks(aList.get(iter));
            }

            if (displayMode == '2') {
                pointerMode = '8';
                drawTriangleSpectrum(aList.get(iter));
            }

            if (displayMode == '3') {
                pointerMode = '#';
                drawBoxedArray(aList.get(iter));
            }

            if (runOK) {
                if (iter < ptrList.size() - 1) {
                    if (pointerMode == '7') {
                        drawTrails();
                    }

                    if (pointerMode == '8') {
                        ptrArrowDraw();
                    }
                    if (pointerMode == '9') {
                        drawPtrLines();
                    }
                }

                if (soundMode) {
                    soundPtr();
                }
            }
            if (runOK) {  // keeps from crashing if a pointer mode
                iter++;                 // is selected before a sort mode.
                //writeSortName((float) (((1.0 - scaleFactor) * height) / 2 / scaleFactor));

                // for some reason, scaling is not working right in P2D.
                // in this build.
                writeSortName(24F);

            }
        } else {
            drawArrayDone = true;


            // noLoop();
        }


    }

    public void keyPressed() {
        inputKey = key;
        showMenu = false;


        int[] arr = genArray(maxArray);


        switch (inputKey) {

            case 'a':
                resetPreSortVars();
                aSize = 80;
                shuffleArray(arr);
                bubbleSort(arr);
                samePitchQuench = true; // definitely want quenching here.
                resetPreDrawVarsAndGo();
                break;

            case 'b':
                resetPreSortVars();
                aSize = 100;
                shuffleArray(arr);
                insertionSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 'c':
                resetPreSortVars();
                aSize = 80;
                shuffleArray(arr);
                selectionSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 'd':
                resetPreSortVars();
                aSize = 100;
                shuffleArray(arr);
                cocktailSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 'e':
                resetPreSortVars();
                aSize = 200;
                shuffleArray(arr);
                shellSort(arr);
                resetPreDrawVarsAndGo();
                break;//

            case 'f':
                resetPreSortVars();
                aSize = 500;
                shuffleArray(arr);
                mergeSort(arr, 0, aSize - 1);
                resetPreDrawVarsAndGo();
                break;

            case 'g':
                resetPreSortVars();
                aSize = 500;
                shuffleArray(arr);
                iterativeMergeSort(arr);
                resetPreDrawVarsAndGo();
                break;


            case 'h':
                resetPreSortVars();
                aSize = 400;
                shuffleArray(arr);
                quickSort(arr, 0, aSize - 1);
                samePitchQuench = false;  // The cut-outs sound worse than a tone-run-on.
                resetPreDrawVarsAndGo();
                break;

            case 'i':
                resetPreSortVars();
                aSize = 800;
                shuffleArray(arr);
                dualPivotQuickSort(arr, 0, aSize -1);
                samePitchQuench = false; // Quicksorts hit the same pitch, but not long; cutouts sound worse.
                resetPreDrawVarsAndGo();
                break;

            case 'j':
                resetPreSortVars();
                aSize = 400;
                shuffleArray(arr);
                heapSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 'k':
                resetPreSortVars();
                aSize = 160;
                shuffleArray(arr);
                combSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 'l':
                resetPreSortVars();
                aSize = 1000;
                base = 10;
                shuffleArray(arr);
                radixSort(arr);   // LSD
                resetPreDrawVarsAndGo();
                break;

            case 'm':
                resetPreSortVars();
                aSize = 800;
                base = 4;
                shuffleArray(arr);
                radixSort(arr);   // LSD
                resetPreDrawVarsAndGo();
                break;

            case 'n':
                resetPreSortVars();
                aSize = 400;
                base = 10;
                shuffleArray(arr);
                radixMSDSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 'o':
                resetPreSortVars();
                aSize = 500;
                base = 4;
                shuffleArray(arr);
                radixMSDSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 'p':
                resetPreSortVars();
                aSize = 500;
                shuffleArray(arr);
                smoothSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 'q':
                resetPreSortVars();
                aSize = 256;
                shuffleArray(arr);
                // params are Array, start index, array size, and 1 (for up, 0 for down)
                bitonicSort(arr, 0, aSize, 1);   // bi-tonic, like bi-weekly. :)
                resetPreDrawVarsAndGo();
                break;

            case ('r'):
                resetPreSortVars();
                aSize = 300;
                shuffleArray(arr);
                cycleSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 's':
                resetPreSortVars();
                aSize = 200;
                shuffleArray(arr);
                pigeonholeSort(arr);
                resetPreDrawVarsAndGo();
                break;

            case 't':
                resetPreSortVars();
                aSize = 600;
                shuffleArray(arr);
                timSort(arr, aSize);
                resetPreDrawVarsAndGo();
                break;

            case '6':
                soundMode = !soundMode;
                break;

            case ' ':
                showMenu = true;
                running = false;
                break;

            case '0':
                background(0);
                displayMode = '0';
                break;

            case '1':
                displayMode = '1'; // triangle
                background(0);
                break;

            case '2':
                displayMode = '2';  //sticks
                background(0);
                break;

            case '3':
                displayMode = '3';  // boxes
                pointerMode = '4';   //disable pointers for box draw.
                background(0);
                break;


            case '7':
                pointerMode = '7';  // flying lines
                break;

            case '8':
                pointerMode = '8';  // arrows
                break;

            case '9':
                pointerMode = '9'; // regular lines
                break;

            case '5':
                pointerMode = '5'; // no pointers
                break;
        }
        if (!running) {
            showMenu = true;
        }
    }

    void displayMenuScreen() {
        var textScaleFactor = 1.6F;
        background(0, 0, 0, 10);

        fill(0, 0, 100, 100);
        strokeWeight(1);
        rectMode(CENTER);
        textAlign(CENTER, CENTER);

        float rowTracker = 0;
        float headerX = (float) (width / 2.0);
        float headerY = (float) (height / 30.0);
        textSize(headerY * textScaleFactor);
        text("(Yet Another) Sorting Visualizer", headerX, headerY, width, headerY * 2);
        rowTracker += (float) (headerY * 2.0);

        // first 10% of screen used
// header 2 smaller
        rowTracker += (float) (height / 40.0);
        headerY = (float) (height / 40.0); // headerX stays to center
        textSize(headerY * textScaleFactor);
        text("coded novicely in Processing 4.3.4/java", headerX, headerY + rowTracker, width, (headerY * 2));




        rowTracker += (float) (headerY * 2.0);
        rowTracker += (float) (height / 15.0);

        headerY = (float) (height / 55.0);
        headerX = (float) (width / 3.0);
        float header2X = (float) (headerX * 2.5);
        textAlign(LEFT, CENTER);
        textSize(headerY * textScaleFactor);
        text("\"A\" for good ol' BubbleSort", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"K\" for CombSort", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);


        rowTracker += (float) (headerY * 2.0);
        rowTracker += (float) (height / 40.0);
        text("\"B\" for InsertionSort", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"L\" for RadixSort LSD, base 10", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);

        rowTracker += headerY * 2;
        rowTracker += (float) (height / 40.0);
        text("\"C\" for SelectionSort", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"M\" for RadixSort LSD, base 4", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);

        rowTracker += headerY * 2;
        rowTracker += (float) (height / 40.0);
        text("\"D\" for CocktailSort", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"N\" for RadixSort MSD, base 10", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);


        rowTracker += headerY * 2;
        rowTracker += (float) (height / 40.0);
        text("\"E\" for ShellSort", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"O\" for RadixSort MSD, base 4", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);

        rowTracker += headerY * 2;
        rowTracker += (float) (height / 40.0);
        text("\"F\" for MergeSort (in-place, recursive)", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"P\" for SmoothSort", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);


        rowTracker += headerY * 2;
        rowTracker += (float) (height / 40.0);
        text("\"G\" for MergeSort (iterative)", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"Q\" for Bi-TonicSort", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);


        rowTracker += headerY * 2;
        rowTracker += (float) (height / 40.0);
        text("\"H\" for QuickSort", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"R\" for CycleSort", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);


        rowTracker += headerY * 2;
        rowTracker += (float) (height / 40.0);
        text("\"I\" for DualPivotQuickSort", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"S\" for PigeonHoleSort", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);

        rowTracker += headerY * 2;
        rowTracker += (float) (height / 40.0);
        text("\"J\" for HeapSort (Max Heap)", headerX, headerY + rowTracker, (float) (width / 2.5), headerY * 2);
        text("\"T\" for TimSort", header2X, headerY + rowTracker, (float) (width / 2.5), headerY * 2);

        rowTracker += headerY * 2;
        rowTracker += (float) (height / 40.0);

        strokeWeight(3);
        stroke(0, 0, 4, 10);
        line(0, rowTracker, width, rowTracker);

        strokeWeight(1);
        stroke(0, 0, 10, 10);

        rowTracker += headerY;
        rowTracker += (float) (height / 144.0);

        textSize((float) (height / 35.0));
        text("Display Modes:  '0' for Spectrum, '1' for Sticks, '2' for Triangle, '3' for Boxes.", (float) (width / 2), headerY + rowTracker, (float) (width / 1.2), headerY * 2);

        rowTracker += headerY * 2;
        rowTracker += (float) (height / 72.0);
        text("Pointer Modes:  '7' for flying lines, '8' for Arrows, '9' for regular lines, '5' for no pointer lines.  '6' to toggle sound on or off.", (float) (width / 2), headerY + rowTracker, (float) (width / 1.2), headerY * 2);

        rowTracker += headerY * 2;
        rowTracker += (float) (height / 72.0);
        text("Space bar for this menu.  Selecting a draw mode before the sort *may* crash, or I *may* have fixed it.  Feel lucky?", (float) (width / 2), headerY + rowTracker, (float) (width / 1.2), headerY * 2);

        rowTracker += headerY * 2;
        rowTracker += (float) (height / 72.0);
        text("Triangle Display Mode(2) only runs with Arrows(8).  Boxes Display Mode(3) has its own pointer display built-in.", (float) (width / 2), headerY + rowTracker, (float) (width / 1.2), headerY * 2);


        runOK = false;
    }

    public static int[] genArray(int elements) {

        int[] genArr = new int[elements * 2];

        for (int i = 0; i < elements; i++) {
            genArr[i] = i;
        }

        return (genArr);
    }

    static int[] setupArray(int size, boolean doShuffle) {


        int[] arr = genArray(size);
        // if passed false, will pass back a sorted array of size.

        if (doShuffle) {
            shuffleArray(arr);
            return arr;
        } else {
            return arr;
        }
    }

    public static void shuffleArray(int[] array) {
        int temp = 0;
        int n = aSize;

        for (int i = 0; i < n; i++) {
            int r = (int) (Math.random() * (i + 1));
            temp = array[r];
            array[r] = array[i];
            array[i] = temp;
            //    snap(array, i, aSize);
        }
    }

    public static void snap(int[] array, int pointer0, int pointer1) {


        // ptr[0] value of index for first pointer
        // ptr[1] value of index for 2nd pointer
        // ptr[2] value of array[pointer0]
        // ptr[3] value of array[pointer1]

        int[] ptr = new int[4];

        // value clamping is used as sometimes a variable too small or two large can get passed.
        // sometimes, to get a more interesting drawing, add or subtract 1 to one of the pointers, to put it ahead of the action.
        // clamping lets me not worry about array access errors when I do this.


        ptr[0] = Math.max(Math.min(pointer0, aSize - 1), 0); // clamps the value recorded to the array dimensions.
        ptr[1] = Math.max(Math.min(pointer1, aSize - 1), 0); // clamps the value recorded to the array dimensions.
        ptr[2] = array[ptr[0]];
        ptr[3] = array[ptr[1]];

        // The pointers are a list, because it can be useful to grab several pointers back.
        ptrList.add(ptr.clone());

        // the arrays are a queue, because they're usually only accessed once.  Until they're not.  Then you need aList.
        // aQueue.add(array.clone());
        aList.add(array.clone());
    }

    void resetPreSortVars() {

        swaps = 0;  // put 'swaps++' in sort algorithms when array elements get exchanged.
        aas = 0;    // put 'aas++' in sort algos at points where there are Array Accesses.
        comps = 0;  // put in 'comps++' where elements are compared < or > or = or 1.
        snaps = 0;  // more diagnostic.  uses 'snaps++' instead of snap() to estimate drawing steps.
        lines = 0;  // place a ++ in code where ever lines are drawn.  If a triangle, 'lines+=3'.
        refresh = 0; // place in code after a background() call.  Counts screen clears.

        // Benchmarking

        iter = 0;                 // iteration variable for draw() loop.  frameCount doesn't work with my code.

        drawArrayDone = false;  // used to do post-sort animation.
        iter2 = 0;         // Iteration variable for post array animation.

        aList.clear(); // where array lists are stored with snap() then played back in draw()
        ptrList.clear(); // where sort pointers are stored with snap() then played back in draw()

        samePitchQuench = true;  // keeps sound from playing same pitch.
    }

    void resetPreDrawVarsAndGo() {
        hueRange = aSize * colourFactor;

        colorMode(HSB, hueRange, 10, 10, 10); // changed from 100 to 10; look for magentas; change accordingly.
        yR = ht / (aSize); // Y axis ratio to set line height scale.
        xR = wd / aSize;          // X axis ratio to scale line strokeWeight


        loop();
        runOK = true;
        running = true;
    }

    public void drawSpectrum(int[] array) {

        // this version onward will draw colourful arrays.
        // it depends on colorMode(HSB, aSize, 100, 100, 100); being called in setup()

        //  snaps++;
        background(0);
        refresh++;


        for (int i = 0; i < aSize; i++) {

            stroke(array[i] + colourOffset, 10, 10, 10);  // each line coloured to its value.
            strokeWeight(xR); //lines fill screen

            //draw main array

            float x1 = i * xR;  //  verticle lines
            //    float x1 = array[i] * xR; // lines anchored on bottom in right places.
            float y1 = height;        // lines bend to the bottom.
            float x2 = i * xR;    //
            float y2 = height - (array[i] * yR);

            line(x1, y1, x2, y2);
            lines++;
        }
    }

    public void drawTriangleSpectrum(int[] array) {


        background(0);
        refresh++;

        // float hR is height ratio in main.
        // float wR is width ratio in main.


        for (int i = 0; i < aSize; i++) {

            stroke(array[i] + colourOffset, 10, 10, 10);  // each line coloured to its value.
            strokeWeight(xR); //lines fill screen

            //draw main array

            float x1 = i * xR;  //  both start and end of xPos here are at the index of the array.

            float y1 = (float) height / 2 + (array[i] * yR) / 2;
            // xPos doesn't move.
            float y2 = (float) height / 2 - (array[i] * yR) / 2;

            line(x1, y1, x1, y2);
            lines++;
        }
    }

    public void drawSticks(int[] array) {

        // this method will draw mixed-up sticks.
        // The bottom of the stick is anchored to its array position
        // which is, for this data, its sorted position.
        // the top of the stick is anchored to its place in the array.
        // as the array is sorted, the sticks will be moved to an upright position.


        background(0);
        refresh++;

        // float hR is height ratio in main.
        // float wR is width ratio in main.

        //  float hueRange = aSize * colourFactor;

        stroke(0, 0, 10, 10);  // white unless colourMode == 1

        for (int i = 0; i < aSize; i++) {

            if (colourMode == 1) {
                stroke(array[i] + colourOffset, 10, 10, 10);  // each line coloured to its value.
            } else {
                stroke(0, 0, 10, 10);  // white unless colourMode == 1
            }
            strokeWeight(xR); //lines fill screen

            //draw main array

            //   float x1 = i * xR;  //  vertical lines
            float x1 = array[i] * xR; // lines anchored on bottom in right places.
            float y1 = height;        // lines bend to the bottom.
            float x2 = i * xR;    //
            float y2 = height - (array[i] * yR);

            line(x1, y1, x2, y2);
            lines++;
        }
    }

    void drawBoxedArray(int[] array) { //  Thanks to SCF@Styrocord for the help!
        noStroke();
        rectMode(CENTER);
        textAlign(CENTER, CENTER);

        if (aSize > 600) {
            textSize(18);
        } else {
            textSize(24);
        }

        if (aSize > 800) {
            textSize(12);
        }

        int row = 20;
        int col = aSize / row; // aSize is the array length; 400 works with this, draws off the screen with other values.

        float rectWd = (float) width / col;
        float rectHt = (float) height / row;

        int[] ptr = ptrList.get(iter);  // iter is the main counter that's incremented in the draw() loop that calls this method.

        int i = 0;
        for (int y = 0; y < row; y++) {

            for (int x = 0; x < col; x++) {

                fill(array[i], 10, 10, 10);    // fill with colour corresponding to the element value.

                if (iter < aList.size() - 1) {   // code for the white & black boxes that show the swap or index pointers.
                    if (array[i] == ptr[2]) {    // stops drawing those before the last loop.
                        fill(0, 0, 0, 10);
                    } else if (array[i] == ptr[3]) {
                        fill(0, 0, 10, 10);
                    }
                }
                float rectCtrX = (float) ((x * rectWd) + (0.5 * rectWd));
                float rectCtrY = (float) ((y * rectHt) + (0.5 * rectHt));

                rect(rectCtrX, rectCtrY, rectWd, rectHt);
                fill((array[i] + hueRange / 2) % hueRange, 10, 10, 10);
                text("" + array[i], rectCtrX, rectCtrY, rectWd, rectHt);
                i++;
            }
        }
    }

    void drawTrails() {

        // this trail-drawing method draws several pointers based on past data,
        // flying in from the top left and bottom right corner.
        // the pointers fade out as the opacity of the line decreases.

        strokeWeight(xR * 2);
        float transp = 5.0F;
        int trails = 10;

        int spaces = 2;
        float steps = (float) transp / trails;


        if (iter > (trails * spaces) && iter < ptrList.size() - 2) {


            for (int j = 0; j < trails * spaces; j += spaces) {


                int[] oldPtr = ptrList.get(iter - j);


                stroke(0, 0, 10, transp);


                float x12 = oldPtr[0] * xR;
                //     float y1  =  height - array[oldPtr[0]] * yR;
                float y1 = height - oldPtr[2] * yR;
                float y2 = height;
                line(x12, y1, width + 100, height + 100);  // anchor end of pointer well offscreen
                lines++;


                stroke(0, 0, 10, transp); //white line

                x12 = oldPtr[1] * xR;
                y1 = 0;
                //      y2 = height-array[oldPtr[1]] * yR;
                y2 = height - oldPtr[3] * yR;

                line(-100, -100, x12, y2);  // -640 anchor end of pointer well offscreen
                transp -= steps;
                lines++;
            }
        } else {
            // since the main code pulls past data, it can't start immediately.
            // this just draws single lines at the start so there won't be a blank
            // spot in the animation.
            int[] ptr = ptrList.get(iter);
            stroke(0, 0, 10, transp);
            float x12 = ptr[0] * xR;
            float y1 = height - ptr[2] * yR;
            float y2 = height;
            line(x12, y1, width + 100, height + 100);  // anchor end of pointer well offscreen
            lines++;

            stroke(0, 0, 10, transp);
            x12 = ptr[1] * xR;
            y1 = 0;
            y2 = height - ptr[3] * yR;

            line(-100, -100, x12, y2);  // -640 anchor end of pointer well offscreen
            transp -= steps;
            lines++;
        }
    }

    void drawPtrLines() {


        if (iter < aList.size() - 1) {

            int[] newPtr = ptrList.get(iter);
            strokeWeight(xR);
            stroke(0, 0, 10, 10);
            float x12 = newPtr[0] * xR;
            float y1 = height + 36;
            float y2 = height - newPtr[2] * yR;

            line(x12, y1, x12, y2);
            lines++;

            strokeWeight(xR);
            stroke(0, 0, 10, 10); //white line
            x12 = newPtr[1] * xR;
            y1 = -36;
            y2 = height - newPtr[3] * yR;

            line(x12, y1, x12, y2);

            lines++;
        }
    }

    void ptrArrowDraw() {

        strokeWeight(xR / 2);


        if (colourMode == 1) {
            stroke(0, 0, 10, 10);
        } else if (colourMode == 0) {
            stroke(0, 10, 10, 10);
        }

        int[] ptr = ptrList.get(iter);

        //  Bottom Up Arrow

        //vertex

        float x1 = ptr[0] * xR;

        //   float y1  =  height - arr0Av * yR;
        float y1 = height - 100;

        // right leg
        float x2 = x1 - 5;
        float y2 = height + 36;

        float x3 = x1 + 5;


        line(x1, y1, x2, y2);
        line(x1, y1, x3, y2);

        lines += 2;

        if (colourMode == 1) {
            stroke(0, 0, 10, 10);
        } else if (colourMode == 0) {
            stroke((float) (aSize * 1.25 / 2), 10, 10, 10);
        }


        // BOTTOM DOWN ARROW

        //vertex
        //    x1 = oldPtr[1] * xR;
        x1 = ptr[1] * xR;
        y1 = 100;

        //right leg
        x2 = x1 - 5;
        y2 = -36;
        x3 = x1 + 5;
        //left leg
        //      line (x3, y2, x1, y1);


        line(x1, y1, x2, y2);
        line(x1, y1, x3, y2);

        lines += 2;
    }

    void bigFinish() { // do final animation 10 lines at a time.
        iter2 = aSize;

        strokeWeight((float) (xR * 1.2)); //lines fill screen
        if (iter2 + 10 > aSize) {
            lilFinish();
        } else {
            for (int i = 0; i < 10; i++) {


                stroke(iter2 + colourOffset, 10, 10, 10);  // full spectrum
                line(iter2 * xR, // X1
                        height, // Y1
                        iter2 * xR, // X2
                        height - (iter2 * yR)); // Y2
                lines++;

                float freq2 = (iter2 + 150);
                float freq3 = (iter2 + 300) * 2;

//                 snd0.play(freq2, 0.1F);
//
//                env0.play(snd0, aT, sT, sL, rT);
//
//                snd1.play(freq3, 0.1F);
//                env1.play(snd1, aT, sT, sL, rT);
                iter2++;
            }
        }
    }

    void lilFinish() { // do final animation just one line at a time.
        strokeWeight((float) (xR * 1.2)); //lines fill screen
        stroke(iter2 + colourOffset, 10, 10, 10);  // full spectrum
        line(iter2 * xR, // X1
                height, // Y1
                iter2 * xR, // X2
                height - (iter2 * yR)); // Y2
        lines++;
        iter2++;
        //  println("lilfinish");
    }

    void bigTriFinish() { // do final animation 10 lines at a time.
        strokeWeight((float) (xR * 1.2)); //lines fill screen
        if (iter2 + 10 > aSize) {
            lilTriFinish();
        } else {
            for (int i = 0; i < 10; i++) {


                stroke(iter2 + colourOffset, 10, 10, 10);  // full spectrum
                line(iter2 * xR, // X1
                        (float) height / 2 + (iter2 * yR) / 2, // Y1
                        iter2 * xR, // X2
                        (float) height / 2 - (iter2 * yR) / 2); // Y2
                lines++;

                float freq2 = (iter2 + 150);
                float freq3 = (iter2 + 300) * 2;

                //snd0.play(freq2, 0.1);

                //env0.play(snd0, aT, sT, sL, rT);

                //snd1.play(freq3, 0.1);
                //env1.play(snd1, aT, sT, sL, rT);
                iter2++;
            }
        }
    }

    void lilTriFinish() { // do final animation just one line at a time.
        strokeWeight((float) (xR * 1.2)); //lines fill screen
        stroke(iter2 + colourOffset, 10, 10, 10);  // full spectrum
        line(iter2 * xR, // X1
                (float) height / 2 + (iter2 * yR) / 2, // Y1
                iter2 * xR, // X2
                (float) height / 2 - (iter2 * yR) / 2); // Y2
        lines++;
        iter2++;
    }

    void soundPtr() {


        int[] ptr = ptrList.get(iter);

        float freq2 = (ptr[2] + 150);
        float freq3 = (ptr[3] + 300) * 2;
        int[] oldPtr = new int[4];
        if (iter > 1) {
            oldPtr = ptrList.get(iter - 1);
        }

        if (ptr[2] > 0 && ptr[2] < aSize - 1) {
            if (ptr[2] != oldPtr[2] || !samePitchQuench) {
                snd0.play(freq2, 0.1F);
                env0.play(snd0, aT, sT, sL, rT);
            }
        }
        if (ptr[3] > 0 && ptr[3] < aSize - 1) {
            if (ptr[3] != oldPtr[3] || !samePitchQuench) {
                snd1.play(freq3, 0.1F);
                env1.play(snd1, aT, sT, sL, rT);
            }
        }
    }

    void writeSortName(float size) {


        fill(0, 0, 10, 10);                 // white text
        textSize(size - 5);
        rectMode(CENTER);
        textAlign(CENTER, CENTER);
        text(sortName, (float) width / 2, height + size / 2, width, size);

    }

    static void bubbleSort(int[] arr) {
        sortName = "Bubble Sort";
        int i, j, temp;
        int n = aSize;
        boolean swapped;

        for (i = 0; i < n - 1; i++) {

            swapped = false;

            for (j = 0; j < n - i - 1; j++) {

                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }

                snap(arr, j, j + 1);
            }
            // If no two elements were
            // swapped by inner loop, then break
            if (!swapped) {
                break;
            }
        }

        //snap(arr, 0, aSize -1);
    }

    public static void cocktailSort(int[] nums) {
        sortName = "Cocktail Shaker Sort";
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i <= aSize - 2; i++) {

                if (nums[i] > nums[i + 1]) {

                    //test if two elements are in the wrong order
                    int temp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = temp;
                    swapped = true;
                    snap(nums, i, i);
                }
            }
            if (!swapped) {
                break;
            }
            swapped = false;
            for (int i = aSize - 2; i >= 0; i--) {
                if (nums[i] > nums[i + 1]) {
                    int temp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = temp;
                    swapped = true;
                    snap(nums, i - 1, i - 1);
                }
            }
        } while (swapped);
        snap(nums, 0, aSize - 1);
    }

    public static void combSort(int[] array) {
        sortName = "Comb Sort";

        int n = aSize;
        snap(array, 0, n - 1);
        // initialize gap
        int gap = n;

        // Initialize swapped as true to make sure that
        // loop runs
        boolean swapped = true;

        // Keep running while gap is more than 1 and last
        // iteration caused a swap
        while (gap >= 1 && swapped) {
            // Find next gap
            gap = getNextGap(gap);

            // Initialize swapped as false so that we can
            // check if swap happened or not
            swapped = false;

            // Compare all elements with current gap
            for (int i = 0; i < n - gap; i++) {
                snap(array, i, i + gap);

                if (array[i] > array[i + gap]) {
                    // Swap arr[i] and arr[i+gap]
                    int temp = array[i];
                    array[i] = array[i + gap];
                    array[i + gap] = temp;

                    //     snap(array, i, i + gap, aList, ptrList);

                    // Set swapped
                    swapped = true;
                }
            }
        }
    }

    public static int getNextGap(int gap) {
        // Shrink gap by Shrink factor
        gap = (gap * 10) / 13;
        return Math.max(gap, 1);
    }

    public static void cycleSort(int[] arr) {
        sortName = "Cycle Sort";
        // count number of memory writes
        int writes = 0;
        int n = aSize;

        // traverse array elements and put it to on
        // the right place
        for (int cycle_start = 0; cycle_start <= n - 2; cycle_start++) {
            // initialize item as starting point
            int item = arr[cycle_start];
            aas++;
            // Find position where we put the item. We basically
            // count all smaller elements on right side of item.
            int pos = cycle_start;
            for (int i = cycle_start + 1; i < n; i++)
                if (arr[i] < item) {
                    pos++;
                    aas++;
                    comps++;
                    snap(arr, 0, i);
                }

            // If item is already in correct position
            if (pos == cycle_start)
                continue;

            // ignore all duplicate elements
            while (item == arr[pos])
                pos += 1;
            aas++;

            // put the item to it's right position
            if (pos != cycle_start) {
                int temp = item;
                item = arr[pos];
                arr[pos] = temp;
                writes++;
                aas += 2;
                snap(arr, 0, pos);
            }

            // Rotate rest of the cycle
            while (pos != cycle_start) {
                pos = cycle_start;

                // Find position where we put the element
                for (int i = cycle_start + 1; i < n; i++)
                    if (arr[i] < item)
                        pos += 1;
                aas++;
                comps++;
                // ignore all duplicate elements
                while (item == arr[pos])
                    pos += 1;

                // put the item to it's right position
                if (item != arr[pos]) {
                    int temp = item;
                    item = arr[pos];
                    arr[pos] = temp;
                    writes++;
                    comps++;
                    aas += 3;
                    snap(arr, 0, pos);
                }
                aas++;
                comps++;
                snap(arr, 0, pos);
            }
            snap(arr, 0, pos);
        }
        snap(arr, 0, aSize);
    }

    static void heapSort(int[] arr) {
        sortName = "Max Heap Sort";
        int n = aSize;
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {

            heapify(arr, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {

            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            swaps++;
            aas += 3;
            snap(arr, 0, i);

            // Call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
        //        snap(arr, 0, 0);
        //      return(arr);   //replace if method needs to be int[]
    }

    static void heapify(int[] arr, int n, int i) {

        // Initialize largest as root
        int largest = i;

        // left index = 2*i + 1
        int l = 2 * i + 1;

        // right index = 2*i + 2
        int r = 2 * i + 2;

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
            aas += 2;
            comps++;
        }

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
            aas += 2;
            comps++;
        }

        // If largest is not root
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            swaps++;
            aas += 3;
            snap(arr, i, largest);
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    void insertionSort(int[] arr) {

        sortName = "Insertion Sort";
        int n = aSize;
        for (int i = 1; i < n; ++i) {
            int key1 = arr[i];
            int j = i - 1;

            snap(arr, i, j + 1);

    /* Move elements of arr[0..i-1], that are
     greater than key, to one position ahead
     of their current position */
            while (j >= 0 && arr[j] > key1) {
                arr[j + 1] = arr[j];
                j = j - 1;

                snap(arr, i, j + 1);
            }
            arr[j + 1] = key1;


            snap(arr, i, j + 1);
        }

        snap(arr, 0, aSize - 1);
    }

    void mergeSort(int[] arr, int left, int right) {

        sortName = "In-place Merge Sort";

        if (left < right) {

            // Same as (l + r) / 2, but avoids overflow
            // for large l and r
            int mid = left + (right - left) / 2;

            // Sort first and second halves

            mergeSort(arr, left, mid);

            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);


            snap(arr, left, right+1);
        }

        //  snap(arr, left, right+1);
    }

    void merge(int[] arr, int start, int mid, int end)
    {


        int start2 = mid + 1;


        // If the direct merge is already sorted
        if (arr[mid] <= arr[start2]) {
            comps++;
            aas+=2;
            return;
        }

        // Two pointers to maintain start
        // of both arrays to merge
        while (start <= mid && start2 <= end) {

            // If element 1 is in right place
            if (arr[start] <= arr[start2]) {
                start++;
                comps+=2;
                aas+=2;
            } else {
                int value = arr[start2];
                aas++;
                int index = start2;

                // Shift all the elements between element 1
                // element 2, right by 1.
                while (index != start) {
                    arr[index] = arr[index - 1];
                    index--;
                    aas+=2;
                }
                arr[start] = value;
                aas++;
                snap(arr, start, start2+1);

                // Update all the pointers
                start++;
                mid++;
                start2++;
            }

            //   snap(arr, start, mid+1);
        }
        snap(arr, start, end+1);
    }

    static void iterativeMergeSort(int[] arr) {

        sortName = "Iterative Merge Sort";

        int n = aSize;

        // Iterate through subarrays of increasing size
        for (int currSize = 1; currSize <= n - 1;
             currSize = 2 * currSize) {

            // Pick starting points of different
            // subarrays of current size
            for (int leftStart = 0; leftStart < n - 1;
                 leftStart += 2 * currSize) {

                // Find endpoints of the subarrays to be merged
                int mid = Math.min(leftStart + currSize - 1, n - 1);
                int rightEnd = Math.min(leftStart + 2 * currSize - 1, n - 1);

                // Merge the subarrays arr[leftStart...mid]
                // and arr[mid+1...rightEnd]
                iterativeMerge(arr, leftStart, mid, rightEnd);
            }
            snap(arr, 0, aSize);
        }
        snap(arr, 0, aSize);
    }

    static void iterativeMerge(int[] arr, int start, int mid, int end)
    {


        int start2 = mid + 1;


        // If the direct merge is already sorted
        if (arr[mid] <= arr[start2]) {
            comps++;
            aas+=2;
            return;
        }

        // Two pointers to maintain start
        // of both arrays to merge
        while (start <= mid && start2 <= end) {

            // If element 1 is in right place
            if (arr[start] <= arr[start2]) {
                start++;
                comps+=2;
                aas+=2;
            } else {
                int value = arr[start2];
                aas++;
                int index = start2;

                // Shift all the elements between element 1
                // element 2, right by 1.
                while (index != start) {
                    arr[index] = arr[index - 1];
                    index--;
                    aas+=2;
                }
                arr[start] = value;
                aas++;
                snap(arr, start, start2+1);

                // Update all the pointers
                start++;
                mid++;
                start2++;
            }

            //   snap(arr, start, mid+1);
        }
        snap(arr, start, end+1);
    }

    public static void pigeonholeSort(int[] arr)
    {
        sortName = "Pigeonhole Sort";
        int min = arr[0];
        int max = arr[0];
        int range, i, j, index;
        int n = aSize;

        for (int a=0; a<n; a++)
        {
            if (arr[a] > max) {
                max = arr[a];
                aas+=2;
            }
            comps++;
            aas++;

            if (arr[a] < min) {
                min = arr[a];
                aas+=2;
            }
            comps++;
        }

        range = max - min + 1;

        int[] phole = new int[range];

        Arrays.fill(phole, 0);

        aas+= range;
        snap(phole, 0, aSize);

        for (i = 0; i<n; i++) {
            phole[arr[i] - min]++;
            aas++;
            snap(phole, i, aSize);
        }
        snap(arr, 0, aSize);
        index = 0;

        for (j = 0; j<range; j++) {
            while (phole[j]-->0) {
                arr[index++]=j+min;
                aas++;
                snap(arr, 0, j);
            }
        }
    }

    static void quickSort(int[] arr, int low, int high) {

        sortName = "QuickSort";

        if (low < high) {

            // partitionIndex is the partition return index of pivot

            int partitionIndex = qsPartition(arr, low, high);

            // Recursion calls for smaller elements
            // and greater or equals elements

            quickSort(arr, low, partitionIndex - 1);

            //      snap(arr, low, partitionIndex);

            quickSort(arr, partitionIndex + 1, high);
//    snap(arr, partitionIndex + 1, high);
        }

    }

    static int qsPartition(int[] arr, int low, int high)
    {

        // Choose the pivot
        int pivot = arr[high];
        aas++; // array access.

        // Index of smaller element and indicates
        // the right position of pivot found so far
        int i = low - 1;

        // Traverse arr[low..high] and move all smaller
        // elements to the left side. Elements from low to
        // i are smaller after every iteration

        for (int j = low; j <= high - 1; j++) {


            if (arr[j] < pivot) {
                i++;

                comps++;  // counts as a comparison.
                aas++; // counts as an array access.

                qsSwap(arr, i, j);
            }
        }

        // Move pivot after smaller elements and
        // return its position

        qsSwap(arr, i + 1, high);

        return i + 1;
    }

    static void qsSwap( int[] arr, int i, int j)
    {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        aas+=4;
        // The _only_ snapshot needed. maybe.

        snap(arr, i, j);
    }

    static int digit_at(int x, int d)
    {
        return (int)(x / Math.pow(base, d - 1)) % base;
    }

    static int[] MSD_sort(int[] arr, int lo, int hi, int d)
    {
        sortName = "Radix MSD Sort, base " + base ;
        //snap(arr, lo, hi);  // Snap 1
        // recursion break condition
        if (hi <= lo) {
            //    snap(arr, lo, hi); // Snap 2
            return arr;
        }

        int[] count = new int[base + 2];

        // temp is created to easily swap Strings in arr[]
        HashMap<Integer, Integer> temp = new HashMap<>();

        // Store occurrences of most significant character
        // from each integer in count[]
        for (int i = lo; i <= hi; i++) {
            int c = digit_at(arr[i], d);
            count[c + 2]++;
        }

        // Change count[] so that count[] now contains actual
        //  position of this digits in temp[]
        for (int r = 0; r < base + 1; r++) {
            count[r + 1] += count[r];
        }
        // Build the temp
        for (int i = lo; i <= hi; i++) {
            int c = digit_at(arr[i], d);

            if (temp.containsKey(count[c + 1]+1)) {

                temp.put(count[c + 1]++, arr[i]);
            } else {

                temp.put(count[c + 1]++, arr[i]);
            }
        }

        // Copy all integers of temp to arr[], so that arr[] now
        // contains partially sorted integers
        for (int i = lo; i <= hi; i++) {
            if (temp.containsKey(i-lo)) {
                arr[i] = temp.get(i - lo);
                snap(arr, i, hi); // Snap3
            }
        }
        // Recursively MSD_sort() on each partially sorted
        // integers set to sort them by their next digit
        for (int r = 0; r < base; r++) {
            arr = MSD_sort(arr, lo + count[r], lo + count[r + 1] - 1, d - 1);
            //   snap(arr, r, aSize); // Snap4
        }

        //  snap(arr, 0, aSize); // Snap5
        return arr;
    }

    // function find the largest integer
    static int getMMax(int[] arr, int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > mx) {
                mx = arr[i];
            }
        }
        return mx;
    }

    // Main function to call MSD_sort
    static void radixMSDSort(int[] arr)
    {
        int n = aSize;
        // Find the maximum number to know number of digits
        int m = getMMax(arr, n);

        // get the length of the largest integer
        int d = (int)Math.floor((Math.log(Math.abs(m))/Math.log(base))) + 1;
//  int d = (int)Math.floor(Math.log10(Math.abs(m))) + 1;  original

        // function call
        MSD_sort(arr, 0, n - 1, d);
    }

    static int getMax(int[] arr, int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > mx) {
                mx = arr[i];
            }
        }
        return mx;
    }

    static void countSort(int[] arr, int n, int exp)
    {
        int[] output = new int[n]; // output array
        int i;
        int[] count = new int[base];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++) {
            count[(arr[i] / exp) % base]++;
        }
        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < base; i++) {
            count[i] += count[i - 1];
        }
        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % base] - 1] = arr[i];
            count[(arr[i] / exp) % base]--;
            snap(output, 0, arr[i]);
        }

        //  snap(output, 0, iter);

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current
        // digit
        for (i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }

    static void radixSort(int[] arr)
    {
        sortName = "Radix LSD Sort, base "+ base;
        int n = aSize;
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Do counting sort for every digit. Note that
        // instead of passing digit number, exp is passed.
        // exp is 10^i where i is current digit number
        for (int exp = 1; m / exp > 0; exp *= base)
            countSort(arr, n, exp);
    }

    static void selectionSort(int[] arr)
    {
        sortName = "Selection Sort";
        int n = aSize;
        for (int i = 0; i < n-1; i++)
        {
            int index = i;
            int min = arr[i];
            for (int j = i+1; j < n; j++)
            {
                if (arr[j] < arr[index])
                {
                    index = j;

                    min = arr[j];
                    snap(arr, i-1, j);
                }
                snap (arr, i-1, j);
            }
            int t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
            snap(arr, i, index);
        }
        snap(arr, 0, aSize -1);
    }

    static void shellSort(int[] arr) {

        sortName = "Shell's Sort";

        int n = aSize;


        for (int gap = n / 2; gap > 0; gap /= 2) {

            for (int i = gap; i < n; i++) {
                int key1 = arr[i];
                aas++;
                int j = i;



                while (j >= gap && arr[j - gap] > key1) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                    comps++;
                    swaps++;
                    aas+=3;

                    snap(arr, i, j - gap);
                }

                arr[j] = key1;
                aas++;
                snap(arr, i, j);
            }
            // needed to complete sort drawing
            snap(arr, 0, aSize - 1);
        }
        // needed to complete sort.
        snap(arr, 0, aSize -1);
    }

    static int leonardo(int k)
    {
        if (k < 2) {
            return 1;
        }
        return leonardo(k - 1) + leonardo(k - 2) + 1;
    }

    static void smoothHeapify(int[] arr, int start, int end)
    {
        int i = start;
        int j = 0;
        int k = 0;
        //  snap(arr, start, end); // Snap 0
        while (k < end - start + 1) {
            if ((k & 0xAAAAAAAA) != 0) {
                j = j + i;
                i = i >> 1;
            } else {
                i = i + j;
                j = j >> 1;
            }

            k = k + 1;
        }

        while (i > 0) {
            j = j >> 1;
            k = i + j;
            while (k < end) {
                if (arr[k] > arr[k - i]) {
                    break;
                }
                int temp = arr[k];
                arr[k] = arr[k - i];
                arr[k - i] = temp;
                k = k + i;
                //       snap(arr, k, i); // Snap 1  Does nothing
            }
            snap(arr, i, j); // Snap C new
            i = j;
        }
    }

    static void smoothSort(int[] arr)
    {
        sortName = "Smooth Sort";
        int n = aSize;

        int p = n - 1;
        int q = p;
        int r = 0;

        // Build the Leonardo heap by merging
        // pairs of adjacent trees
        while (p > 0) {
            if ((r & 0x03) == 0) {
                smoothHeapify(arr, r, q);
                //       snap(arr, r, q);      //Snap A new
            }

            if (leonardo(r) == p) {
                r = r + 1;
            } else {
                r = r - 1;
                q = q - leonardo(r);
                smoothHeapify(arr, r, q);
                //        snap(arr, r, q); //  Snap B new
                q = r - 1;
                r = r + 1;
            }

            int temp = arr[0];
            arr[0] = arr[p];
            arr[p] = temp;
            snap(arr, temp, p);  // Snap 2 Doesn't do much.
            p = p - 1;
        }

        // Convert the Leonardo heap
        // back into an array
        for (int i = 0; i < n - 1; i++) {
            int j = i + 1;
            //      snap(arr, i, j); // Snap 3
            while (j > 0 && arr[j] < arr[j - 1]) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j = j - 1;
            }
            snap(arr, i, j);   //Snap 4 needs to be here or sort draw won't finish.
        }
    }

    public static int minRunLength(int n)
    {
        assert n >= 0;

        // Becomes 1 if any 1 bits are shifted off
        int r = 0;
        while (n >= MIN_MERGE) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }

    public static void timInsertionSort(int[] arr, int left, int right)
    {
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            aas++;
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
                comps++;
                aas+=3;
                //     snap(arr, i, j);
            }
            arr[j + 1] = temp;
            aas++;
            snap(arr, i, j+1);
        }
    }

    // Merge function merges the sorted runs
    static void timMerge(int[] arr, int start, int mid, int end)
    {


        int start2 = mid + 1;


        // If the direct merge is already sorted
        if (arr[mid] <= arr[start2]) {
            comps++;
            aas+=2;
            return;
        }

        // Two pointers to maintain start
        // of both arrays to merge
        while (start <= mid && start2 <= end) {

            // If element 1 is in right place
            if (arr[start] <= arr[start2]) {
                start++;
                comps++;
                aas+=2;
            } else {
                int value = arr[start2];
                aas++;
                int index = start2;

                // Shift all the elements between element 1
                // element 2, right by 1.
                while (index != start) {
                    arr[index] = arr[index - 1];
                    index--;
                    aas+=2;
                }
                arr[start] = value;
                aas++;
                snap(arr, start, start2 + 1);

                // Update all the pointers
                start++;
                mid++;
                start2++;
            }

            //   snap(arr, start, mid+1);
        }
        snap(arr, start, end);
    }

    public static void timSort(int[] arr, int n)
    {
        sortName = "Tim Sort";
        int minRun = minRunLength(MIN_MERGE);

        // Sort individual subarrays of size RUN
        for (int i = 0; i < n; i += minRun) {
            timInsertionSort(arr, i, Math.min((i + MIN_MERGE - 1), (n - 1)));
        }

        // Start merging from size
        // RUN (or 32). It will
        // merge to form size 64,
        // then 128, 256 and so on
        // ....
        for (int size = minRun; size < n; size = 2 * size) {

            // Pick starting point
            // of left sub array. We
            // are going to merge
            // arr[left..left+size-1]
            // and arr[left+size, left+2*size-1]
            // After every merge, we
            // increase left by 2*size
            for (int left = 0; left < n; left += 2 * size) {

                // Find ending point of left sub array
                // mid+1 is starting point of right sub
                // array
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1),
                        (n - 1));

                // Merge sub array arr[left.....mid] &
                // arr[mid+1....right]
                if (mid < right)
                    timMerge(arr, left, mid, right);
            }
        }
    }

    void compAndSwap(int[] a, int i, int j, int dir)
    {
        if ((a[i] > a[j] && dir == 1)
                || (a[i] < a[j] && dir == 0)) {
            // Swapping elements
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            snap(a, i, j);
        }
    }

    void bitonicMerge(int[] a, int low, int cnt, int dir)
    {
        if (cnt > 1) {
            int k = cnt / 2;
            for (int i = low; i < low + k; i++)
                compAndSwap(a, i, i + k, dir);
            bitonicMerge(a, low, k, dir);
            bitonicMerge(a, low + k, k, dir);
        }
    }

    void bitonicSort(int[] a, int low, int cnt, int dir)
    {
        sortName = "Bi-tonic Sort";
        if (cnt > 1) {
            int k = cnt / 2;

            // sort in ascending order since dir here is 1
            bitonicSort(a, low, k, 1);

            // sort in descending order since dir here is 0
            bitonicSort(a, low + k, k, 0);

            // Will merge whole sequence in ascending order
            // since dir=1.
            bitonicMerge(a, low, cnt, dir);
        }
    }

    static void dpqsSwap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        snap(arr, i, j);
    }

    static void dualPivotQuickSort(int[] arr, int low, int high)
    {
        sortName = "Dual Pivot QuickSort";
        if (low < high)
        {

            // piv[] stores left pivot and right pivot.
            // piv[0] means left pivot and
            // piv[1] means right pivot
            int[] piv;
            piv = dpqsPartition(arr, low, high);

            dualPivotQuickSort(arr, low, piv[0] - 1);
            //      snap(arr, low, piv[0] -1);
            dualPivotQuickSort(arr, piv[0] + 1, piv[1] - 1);
            //     snap(arr, piv[0] + 1, piv[1] -1);
            dualPivotQuickSort(arr, piv[1] + 1, high);
            //      snap(arr,piv[1] + 1, high);
        }
    }

    static int[] dpqsPartition(int[] arr, int low, int high)
    {
        if (arr[low] > arr[high])
            dpqsSwap(arr, low, high);

        // p is the left pivot, and q
        // is the right pivot.
        int j = low + 1;
        int g = high - 1, k = low + 1,
                p = arr[low], q = arr[high];

        while (k <= g)
        {

            // If elements are less than the left pivot
            if (arr[k] < p)
            {
                dpqsSwap(arr, k, j);
                j++;
            }

            // If elements are greater than or equal
            // to the right pivot
            else if (arr[k] >= q)
            {
                while (arr[g] > q && k < g)
                    g--;

                dpqsSwap(arr, k, g);
                g--;

                if (arr[k] < p)
                {
                    dpqsSwap(arr, k, j);
                    j++;
                }
            }
            k++;
        }
        j--;
        g++;

        // Bring pivots to their appropriate positions.
        dpqsSwap(arr, low, j);
        dpqsSwap(arr, high, g);

        // Returning the indices of the pivots
        // because we cannot return two elements
        // from a function, we do that using an array.
        return new int[] { j, g };
    }

}
