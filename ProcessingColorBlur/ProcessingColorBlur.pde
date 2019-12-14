// A3_01  Create a subclass of PImage that implements a mosaic(int blockSize) method
//
// Copyright © 2018 Jordan White (jordan_white@sfu.ca)

// Public declarations
PFont dinBold, dinReg;
JImage orangeImg, homeImg, plantImg;
int[] blockSizes = {1, 1, 1};

int image1sz = 256;
int image2sz = 256;
int image3sz = 256;

// Setup the scene
void setup() {
  // Standard
  size(1560, 810);
  background(255);
  pixelDensity(2);
  
  // Font
  dinBold = createFont("DinBold.TTF", 32);
  dinReg = createFont("DIN Alternate Regular.otf", 30);

  // Load images
  orangeImg = new JImage("oranges.jpg");
  homeImg = new JImage("home.jpg");
  plantImg = new JImage("plants.jpg");
  
  // Draw images first time
  orangeImg.mosaic(1, 15, 45);
  homeImg.mosaic(1, 500 + 30, 45);
  plantImg.mosaic(1, 1000 + 45, 45);
}

// Draw the scene
void draw() {
  drawInstructions();
  
  //noLoop();
}

// Mouse click
void mouseClicked() {
  // Image 1
  if(mouseX > 15 && mouseX < 515 && mouseY > 45 && mouseY < 795) {
    println("orange");
    fill(255);
    rect(15, 45, 500, 750);
    
    int sz = blockSizes[0] * 2;
    if (sz > image1sz) sz = 1;
    blockSizes[0] = sz;
    
    orangeImg.mosaic(blockSizes[0], 15, 45);
  } 
  // Image 2
  else if (mouseX > 530 && mouseX < 1030 && mouseY > 45 && mouseY < 795) {
    println("home");
    fill(255);
    rect(530, 45, 500, 750);
    
    int sz = blockSizes[1] * 2;
    if (sz > image2sz) sz = 1;
    blockSizes[1] = sz;
    
    homeImg.mosaic(blockSizes[1], 500 + 30, 45);
  }
  // Image 3
  else if (mouseX > 1045 && mouseX < 1545 && mouseY > 45 && mouseY < 795) {
    println("plant");
    fill(255);
    rect(1045, 45, 500, 750);
    
    int sz = blockSizes[2] * 2;
    if (sz > image3sz) sz = 1;
    blockSizes[2] = sz;
    
    plantImg.mosaic(blockSizes[2], 1000 + 45, 45);
  }
}

// Draws the instructions on the canvas
void drawInstructions() {
  textFont(dinBold);
  fill(100, 100, 100);
  text("Click", 15, 30);
  
  textFont(dinReg);
  text("repeatedly on each image", 95, 30);
}
