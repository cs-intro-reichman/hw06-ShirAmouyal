// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;
import java.awt.Image;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		imageOut = flippedHorizontally(tinypic);
		System.out.println();
		print(imageOut);
		Color [][] imageOut2;
		imageOut2=grayScaled(tinypic);
		System.out.println();
		print(imageOut2);
		Color [][] imageOut3;
		imageOut3=flippedHorizontally(tinypic);
		System.out.println();
		print(imageOut3);
		Color [][] imageOut4;
		imageOut4=scaled(tinypic, 3, 5);
		System.out.println();
		print(imageOut4);
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		int a,b,c;
		for(int i=0;i<image.length;i++){
			for(int k=0;k<image[0].length;k++){
				a=in.readInt();
				b=in.readInt();
				c=in.readInt();
				image[i][k]=new Color(a,b,c);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		
	  for(int i=0;i<image.length;i++){
		
		for(int k=0;k<image[0].length;k++){
			Color color=(image)[i][k];
			print(color);
		}
		System.out.println();
	  }

	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int rows=image.length;
		int cols=image[0].length;
		Color[][] Nimage = new Color[rows][cols];
		for(int i=0;i<rows;i++){
			
			for(int k=0;k<cols;k++){
				
				Nimage[i][k]= image[i][cols-1-k];
			}
		}
		return Nimage;
	}
	public static Color[][] flippedVertically(Color[][] image){
		int rows=image.length;
		int cols=image[0].length;
		Color[][] Nimage = new Color[rows][cols];
		for(int i=0;i<rows;i++){
			for(int k=0;k<cols;k++){
				
			 Nimage[i][k]=image[rows-1-i][k];
			}
		}
		return Nimage;
		
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		Color c;
		int lum=(int)((0.299*pixel.getRed())+(0.587*pixel.getGreen())+(0.114*pixel.getBlue()));
		c=new Color(lum,lum,lum);
		return c;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] NewImage=new Color[image.length][image[0].length];
		for(int i=0;i<image.length;i++){
			for(int k=0;k<image[0].length;k++){
				NewImage[i][k]=luminance((image[i][k]));
			}
		}
		return NewImage;
	}	
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] NewScaled=new Color[height][width];
		int Nheight=image.length;
		int Nwidth=image[0].length;
		for(int i=0;i<height;i++){
			for(int k=0;k<width;k++){
             NewScaled[i][k]=image[i*Nheight/height][k*Nwidth/width];
			}
		}
		return NewScaled;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int red1=c1.getRed();
		int green1=c1.getGreen();
		int blue1=c1.getBlue();
		int red2=c2.getRed();
		int green2=c2.getGreen();
		int blue2=c2.getBlue();
		int newRed=(int)((red1*alpha)+((1-alpha)*red2));
		int newBlue=(int)((blue1*alpha)+((1-alpha)*blue2));
		int newGreen=(int)((green1*alpha)+((1-alpha)*green2));
		Color col=new Color(newRed,newGreen,newBlue);
		return col;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] belndColor=new Color[image1.length][image1[0].length];
		if(image1.length != image2.length || image1[0].length!=image2[0].length){
			image2=scaled(image2,image1[0].length, image1.length)
		}
		for(int i=0;i<image1.length;i++){
			for(int j=0;j<image1[0].length;j++){
				belndColor[i][j]=blend(image1[i][j],image2[i][j], alpha);
			}
		}
		return belndColor;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		Color[][] newT;
		newT=scaled(target, source[0].length, source.length);
		double alpha;
		for(int i=0;i<n;i++){
			alpha=(double)(n-i)/(double)(n);
			display(blend(source,newT,alpha));
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();

	}
}
