package com.shortandprecise.loadgenerator;

import com.shortandprecise.loadgenerator.process.LoadRunner;

/**
 * Starting point of the application
 *
 * @author rejuan
 */
public class App {

	public static void main(String[] args) {
		new Thread(new LoadRunner()).start();
	}
}
