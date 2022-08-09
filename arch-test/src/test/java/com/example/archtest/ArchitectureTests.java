package com.example.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

@AnalyzeClasses(packages = "com.example.archtest", importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class })
public class ArchitectureTests {
    @ArchTest
    static ArchRule no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

    @ArchTest
    static ArchRule do_not_use_jodatime = NO_CLASSES_SHOULD_USE_JODATIME;

    @ArchTest
    static ArchRule no_cycles = SlicesRuleDefinition.slices().matching("..archtest.(*)..").should().beFreeOfCycles();

    @ArchTest
    static ArchRule layered_architecture =
            layeredArchitecture()
                    .layer("Repository").definedBy("..repository..")
                    .layer("Service").definedBy("..service..")
                    .layer("Controller").definedBy("..controller..")

                    .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                    .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");

    @ArchTest
    static ArchRule repository_package_dependencies =
            noClasses().that().resideInAPackage("..repository..")
                    .should().dependOnClassesThat().resideInAPackage("..service..")
                    .orShould().dependOnClassesThat().resideInAPackage("..controller..");

    @ArchTest
    static ArchRule controller_package_dependencies =
            noClasses().that().resideInAPackage("..service..")
                    .should().dependOnClassesThat().resideInAPackage("..controller..");

}
