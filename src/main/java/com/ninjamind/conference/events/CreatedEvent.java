package com.ninjamind.conference.events;

/**
 * AbstractEvent retourne lors d'une creation
 *
 * @author EHRET_G
 */
public class CreatedEvent<Dto> extends AbstractEvent {
    /**
     * Entity valid
     */
    protected boolean validEntity = true;

    /**
     *
     * @param value
     */
    public CreatedEvent(Dto value) {
        super(value);
    }

    /**
     * @param validEntity
     * @param value
     */
    public CreatedEvent(boolean validEntity, Dto value) {
        super(value);
        this.validEntity=validEntity;
    }

    public boolean isValidEntity() {
        return validEntity;
    }
}
