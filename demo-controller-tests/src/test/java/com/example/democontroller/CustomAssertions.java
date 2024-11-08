package com.example.democontroller;

import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomAssertions extends Assertions {

    public static MyCustomAssert assertThat(String toAssert) {
        return new MyCustomAssert(toAssert);
    }

    static class MyCustomAssert extends AbstractStringAssert<MyCustomAssert> {
        private final String actual;

        public MyCustomAssert(String actual) {
            super(actual, MyCustomAssert.class);
            this.actual = actual;
        }

        public MyCustomAssert containsMyName() {
            assertThat(actual)
                    .contains("Andrei")
                    .withFailMessage("Expected to contain my name: Andrei");

            return this;
        }
    }

}
