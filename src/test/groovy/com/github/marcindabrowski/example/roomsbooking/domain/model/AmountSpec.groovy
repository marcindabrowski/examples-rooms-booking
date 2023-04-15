package com.github.marcindabrowski.example.roomsbooking.domain.model

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static java.math.BigDecimal.ONE
import static java.math.BigDecimal.TEN
import static nl.jqno.equalsverifier.Warning.BIGDECIMAL_EQUALITY
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS

class AmountSpec extends Specification {

    def "should Amount.of() method return Amount.ZERO when value was null"() {
        when: "I pass null to the Amount.of() method"
            Amount amount = Amount.of(null)

        then: "I got null as Amount object"
            amount == Amount.ZERO
    }

    def "should Amount.of() method return non null Amount with wrapped BigDecimal value"() {
        given: "a BigDecimal value exists"
            BigDecimal value = ONE

        when: "I pass BigDecimal value to the Amount.of() method"
            Amount amount = Amount.of(value)

        then: "I got Amount object with wrapped BigDecimal value"
            amount.value() == value
    }

    def "should Amount.of() method throw IllegalArgumentException on negative value"() {
        given: "a negative BigDecimal value exists"
            BigDecimal value = BigDecimal.valueOf(-1)

        when: "I pass BigDecimal value to the Amount.of() method"
            Amount.of(value)

        then: "IllegalArgumentException was thrown"
            IllegalArgumentException exception = thrown(IllegalArgumentException)

        and:
            exception.message == "Amount's value must not be negative, but got $value"
    }

    def "should sum two Amounts and keep original objects unchanged"() {
        given: "an augend Amount exists"
            Amount augend = Amount.of(TEN)

        and: "an addend Amount exists"
            Amount addend = Amount.of(ONE)

        when: "I sum augend and addend"
            Amount sum = augend.add(addend)

        then: "sum value is a sum of augend and addend values"
            sum.value() == TEN + ONE

        and: "sum is a new object"
            sum !== augend
            sum !== addend

        and: "augend value was unchanged"
            augend.value() == TEN

        and: "addend value was unchanged"
            addend.value() == ONE
    }

    def "should return the augend when null was passed to sum"() {
        given: "an augend Amount exists"
            Amount augend = Amount.of(TEN)

        and: "an addend Amount is null"
            Amount addend = null

        when: "I sum augend and addend"
            Amount sum = augend.add(addend)

        then: "sum is a reference to augend"
            sum === augend

        and: "augend value was unchanged"
            augend.value() == TEN
    }

    def "should implement Java standard equals and hashCode contract"() {
        when: "I check Amount equals and hashCode methods"
            EqualsVerifier.forClass(Amount)
                    .suppress(BIGDECIMAL_EQUALITY)
                    .suppress(NULL_FIELDS)
                    .verify()

        then: "no exception was thrown"
            noExceptionThrown()
    }
}
