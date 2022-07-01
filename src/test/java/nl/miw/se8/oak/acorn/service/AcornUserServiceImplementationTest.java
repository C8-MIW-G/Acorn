package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AcornUserServiceImplementationTest {

    // TODO - Ask Joost about Mockito testing!

    @Test
    void OnlyOneSysAdmin() {
        Role adminRole = new Role("ROLE_ADMIN");
        AcornUser admin = new AcornUser();
        admin.setRoles(List.of(adminRole));

        RoleService roleService = Mockito.mock(RoleService.class);
        Mockito.when(roleService.findByName(Mockito.anyString())).thenReturn(Optional.of(adminRole));

        AcornUserService acornUserService = Mockito.mock(AcornUserService.class);
        Mockito.when(acornUserService.findByRolesContains(Mockito.any())).thenReturn(List.of(admin));

        assertFalse(acornUserService.moreThanOneSysAdmin());
    }

    @Test
    void TwoSysAdmins() {
        Role adminRole = new Role("ROLE_ADMIN");
        AcornUser admin = new AcornUser();
        AcornUser admin2 = new AcornUser();
        admin.setRoles(List.of(adminRole));
        admin2.setRoles(List.of(adminRole));

        RoleService roleService = Mockito.mock(RoleService.class);
        Mockito.when(roleService.findByName(Mockito.anyString())).thenReturn(Optional.of(adminRole));

        AcornUserService acornUserService = Mockito.mock(AcornUserService.class);
        Mockito.when(acornUserService.findByRolesContains(Mockito.any())).thenReturn(List.of(admin, admin2));

        assertTrue(acornUserService.moreThanOneSysAdmin());
    }
}