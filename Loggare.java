package com.mycompany.dbprogrammet;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Loggare {

    Epost epost = new Epost();
    static Handler fileHandler = null;
    private static final Logger LOGGER = Logger.getLogger(Program.class
            .getClass().getName());

    public void log_info(String info, String fel, Exception ex) {
        setup(fel, ex);
        epost.skicka_loggar(System.getProperty("user.dir") + "//Sparat//Loggar//" + fel, fel);
    }

    public void setup(String fel, Exception ex) {
        try {
            fileHandler = new FileHandler(System.getProperty("user.dir") + "//Sparat//Loggar//" + fel);
            File file = new File(System.getProperty("user.dir") + "//Sparat//Loggar//" + fel);
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);

            LOGGER.addHandler(fileHandler);

            try (PrintStream ps = new PrintStream(file)) {
                ex.printStackTrace(ps);
            }

        } catch (IOException e) {
        }

    }

}
