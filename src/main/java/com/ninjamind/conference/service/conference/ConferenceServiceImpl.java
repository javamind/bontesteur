package com.ninjamind.conference.service.conference;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.repository.CountryRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestion des {@link com.ninjamind.conference.domain.Conference}
 *
 * @author EHRET_G
 */
@Service
@Transactional
public class ConferenceServiceImpl implements ConferenceService {
    private static final Logger LOG = LoggerFactory.make();

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    /**
     * Recuperation de la liste des conferences
     *
     * @return
     */
    @Override
    public List<Conference> getAllConference() {
        //Pour le moment nous n'avons pas de critère de filtre dans ReadAllConferenceRequestconference
        return conferenceRepository.findAll(sortByNameAsc());
    }


    /**
     * Recuperation d'une conference vi a son ID
     *
     * @param conference
     * @return
     */
    @Override
    public Conference getConference(Conference conference) {
        Preconditions.checkNotNull(conference);
        Preconditions.checkNotNull(conference.getId(), "id is required for search conference");

        //Recherche de l'element par l'id
        return conferenceRepository.findOne(conference.getId());
    }

    /**
     * Creation d'une nouvelle conference
     *
     * @param conference
     * @return
     */
    @Override
    public Conference createConference(Conference conference) {
        Preconditions.checkNotNull(conference);
        Preconditions.checkNotNull(conference.getName(), "conference is required to create it");

        Conference conferenceCreated = transformAndSaveConferenceDetailToConference(conference, true);
        LOG.debug(String.format("Creation de la conference ayant id=[%d] name=[%s]", conferenceCreated.getId(), conference.getName()));
        return conferenceCreated;
    }

    @Override
    public Conference updateConference(Conference conference) {
        Preconditions.checkNotNull(conference);
        Preconditions.checkNotNull(conference.getName(), "conference is required to update it");

        Conference conferenceUpdated = transformAndSaveConferenceDetailToConference(conference, false);
        LOG.debug(String.format("Modification de la conference ayant id=[%d] name=[%s]", conference.getId(), conference.getName()));
        return conferenceUpdated;
    }

    /**
     * Suppression d'une conference
     *
     * @param conference
     * @return
     */
    @Override
    public boolean deleteConference(Conference conference) {
        Preconditions.checkNotNull(conference);
        Preconditions.checkNotNull(conference.getId(), "conference is required to delete conference");

        //Recherche de l'element par l'id
        Conference conf = conferenceRepository.findOne(conference.getId());

        if (conf != null) {
            conferenceRepository.delete(conf);
            LOG.debug(String.format("Suppression de la conference ayant id=[%s]", conference.getId()));
            return true;
        }
        else {
            LOG.debug(String.format("La conference ayant id=[%s] n'existe pas", conference.getId()));
        }
        return false;
    }

    /**
     * Permet de convertir la donnée reçue
     *
     * @param conference
     * @return
     */
    private Conference transformAndSaveConferenceDetailToConference(Conference conference, boolean creation) {
        //Le pays envoye est simplement un code on doit mettre a jour le pays avec les données présentes
        //en base de données
        if (conference.getCountry() != null) {
            conference.setCountry(countryRepository.findCountryByCode(conference.getCountry().getCode()));
        }

        //Si pas en creation on regarde si enreg existe
        if (!creation) {
            Conference conferenceToPersist = conferenceRepository.findOne(conference.getId());
            if (conferenceToPersist == null) {
                return null;
            }
            BeanUtils.copyProperties(conference, conferenceToPersist, "version");
            return conferenceToPersist;

        }
        else {
            //On enregistre
            return conferenceRepository.save(conference);
        }
    }




    /**
     * Returns a Sort object which sorts conference in ascending order by using the name.
     *
     * @return
     */
    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    @Override
    public Conference getCoolestConference() {

        List<ResultConfCalculator> results = conferenceRepository.findAll()
                .stream()
                .filter(conference -> {
                    //Si une des donnees est vide conf est hors concours
                    if (conference.getNbTwitterFollowers() == null ||
                            conference.getNbAttendees() == null ||
                            conference.getNbHoursToSellTicket() == null ||
                            conference.getNbConferenceSlots() == null ||
                            conference.getNbConferenceProposals() == null) {
                        LOG.info(String.format("La conference %s n'est pas prise en compte car les donnees ne sont pas toutes renseignees", conference.getName()));
                        return false;
                    }
                    return true;
                })
                .map(conference -> {
                    //Calcul de l'interet speaker
                    Double speakerInterest = conference.getNbConferenceSlots().doubleValue() / conference.getNbConferenceProposals();
                    //Cacul interet social
                    Double socialInterest = conference.getNbTwitterFollowers().doubleValue() / conference.getNbAttendees();
                    //Cacul interet participant
                    Double attendeeInterest = conference.getNbHoursToSellTicket().doubleValue() * 60 / conference.getNbAttendees();

                    return new ResultConfCalculator(conference, speakerInterest, socialInterest, attendeeInterest);

                })
                .sorted((o1, o2) -> {
                    int compSpeaker = o1.speakerInterest.compareTo(o2.speakerInterest);
                    int compSocial = o1.socialInterest.compareTo(o2.socialInterest);
                    int compAttendee = o1.attendeeInterest.compareTo(o2.attendeeInterest);

                    //Si le ratio speaker est plus faible c'est que l'interet speaker est plus grand puisqu'il propose plus
                    int pointConf1 = compSpeaker <= 0 ? 1 : 0;
                    int pointConf2 = compSpeaker >= 0 ? 1 : 0;
                    //Si le ratio social est plus fort c'est que les participants sont plus intï¿½resses par la conf car il s'abonnent plus
                    pointConf1 += compSocial >= 0 ? 1 : 0;
                    pointConf2 += compSocial <= 0 ? 1 : 0;
                    //Si le ratio participant est plus faible c'est que les participants sont plus intï¿½resses par la conf car il reserve plus vite
                    pointConf1 += compAttendee <= 0 ? 1 : 0;
                    pointConf2 += compAttendee >= 0 ? 1 : 0;

                    return pointConf2 - pointConf1;
                })
                .collect(Collectors.toList());


        if (results == null || results.isEmpty()) {
            LOG.info("Aucune conference ne ressort gagnante");
            return null;
        }
        return results.iterator().next().conference;
    }

    protected class ResultConfCalculator {
        private Conference conference;
        private Double speakerInterest;
        private Double socialInterest;
        private Double attendeeInterest;

        public ResultConfCalculator(Conference conference, Double speakerInterest, Double socialInterest, Double attendeeInterest) {
            this.conference = conference;
            this.speakerInterest = speakerInterest;
            this.socialInterest = socialInterest;
            this.attendeeInterest = attendeeInterest;
        }
    }
}
