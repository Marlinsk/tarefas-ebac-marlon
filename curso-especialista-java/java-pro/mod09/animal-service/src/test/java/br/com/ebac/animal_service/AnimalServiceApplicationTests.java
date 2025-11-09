package br.com.ebac.animal_service;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Animal Service - Test Suite Completa")
@SelectPackages({
    "br.com.ebac.animal_service.domain",
    "br.com.ebac.animal_service.usecase",
    "br.com.ebac.animal_service.infrastructure",
    "br.com.ebac.animal_service.interfaces"
})
class AnimalServiceApplicationTests { }
