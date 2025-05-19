 import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Parameter;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;
import uk.ac.leedsbeckett.oop.LBUGraphics;

public class TurtleGraphics extends LBUGraphics {

    String commandList = "";

    public static void main(String[] args) {
        new TurtleGraphics(); //create instance of class that extends LBUGraphics (could be separate class without main), gets out of static context
    }

    public TurtleGraphics() {
        JFrame MainFrame = new JFrame();                //create a frame to display the turtle panel on
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Make sure the app exits when closed
        MainFrame.setLayout(new FlowLayout());  //not strictly necessary
        MainFrame.add(this);                                    //"this" is this object that extends turtle graphics so we are adding a turtle graphics panel to the frame
        MainFrame.pack();                                               //set the frame to a size we can see
        MainFrame.setVisible(true);
        //about();                           //call the LBUGraphics about method to display version information.
    }

    public void drawSquare (int size) {
        for (int i = 0; i < 4; i++) {
            forward(size);
            right(90);
        }
    }

    public void drawTriangle(int size) {
        for (int i = 0; i < 3; i++) {
            forward(size);
            right(120);
        }

    }

    @Override
    public void processCommand(String command) {
        int a;
        delveCommand(command);
        commandList += command + "\n";
    }

    public void delveCommand(String command) {
        System.out.println("in processCommand");
        String[] split_up_text = command.split(" ");

        if (split_up_text.length > 0) {
            switch (split_up_text[0]) {

                case "move": //starts to draw
                    if (split_up_text.length > 1) {
                        try {
                            int parameter = Integer.parseInt(split_up_text[1]);
                            forward(parameter);
                        } catch (NumberFormatException e) {
                            System.out.println("invalid parameter");
                        }
                    } else {
                        System.out.println("Missing parameter ");
                    }
                    break;
                case "pendown":
                    setPenState(true); //starts the drawing
                    break;
                case "penup":
                    setPenState(false);
                    break;
                case "red":
                    setPenColour(Color.RED);
                    break;

                case "forward":
                    forward(100); // moves turtle forward 100 units
                    break;

                case "backward":
                    forward(100);
                    break;

                case "left":
                    left(90);
                    break;

                case "right":
                    right(90);
                    break;

                case "reset":
                    reset(); // reset turtle position and screen
                    break;

                case "clear":
                    clear();
                    break;

                case "save":

                    try {
                        FileWriter file = new FileWriter("program.txt");
                        BufferedWriter output = new BufferedWriter(file);
                        output.write(commandList);
                        output.close();
                    } catch (IOException e) {
                        System.out.println("error occurred while writing to file ");
                    }
                    break;

                case "Load":
                    File file = new File("program.txt");
                    FileReader fileReader = null;
                    try {
                        fileReader = new FileReader(file);
                        BufferedReader br = new BufferedReader(fileReader);
                        String line;
                        while ((line = br.readLine()) != null) // get back nothing its end of file eventually ypou will get nothing back which means we have reached end of file
                        {
                            if (!line.equals("load")) {
                                delveCommand(line);

                            }
                            delveCommand(line);
                        }
                    } catch (
                            IOException e) {// Catch and io exception also catching file not found exception / catching both
                        System.out.println("error occurred while reading to file ");

                    }

                    break;

                case "savingimg":
                    try {
                        BufferedImage bufferedImage = getBufferedImage();
                        ImageIO.write(bufferedImage, "png", new File("saved_image.png")); //save image , use image io to write to file after opening it
                        System.out.println("Image saved. ");
                    } catch (IOException e) {
                        System.out.println("save failed. ");
                    }
                    break;

                case "square": // square 100
                    if (split_up_text.length > 1) {
                        try {
                            int size = Integer.parseInt(split_up_text[1]);
                            drawSquare(size);
                        } catch (NumberFormatException e) {
                            System.out.println("square size not valid. ");
                        }
                    }else {
                        System.out.println("Parameter missing for square. ");
                    }
                    break;
                case "triangle": // makes a triangle triangle 80 - makes equilateral triangle
                    if (split_up_text.length > 1) {
                        try {
                            int size = Integer.parseInt(split_up_text[1]);
                            drawTriangle(size);
                        } catch (NumberFormatException e) {
                            System.out.println("Triangle size not valid. ");
                        }
                    }else {
                        System.out.println("Parameter missing for Triangle. ");
                    }
                    break;

                case "pencolour":  //255,0,255 = bright pink  //0,255,0 = green  //0,0,0 = black

                    if (split_up_text.length > 1) {
                        try {
                            String[] rgb = split_up_text[1].split(",");
                            int r = Integer.parseInt(rgb[0]);
                            int g = Integer.parseInt(rgb[1]);
                            int b = Integer.parseInt(rgb[2]);
                            setPenColour(new Color(r, g, b));
                        } catch (Exception e) {
                            System.out.println("RGB format not valid. ");
                        }
                    }else {
                        System.out.println("RGB values missing");
                    }
                    break;

                case"penwidth":  // smaller number smaller pixel larger number bigger pixel thickness of pen stroke
                    if (split_up_text.length > 1) {
                        try {
                            int width = Integer.parseInt(split_up_text[1]);
                            setStroke(width);
                        } catch (NumberFormatException e) {
                            System.out.println("pen width not valid. ");
                        }
                        System.out.println("pen width missing. ");
                    }
                    break;

                default:
                    JOptionPane.showMessageDialog(this, "Invalid command ", "ERROR", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }
}


//String parameter is the text typed into the LBUGraphics JTextfield
//lands here if return was pressed or "ok" JButton clicked

//TO DO
//this method must be provided because LBUGraphics will call it when it's JTextField is used
