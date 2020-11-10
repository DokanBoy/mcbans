package su.mcstudio.mcbans.module;

import com.google.inject.Provides;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import dev.simplix.core.common.i18n.LocalizationManagerFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.util.Locale;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 06.11.2020 23:41
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonLocalizationModule extends AbstractSimplixModule {

    File file;

    public CommonLocalizationModule(File file) {
        this.file = file;
    }

    @Provides
    @Private
    public LocalizationManager localizationManager(LocalizationManagerFactory factory) {
        LocalizationManager localizationManager = factory.create(new File(file, "lang"));
        localizationManager.defaultLocale(new Locale("ru"));

        return localizationManager;
    }

}