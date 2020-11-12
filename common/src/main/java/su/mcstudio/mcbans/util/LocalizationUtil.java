package su.mcstudio.mcbans.util;

import com.google.inject.Key;
import dev.simplix.core.common.TimeFormatUtil;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import dev.simplix.core.common.inject.SimplixInstaller;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 09.11.2020 1:56
 */
@UtilityClass
public class LocalizationUtil {

    private final LocalizationManager localizationManager = SimplixInstaller.instance()
                                                                            .findInjector(LocalizationManager.class)
                                                                            .orElseThrow(NullPointerException::new)
                                                                            .getInstance(Key.get(LocalizationManager.class, Private.class));

    private final String console = localizationManager.localized("console");

    public String getFormattedBanMessage(String reason, UUID executor, long millis) {
        return localizationManager.localized("ban-message")
                                  .replace("{reason}", reason)
                                  .replace("{executor}", executor.equals(UUIDUtil.consoleUUID()) ? console : executor.toString())
                                  .replace("{date}", TimeFormatUtil.calculateDateFormatted(millis))
                                  .replace('&', '§');
    }

    public String getFormattedMuteMessage(String reason, UUID executor, long millis) {
        return localizationManager.localized("mute-message")
                                  .replace("{reason}", reason)
                                  .replace("{executor}", executor.equals(UUIDUtil.consoleUUID()) ? console : executor.toString())
                                  .replace("{date}", TimeFormatUtil.calculateDateFormatted(millis))
                                  .replace('&', '§');
    }

    public String getFormattedKickMessage(String reason, UUID executor) {
        return localizationManager.localized("kick-message")
                                  .replace("{reason}", reason)
                                  .replace("{executor}", executor.equals(UUIDUtil.consoleUUID()) ? console : executor.toString())
                                  .replace('&', '§');
    }

}
