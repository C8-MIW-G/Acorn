package nl.miw.se8.oak.acorn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

/**
 * Author: Thijs van Blanken
 * Created on: 27-6-2022
 */

@Entity
public class Privilege {

    @Id @GeneratedValue
    private Long id;

    private String name;
    
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(String name) {
        this.name = name;
    }
}
