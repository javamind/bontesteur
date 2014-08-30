package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.exception.ConferenceNotFoundException;

import java.util.List;

/**
 * Service permettant de détermine les favoris des utilisateurs
 */
public interface FavoriteService {

    /**
     * Recupération des conference les plus hypes parmis toutes les conférences
     * Basé sur le Nb de Followers Twitter (>800)
     * @return les conferences sont classees de la plus hype à la moins hype!
     */
    List<Conference> getTheHypestConfs() throws ConferenceNotFoundException;

}
