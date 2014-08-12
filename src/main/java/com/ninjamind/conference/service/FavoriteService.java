package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Conference;

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
    List<Conference> getTheHypestConfs() throws Exception;

}
