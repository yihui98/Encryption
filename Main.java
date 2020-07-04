package encryptdecrypt;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static String readFileAsString(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

    public static void main(String[] args) {

        /*
        Scanner input = new Scanner(System.in);
        String type = input.nextLine();
        String phrase = input.nextLine();
        int number = input.nextInt();
        */

        String alg = "shift";

        boolean state = false;
        boolean inOrOut = false;
        String out = "";
        String newtext = "";


        String type = "enc";
        String phrase = "";
        int number = 0;

        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-key")) {
                number = Integer.parseInt(args[i + 1]);
            } else if (args[i].equals("-data")) {
                phrase = args[i + 1];
                state = true;
            } else if (args[i].equals("-mode")) {
                type = args[i + 1];
            } else if (args[i].equals("-in")) {
                if (state == false) {
                    phrase = args[i + 1];
                }
            } else if (args[i].equals("-out")) {
                out = args[i + 1];
            } else if (args[i].equals("-alg")) {
                alg = args[i + 1];
            }
        }


        if (state == false) {
            try {
                String holder = phrase;
                phrase = readFileAsString(holder);
            } catch (IOException e) {
                System.out.println("Error");
            }
        }


        if (alg.equals("shift")) {
            if (type.equals("enc")) {
                newtext = sencrypt(phrase, number);
            } else if (type.equals("dec")) {
                newtext = sdecrypt(phrase, number);
            }
        } else {
            if (type.equals("enc")) {
                newtext = encrypt(phrase, number);
            } else if (type.equals("dec")) {
                newtext = decrypt(phrase, number);
            }
        }

        if (!out.equals("")) {
            File file = new File(out);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(newtext);
            } catch (IOException e) {
                System.out.println("Error");
            }
        } else {
            System.out.println(newtext);
        }
    }


        public static String encrypt (String phrase,int number){
            int max = 122;
            int min = 97;
            String empty = "";
            for (int i = 0; i < phrase.length(); i++) {
                int x = phrase.charAt(i);
                x = x + number;
                empty += (char) x;

            }
            return empty;
        }

        public static String decrypt (String phrase,int number){
            int max = 122;
            int min = 97;
            String empty = "";
            for (int i = 0; i < phrase.length(); i++) {
                int x = phrase.charAt(i);
                x = x - number;
                empty += (char) x;
            }
            return empty;
        }


        public static String sencrypt (String phrase,int number){
            int max = 122;
            int min = 97;
            String empty = "";
            for (int i = 0; i < phrase.length(); i++) {
                int x = phrase.charAt(i);
                if (Character.toString(x).matches("[a-z]")) {
                    if (x + number > max) {
                        x = x + number - max + min - 1;
                    } else {
                        x = x + number;
                    }
                } else if (Character.toString(x).matches("[A-Z]")) {
                    int Amax = 90;
                    int Amin = 65;
                    if (x + number > Amax) {
                        x = x + number - Amax + Amin - 1;
                    } else {
                        x = x + number;
                    }
                } else {
                    x += 0;
                }

                empty += (char) x;

            }
            return empty;
        }


        public static String sdecrypt (String phrase,int number){
            int max = 122;
            int min = 97;
            String empty = "";
            for (int i = 0; i < phrase.length(); i++) {
                int x = phrase.charAt(i);
                if (Character.toString(x).matches("[a-z]")) {
                    if (x - number < min) {
                        x = x - number + max - min + 1;
                    } else {
                        x = x - number;
                    }
                } else if (Character.toString(x).matches("[A-Z]")) {
                    int Amax = 90;
                    int Amin = 65;
                    if (x - number < Amax) {
                        x = x - number + Amax - Amin + 1;
                    } else {
                        x = x - number;
                    }
                } else {
                    x += 0;
                }

                empty += (char) x;

            }
            return empty;
        }
    }








