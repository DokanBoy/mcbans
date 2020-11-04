package su.mcstudio.mcbans.model;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import su.mcstudio.mcbans.model.violation.Violation;

import java.util.List;
import java.util.UUID;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:31
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class User {

    @NonNull UUID uuid;

    @NonNull List<Violation> violations;

    public User(@NonNull UUID uuid) {
        this.uuid = uuid;
        this.violations = Lists.newArrayList();
    }

}
