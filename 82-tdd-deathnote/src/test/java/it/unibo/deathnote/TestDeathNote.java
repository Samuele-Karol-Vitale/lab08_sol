package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNote note = null;
    //names
    static private String NAME1 = "Matteo Salvini";
    static private String NAME2 = "Ayn Rand";
    //death causes
    static private String DEATH_CAUSE1 = "heart attack";
    static private String DEATH_CAUSE2 = "karting accident";
    //death details
    static private String DEATH_DETAIL1 = "ran for too long";
    static private String DEATH_DETAIL2 = "no one is around";
    //waiting times are expressed im milliseconds
    static private long DELAY = 60;
    static private long MILLIS_TO_WAIT_FOR_DEATH_CAUSE = DeathNoteImpl.MILLIS_TO_WAIT_FOR_DEATH + DELAY;
    static private long MILLIS_TO_WAIT_FOR_DEATH_DETAILS = DeathNoteImpl.MILLIS_TO_WAIT_FOR_CHANGE_DEATH_DETAILS + DELAY;
    //before each test the death note must be empty
    @BeforeEach
    void prepareNoteForTest() {
        note = new DeathNoteImpl();
    }
    @Test
    //in this test we check that we can't request for 0 or negative rule
    void testZeroOrNegativeRules() {
        int negativeNumberRuleRequested = -100;
        assertThrows( IllegalArgumentException.class , () -> note.getRule(negativeNumberRuleRequested));
        assertThrows(IllegalArgumentException.class , () -> note.getRule(0));
    }
    //in this test we check that every rule is a not null and not empty string
    @Test 
    void testNoRuleIsEmptyOrNull() {
        for(int ruleNumberTested = 1; ruleNumberTested <= DeathNote.RULES.size(); ruleNumberTested++) {
            assertTrue(!(note.getRule(ruleNumberTested).isEmpty() || note.getRule(ruleNumberTested).isBlank()));
        }
    }
    /*
    controlliamo che se un nome non è stato scritto, allora è possibile farlo. Inoltre controlliamo che il nome di un'altra persona oppure che la
    stringa vuota non sono state scritte nel quaderno.
    */
    @Test
    void testHumanDies() {
        assertTrue(!note.isNameWritten(NAME1));
        note.writeName(NAME1);
        assertTrue(!note.isNameWritten(NAME2));
        assertTrue(!note.isNameWritten(""));
    }
    /*
    qui controlliamo che se scriviamo una causa di morte prima di aver scritto il nome di una persona viene generata un'eccezione.
    successivamente controlliamo che la causa di morte di deafult è un'infarto.
    infine controlliamo che non è possibile cambiare la causa di morte dopo che sono passati 40 millisecondi
     */
    @Test 
    void testDeathCause() {
        assertThrows(IllegalStateException.class,() -> note.writeDeathCause(DEATH_CAUSE1));
        note.writeName(NAME1);
        assertTrue(note.getDeathCause(NAME1).equals(DEATH_CAUSE1));
        note.writeName(NAME2);
        assertTrue(note.writeDeathCause(DEATH_CAUSE2));
        assertTrue(note.getDeathCause(NAME2).equals(DEATH_CAUSE2));
        long initialTime = System.currentTimeMillis();
        while (wait(initialTime, MILLIS_TO_WAIT_FOR_DEATH_CAUSE));
        note.writeDeathCause(DEATH_CAUSE1);
        assertTrue(note.getDeathCause(NAME2).equals(DEATH_CAUSE2));
    }
    /*
    qui controlliamo che se scriviamo dei dettagli di morte prima di aver scritto il nome di una persona viene generata un'eccezione.
    successivamente controlliamo che i dettagli di morte di deafult sono uguali ad una stringa vuota.
    infine controlliamo che non è possibile cambiare i dettagli di morte di deafult dopo che sono passati 6 secondi e 40 millisecondi
     */
    @Test 
    void testDeathDetails() {
        assertThrows(IllegalStateException.class,() -> note.writeDetails(DEATH_CAUSE1));
        note.writeName(NAME1);
        assertTrue(note.getDeathDetails(NAME1).isEmpty());
        assertTrue(note.writeDetails(DEATH_DETAIL1));
        assertTrue(note.getDeathDetails(NAME1).equals(DEATH_DETAIL1));
        note.writeName(NAME2);
        long initialTime = System.currentTimeMillis();
        while (wait(initialTime, MILLIS_TO_WAIT_FOR_DEATH_DETAILS));
        note.writeDetails(DEATH_DETAIL2);
        assertTrue(!note.getDeathDetails(NAME2).equals(DEATH_DETAIL1));
    }
    private boolean wait(final long startingTime, final long waitingTime) {
        return System.currentTimeMillis() - startingTime < waitingTime;
    }
}