package com.softdignitas.ddd.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MaterialTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Material getMaterialSample1() {
        return new Material()
            .id("id1")
            .factura("factura1")
            .denumire("denumire1")
            .lot("lot1")
            .dilutie("dilutie1")
            .timpContact(1)
            .metodaAplicare("metodaAplicare1")
            .gramaj(1)
            .cantitate(1);
    }

    public static Material getMaterialSample2() {
        return new Material()
            .id("id2")
            .factura("factura2")
            .denumire("denumire2")
            .lot("lot2")
            .dilutie("dilutie2")
            .timpContact(2)
            .metodaAplicare("metodaAplicare2")
            .gramaj(2)
            .cantitate(2);
    }

    public static Material getMaterialRandomSampleGenerator() {
        return new Material()
            .id(UUID.randomUUID().toString())
            .factura(UUID.randomUUID().toString())
            .denumire(UUID.randomUUID().toString())
            .lot(UUID.randomUUID().toString())
            .dilutie(UUID.randomUUID().toString())
            .timpContact(intCount.incrementAndGet())
            .metodaAplicare(UUID.randomUUID().toString())
            .gramaj(intCount.incrementAndGet())
            .cantitate(intCount.incrementAndGet());
    }
}
