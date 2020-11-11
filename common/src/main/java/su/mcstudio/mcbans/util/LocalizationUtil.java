package su.mcstudio.mcbans.util;

import dev.simplix.core.common.TimeFormatUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 09.11.2020 1:56
 */
@Slf4j
@UtilityClass
public class LocalizationUtil {

    public String getFormattedBanMessage(String original, String reason, UUID executor, long micros) {
        return original.replace("{reason}", reason)
                       .replace("{executor}", executor.toString())
                       .replace("{date}", TimeFormatUtil.calculateDateFormatted(micros));
    }

    public String getFormattedMuteMessage(String original, String reason, UUID executor, long micros) {
        return original.replace("{reason}", reason)
                       .replace("{executor}", executor.toString())
                       .replace("{date}", TimeFormatUtil.calculateDateFormatted(micros));
    }

    public String getFormattedKickMessage(String original, String reason, UUID executor) {
        return original.replace("{reason}", reason)
                       .replace("{executor}", executor.toString());
    }

}
