package com.softdignitas.ddd.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CompanieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Companie getCompanieSample1() {
        return new Companie().id(1L).iD("iD1").nUME("nUME1");
    }

    public static Companie getCompanieSample2() {
        return new Companie().id(2L).iD("iD2").nUME("nUME2");
    }

    public static Companie getCompanieRandomSampleGenerator() {
        return new Companie().id(longCount.incrementAndGet()).iD(UUID.randomUUID().toString()).nUME(UUID.randomUUID().toString());
    }
}
