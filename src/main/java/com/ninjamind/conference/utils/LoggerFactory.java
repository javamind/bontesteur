// $Id$
// Copyright (c) 2011 Boiron - Tous droits réservés.

package com.ninjamind.conference.utils;

import org.apache.log4j.Logger;

/**
 * Factory de logger.
 *
 * @author pernin_v
 * @see "http://www.javaspecialists.co.za/archive/newsletter.do?issue=137"
 * @since 0.1
 */
public final class LoggerFactory {

    private LoggerFactory() {
    }

    /**
     * Renvoie un logger correspondant à la classe appelante
     *
     * @return Logger
     */
    public static Logger make() {

        Throwable t = new Throwable();  //NOPMD Récupération du nom de la classe courante génériquement
        StackTraceElement directCaller = t.getStackTrace()[1];
        return Logger.getLogger(directCaller.getClassName());
    }
}