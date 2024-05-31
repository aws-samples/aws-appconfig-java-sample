// depreciation example - sun.misc.BASE64Encoder;
package com.amazonaws.samples.appconfig.utils;
import sun.misc.BASE64Encoder;

import java.util.Date;


public class Encoder {

    Date defaultDate = new Date(1999, 0, 1);

    byte[] bytes = new byte[57];
    String enc1 = new sun.misc.BASE64Encoder().encode(bytes);


}