package com.ninjamind.conference.controller;

import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.dto.SpeakerDetail;
import com.ninjamind.conference.service.speaker.SpeakerService;
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
@RequestMapping("/speaker")
public class SpeakerController {
    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.make();
    /**
     * Service associe permettant de gerer les {@link com.ninjamind.conference.domain.Speaker}
     */
    @Autowired
    private SpeakerService speakerService;

    /**
     * Essaye de creer un nouvel enregistrement
     * <code>
     * uri : /speakers
     * status :
     * <ul>
     *  <li>201 - {@link org.springframework.http.HttpStatus#CREATED}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param speaker
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SpeakerDetail> create(@RequestBody  SpeakerDetail speaker) {
        try {
            return new ResponseEntity(new SpeakerDetail(speakerService.createSpeaker(speaker.toSpeaker())), HttpStatus.CREATED);
        } catch (NullPointerException | DataAccessException e) {
            LOG.error("Error on save conference", e);
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Essaye de supprimer un enregistrement
     * <code>
     * uri : /speakers/{id}
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
    public ResponseEntity<SpeakerDetail> delete(@PathVariable String id) {
        if (speakerService.deleteSpeaker(new Speaker().setId(Utils.stringToLong(id)))) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /**
     * Essaye de modifier un enregistrement
     * <code>
     * uri : /speakers
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     *  <li>404 - {@link org.springframework.http.HttpStatus#NOT_FOUND}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param speaker
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, consumes="application/json")
    @ResponseBody
    public ResponseEntity<SpeakerDetail> update(@RequestBody  SpeakerDetail speaker) {
        try {
            Speaker speak = speakerService.updateSpeaker(speaker.toSpeaker());
            if (speak == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new SpeakerDetail(speak), HttpStatus.OK);
        } catch (NullPointerException | DataAccessException e) {
            LOG.error("Error on save conference", e);
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    /**
     * Retourne la liste complete
     * <code>
     * uri : /speakers
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
    public List<SpeakerDetail> getAll() {
        return Lists.transform(speakerService.getAllSpeaker(), speaker -> new SpeakerDetail(speaker));
    }

    /**
     * Retourne l'enregistrement suivant id
     * <code>
     * uri : /speakers/{id}
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
    public ResponseEntity<SpeakerDetail> getById(@PathVariable String id) {
        Speaker readSpeakerEvent = speakerService.getSpeaker(new Speaker().setId(Utils.stringToLong(id)));

        if(readSpeakerEvent==null){
            return new ResponseEntity<SpeakerDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SpeakerDetail>(new SpeakerDetail(readSpeakerEvent), HttpStatus.OK);
    }
}
