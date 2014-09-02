package com.ninjamind.conference.service.talk;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
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
public class TalkServiceImpl implements TalkService {
    private static final Logger LOG = LoggerFactory.make();

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
     * @param talk
     * @return
     */
    @Override
    public Talk getTalk(Talk talk) {
        Preconditions.checkNotNull(talk);
        Preconditions.checkNotNull(talk.getId(), "id is required for search talk");

        //Recherche de l'element par l'id
        return talkRepository.findOne(talk.getId());
    }

    /**
     * Creation d'une nouvelle talk
     * @param talk
     * @return
     */
    @Override
    public Talk createTalk(Talk talk) {
        Preconditions.checkNotNull(talk);
        Preconditions.checkNotNull(talk.getName(), "talk is required to create it");

        Talk talkCreated = transformAndSaveTalkDetailToTalk(talk, true);
        LOG.debug(String.format("Creation du talk ayant id=[%d] name=[%s]",talkCreated.getId(), talkCreated.getName()));
        return talkCreated;
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
            //On copie les objets de la couche presentation dans l'objet attache a la session Hibernate
            BeanUtils.copyProperties(talk, talkToPersist, "version");
            return talkToPersist;

        }
        else{
            //On enregistre
            return talkRepository.save(talk);
        }
    }

    @Override
    public Talk updateTalk(Talk talk) {
        Preconditions.checkNotNull(talk);
        Preconditions.checkNotNull(talk.getId(), "talk is required to update it");

        Talk talkUpdated = transformAndSaveTalkDetailToTalk(talk, false);
        LOG.debug(String.format("Modification du talk ayant id=[%d] name=[%s]", talk.getId(), talk.getName()));
        return talkUpdated;
    }

    /**
     * Suppression d'une talk
     * @param talk
     * @return
     */
    @Override
    public boolean deleteTalk(Talk talk) {
        Preconditions.checkNotNull(talk);
        Preconditions.checkNotNull(talk.getId(), "talk is required to delete talk");

        //Recherche de l'element par l'id
        Talk talkToDelete = talkRepository.findOne(talk.getId());

        if(talkToDelete!=null){
            talkRepository.delete(talkToDelete);
            LOG.debug(String.format("Suppression du talk ayant id=[%s]", talk.getId()));
            return true;
        }
        else{
            LOG.debug(String.format("Le talk ayant id=[%s] n'existe pas", talk.getId()));
        }
        return false;
    }

    /**
     * Returns a Sort object which sorts talk in ascending order by using the name.
     * @return
     */
    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    @VisibleForTesting
    public TalkServiceImpl setTalkRepository(TalkRepository talkRepository) {
        this.talkRepository = talkRepository;
        return this;
    }
}
