package org.runtime;

import org.controller.System;
import org.view.TextView;

public class Main {
    public static void main(String[] args) {
        java.lang.System.out.println("This is the Runtime");

        TextView textView = new TextView(java.lang.System.in, java.lang.System.out);

        org.controller.System sys = new System(textView);

        sys.initializePlayers(0);
    }
}