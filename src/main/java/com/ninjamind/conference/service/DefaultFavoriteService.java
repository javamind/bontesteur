package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation de {@link com.ninjamind.conference.service.FavoriteService}
 *
 * @author EHRET_G
 * @author Agnes
 */
public class DefaultFavoriteService implements FavoriteService {

    private static Logger LOG = LoggerFactory.make();

    @Autowired
    private ConferenceRepository conferenceRepository;


    @Override
    public List<Conference> getTheHypestConfs() throws Exception {

        List<Conference> results = conferenceRepository.findAll()
                .stream()
                .filter(conference -> {
                    return (conference.getProposalsRatio() != null);
                })
                .sorted(new Comparator<Conference>() {
                    @Override
                    // les conferences sont classees par ordre decroissant du nb de followers Twitter
                    public int compare(Conference c1, Conference c2) {
                        return Double.compare(c1.getProposalsRatio(), c2.getProposalsRatio());
                    }
                })
                .collect(Collectors.toList());

        if (results == null || results.isEmpty()) {
            throw new Exception("Aucune conference evaluee");
        }
        return results;
    }
}
