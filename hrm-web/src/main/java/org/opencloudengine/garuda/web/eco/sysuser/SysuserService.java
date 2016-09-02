package org.opencloudengine.garuda.web.eco.sysuser;

import org.opencloudengine.garuda.model.Sysuser;

import java.util.List;

public interface SysuserService {


    Sysuser createUser(String name,
                        String password,
                       String defaultUser,
                        String description
    );

    List<Sysuser> selectAll();

    List<Sysuser> select(int limit, Long skip);

    Sysuser selectById(String id);

    Sysuser cashById(String id);

    List<Sysuser> selectLikeName(String name, int limit, Long skip);

    Sysuser selectByName(String name);

    Long count();

    Long countLikeName(String name);

    Sysuser updateById(Sysuser sysuser);

    Sysuser updateById(String id,
                       String password,
                       String defaultUser,
                       String description);

    void deleteById(String id);
}
