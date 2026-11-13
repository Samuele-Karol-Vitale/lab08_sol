package it.unibo.deathnote.impl;

import org.apache.commons.lang3.tuple.Pair;
public class DeathCauseDetails extends Pair<String, String>{
    private String key;
    private String value;
    public DeathCauseDetails(String key, String value) {
        this.key =  key;
        this.value = value;
    }
    @Override
    public String setValue(String arg0) {
        String returnOldValue = this.value;
        this.value = arg0;
        return returnOldValue;
    }

    @Override
    public String getLeft() {
        return key;
    }

    @Override
    public String getRight() {
        return value;
    }

    
}