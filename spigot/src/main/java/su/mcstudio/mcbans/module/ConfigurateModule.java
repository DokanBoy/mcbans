package su.mcstudio.mcbans.module;

import com.google.inject.Binder;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import lombok.SneakyThrows;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;

import java.io.File;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 11.11.2020 4:37
 */
public class ConfigurateModule extends AbstractSimplixModule {

    private final ConfigurationNode configuration;

    public ConfigurateModule(ConfigurationNode configuration) {
        this.configuration = configuration;
    }

    @Override
    @SneakyThrows
    public void configure(Binder binder) {
        super.configure(binder);

        binder.bind(ConfigurationNode.class).toInstance(configuration);
    }
}
