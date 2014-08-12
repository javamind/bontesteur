package com.ninjamind.conference.service.talk;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.events.dto.TalkDetail;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Gestion des {@link com.ninjamind.conference.domain.Talk}
 * @author EHRET_G
 */
@Service
@Transactional
public class TalkServiceImpl implements TalkService
{
    private static Logger LOG = LoggerFactory.make();

    @Autowired
    private TalkRepository talkRepository;

    /**
     * Recuperation de la liste des talks
     * @return
     */
    @Override
    public List<Talk> getAllTalk() {
       return talkRepository.findAll(sortByNameAsc());
    }

    /**
     * Recuperation d'un talk vi a son ID
     * @param event
     * @return
     */
    @Override
    public Talk getTalk(Talk event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "id is required for search talk");

        //Recherche de l'element par l'id
        return talkRepository.findOne(event.getId());
    }

    /**
     * Creation d'une nouvelle talk
     * @param event
     * @return
     */
    @Override
    public CreatedEvent<Talk> createTalk(Talk event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getName(), "talk is required to create it");

        CreatedEvent<Talk> eventReturned = new CreatedEvent(transformAndSaveTalkDetailToTalk(event, true));

        LOG.debug(String.format("Creation du talk ayant id=[%d] name=[%s] UUID:%s",
                ((Talk)eventReturned.getValue()).getId(), event.getName(),
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Permet de convertir la donnée reçue
     * @param talk
     * @return
     */
    private Talk transformAndSaveTalkDetailToTalk(Talk talk, boolean creation) {
        //Si pas en creation on regarde si enreg existe
        if(!creation){
            Talk talkToPersist = talkRepository.findOne(talk.getId());
            if(talkToPersist==null){
                return null;
            }
            talkToPersist.setLevel(talk.getLevel());
            talkToPersist.setNbpeoplemax(talk.getNbpeoplemax());
            talkToPersist.setPlace(talk.getPlace());
            talkToPersist.setDescription(talk.getDescription());
            talkToPersist.setName(talk.getName());
            return talkToPersist;
        }
        else{
            //On enregistre
            return talkRepository.save(talk);
        }
    }

    @Override
    public UpdatedEvent<Talk> updateTalk(Talk event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "talk is required to update it");

        Talk talkUpdated = transformAndSaveTalkDetailToTalk(event, false);
        UpdatedEvent<Talk> eventReturned = new UpdatedEvent(talkUpdated!=null, talkUpdated);

        LOG.debug(String.format("Modification du talk ayant id=[%d] name=[%s] UUID:%s",
                talkUpdated !=null ? talkUpdated.getId() : null,
                talkUpdated !=null ? talkUpdated.getName() : null,
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Suppression d'une talk
     * @param event
     * @return
     */
    @Override
    public DeletedEvent<Talk> deleteTalk(Talk event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "talk is required to delete talk");

        //Recherche de l'element par l'id
        Talk talk = talkRepository.findOne(event.getId());
        DeletedEvent<Talk> eventReturned = null;

        if(talk!=null){
            talkRepository.delete(talk);
            eventReturned = new DeletedEvent(true, new TalkDetail(talk));
            LOG.debug(String.format("Suppression du talk ayant id=[%s] UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        else{
            eventReturned = new DeletedEvent(false, null);
            LOG.debug(String.format("Le talk ayant id=[%s] n'existe pas UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        return eventReturned;
    }

    /**
     * Returns a Sort object which sorts talk in ascending order by using the name.
     * @return
     */
    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }
}
