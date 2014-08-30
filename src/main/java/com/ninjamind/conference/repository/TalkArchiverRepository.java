package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Talk;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository associé au {@link com.ninjamind.conference.domain.Talk}
 *
 * @author ehret_g
 */
public interface TalkArchiverRepository extends Repository<Talk, Long> {
    /**
     * Permet de récupérer la liste des talks à archiver
     * @param year
     * @return
     */
    List<Talk> findTalkToArchive(Integer year);

    /**
     * Permet d'archiver la liste des talks
     * @param year
     * @return
     */
    int archiveTalks(Integer year);
}
