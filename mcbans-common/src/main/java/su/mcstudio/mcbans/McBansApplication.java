package su.mcstudio.mcbans;

import dev.simplix.core.common.CommonSimplixModule;
import dev.simplix.core.common.aop.ScanComponents;
import dev.simplix.core.common.aop.SimplixApplication;
import dev.simplix.core.common.inject.SimplixInstaller;
import dev.simplix.core.common.platform.Platform;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:09
 */
@SimplixApplication(name = "MCBans",
                    version = "1.0.0",
                    authors = "DokanBoy",
                    workingDirectory = "plugins/MCBans")
@ScanComponents({"dev.simplix.core"})
public final class McBansApplication {

    public static void main(String[] args) {
        SimplixInstaller.instance()
                        .register(McBansApplication.class, new CommonSimplixModule());
        SimplixInstaller.instance()
                        .install(Platform.STANDALONE);
        System.out.println("Startup");
    }

}
