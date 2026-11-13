package it.unibo.deathnote.impl;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.deathnote.api.DeathNote;
public class DeathNoteImpl implements DeathNote{
    static public long MILLIS_TO_WAIT_FOR_DEATH = 40;
    static public  long MILLIS_TO_WAIT_FOR_CHANGE_DEATH_DETAILS = 6040;
    static public String DEFAULT_DEATH_CAUSE = "heart attack";
    static public String DEFAULT_DEATH_DETAILS = "";
    private Map<String, Pair<String, String>> noteBookContent;
    private String selectedPersonToKill;
    private long initialDeathSentenceTime;
    public DeathNoteImpl() {
        
        noteBookContent = new HashMap<>();
        selectedPersonToKill = null;
        initialDeathSentenceTime = 0;
    }
    /**    
     * @{inheritedoc}
     */
    public String getRule(int ruleNumber) {
        if(ruleNumber <= 0 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException();
        }
        int ruleIndex = ruleNumber - 1;
        return new String(DeathNote.RULES.get(ruleIndex));
    }

    /**    
     * @{inheritedoc}
     */
    public void writeName(String name) {
        if(name == null) {
            throw new NullPointerException();
        }
        noteBookContent.put(
            name,
            new DeathCauseDetails(DEFAULT_DEATH_CAUSE, DEFAULT_DEATH_DETAILS)
        );
        selectedPersonToKill = name;
        initialDeathSentenceTime = System.currentTimeMillis();
    }

    /**    
     * @{inheritedoc}
     */
    public boolean writeDeathCause(String cause) {
        if(cause == null || selectedPersonToKill == null) {
            throw new IllegalStateException();
        }
        if(isInTime(initialDeathSentenceTime, MILLIS_TO_WAIT_FOR_DEATH)) {
            noteBookContent.put(
                selectedPersonToKill, 
                new DeathCauseDetails(
                    cause, 
                    noteBookContent.get(selectedPersonToKill).getRight()
                )
            );
            return true;
        }
        return false;
        
    }

    /**    
     * @{inheritedoc}
     */
    public boolean writeDetails(String details) {
        if(details == null || selectedPersonToKill == null) {
            throw new IllegalStateException();
        }
        if(isInTime(initialDeathSentenceTime, MILLIS_TO_WAIT_FOR_CHANGE_DEATH_DETAILS)) {
            noteBookContent.get(selectedPersonToKill).setValue(details);
            return true;
        }
        return false;
    }

    /**    
     * @{inheritedoc}
     */
    public String getDeathCause(String name) {
       if(!isNameWritten(name)) {
            throw new IllegalArgumentException();
        }
        return noteBookContent.get(name).getLeft();
    }

    /**    
     * @{inheritedoc}
     */
    public String getDeathDetails(String name) {
       if(!isNameWritten(name)) {
            throw new IllegalArgumentException();
        }
        return noteBookContent.get(name).getRight();
    }
    /**    
     * @{inheritedoc}
     */
    public boolean isNameWritten(String name) {
        return noteBookContent.get(name) != null;
    }
    private boolean isInTime(long initialTime,long waitingTime) {
        return System.currentTimeMillis() - initialTime < waitingTime;
    }
}
