package com.ninjamind.conference.controller;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.events.dto.TalkDetail;
import com.ninjamind.conference.service.talk.TalkService;
import com.ninjamind.conference.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/talks")
public class TalkCommandsController {
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
        CreatedEvent<Talk> createdTalkEvent =  talkService.createTalk(talk.toTalk());

        if(!createdTalkEvent.isValidEntity()){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(new TalkDetail((Talk)createdTalkEvent.getValue()), HttpStatus.CREATED);
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
        DeletedEvent<Talk> deletedTalkEvent =  talkService.deleteTalk(new Talk(Utils.stringToLong(id)));

        if(!deletedTalkEvent.isEntityFound()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new TalkDetail((Talk)deletedTalkEvent.getValue()), HttpStatus.OK);
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
        UpdatedEvent<Talk> updatedTalkEvent =  talkService.updateTalk(talk.toTalk());

        if(!updatedTalkEvent.isEntityFound()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if(!updatedTalkEvent.isValidEntity()){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(new TalkDetail((Talk)updatedTalkEvent.getValue()), HttpStatus.OK);
    }

}
