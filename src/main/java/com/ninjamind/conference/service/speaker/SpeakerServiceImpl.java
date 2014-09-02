package com.ninjamind.conference.service.speaker;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.repository.CountryRepository;
import com.ninjamind.conference.repository.SpeakerRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
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
     * @param speaker
     * @return
     */
    @Override
    public Speaker getSpeaker(Speaker speaker) {
        Preconditions.checkNotNull(speaker);
        Preconditions.checkNotNull(speaker.getId(), "id is required for search speaker");
        return speakerRepository.findOne(speaker.getId());

    }

    /**
     * Creation d'une nouvelle speaker
     * @param speaker
     * @return
     */
    @Override
    public Speaker createSpeaker(Speaker speaker) {
        Preconditions.checkNotNull(speaker);
        Preconditions.checkNotNull(speaker.getLastname(), "speaker is required to create it");

        Speaker speakerCreated = transformAndSaveSpeakerDetailToSpeaker(speaker, true);
        LOG.debug(String.format("Creation du speaker ayant id=[%d] name=[%s]", speakerCreated.getId(), speaker.getLastname()));
        return speakerCreated;
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
            BeanUtils.copyProperties(speaker, speakerToPersist, "version");
            return speakerToPersist;
        }
        else{
            //On enregistre
            return speakerRepository.save(speaker);
        }
    }

    @Override
    public Speaker updateSpeaker(Speaker speaker) {
        Preconditions.checkNotNull(speaker);
        Preconditions.checkNotNull(speaker.getId(), "speaker is required to update it");

        Speaker speakerUpdated = transformAndSaveSpeakerDetailToSpeaker(speaker, false);
        LOG.debug(String.format("Modification du speaker ayant id=[%d] name=[%s]", speaker.getId(), speaker.getLastname()));
        return speakerUpdated;
    }

    /**
     * Suppression d'une speaker
     * @param speaker
     * @return
     */
    @Override
    public boolean deleteSpeaker(Speaker speaker) {
        Preconditions.checkNotNull(speaker);
        Preconditions.checkNotNull(speaker.getId(), "speaker is required to delete speaker");

        //Recherche de l'element par l'id
        Speaker speakerToDelete = speakerRepository.findOne(speaker.getId());

        if(speakerToDelete!=null){
            speakerRepository.delete(speakerToDelete);
            LOG.debug(String.format("Suppression de la speaker ayant id=[%s]", speaker.getId()));
            return true;
        }
        else{
            LOG.debug(String.format("La speaker ayant id=[%s] n'existe pas", speaker.getId()));
        }
        return false;
    }

    /**
     * Returns a Sort object which sorts speaker in ascending order by using the name.
     * @return
     */
    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "lastname");
    }
}
