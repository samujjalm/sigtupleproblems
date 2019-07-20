package dev.samujjal.coding.interview;

import dev.samujjal.coding.interview.service.Solution2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class SigtupleRunner implements CommandLineRunner {
  @Autowired
  private Solution2Service solution2Service;
  @Override
  public void run(String... args) throws Exception {
    solution2Service.run();
  }
}
