package org.example.project;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AcessorioRepositoryCrudTest.class,
        CarroRepositoryCrudTest.class,
        DomainRelationalTest.class,
        MarcaRepositoryCrudTest.class
})
public class Alltests { }
