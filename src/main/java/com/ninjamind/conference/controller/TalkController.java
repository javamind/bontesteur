package com.ninjamind.conference.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.dto.TalkDetail;
import com.ninjamind.conference.service.talk.TalkService;
import com.ninjamind.conference.utils.LoggerFactory;
import com.ninjamind.conference.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/talk")
public class TalkController {
    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.make();
    /**
     * Service associe permettant de gerer les {@link com.ninjamind.conference.domain.Talk}
     */
    @Autowired
    private TalkService talkService;

    /**
     * Essaye de creer un nouvel enregistrement
     * <code>
     * uri : /talks
     * status :
     * <ul>
     *  <li>201 - {@link org.springframework.http.HttpStatus#CREATED}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param talk
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TalkDetail> create(@RequestBody  TalkDetail talk) {
        try {
            return new ResponseEntity(new TalkDetail(talkService.createTalk(talk.toTalk())), HttpStatus.CREATED);
        } catch (NullPointerException | DataAccessException e) {
            LOG.error("Error on save conference", e);
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    /**
     * Essaye de supprimer un enregistrement
     * <code>
     * uri : /talks/{id}
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     *  <li>404 - {@link org.springframework.http.HttpStatus#NOT_FOUND}</li>
     * </ul>
     * </code>
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<TalkDetail> delete(@PathVariable String id) {
        if (talkService.deleteTalk(new Talk().setId(Utils.stringToLong(id)))) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /**
     * Essaye de modifier un enregistrement
     * <code>
     * uri : /talks
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     *  <li>404 - {@link org.springframework.http.HttpStatus#NOT_FOUND}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param talk
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, consumes="application/json")
    @ResponseBody
    public ResponseEntity<TalkDetail> update(@RequestBody TalkDetail talk) {
        try {
            Talk talkUp = talkService.updateTalk(talk.toTalk());
            if (talkUp == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new TalkDetail(talkUp), HttpStatus.OK);
        } catch (NullPointerException | DataAccessException e) {
            LOG.error("Error on save conference", e);
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Retourne la liste complete
     * <code>
     * uri : /talks
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     * </ul>
     * </code>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TalkDetail> getAll() {
        return Lists.transform(talkService.getAllTalk(), talk -> new TalkDetail(talk));
    }

    /**
     * Retourne l'enregistrement suivant id
     * <code>
     * uri : /talks/{id}
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     *  <li>404 - {@link org.springframework.http.HttpStatus#NOT_FOUND}</li>
     * </ul>
     * </code>
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public ResponseEntity<TalkDetail> getById(@PathVariable String id) {
        Talk readTalkEvent = talkService.getTalk(new Talk(Utils.stringToLong(id)));

        if(readTalkEvent==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new TalkDetail(readTalkEvent), HttpStatus.OK);
    }
}
