package com.ninjamind.conference.service.talk;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.repository.TalkRepository;

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
     * @param talk
     * @return
     */
    Talk getTalk(Talk talk);


    /**
     * Creation d'un Talk
     * @param talk
     */
    Talk createTalk(Talk talk);

    /**
     * Mise a jour d'un Talk
     * @param talk
     */
    Talk updateTalk(Talk talk);


    /**
     * Suppression d'un Talk
     * @param talk
     * @return
     */
    boolean deleteTalk(Talk talk);

    /**
     *
     * @param talkRepository
     * @return
     */
    TalkServiceImpl setTalkRepository(TalkRepository talkRepository);
}
