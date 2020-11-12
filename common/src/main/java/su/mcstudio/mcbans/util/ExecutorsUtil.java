package su.mcstudio.mcbans.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Executors;

/**
 * Created by: Alexey Zakharov <alexey@zakharov.pw>
 * Date: 13.11.2020 0:44
 */
@UtilityClass
public class ExecutorsUtil {

    public ListeningExecutorService getListeningExecutorService() {
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

}
