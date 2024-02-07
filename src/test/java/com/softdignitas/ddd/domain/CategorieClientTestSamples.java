package com.softdignitas.ddd.domain;

import java.util.UUID;

public class CategorieClientTestSamples {

    public static CategorieClient getCategorieClientSample1() {
        return new CategorieClient().id("id1").nume("nume1");
    }

    public static CategorieClient getCategorieClientSample2() {
        return new CategorieClient().id("id2").nume("nume2");
    }

    public static CategorieClient getCategorieClientRandomSampleGenerator() {
        return new CategorieClient().id(UUID.randomUUID().toString()).nume(UUID.randomUUID().toString());
    }
}
