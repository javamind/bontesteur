package com.ninjamind.conference.events;

/**
 * AbstractEvent retourne lors d'une mise a jour d'entite
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class UpdatedEvent<Dto> extends AbstractEvent {
    /**
     * Entity valid
     */
    protected boolean validEntity = true;

    /**
     *
     * @param entityFound
     * @param value
     */
    public UpdatedEvent(boolean entityFound, Dto value) {
        super(entityFound, value);
    }

    /**
     * @param validEntity
     * @param entityFound
     * @param value
     */
    public UpdatedEvent(boolean validEntity, boolean entityFound, Dto value) {
        super(entityFound, value);
        this.validEntity=validEntity;
    }

    public boolean isValidEntity() {
        return validEntity;
    }
}
