package dev.samujjal.coding.interview.service;

import dev.samujjal.coding.interview.domain.DeliverySlot;
import dev.samujjal.coding.interview.domain.Medicine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class Solution2Service {
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<DeliverySlot> getDeliverySlots() {
        Scanner in = new Scanner(System.in);
        char response = 'y';
        List<DeliverySlot> deliverySlots = new ArrayList<>();
        while (response == 'y') {
            log.info("Add delivery slots: ");
            log.info("Start slot (yyyy-MM-dd HH:mm): ");
            DeliverySlot deliverySlot = new DeliverySlot();
            deliverySlot.setStartTime(LocalDateTime.parse(in.nextLine(), dateFormat));
            log.info("End slot (yyyy-MM-dd HH:mm): ");
            deliverySlot.setEndTime(deliverySlot.getStartTime().plusHours(2));
            log.info("Want to add another slot, press y for yes else n");
            response = in.nextLine().toCharArray()[0];
            deliverySlots.add(deliverySlot);
        }
        in.close();
        return deliverySlots;
    }

    public List<DeliverySlot> getAutomatedDeliverySlots() {
        List<DeliverySlot> deliverySlots = new ArrayList<>();
        deliverySlots.add(new DeliverySlot(LocalDateTime.of(2019, Month.JUNE, 7, 6, 0),
                LocalDateTime.of(2019, Month.JUNE, 7, 8, 0), 100, null, "Paracetamol"));

        deliverySlots.add(new DeliverySlot(LocalDateTime.of(2019, Month.JUNE, 5, 5, 0),
                LocalDateTime.of(2019, Month.JUNE, 5, 7, 0), 100, null, "Paracetamol"));

        deliverySlots.add(new DeliverySlot(LocalDateTime.of(2019, Month.JUNE, 9, 11, 0),
                LocalDateTime.of(2019, Month.JUNE, 9, 13, 0), 100, null, "Dolo 650"));

        deliverySlots.add(new DeliverySlot(LocalDateTime.of(2019, Month.JUNE, 6, 15, 0),
                LocalDateTime.of(2019, Month.JUNE, 6, 17, 0), 100, null, "Dolo 650"));

        deliverySlots.sort(Comparator.comparing(DeliverySlot::getStartTime));

        return deliverySlots;

    }

    public List<Medicine> getMedicines() {
        List<Medicine> medicineList = new ArrayList<>();
        medicineList.add(new Medicine("Paracetamol", LocalDateTime.of(2019, Month.JUNE, 5, 5, 0),
                LocalDateTime.of(2019, Month.JUNE, 8, 23, 0), 50));

        medicineList.add(new Medicine("Paracetamol", LocalDateTime.of(2019, Month.JUNE, 7, 6, 0),
                LocalDateTime.of(2019, Month.JUNE, 10, 23, 0), 80));

        medicineList.add(new Medicine("Paracetamol", LocalDateTime.of(2019, Month.JUNE, 11, 12, 0),
                LocalDateTime.of(2019, Month.JUNE, 15, 23, 0), 100));

        medicineList.add(new Medicine("Dolo 650", LocalDateTime.of(2019, Month.JUNE, 6, 15, 0),
                LocalDateTime.of(2019, Month.JUNE, 10, 23, 0), 80));

        medicineList.add(new Medicine("Dolo 650", LocalDateTime.of(2019, Month.JUNE, 9, 11, 0),
                LocalDateTime.of(2019, Month.JUNE, 14, 23, 0), 60));

        return medicineList;
    }

    public void run() {
        List<DeliverySlot> sortedDeliverySlots = getAutomatedDeliverySlots();

        List<Medicine> medicineList = getMedicines();
        Map<String, List<Medicine>> medicineMap = new HashMap<>();
        medicineList.forEach(medicine -> {
            medicineMap.computeIfAbsent(medicine.getName(), k -> new ArrayList<>());
            medicineMap.get(medicine.getName()).add(medicine);
        });

        medicineMap.forEach((s, medicines) -> {
            medicines.sort(Comparator.comparing(Medicine::getAvailableDate));
        });

        sortedDeliverySlots.forEach(deliverySlot -> setSlotAvalailibility(deliverySlot, medicineMap));

        sortedDeliverySlots.forEach(deliverySlot -> log.info("{}", deliverySlot));
    }

    private void setSlotAvalailibility(DeliverySlot deliverySlot, Map<String, List<Medicine>> medicineMap) {
        List<Medicine> medicineList = medicineMap.get(deliverySlot.getMedicineName());
        medicineList.sort(Comparator.comparing(Medicine::getAvailableDate));
        //TODO: apply binary search based for delivery slot against medicine slotstarttime
        int tentativeStock = 0;
        for (int i = 0; i < medicineList.size(); i++) {
            Medicine medicine = medicineList.get(i);
            if (medicine.getStockUnits() > 0 && tentativeStock < deliverySlot.getRequestedUnits() &&
                    (deliverySlot.getStartTime().isEqual(medicine.getAvailableDate()) || deliverySlot.getStartTime().isAfter(medicine.getAvailableDate()))

                    && deliverySlot.getStartTime().isBefore(medicine.getExpiryDate())
            ) {
                if (medicine.getStockUnits() >= deliverySlot.getRequestedUnits()) {
                    tentativeStock = deliverySlot.getRequestedUnits();
                    break;
                } else {
                    tentativeStock += medicine.getStockUnits();
                }
            }
        }
        deliverySlot.setSlotAvailable(tentativeStock >= deliverySlot.getRequestedUnits());

    }
}
