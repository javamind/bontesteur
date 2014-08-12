package com.ninjamind.conference.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.dto.SpeakerDetail;
import com.ninjamind.conference.service.speaker.SpeakerService;
import com.ninjamind.conference.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/speakers")
public class SpeakerQueriesController {
    /**
     * Service associe permettant de gerer les {@link com.ninjamind.conference.domain.Speaker}
     */
    @Autowired
    private SpeakerService speakerService;

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
        return Lists.transform(speakerService.getAllSpeaker(), new Function<Speaker, SpeakerDetail>() {
            @Override
            public SpeakerDetail apply(Speaker speaker) {
                return new SpeakerDetail(speaker);
            }
        });
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
        Speaker readSpeakerEvent = speakerService.getSpeaker(new Speaker(Utils.stringToLong(id)));

        if(readSpeakerEvent==null){
            return new ResponseEntity<SpeakerDetail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SpeakerDetail>(new SpeakerDetail(readSpeakerEvent), HttpStatus.OK);
    }

}
