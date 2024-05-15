package com.example.springbootlab1.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class RequestsCounterService {
  private RequestsCounterService(){}
  private static final AtomicInteger count = new AtomicInteger(0);

  public static void increment(){
    count.incrementAndGet();
  }

  public static int getCount() {
    return count.get();
  }
}
