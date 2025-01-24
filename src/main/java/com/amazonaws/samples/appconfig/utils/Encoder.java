// depreciation example - sun.misc.BASE64Encoder;
package com.amazonaws.samples.appconfig.utils;
//import sun.misc.BASE64Encoder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;


public class Encoder {

    Logger logger = Logger.getLogger(Encoder.class.getName());

    private String unUsedVariable;

    Date defaultDate;
    
    {
        String unused = "ast-grep unused string example";
        Calendar calendar = new GregorianCalendar(1999, Calendar.JANUARY, 1);
        defaultDate = calendar.getTime();
        System.out.println("test ast-grep "+defaultDate);
    }

    byte[] bytes = new byte[57];
//    String enc1 = new sun.misc.BASE64Encoder().encode(bytes);



}
