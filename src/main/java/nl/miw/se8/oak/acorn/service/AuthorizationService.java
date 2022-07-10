package nl.miw.se8.oak.acorn.service;

public interface AuthorizationService {
    boolean userCanAccessPantry(Long pantryId);
    boolean userCanEditPantry(Long pantryId);
    boolean currentUserIsAdminOfPantry(Long pantryId);
}
