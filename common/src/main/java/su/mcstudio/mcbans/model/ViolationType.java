package su.mcstudio.mcbans.model;

import lombok.Getter;
import lombok.NonNull;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:33
 */
public enum ViolationType {

    KICK("kick"),
    BAN("ban"),
    MUTE("mute"),
    ;

    @Getter
    private final String name;

    ViolationType(@NonNull String name) {
        this.name = name;
    }

}
