package su.mcstudio.mcbans.module;

import com.google.inject.Binder;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import su.mcstudio.mcbans.service.CacheService;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.service.impl.CacheServiceImpl;
import su.mcstudio.mcbans.service.impl.ViolationServiceImpl;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 16:16
 */
public class CommonServiceModule extends AbstractSimplixModule {

    @Override
    public void configure(Binder binder) {
        super.configure(binder);

        binder.bind(ViolationService.class).to(ViolationServiceImpl.class);
        binder.bind(CacheService.class).to(CacheServiceImpl.class);
    }

}