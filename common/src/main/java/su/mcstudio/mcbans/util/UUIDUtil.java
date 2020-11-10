package su.mcstudio.mcbans.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 16:41
 */
@UtilityClass
public class UUIDUtil {

    private static final UUID CONSOLE_UUID = new UUID(0, 0);

    public UUID consoleUUID() {
        return CONSOLE_UUID;
    }

}
