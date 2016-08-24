package org.opencloudengine.garuda.web.eco.sysuser;

import org.opencloudengine.garuda.backend.system.SystemService;
import org.opencloudengine.garuda.util.StringUtils;
import org.opencloudengine.garuda.web.configuration.ConfigurationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class SysuserServiceImpl implements SysuserService {
    @Autowired
    @Qualifier("config")
    private Properties config;

    @Autowired
    private SysuserRepository sysuserRepository;

    @Autowired
    ConfigurationHelper configurationHelper;

    @Autowired
    SystemService systemService;

    private void makeUnDefaultUser() {
        List<Sysuser> sysusers = sysuserRepository.selectAll();
        for (int i = 0; i < sysusers.size(); i++) {
            sysusers.get(i).setDefaultUser("N");
        }
        sysuserRepository.bulk(sysusers);
    }

    @Override
    public Sysuser createUser(String name,
                              String password,
                              String defaultUser,
                              String description) {

        Sysuser sysuser = new Sysuser();
        sysuser.setName(name);
        sysuser.setPassword(password);
        sysuser.setDefaultUser(defaultUser);
        sysuser.setDescription(description);

        if ("Y".equals(defaultUser)) {
            this.makeUnDefaultUser();
        }

        //사용자 이름은 업데이트가 안된다.
        //사용자가 있다면 삭제한다.
        boolean existUser = systemService.existUser(name);
        if(existUser){
            systemService.deleteUser(name);
        }
        systemService.createUser("/home", name , name);
        systemService.changeUser(name, password);

        return sysuserRepository.insert(sysuser);
    }

    @Override
    public List<Sysuser> selectAll() {
        return sysuserRepository.selectAll();
    }

    @Override
    public List<Sysuser> select(int limit, Long skip) {
        return sysuserRepository.select(limit, skip);
    }

    @Override
    public Sysuser selectById(String id) {
        return sysuserRepository.selectById(id);
    }

    @Override
    public Sysuser cashById(String id) {
        Sysuser cashUser = null;
        List<Sysuser> cash = sysuserRepository.getCash();
        for (Sysuser sysuser : cash) {
            if (sysuser.get_id().equals(id)) {
                cashUser = sysuser;
            }
        }
        return cashUser;
    }

    @Override
    public List<Sysuser> selectLikeName(String name, int limit, Long skip) {
        return sysuserRepository.selectLikeName(name, limit, skip);
    }

    @Override
    public Sysuser selectByName(String name) {
        return sysuserRepository.selectByName(name);
    }

    @Override
    public Long count() {
        return sysuserRepository.count();
    }

    @Override
    public Long countLikeName(String name) {
        return sysuserRepository.countLikeName(name);
    }

    @Override
    public Sysuser updateById(Sysuser sysuser) {
        if ("Y".equals(sysuser.getDefaultUser())) {
            this.makeUnDefaultUser();
        }
        return sysuserRepository.updateById(sysuser);
    }

    @Override
    public Sysuser updateById(String id,
                              String password,
                              String defaultUser,
                              String description) {
        Sysuser sysuser = new Sysuser();
        sysuser.set_id(id);
        sysuser.setPassword(password);
        sysuser.setDefaultUser(defaultUser);
        sysuser.setDescription(description);

        if ("Y".equals(defaultUser)) {
            this.makeUnDefaultUser();
        }

        return sysuserRepository.updateById(sysuser);
    }

    @Override
    public void deleteById(String id) {
        sysuserRepository.deleteById(id);
    }
}
