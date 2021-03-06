package org.opencloudengine.garuda.backend.clientjob;

import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.request.BasicClientRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ClientJobService {

    ClientJob run(BasicClientRequest clientRequest, String executeFrom) throws Exception;

    ClientJob kill(String clientJobId) throws Exception;

    ClientJob getDataFromFileSystem(ClientJob clientJob);

    ClientJob insert(ClientJob clientJob);

    List<ClientJob> selectAll();

    List<ClientJob> select(int limit, Long skip);

    List<ClientJob> selectByClientJobTypeAndExecuteFrom(int limit, Long skip, String clientJobType, String executeFrom);

    List<ClientJob> selectRunning();

    List<ClientJob> selectStopping();

    ClientJob selectById(String id);

    ClientJob selectByClientJobId(String clientJobId);

    Long count();

    ClientJob updateById(ClientJob clientJob);

    void deleteById(String id);

    void bulk(List<ClientJob> clientJobList);
}
