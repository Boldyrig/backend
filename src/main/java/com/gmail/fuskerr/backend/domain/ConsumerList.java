package com.gmail.fuskerr.backend.domain;

import java.util.List;

@FunctionalInterface
public interface ConsumerList <T> {
    void accept(List<T> list);
}
