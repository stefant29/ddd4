package com.softdignitas.ddd.domain;

import java.util.UUID;

public class CompanieTestSamples {

    public static Companie getCompanieSample1() {
        return new Companie().id("id1").nume("nume1");
    }

    public static Companie getCompanieSample2() {
        return new Companie().id("id2").nume("nume2");
    }

    public static Companie getCompanieRandomSampleGenerator() {
        return new Companie().id(UUID.randomUUID().toString()).nume(UUID.randomUUID().toString());
    }
}
