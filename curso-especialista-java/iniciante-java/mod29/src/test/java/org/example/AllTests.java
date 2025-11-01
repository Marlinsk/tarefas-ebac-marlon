package org.example;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ClienteDAOTest.class, ProdutoDAOTest.class })
public class AllTests { }
