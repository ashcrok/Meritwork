/*
 * File created by ashcrok
 *                 Mihai Pricop
 */
package models.service;

import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import models.Session;

/**
 *
 * @author ashcrok
 */
@Stateless
@Path("session")
public class SessionFacadeREST extends AbstractFacade<Session> {
    @PersistenceContext(unitName = "testsPU")
    private EntityManager em;

    public SessionFacadeREST() {
        super(Session.class);
    }
    
    @POST
    @Path("/create")
    @Consumes({"application/x-www-form-urlencoded","multipart/form-data"})
    @Produces({"multipart/form-data"})
    public String generate(String string) {
        int userId = Integer.parseInt(string.split("=")[1]);
        List<Session> sessions = super.findAll();
        for (int i=0; i<sessions.size(); i++) {
            if (sessions.get(i).getPayload() == userId) {
                return sessions.get(i).getId();
            }
        }
        String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        Session session = new Session();
        session.setId(sessionId);
        session.setPayload(userId);
        super.create(session);
        return sessionId;
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Session entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Session entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Session find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Session> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Session> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
