package com.alura.hotel.main;

import java.awt.EventQueue;

import com.alura.hotel.view.MenuPrincipal;

public class Hotel {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}