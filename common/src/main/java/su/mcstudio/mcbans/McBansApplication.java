package su.mcstudio.mcbans;

import dev.simplix.core.common.aop.ScanComponents;
import dev.simplix.core.common.aop.SimplixApplication;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 01.11.2020 18:09
 */
@SimplixApplication(name = "MCBans",
                    version = "1.0.0",
                    authors = "DokanBoy",
                    dependencies = {"SimplixCore"},
                    workingDirectory = "plugins/MCBans")
@ScanComponents("su.mcstudio.mcbans")
public final class McBansApplication {

}
