package com.ninjamind.conference.events;

import java.util.UUID;

/**
 * Tous les evenements sont au moins identifies par un identifiant unique
 * @author ehret_g
 */
public abstract class AbstractEvent<Dto> {
    /**
     * UUID
     */
    protected UUID key;
    /**
     * Enreg retourne
     */
    protected Dto value;
    /**
     * Entity found
     */
    protected boolean entityFound = true;

    /**
     *
     */
    protected AbstractEvent() {
        this.key = UUID.randomUUID();
    }

    /**
     *
     * @param value
     * @param entityFound
     */
    public AbstractEvent(boolean entityFound, Dto value) {
        this(value);
        this.entityFound = entityFound;
    }

    /**
     * @param value
     */
    public AbstractEvent(Dto value) {
        this();
        this.value = value;
    }

    /**
     *
     * @return
     */
    public boolean isEntityFound() {
        return entityFound;
    }

    /**
     *
     * @return
     */
    public Dto getValue() {
        return value;
    }



    public UUID getKey() {
        return key;
    }
}
