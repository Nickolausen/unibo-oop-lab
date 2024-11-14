package it.unibo.deathnote.impl;
import java.util.*;

import it.unibo.deathnote.api.*;

public class DeathNoteImplementation implements DeathNote {
    
    private Map<String, DeathInfo> actualDeathNote;
    private String lastNameWritten;

    public DeathNoteImplementation() {
        this.actualDeathNote = new HashMap<String, DeathInfo>();
        this.lastNameWritten = "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(int ruleNumber) throws IllegalArgumentException {
        if (ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("Invalid Rule Number (received " + ruleNumber + ")");
        }
        final int ruleIdx = ruleNumber - 1;
        return RULES.get(ruleIdx);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(String name) throws NullPointerException {
        Objects.requireNonNull(name, "Cannot write null names!");
        actualDeathNote.put(name, new DeathInfo());
        lastNameWritten = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(String cause) throws IllegalStateException, NullPointerException {
        Objects.requireNonNull(cause, "Cannot write null death cause!");
        if (lastNameWritten.isEmpty()) {
            throw new IllegalStateException("Cannot write death cause before writing the dead's name!");
        }
        long now = System.currentTimeMillis(); 
        return this.actualDeathNote.get(lastNameWritten).setDeathCause(cause, now);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(String details) throws IllegalStateException, NullPointerException {
        Objects.requireNonNull(details, "Cannot write null death cause!");
        if (lastNameWritten.isEmpty()) {
            throw new IllegalStateException("Cannot write death details before writing the dead's name!");
        }
        long now = System.currentTimeMillis(); 
        return this.actualDeathNote.get(lastNameWritten).setDeathDetails(details, now);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(String name) throws IllegalArgumentException {
        requireNameInDeathNote(name);
        return this.actualDeathNote.get(name).deathCause;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(String name) throws IllegalArgumentException {
        requireNameInDeathNote(name);
        return this.actualDeathNote.get(name).deathDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(String name) {
        return this.actualDeathNote.keySet().contains(name);
    }

    /**
     * Checks if the given name is already written in the death note;
     * if not, throws an IllegalArgumentException
     * 
     * @param name The name that should be found inside the death note
     * @throws IllegalArgumentException
     */
    private void requireNameInDeathNote(final String name) throws IllegalArgumentException {
        if (!this.isNameWritten(name)) {
            throw new IllegalArgumentException("'" + name + "' not written in death note!");
        }
    }

    private static class DeathInfo {
        private String deathCause; 
        private String deathDetails;
        private long deathTime;
        private static String DEFAULT_DEATH_CAUSE = "heart attack";
        private static int DEATH_CAUSE_TIMEOUT_MS = 40;
        private static int DEATH_DETAILS_TIMEOUT_MS = DEATH_CAUSE_TIMEOUT_MS + 6000;

        public DeathInfo() {
            this.deathTime = System.currentTimeMillis();
            this.deathCause = DEFAULT_DEATH_CAUSE;
            this.deathDetails = "";
        }
        
        /**
         * Write the cause of death for the last 
         * name written in the diary.
         * 
         * @param deathCause cause of death of the last person written in the diary.
         * @param registrationTime time (in ms) in which the cause of death has been written.
         * @return true if the registration happened before the millisecond timeout, false otherwise
         */
        public boolean setDeathCause(String deathCause, long registrationTime) {
            if (registrationTime - deathTime <= DEATH_CAUSE_TIMEOUT_MS) {
                this.deathCause = deathCause;
                return true;
            }
            return false;
        }

        public boolean setDeathDetails(String deathDetails, long registrationTime) {
            if (registrationTime - deathTime <= DEATH_DETAILS_TIMEOUT_MS) {
                this.deathDetails = deathDetails;
                return true;
            }
            return false;
        }
    }

}