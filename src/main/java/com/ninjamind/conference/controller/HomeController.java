package com.ninjamind.conference.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ehret_g
 */
@Controller
@RequestMapping("/")
public class HomeController {
    /**
     *
     * <code>
     * uri : /
     * status : 200 - {@link org.springframework.http.HttpStatus#OK}
     * </code>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> home() {
        return new ResponseEntity("Page d'accueil presentation Devox2014", HttpStatus.OK);
    }
}
