package com.softdignitas.ddd.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcesVerbalTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ProcesVerbal getProcesVerbalSample1() {
        return new ProcesVerbal()
            .id("id1")
            .numarProcesVerbal(1)
            .reprezentant("reprezentant1")
            .spatii("spatii1")
            .suprafata(1)
            .rapelDezinsectie(1)
            .rapelDeratizare(1);
    }

    public static ProcesVerbal getProcesVerbalSample2() {
        return new ProcesVerbal()
            .id("id2")
            .numarProcesVerbal(2)
            .reprezentant("reprezentant2")
            .spatii("spatii2")
            .suprafata(2)
            .rapelDezinsectie(2)
            .rapelDeratizare(2);
    }

    public static ProcesVerbal getProcesVerbalRandomSampleGenerator() {
        return new ProcesVerbal()
            .id(UUID.randomUUID().toString())
            .numarProcesVerbal(intCount.incrementAndGet())
            .reprezentant(UUID.randomUUID().toString())
            .spatii(UUID.randomUUID().toString())
            .suprafata(intCount.incrementAndGet())
            .rapelDezinsectie(intCount.incrementAndGet())
            .rapelDeratizare(intCount.incrementAndGet());
    }
}
