package org.opencloudengine.garuda.web.eco.sysuser;

import org.apache.hadoop.fs.FileSystem;
import org.opencloudengine.garuda.backend.system.SystemService;
import org.opencloudengine.garuda.util.StringUtils;
import org.opencloudengine.garuda.web.configuration.ConfigurationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.opencloudengine.garuda.model.Sysuser;

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

        Sysuser insert = sysuserRepository.insert(sysuser);
        this.createUser(insert);
        return insert;
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

        Sysuser updateById = sysuserRepository.updateById(sysuser);
        this.updateById(updateById);
        return updateById;
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

        Sysuser updateById = sysuserRepository.updateById(sysuser);
        this.updateById(updateById);
        return updateById;
    }

    @Override
    public void deleteById(String id) {
        Sysuser sysuser = sysuserRepository.selectById(id);
        this.deleteUser(sysuser);
        sysuserRepository.deleteById(id);
    }

    /**
     * 시스템에 유저를 생성하고 하둡 시스템에 유저 디렉토리를 생성한다.
     *
     * @param sysuser
     */
    private void createUser(Sysuser sysuser) {
        String name = sysuser.getName();
        String password = sysuser.getPassword();

        //사용자가 있다면 삭제한다.
        boolean existUser = systemService.existUser(name);
        if (existUser) {
            systemService.deleteUser(name);
        }
        systemService.createUser("/home", name, name);
        systemService.changeUser(name, password);


    }

    /**
     * 시스템의 유저의 비밀번호를 업데이트한다.
     *
     * @param sysuser
     */
    private void updateUser(Sysuser sysuser) {
        String name = sysuser.getName();
        String password = sysuser.getPassword();

        //사용자가 없다면 생성한다.
        boolean existUser = systemService.existUser(name);
        if (!existUser) {
            systemService.createUser("/home", name, name);
        }
        systemService.changeUser(name, password);
    }

    /**
     * 시스템의 유저를 삭제하고, 하둡 시스템의 유저 디렉토리를 삭제한다.
     *
     * @param sysuser
     */
    private void deleteUser(Sysuser sysuser) {
        String name = sysuser.getName();

        //사용자가 있다면 삭제한다.
        boolean existUser = systemService.existUser(name);
        if (existUser) {
            systemService.deleteUser(name);
        }
    }
}
