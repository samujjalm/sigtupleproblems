package dev.samujjal.coding.interview.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Medicine {
  private String name;
  private LocalDateTime availableDate;
  private LocalDateTime expiryDate;
  @EqualsAndHashCode.Exclude
  private int stockUnits;
}
