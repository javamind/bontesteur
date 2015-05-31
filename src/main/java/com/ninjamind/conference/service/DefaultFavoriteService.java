package com.ninjamind.conference.service;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.exception.ConferenceNotFoundException;
import com.ninjamind.conference.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation de {@link com.ninjamind.conference.service.FavoriteService}
 *
 * @author EHRET_G
 * @author Agnes
 */
public class DefaultFavoriteService implements FavoriteService {

    @Autowired
    private ConferenceRepository conferenceRepository;


    @Override
    public List<Conference> getTheHypestConfs() throws ConferenceNotFoundException {

        List<Conference> results = conferenceRepository
                .findAll()
                .stream()
                .filter(conference -> conference.getProposalsRatio() != null)
                .sorted((c1, c2) -> Double.compare(c1.getProposalsRatio(), c2.getProposalsRatio()))
                .collect(Collectors.toList());

        if (results == null || results.isEmpty()) {
            throw new ConferenceNotFoundException("Aucune conference evaluee");
        }
        return results;
    }

    @VisibleForTesting
    public void setConferenceRepository(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

}
