package com.ninjamind.conference.service.talk;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import java.util.List;

/**
 * Service lié aux {@link com.ninjamind.conference.domain.Talk}
 */
public interface TalkService {

    /**
     * Permet de retourner la liste des Talks
     * @return
     */
    List<Talk> getAllTalk();

    /**
     * Permet de retourner un Talk
     * @param event
     * @return
     */
    Talk getTalk(Talk event);

    /**
     * Creation d'un Talk
     * @param event
     * @return
     */
    CreatedEvent<Talk> createTalk(Talk event);

    /**
     * Modification d'un Talk
     * @param event
     * @return
     */
    UpdatedEvent<Talk> updateTalk(Talk event);

    /**
     * Suppression d'un Talk
     * @param event
     * @return
     */
    DeletedEvent deleteTalk(Talk event);
}
