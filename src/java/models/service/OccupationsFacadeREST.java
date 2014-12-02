/*
 * File created by ashcrok
 *                 Mihai Pricop
 */
package models.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import models.Occupations;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ashcrok
 */
@Stateless
@Path("occupations")
public class OccupationsFacadeREST extends AbstractFacade<Occupations> {
    @PersistenceContext(unitName = "testsPU")
    private EntityManager em;

    public OccupationsFacadeREST() {
        super(Occupations.class);
    }

    
    
    /* ADD NEW OCCUPATION */
    @POST
    @Path("/add")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String add(String data) {
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("occupation")) {
                result.put("success", "false");
                result.put("errorMessage", "Fields missing.");
            } else {
                
                Occupations occupation = new Occupations();
                occupation.setOccupation(dataJson.getString("occupation"));
                if (dataJson.has("industry")) {
                    occupation.setIndustry(dataJson.getString("industry"));
                }
                
                super.create(occupation);
                
                result.put("success","true");
            }
            
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught");
            } catch (Exception ex) {}
            System.out.println("ADD New Occupation Error Catched");
            e.printStackTrace();
        }
        
        return result.toString();
    }
    
    
    /* DELETE OCCUPATION */
    @POST
    @Path("/delete")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String delete(String data) {
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("occupation")) {
                result.put("success", "false");
                result.put("errorMessage", "Fields missing.");
            } else {
                
                Occupations occupation = super.find(dataJson.getString("occupation"));
                super.remove(occupation);
                
                result.put("success","true");
            }
            
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught");
            } catch (Exception ex) {}
            System.out.println("DELETE Occupation Error Catched");
            e.printStackTrace();
        }
        
        return result.toString();
    }
    
    
    /* FIND ALL OCCUPATIONS */
    @GET
    @Path("/find")
    @Produces({"application/json"})
    public String find() {
        JSONObject result = new JSONObject();
        
        try {
            List<Occupations> occupations = super.findAll();

            JSONArray occupationsJson = new JSONArray();
            for (Occupations occupation : occupations) {
                JSONObject occupationJson = new JSONObject(occupation.toJSONString());
                occupationsJson.put(occupationJson);
            }
            
            result.put("occupations", occupationsJson);
            
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught");
            } catch (Exception ex) {}
            System.out.println("FIND Occupation Error Catched");
            e.printStackTrace();
        }
        
        return result.toString();
    }
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
