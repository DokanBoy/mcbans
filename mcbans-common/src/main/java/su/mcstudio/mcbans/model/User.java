package su.mcstudio.mcbans.model;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import su.mcstudio.mcbans.model.punishment.Punishment;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:31
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class User {

    @NonNull UUID uuid;

    @NonNull List<Punishment> punishments;

    public User(@NonNull UUID uuid) {
        this.uuid = uuid;
        punishments = Lists.newArrayList();
    }

}
