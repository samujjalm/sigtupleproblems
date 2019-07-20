package dev.samujjal.coding.interview.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Solution1Service {

    public int[] getBuildingInput(){
        return new int[]{0, 2, 0, 0, 6,0, 0, 0, 4};
    }

    public void run(){
        int[] buildingStats = getBuildingInput();
        int[] leftHighest = new int[buildingStats.length];
        leftHighest[0] = buildingStats[0];
        int[] rightHighest = new int[buildingStats.length];
        rightHighest[rightHighest.length-1] = buildingStats[buildingStats.length - 1];

        for (int i = 1; i < buildingStats.length; i++) {
            leftHighest[i] = Math.max(leftHighest[i-1], buildingStats[i]);
        }

        for (int i = rightHighest.length-2; i >= 0 ; i--) {
            rightHighest[i] = Math.max(rightHighest[i+1], buildingStats[i]);
        }

        int totalVolumne = 0;
        for (int i = 0; i < buildingStats.length; i++) {
            totalVolumne += Math.min(leftHighest[i], rightHighest[i]) - buildingStats[i];
        }

        log.info("Total possible rainwater harvested : {}", totalVolumne);
    }
}
