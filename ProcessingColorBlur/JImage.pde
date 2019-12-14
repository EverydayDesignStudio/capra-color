// Subclass of PImage with a mosaic function
//
// Copyright © 2018 Jordan White (jordan_white@sfu.ca)

class JImage extends PImage {
  public PImage img;

  public JImage(String filename) {
    super();
    img = loadImage(filename);
  }

  // Creates a mosaic of a certain block size
  void mosaic(int blockSize, int xPos, int yPos) {
    int w = this.img.width/blockSize;
    int h = this.img.height/blockSize;

    PImage smallerImg = createImage(w, h, RGB);
    smallerImg.copy(this.img, 0, 0, this.img.width, this.img.height, 0, 0, w, h);
    smallerImg.loadPixels();

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        // For an explanation of this formula
        // https://processing.org/tutorials/pixels/
        int i = x + (y * w); 
        color c = smallerImg.pixels[i];
        fill(c);
        noStroke();
        rect(xPos + x*blockSize, yPos + y*blockSize, blockSize, blockSize);
      }
    }
  }

  public void customTestMethod() {
    println("Which object is being called");
  }
}
