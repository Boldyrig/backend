package com.gmail.fuskerr.backend.core;

import java.util.List;

@FunctionalInterface
public interface ConsumerList <T> {
    void accept(List<T> list);
}
