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
public class DeliverySlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
