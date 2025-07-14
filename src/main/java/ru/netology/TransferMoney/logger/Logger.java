package ru.netology.TransferMoney.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Logger {
    private final AtomicInteger counter = new AtomicInteger();

    private static volatile Logger logger;

    private Logger(){}

    public static Logger getInstance() {
        if (logger == null) {
            synchronized (Logger.class) {
                if (logger == null) {
                    logger = new Logger();
                }
            }
        }
        return logger;
    }

    public void log(String msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write("[" + LocalDateTime.now().format(formatter) + " - " + counter.getAndIncrement() + "] " + msg);
            writer.append('\n');
        } catch (IOException e) {
            System.err.println("Logger error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
