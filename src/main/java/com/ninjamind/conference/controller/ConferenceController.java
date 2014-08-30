package com.ninjamind.conference.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.events.dto.ConferenceDetail;
import com.ninjamind.conference.service.conference.ConferenceService;
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
@RequestMapping("/conferences")
public class ConferenceController {
    /**
     * Service associe permettant de gerer les {@link com.ninjamind.conference.domain.Conference}
     */
    @Autowired
    private ConferenceService conferenceService;

    /**
     * Essaye de creer un nouvel enregistrement
     * <code>
     * uri : /conferences
     * status :
     * <ul>
     *  <li>201 - {@link org.springframework.http.HttpStatus#CREATED}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param conference
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ConferenceDetail> create(@RequestBody  ConferenceDetail conference) {
        CreatedEvent<Conference> createdConferenceEvent =  conferenceService.createConference(conference.toConference());

        if(!createdConferenceEvent.isValidEntity()){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(new ConferenceDetail((Conference) createdConferenceEvent.getValue()), HttpStatus.CREATED);
    }

    /**
     * Essaye de supprimer un enregistrement
     * <code>
     * uri : /conferences/{id}
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
    public ResponseEntity<ConferenceDetail> delete(@PathVariable String id) {
        DeletedEvent<Conference> deletedConferenceEvent =  conferenceService.deleteConference(new Conference().setId(Utils.stringToLong(id)));

        if(!deletedConferenceEvent.isEntityFound()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new ConferenceDetail((Conference) deletedConferenceEvent.getValue()), HttpStatus.OK);
    }

    /**
     * Essaye de modifier un enregistrement
     * <code>
     * uri : /conferences
     * status :
     * <ul>
     *  <li>200 - {@link org.springframework.http.HttpStatus#OK}</li>
     *  <li>404 - {@link org.springframework.http.HttpStatus#NOT_FOUND}</li>
     *  <li>406 - {@link org.springframework.http.HttpStatus#NOT_ACCEPTABLE}</li>
     * </ul>
     * </code>
     * @param conference
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, consumes="application/json")
    @ResponseBody
    public ResponseEntity<ConferenceDetail> update(@RequestBody  ConferenceDetail conference) {
        UpdatedEvent<Conference> updatedConferenceEvent =  conferenceService.updateConference(conference.toConference());

        if(!updatedConferenceEvent.isEntityFound()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if(!updatedConferenceEvent.isValidEntity()){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(new ConferenceDetail((Conference) updatedConferenceEvent.getValue()), HttpStatus.OK);
    }

    /**
     * Retourne la liste complete
     * <code>
     * uri : /conferences
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
    public List<ConferenceDetail> getAll() {
        return Lists.transform(conferenceService.getAllConference(), new Function<Conference, ConferenceDetail>() {
            @Override
            public ConferenceDetail apply(Conference conference) {
                return new ConferenceDetail(conference);
            }
        });
    }

    /**
     * Retourne l'enregistrement suivant id
     * <code>
     * uri : /conferences/{id}
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
    public ResponseEntity<ConferenceDetail> getById(@PathVariable String id) {
        Conference readConferenceEvent = conferenceService.getConference(new Conference().setId(Utils.stringToLong(id)));

        if(readConferenceEvent==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new ConferenceDetail(readConferenceEvent), HttpStatus.OK);
    }
}
