package org.example.test;

import org.example.test.domain.TesteClienteTest;
import org.example.test.domain.TesteContratoTest;
import org.example.test.service.ClienteServiceTest;
import org.example.test.service.ContratoServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ClienteServiceTest.class, ContratoServiceTest.class, TesteClienteTest.class, TesteContratoTest.class })
public class AllTests { }
