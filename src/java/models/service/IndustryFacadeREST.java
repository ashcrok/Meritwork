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
import models.Industry;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ashcrok
 */
@Stateless
@Path("industry")
public class IndustryFacadeREST extends AbstractFacade<Industry> {
    @PersistenceContext(unitName = "testsPU")
    private EntityManager em;

    public IndustryFacadeREST() {
        super(Industry.class);
    }
    
    

    /* ADD NEW INDUSTRY CLASSIFICATION */
    @POST
    @Path("/add")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String add(String data) {
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("industry")) {
                result.put("success", "false");
                result.put("errorMessage", "Fields missing.");
            } else {
                
                Industry industry = new Industry();
                industry.setIndustry(dataJson.getString("industry"));
                if (dataJson.has("template")) {
                    industry.setTemplate(dataJson.getString("template"));
                }
                
                super.create(industry);
                
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
    
    
    /* DELETE A INDUSTRY CLASSIFICATION */
    @POST
    @Path("/delete")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String delete(String data) {
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("industry")) {
                result.put("success", "false");
                result.put("errorMessage", "Fields missing.");
            } else {
                
                Industry industry = super.find(dataJson.getString("industry"));
                super.remove(industry);
                
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
    
    
    /* FIND ALL INDUSTRY CLASSIFICATIONS */
    @GET
    @Path("/find")
    @Produces({"application/json"})
    public String find() {
        JSONObject result = new JSONObject();
        
        try {
            List<Industry> industries = super.findAll();

            JSONArray industriesJson = new JSONArray();
            for (Industry industry : industries) {
                JSONObject industryJson = new JSONObject(industry.toJSONString());
                industriesJson.put(industryJson);
            }
            
            result.put("industries", industriesJson);
            
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
