/*
 * File created by ashcrok
 *                 Mihai Pricop
 */
package models.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author ashcrok
 */
@javax.ws.rs.ApplicationPath("wr")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(models.service.IndustryFacadeREST.class);
        resources.add(models.service.MenusFacadeREST.class);
        resources.add(models.service.OccupationsFacadeREST.class);
        resources.add(models.service.ServicesFacadeREST.class);
        resources.add(models.service.SessionFacadeREST.class);
        resources.add(models.service.UsersFacadeREST.class);
    }
    
}
