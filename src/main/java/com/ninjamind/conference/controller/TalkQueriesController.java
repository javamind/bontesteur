package com.ninjamind.conference.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.dto.TalkDetail;
import com.ninjamind.conference.service.talk.TalkService;
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
@RequestMapping("/talks")
public class TalkQueriesController {
    /**
     * Service associe permettant de gerer les {@link com.ninjamind.conference.domain.Talk}
     */
    @Autowired
    private TalkService talkService;

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
        return Lists.transform(talkService.getAllTalk(), new Function<Talk, TalkDetail>() {
            @Override
            public TalkDetail apply(Talk talk) {
                return new TalkDetail(talk);
            }
        });
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
