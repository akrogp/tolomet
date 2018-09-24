package com.akrog.tolomet.model;

public interface Consumer<T> {
    void accept(T value);
}
