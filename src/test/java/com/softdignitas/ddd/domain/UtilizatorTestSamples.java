package com.softdignitas.ddd.domain;

import java.util.UUID;

public class UtilizatorTestSamples {

    public static Utilizator getUtilizatorSample1() {
        return new Utilizator().id("id1").nume("nume1").prenume("prenume1");
    }

    public static Utilizator getUtilizatorSample2() {
        return new Utilizator().id("id2").nume("nume2").prenume("prenume2");
    }

    public static Utilizator getUtilizatorRandomSampleGenerator() {
        return new Utilizator().id(UUID.randomUUID().toString()).nume(UUID.randomUUID().toString()).prenume(UUID.randomUUID().toString());
    }
}
