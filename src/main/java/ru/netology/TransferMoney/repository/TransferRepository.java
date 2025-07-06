package ru.netology.TransferMoney.repository;

import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferRepository {

    private AtomicInteger id;

    public TransferRepository() {
        id = new AtomicInteger(0);
    }

    public int getId() {
        return id.incrementAndGet();
    }

    public int getLastId() {
        return id.get();
    }
}
