package com.softdignitas.ddd.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Client getClientSample1() {
        return new Client()
            .id("id1")
            .denumire("denumire1")
            .codFiscal("codFiscal1")
            .numarRegistruComert("numarRegistruComert1")
            .adresaPunctLucru("adresaPunctLucru1")
            .telefon("telefon1")
            .email("email1")
            .persoanaContact("persoanaContact1")
            .contract("contract1")
            .frecventaDeratizare(1)
            .frecventaDezinsectie(1)
            .frecventaDezinfectie(1);
    }

    public static Client getClientSample2() {
        return new Client()
            .id("id2")
            .denumire("denumire2")
            .codFiscal("codFiscal2")
            .numarRegistruComert("numarRegistruComert2")
            .adresaPunctLucru("adresaPunctLucru2")
            .telefon("telefon2")
            .email("email2")
            .persoanaContact("persoanaContact2")
            .contract("contract2")
            .frecventaDeratizare(2)
            .frecventaDezinsectie(2)
            .frecventaDezinfectie(2);
    }

    public static Client getClientRandomSampleGenerator() {
        return new Client()
            .id(UUID.randomUUID().toString())
            .denumire(UUID.randomUUID().toString())
            .codFiscal(UUID.randomUUID().toString())
            .numarRegistruComert(UUID.randomUUID().toString())
            .adresaPunctLucru(UUID.randomUUID().toString())
            .telefon(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .persoanaContact(UUID.randomUUID().toString())
            .contract(UUID.randomUUID().toString())
            .frecventaDeratizare(intCount.incrementAndGet())
            .frecventaDezinsectie(intCount.incrementAndGet())
            .frecventaDezinfectie(intCount.incrementAndGet());
    }
}
