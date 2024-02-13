package com.softdignitas.ddd.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class JTMaterialProcesVerbalTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static JTMaterialProcesVerbal getJTMaterialProcesVerbalSample1() {
        return new JTMaterialProcesVerbal().id("id1").cantitate(1);
    }

    public static JTMaterialProcesVerbal getJTMaterialProcesVerbalSample2() {
        return new JTMaterialProcesVerbal().id("id2").cantitate(2);
    }

    public static JTMaterialProcesVerbal getJTMaterialProcesVerbalRandomSampleGenerator() {
        return new JTMaterialProcesVerbal().id(UUID.randomUUID().toString()).cantitate(intCount.incrementAndGet());
    }
}
