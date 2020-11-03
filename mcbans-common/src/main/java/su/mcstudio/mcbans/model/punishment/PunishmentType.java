package su.mcstudio.mcbans.model.punishment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:33
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PunishmentType {

    KICK("kick"),
    BAN("ban"),
    MUTE("mute"),
    ;

    @Getter
    String name;

    PunishmentType(@NonNull String name) {
        this.name = name;
    }

}
