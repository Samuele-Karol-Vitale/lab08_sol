package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
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
        
    }

}