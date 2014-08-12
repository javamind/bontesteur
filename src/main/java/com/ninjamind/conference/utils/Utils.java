package com.ninjamind.conference.utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link }
 *
 * @author EHRET_G
 */
public class Utils {
    /**
     * Message
     */
    public static final SimpleDateFormat DATE_FORMAT_JSON = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * Logger commun a la publication
     */
    private static final Logger LOG = LoggerFactory.make();

    /**
     * Permet de convertir une date recue au format Json en Java
     * @param date
     * @return
     */
    public static Date dateJsonToJava(String date){
        if(date==null){
            return null;
        }
        try {
            return DATE_FORMAT_JSON.parse(date);
        } catch (ParseException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * Permet de convertir une date java au format Json
     * @param date
     * @return
     */
    public static String dateJavaToJson(Date date){
        if(date==null){
            return null;
        }
        return DATE_FORMAT_JSON.format(date);
    }

    /**
     * Permet de convertir une chaine de cractere en Long
     * @param number
     * @return
     */
    public static Long stringToLong(String number){
        if(number==null){
            return null;
        }
        try{
            return Long.valueOf(number);
        }
        catch (NumberFormatException e){
            return null;
        }
    }
}
