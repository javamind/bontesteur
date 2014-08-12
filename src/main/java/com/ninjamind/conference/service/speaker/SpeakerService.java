package com.ninjamind.conference.service.speaker;

import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;

import java.util.List;

/**
 * Service lié aux {@link com.ninjamind.conference.domain.Speaker}
 */
public interface SpeakerService {

    /**
     * Permet de retourner la liste des speakers
     * @return
     */
    List<Speaker> getAllSpeaker();

    /**
     * Permet de retourner un speaker
     * @param event
     * @return
     */
    Speaker getSpeaker(Speaker event);

    /**
     * Creation d'un speaker
     * @param event
     * @return
     */
    CreatedEvent<Speaker> createSpeaker(Speaker event);

    /**
     * Modification d'un speaker
     * @param event
     * @return
     */
    UpdatedEvent<Speaker> updateSpeaker(Speaker event);

    /**
     * Suppression d'un speaker
     * @param event
     * @return
     */
    DeletedEvent<Speaker> deleteSpeaker(Speaker event);
}
