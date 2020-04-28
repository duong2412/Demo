package travel.service;

import travel.entity.Role;

import java.util.List;

public interface RoleService {
    Role findOne(long id);

    List<Role> findAll();
}
