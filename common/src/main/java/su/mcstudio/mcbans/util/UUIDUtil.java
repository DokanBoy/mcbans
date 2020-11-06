package su.mcstudio.mcbans.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 16:41
 */
@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UUIDUtil {

    UUID CONSOLE_UUID = new UUID(0, 0);

    public UUID consoleUUID() {
        return CONSOLE_UUID;
    }

}
