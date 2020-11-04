package su.mcstudio.mcbans.module;

import com.google.inject.Binder;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import su.mcstudio.mcbans.service.ViolationService;
import su.mcstudio.mcbans.service.UserService;
import su.mcstudio.mcbans.service.impl.ViolationServiceImpl;
import su.mcstudio.mcbans.service.impl.UserServiceImpl;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 03.11.2020 16:16
 */
public class CommonServiceModule extends AbstractSimplixModule {

    @Override
    public void configure(Binder binder) {
        super.configure(binder);

        binder.bind(UserService.class).to(UserServiceImpl.class);
        binder.bind(ViolationService.class).to(ViolationServiceImpl.class);
    }
}