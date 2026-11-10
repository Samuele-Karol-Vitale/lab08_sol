package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    static private String MATTEO_SALVINI = "Matteo Salvini";
    static private String AYN_RAND = "Ayn Rand";
    static private String DEATH_CAUSE1 = "heart attack";
    static private String DEATH_CAUSE2 = "karting accident";
    private DeathNote note = null;
    @BeforeEach
    void prepareNoteForTest() {
        note = new DeathNoteImpl();
    }
    @Test 
    void testZeroOrNegativeRules() {
        int negativeNumberRuleRequested = -100;
        assertThrows( IllegalArgumentException.class , () -> note.getRule(negativeNumberRuleRequested));
        assertThrows(IllegalArgumentException.class , () -> note.getRule(0));
    }
    @Test 
    void testNoRuleIsEmptyOrNull() {
        for(int ruleNumberTested = 1; ruleNumberTested <= DeathNote.RULES.size(); ruleNumberTested++) {
            assertTrue(!(note.getRule(ruleNumberTested).isEmpty() || note.getRule(ruleNumberTested).isBlank()));
        }
    }
    @Test
    void testHumanDies() {
        assertTrue(!note.isNameWritten(MATTEO_SALVINI));
        note.writeName(MATTEO_SALVINI);
        assertTrue(!note.isNameWritten(AYN_RAND));
        assertTrue(!note.isNameWritten(""));
    }
    @Test 
    void testDeathCause() {
        assertThrows(IllegalStateException.class,() -> note.writeDeathCause(DEATH_CAUSE1));
        note.writeName(MATTEO_SALVINI);
        assertTrue(note.getDeathCause(MATTEO_SALVINI).equals(DEATH_CAUSE1));
        note.writeName(AYN_RAND);
        note.writeDeathCause(DEATH_CAUSE2);
    }
}