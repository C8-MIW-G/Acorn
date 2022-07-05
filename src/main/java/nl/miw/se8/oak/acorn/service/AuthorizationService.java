package nl.miw.se8.oak.acorn.service;

public interface AuthorizationService {
    boolean userCanAccessPantry(Long pantryId);
}
