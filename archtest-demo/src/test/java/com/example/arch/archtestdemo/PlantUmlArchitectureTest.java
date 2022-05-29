package com.example.arch.archtestdemo;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.plantuml.PlantUmlArchCondition.Configurations.consideringOnlyDependenciesInDiagram;
import static com.tngtech.archunit.library.plantuml.PlantUmlArchCondition.adhereToPlantUmlDiagram;

@AnalyzeClasses(packages = "com.example.arch.archtestdemo")
public class PlantUmlArchitectureTest {
    private static final URL plantUmlDiagram = PlantUmlArchitectureTest.class.getResource("/archtest.puml");

    @ArchTest
    static final ArchRule classes_should_adhere_to_shopping_example_considering_only_dependencies_in_diagram =
            classes().should(adhereToPlantUmlDiagram(plantUmlDiagram, consideringOnlyDependenciesInDiagram()));

    @ArchTest
    static final ArchRule assertDependencies =
        classes().that().resideInAPackage("..service..")
                .should().onlyHaveDependentClassesThat().resideInAPackage("..controller..");

}
