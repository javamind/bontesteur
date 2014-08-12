package com.ninjamind.conference.events;

/**
 * AbstractEvent retourne lors d'une creation
 *
 * @author EHRET_G
 */
public class DeletedEvent<Dto> extends AbstractEvent {

    public DeletedEvent(boolean entityFound, Object value) {
        super(entityFound, value);
    }
}
