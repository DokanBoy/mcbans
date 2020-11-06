package su.mcstudio.mcbans.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.Locale;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 23:34
 */
@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocaleUtil {

    Locale RUSSIAN_LOCALE = new Locale("ru");

    public Locale russianLocale() {
        return RUSSIAN_LOCALE;
    }

}
