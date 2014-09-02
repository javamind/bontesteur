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
     * @param speaker
     * @return
     */
    Speaker getSpeaker(Speaker speaker);

    /**
     * Creation d'un speaker
     * @param speaker
     * @return
     */
    Speaker createSpeaker(Speaker speaker);


    /**
     * Mise a jour d'un speaker
     * @param speaker
     * @return
     */
    Speaker updateSpeaker(Speaker speaker);

    /**
     * Suppression d'un speaker
     * @param speaker
     * @return
     */
    boolean deleteSpeaker(Speaker speaker);


}
