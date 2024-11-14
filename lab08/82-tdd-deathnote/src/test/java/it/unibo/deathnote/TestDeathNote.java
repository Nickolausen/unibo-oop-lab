package it.unibo.deathnote;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import it.unibo.deathnote.impl.*;
import it.unibo.deathnote.api.*;

class TestDeathNote {
    private DeathNoteImplementation deathNote;
    private Set<Integer> invalidRuleNumbers = Set.of(-1, 0, DeathNote.RULES.size() + 1);
    
    private static String LUDOVICO_SPITALERI = "Ludovico Maria Spitaleri";
    private static String LUDOVICO_SPITALERI_DEATH_CAUSE = "Excessive fun at Mirabilandia, especially with iSpeed";
    private static String LUDOVICO_SPITALERI_DEATH_DETAILS = "Smashed a guardrail on the road";
    private static String REQUIRED_HUMAN_NAME = "Samuele Trapani";
    private static String REQUIRED_DEATH_CAUSE = "karting accident";

    private static class Death {
        static String EXPECTED_DEFAULT_DEATH_CAUSE = "heart attack";
        static String EXPECTED_DEFAULT_DEATH_DETAILS = "ran for too long"; 
    }

    @BeforeEach
    void setup() {
        this.deathNote = new DeathNoteImplementation();
    }

    void checkStringNotEmptyOrBlank(final String str) {
        assertFalse(str.isBlank() || str.isEmpty());
    }
    /** 
     * Rule number 0 and negative rules do not exist in the DeathNote rules.
     */
    @Test
    void testInvalidRuleNumber() {
        /* 
         * check that the exceptions are thrown correctly, 
         * that their type is the expected one, and that 
         * the message is not null, empty, or blank.
        */
        for (final var invalidRuleNumber: invalidRuleNumbers) {
            try {
                this.deathNote.getRule(invalidRuleNumber);
            } catch (final Exception e) {
                final String exceptionMsg = e.getMessage();
                assertAll("Grouped assertions for invalid number #" + invalidRuleNumber,
                    () -> assertEquals(IllegalArgumentException.class, e.getClass()),
                    () -> assertNotNull(exceptionMsg),
                    () -> checkStringNotEmptyOrBlank(exceptionMsg));
            }
        }
    }

    /**
     * No rule is empty or null in the DeathNote rules.
     */
    @Test
    void testValidRules() {
        // for all the valid rules, check that none is null or blank
        for (final var rule: DeathNote.RULES) {
            checkStringNotEmptyOrBlank(rule);
        }
    }

    /**
     * The human whose name is written in the DeathNote will eventually die.
     */
    @Test
    void testDeathNoteNameWriting() {
        // verify that the human has not been written in the notebook yet
        assertFalse(this.deathNote.isNameWritten(LUDOVICO_SPITALERI));
        // write the human in the notebook
        this.deathNote.writeName(LUDOVICO_SPITALERI);
        // verify that the human has been written in the notebook
        assertTrue(this.deathNote.isNameWritten(LUDOVICO_SPITALERI));
        // verify that another human has not been written in the notebook
        assertFalse(this.deathNote.isNameWritten(REQUIRED_HUMAN_NAME));
        // verify that the empty string has not been written in the notebook
        assertFalse(this.deathNote.isNameWritten(""));
    }

    /**
     * Only if the cause of death is written within the next 40 milliseconds of writing the person's name, it will happen.
     * @throws InterruptedException
     */
    @Test
    void testDeathCauseTimeout() throws InterruptedException {
        // check that writing a cause of death before writing a name throws the correct exception
        assertThrows(IllegalStateException.class, () -> this.deathNote.writeDeathCause(LUDOVICO_SPITALERI_DEATH_CAUSE));
        // write the name of a human in the notebook
        this.deathNote.writeName(LUDOVICO_SPITALERI);
        // verify that the cause of death is a heart attack
        assertEquals(Death.EXPECTED_DEFAULT_DEATH_CAUSE, this.deathNote.getDeathCause(LUDOVICO_SPITALERI));
        // write the name of another human in the notebook
        this.deathNote.writeName(REQUIRED_HUMAN_NAME);
        // set the cause of death to "karting accident"
        final boolean correctlyWritten = this.deathNote.writeDeathCause(REQUIRED_DEATH_CAUSE);
        /*
         * verify that the cause of death has been set correctly 
         * (returned true, and the cause is indeed "karting accident")
         */
        assertAll(
            () -> assertTrue(correctlyWritten),
            () -> assertEquals(REQUIRED_DEATH_CAUSE, this.deathNote.getDeathCause(REQUIRED_HUMAN_NAME))
        );
        // sleep for 100ms
        Thread.sleep(100);
        // try to change the cause of death
        final boolean successfullyChangedDeathCause = this.deathNote.writeDeathCause(Death.EXPECTED_DEFAULT_DEATH_CAUSE);
        // verify that the cause of death has not been changed
        assertFalse(successfullyChangedDeathCause);
    }

    /**
     * Only if the cause of death is written within the next 6 seconds and 40 milliseconds of writing the death's details, it will happen.
     * @throws InterruptedException
     */
    @Test
    void testDeathDetailsTimeout() throws InterruptedException {
        // check that writing the death details before writing a name throws the correct exception
        assertThrows(IllegalStateException.class, () -> this.deathNote.writeDeathCause(LUDOVICO_SPITALERI_DEATH_CAUSE));
        // write the name of a human in the notebook
        this.deathNote.writeName(REQUIRED_HUMAN_NAME);
        // verify that the details of the death are currently empty
        assertTrue(this.deathNote.getDeathDetails(REQUIRED_HUMAN_NAME).isBlank());
        // set the details of the death to "ran for too long"
        final boolean correctlyWritten = this.deathNote.writeDetails(Death.EXPECTED_DEFAULT_DEATH_DETAILS);
        /*
         * verify that death details have been set correctly 
         * (returned true, and the details are indeed "ran for too long")
         */
        assertAll(
            () -> assertTrue(correctlyWritten),
            () -> assertEquals(Death.EXPECTED_DEFAULT_DEATH_DETAILS, this.deathNote.getDeathDetails(REQUIRED_HUMAN_NAME))
        );
        // write the name of another human in the notebook
        this.deathNote.writeName(LUDOVICO_SPITALERI);
        // sleep for 6100ms
        Thread.sleep(6100);
        // try to change the details
        final boolean successfullyChangedDeathDetails = this.deathNote.writeDetails(LUDOVICO_SPITALERI_DEATH_DETAILS);
        // verify that the details have not been changed
        assertFalse(successfullyChangedDeathDetails);
    }
}