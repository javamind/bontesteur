package com.ninjamind.conference.service.speaker;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.events.dto.SpeakerDetail;
import com.ninjamind.conference.repository.CountryRepository;
import com.ninjamind.conference.repository.SpeakerRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Gestion des {@link com.ninjamind.conference.domain.Speaker}
 * @author EHRET_G
 */
@Service
@Transactional
public class SpeakerServiceImpl implements SpeakerService {
    private static final Logger LOG = LoggerFactory.make();

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Recuperation de la liste des speakers
     * @return
     */
    @Override
    public List<Speaker> getAllSpeaker() {
        return speakerRepository.findAll(sortByNameAsc());
    }

    /**
     * Recuperation d'un speaker vi a son ID
     * @param event
     * @return
     */
    @Override
    public Speaker getSpeaker(Speaker event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "id is required for search speaker");
        return speakerRepository.findOne(event.getId());

    }

    /**
     * Creation d'une nouvelle speaker
     * @param event
     * @return
     */
    @Override
    public CreatedEvent<Speaker> createSpeaker(Speaker event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getLastname(), "speaker is required to create it");

        CreatedEvent<Speaker> eventReturned = new CreatedEvent(transformAndSaveSpeakerDetailToSpeaker(event, true));

        LOG.debug(String.format("Creation du speaker ayant id=[%d] name=[%s] UUID:%s",
                ((Speaker) eventReturned.getValue()).getId(), event.getLastname(),
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Permet de convertir la donnée reçue
     * @param speaker
     * @return
     */
    private Speaker transformAndSaveSpeakerDetailToSpeaker(Speaker speaker, boolean creation) {
        //Le pays envoye est simplement un code on doit mettre a jour le pays avec les données présentes
        //en base de données
        if(speaker.getCountry()!=null) {
            speaker.setCountry(countryRepository.findCountryByCode(speaker.getCountry().getCode()));
        }

        //Si pas en creation on regarde si enreg existe
        if(!creation){
            Speaker speakerToPersist = speakerRepository.findOne(speaker.getId());
            if(speakerToPersist==null){
                return null;
            }
            speakerToPersist.setCountry(speaker.getCountry());
            speakerToPersist.setPostalCode(speaker.getPostalCode());
            speakerToPersist.setCity(speaker.getCity());
            speakerToPersist.setFirstname(speaker.getFirstname());
            speakerToPersist.setLastname(speaker.getLastname());
            speakerToPersist.setCompany(speaker.getCompany());
            speakerToPersist.setStreetAdress(speaker.getStreetAdress());
            return speakerToPersist;
        }
        else{
            //On enregistre
            return speakerRepository.save(speaker);
        }
    }

    @Override
    public UpdatedEvent<Speaker> updateSpeaker(Speaker event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "speaker is required to update it");

        Speaker speakerUpdated = transformAndSaveSpeakerDetailToSpeaker(event, false);
        UpdatedEvent<Speaker> eventReturned = new UpdatedEvent(speakerUpdated!=null , speakerUpdated);

        LOG.debug(String.format("Modification du speaker ayant id=[%d] name=[%s] UUID:%s",
                speakerUpdated !=null ? speakerUpdated.getId() : null,
                speakerUpdated !=null ? speakerUpdated.getLastname() : null,
                eventReturned.getKey().toString()));

        return eventReturned;
    }

    /**
     * Suppression d'une speaker
     * @param event
     * @return
     */
    @Override
    public DeletedEvent<Speaker> deleteSpeaker(Speaker event) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(event.getId(), "speaker is required to delete speaker");

        //Recherche de l'element par l'id
        Speaker speaker = speakerRepository.findOne(event.getId());
        DeletedEvent<Speaker> eventReturned = null;

        if(speaker!=null){
            speakerRepository.delete(speaker);
            eventReturned = new DeletedEvent(true, new SpeakerDetail(speaker));
            LOG.debug(String.format("Suppression de la speaker ayant id=[%s] UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        else{
            eventReturned = new DeletedEvent(false, null);
            LOG.debug(String.format("La speaker ayant id=[%s] n'existe pas UUID:%s", event.getId(), eventReturned.getKey().toString()));
        }
        return eventReturned;
    }

    /**
     * Returns a Sort object which sorts speaker in ascending order by using the name.
     * @return
     */
    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "lastname");
    }
}
