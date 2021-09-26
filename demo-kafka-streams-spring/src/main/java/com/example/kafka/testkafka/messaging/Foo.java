package com.example.kafka.testkafka.messaging;

public class Foo {
    private String testProperty;

    public Foo() {

    }

    public Foo(String testProperty) {
        this.testProperty = testProperty;
    }

    public String getTestProperty() {
        return testProperty;
    }

    public void setTestProperty(String testProperty) {
        this.testProperty = testProperty;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "testProperty='" + testProperty + '\'' +
                '}';
    }
}
