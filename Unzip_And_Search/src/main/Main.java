package main;

import java.awt.EventQueue;

import controller.Controller_main;

class Main {
	public static void main(String args[]) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Controller_main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
