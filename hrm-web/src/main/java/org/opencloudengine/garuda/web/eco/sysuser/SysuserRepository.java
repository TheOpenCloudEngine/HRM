package org.opencloudengine.garuda.web.eco.sysuser;

import java.util.List;

public interface SysuserRepository {

    List<Sysuser> getCash();

    Sysuser insert(Sysuser sysuser);

    List<Sysuser> selectAll();

    List<Sysuser> select(int limit, Long skip);

    Sysuser selectById(String id);

    List<Sysuser> selectByIds(List<String> ids);

    List<Sysuser> selectLikeName(String name, int limit, Long skip);

    Sysuser selectByName(String name);

    Long count();

    Long countLikeName(String name);

    Sysuser updateById(Sysuser sysuser);

    void deleteById(String id);

    void bulk(List<Sysuser> sysuserList);
}
