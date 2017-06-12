package com.shortandprecise;


/**
 * Created by rejuan on 6/12/17.
 */
public class App {

    public static void main(String[] args) {
        if (args.length == 2) {
            String url = args[0];
            int numberOfClient = 100;
            try {
                numberOfClient = Integer.parseInt(args[1]);
            } catch (Exception ex) {
                System.out.println("Invalid number of client.");
                System.out.println("Starting with default 100");
            }

            new Thread(new ClientGenerator(url, numberOfClient)).start();

        } else {
            System.out.println("Set URL and number of client as argument respectively.");
        }
    }
}
