package com.tvh.bootcamp.infrastructure;

import java.util.List;


public interface IRepository<T> {
    void add(T toAdd);
    List<T> getAll();
}
