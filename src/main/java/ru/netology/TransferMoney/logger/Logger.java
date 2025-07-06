package ru.netology.TransferMoney.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    protected int num = 1;

    private static Logger logger;

    private Logger(){}

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public void log(String msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss ");
        System.out.println("[" + LocalDateTime.now().format(formatter) + num++ + "] " + msg);
    }
}
