package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static String getAPIFormattedDate (Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);

    }

}
