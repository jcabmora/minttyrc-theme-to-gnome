import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;


public class GetMinttyTheme{

  private static String ZEROS = "00";

public static void main(String args[]){
  if (args.length > 0 ){
    try{
      File inputFile = new File(args[0]);

      if (inputFile.exists() && inputFile.isFile()){
         FileInputStream fis = new FileInputStream(inputFile);
         Properties prop = new Properties();
         prop.load(fis);
         fis.close();

         String[] colorKeys = {
           "Black",
           "Red",
           "Green",
           "Yellow",
           "Blue",
           "Magenta",
           "Cyan",
           "White",
           "BoldBlack",
           "BoldRed",
           "BoldGreen",
           "BoldYellow",
           "BoldBlue",
           "BoldMagenta",
           "BoldCyan",
           "BoldWhite"
       };


       StringBuilder palette = new StringBuilder("");

       for(String colorKey : colorKeys){
         String colorValueAsRGBString = prop.getProperty(colorKey);
         if(colorValueAsRGBString != null){
           palette.append("#");
           palette.append(getColorValue(colorValueAsRGBString));
           palette.append(":");
          }
       }

       palette.deleteCharAt(palette.length()-1);

       String fgValueAdRGBString = prop.getProperty("ForegroundColour");
       String bgValueAdRGBString = prop.getProperty("BackgroundColour");


       int dotLoc = args[0].lastIndexOf('.');

       String outputFileName = args[0] + ".sh";

       if(dotLoc > 0){
         outputFileName = args[0].substring(0,dotLoc) + ".sh";
       }

       File outputFile = new File(outputFileName);

       PrintWriter pw = new PrintWriter(new FileWriter(outputFile));

       pw.write("#!/bin/bash \n");
       pw.write("# Custom theme for gnome-terminal \n");
       pw.write("gnome_term_profile=/apps/gnome-terminal/profiles/Default \n");
       pw.write("palette=\"" + palette.toString() + "\" \n");
       if(fgValueAdRGBString != null){
         String fg_color = getColorValue(fgValueAdRGBString);
         pw.write("fg_color=\"#" + fg_color + "\" \n");
       }
       if(bgValueAdRGBString != null){
         String bg_color = getColorValue(bgValueAdRGBString);
         pw.write("bg_color=\"#" + bg_color + "\" \n");
       }
       pw.write("bd_color=\"#000000000000\" \n");
       pw.write("gconftool-2 -s -t string $gnome_term_profile/palette $palette \n");
       pw.write("gconftool-2 -s -t string $gnome_term_profile/bold_color $bd_color \n");
       pw.write("gconftool-2 -s -t string $gnome_term_profile/background_color $bg_color \n");
       pw.write("gconftool-2 -s -t string $gnome_term_profile/foreground_color $fg_color \n");
       pw.write("gconftool-2 -s -t bool $gnome_term_profile/use_theme_colors false \n");
       pw.write("gconftool-2 -s -t bool $gnome_term_profile/bold_color_same_as_fg true \n"); //TODO: add code to adjust bold color from fg color
       pw.flush();
    	}
    	else
    	System.out.println(args[0] + " is not a valid file");
      }
      catch(IOException ioe){
        System.out.println("Unable to read from file " + args[0]);
      }
  }
  else
  System.out.println("Please provide a file name.");
}

  private static String formatHexOctet(String number){
    String hexNumber = Integer.toHexString(Integer.parseInt(number));
    return hexNumber.length() < 2 ? ZEROS.substring(hexNumber.length())+hexNumber: hexNumber;
  }

  private static String getColorValue(String colorValueAsRGBList){
    StringBuilder colorValue = new StringBuilder("");
    if(colorValueAsRGBList != null){
      String[] RGB = colorValueAsRGBList.split(",");
      String red = formatHexOctet(RGB[0]);
      colorValue.append(red);
      colorValue.append(red);
      String green = formatHexOctet(RGB[1]);
      colorValue.append(green);
      colorValue.append(green);
      String blue = formatHexOctet(RGB[2]);
      colorValue.append(blue);
      colorValue.append(blue);
    }
    return colorValue.toString();
  }



}
