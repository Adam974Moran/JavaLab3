package com.example.springbootlab1.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class RequestsCounterService {
  private final AtomicInteger count = new AtomicInteger(0);

  public void increment(){
    count.incrementAndGet();
  }

  public int getCount() {
    return count.get();
  }
}
