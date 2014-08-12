package com.ninjamind.conference.controller;

import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handler d'exception se chargeant des exceptions pouvant etre levees
 * @author EHRET_G
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    /**
     * Logger commun a la publication
     */
    private static final Logger LOG = LoggerFactory.make();

    /**
     * Traitement des erreurs de persistence
     * @param e
     */
    @ExceptionHandler
    public ResponseEntity<String> handleException(JpaSystemException  e){
        //L'excpetion  JpaSystemException et liée à une {@link javax.persistence.PersistenceException} qui elle même encapsule
        //des exceptions plus précises
        if(e.getCause()!=null && e.getCause().getCause()!=null){
            switch (e.getCause().getCause().getClass().toString()){
                case "org.hibernate.PropertyValueException":
                    return new ResponseEntity("Persistence error : " + e.getCause().getMessage(), HttpStatus.NOT_ACCEPTABLE);
                case "javax.persistence.NoResultException":
                case "javax.persistence.EntityNotFoundException":
                    return new ResponseEntity("Persistence error : " + e.getCause().getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity("Persistence error : " + e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Traitement des erreurs NullPointerException
     * @param e
     */
    @ExceptionHandler
    public ResponseEntity<String> handleNullPointerException(NullPointerException  e){
        LOG.error(e);
        return new ResponseEntity("NullPointer : " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Traitement des erreurs de IllegalArgumentException
     * @param e
     */
    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalArgumentException  e){
        LOG.error(e);
        return new ResponseEntity("Illegal Argument : " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Traitement des erreurs de persistence
     * @param e
     */
    @ExceptionHandler
    public ResponseEntity<String> handleException(RuntimeException  e){
        LOG.error(e);
        return new ResponseEntity("Internal error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
