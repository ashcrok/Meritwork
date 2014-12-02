/*
 * File created by ashcrok
 *                 Mihai Pricop
 */
package models.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import models.Services;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ashcrok
 */
@Stateless
@Path("services")
public class ServicesFacadeREST extends AbstractFacade<Services> {
    @PersistenceContext(unitName = "testsPU")
    private EntityManager em;

    public ServicesFacadeREST() {
        super(Services.class);
    }

    
    /* ********************************************************************** */
    
    @POST
    @Path("/add")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String add(String data) {
        
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("service")    ||
                !dataJson.has("sessionId")  ||
                !dataJson.has("occupation") ||
                !dataJson.has("industry")) {
                    result.put("success", "false");
                    result.put("errorMessage", "Fields missing.");
            } else {
                
                /* START: GET USER ID AFTER SESSION ID */
                URL url = new URL(tools.Config.serverUrl + "wr/session/" + dataJson.getString("sessionId"));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                        + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String sessionCall = "";
                String output;
                while ((output = br.readLine()) != null) {
                    sessionCall += output;
                }

                JSONObject sessionCallJson = new JSONObject(sessionCall);
                int author = sessionCallJson.getInt("payload");
                /* END: FOUND USER ID AFTER SESSION ID */ 
                
                boolean duplicate = false;
                Query query = em.createNamedQuery("Services.findByAuthor");
                query.setParameter("author", author);
                List<Services> services = query.getResultList();
                
                for (Services service : services) {
                    if (service.getService().equals(dataJson.getString("service"))) {
                        duplicate = true;
                    }
                }
                
                if (duplicate) {
                    result.put("success", "false");
                    result.put("errorMessage", "Duplicated service.");
                } else {
                    Services service = new Services();
                    service.setAuthor(author);
                    service.setService(dataJson.getString("service"));
                    service.setOccupation(dataJson.getString("occupation"));
                    service.setIndustry(dataJson.getString("industry"));
                    if (dataJson.has("location")) {
                        service.setLocation(dataJson.getString("location"));
                    }
                    if (dataJson.has("contact")) {
                        service.setContact(dataJson.getString("contact"));
                    } else { service.setContact("[]"); }
                    if (dataJson.has("description")) {
                        service.setDescription(dataJson.getString("description"));
                    }
                    if (dataJson.has("template")) {
                        service.setTemplate(dataJson.getInt("template"));
                    }
                    int timestamp = (int) (System.currentTimeMillis() / 1000L);
                    service.setCreatedAt(timestamp);
                    service.setContactPrivacy(true);

                    super.create(service);

                    result.put("success", "true");
                }
            }
            
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught.");
            } catch (Exception ex) {}
            System.out.println("[ServiceFacadeRest] ADD Service Error Catched.");
            e.printStackTrace();
        }
        
        return result.toString();
    }
    
    
    
    @POST
    @Path("/find")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String find(String data) {
        
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("sessionId")) {
                    result.put("success", "false");
                    result.put("errorMessage", "Fields missing.");
            } else {
                
                /* START: GET USER ID AFTER SESSION ID */
                URL url = new URL(tools.Config.serverUrl + "wr/session/" + dataJson.getString("sessionId"));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                        + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String sessionCall = "";
                String output;
                while ((output = br.readLine()) != null) {
                    sessionCall += output;
                }

                JSONObject sessionCallJson = new JSONObject(sessionCall);
                int author = sessionCallJson.getInt("payload");
                /* END: FOUND USER ID AFTER SESSION ID */ 
                                
                Query query = em.createNamedQuery("Services.findByAuthor");
                query.setParameter("author", author);
                List<Services> services = query.getResultList();
                
                JSONArray servicesJson = new JSONArray();
                for (Services service : services) {
                    JSONObject serviceJson = new JSONObject(service.toJSONString());
                    servicesJson.put(serviceJson);
                }
                
                result.put("services",servicesJson);
            }
            
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught.");
            } catch (Exception ex) {}
            System.out.println("[ServiceFacadeRest] FIND Service Error Catched.");
            e.printStackTrace();
        }
        
        return result.toString();
    }
    
    
    
    @POST
    @Path("/update")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String update(String data) {
        
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("sessionId") ||
                !dataJson.has("service")) {
                    result.put("success", "false");
                    result.put("errorMessage", "Fields missing.");
            } else {
                
                /* START: GET USER ID AFTER SESSION ID */
                URL url = new URL(tools.Config.serverUrl + "wr/session/" + dataJson.getString("sessionId"));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                        + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String sessionCall = "";
                String output;
                while ((output = br.readLine()) != null) {
                    sessionCall += output;
                }

                JSONObject sessionCallJson = new JSONObject(sessionCall);
                int author = sessionCallJson.getInt("payload");
                /* END: FOUND USER ID AFTER SESSION ID */ 
                
                Query query = em.createNamedQuery("Services.findByAuthor");
                query.setParameter("author", author);
                List<Services> services = query.getResultList();
                
                for (Services service : services) {
                    if (service.getService().equals(dataJson.getString("service"))) {
                        if (dataJson.has("occupation"))     { service.setOccupation(dataJson.getString("occupation")); }
                        if (dataJson.has("industry"))       { service.setIndustry(dataJson.getString("industry")); }
                        if (dataJson.has("location"))       { service.setLocation(dataJson.getString("location")); }
                        if (dataJson.has("contact"))        { service.setContact(dataJson.getString("contact")); }
                        if (dataJson.has("contact_privacy")){ service.setContactPrivacy(dataJson.getBoolean("contact_privacy")); }
                        if (dataJson.has("description"))    { service.setDescription(dataJson.getString("description")); }
                        if (dataJson.has("tempalte"))       { service.setTemplate(dataJson.getInt("template")); }
                        super.edit(service);
                    }
                }
                
                result.put("success", "true");
            }
            
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught.");
            } catch (Exception ex) {}
            System.out.println("[ServiceFacadeRest] UPDATE Service Error Catched.");
            e.printStackTrace();
        }
        
        return result.toString();
    }
    
    
    
    @POST
    @Path("/delete")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String delete(String data) {
        
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("sessionId") ||
                !dataJson.has("service")) {
                    result.put("success", "false");
                    result.put("errorMessage", "Fields missing.");
            } else {
                
                /* START: GET USER ID AFTER SESSION ID */
                URL url = new URL(tools.Config.serverUrl + "wr/session/" + dataJson.getString("sessionId"));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                        + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String sessionCall = "";
                String output;
                while ((output = br.readLine()) != null) {
                    sessionCall += output;
                }

                JSONObject sessionCallJson = new JSONObject(sessionCall);
                int author = sessionCallJson.getInt("payload");
                /* END: FOUND USER ID AFTER SESSION ID */ 
                
                Query query = em.createNamedQuery("Services.findByAuthor");
                query.setParameter("author", author);
                List<Services> services = query.getResultList();
                
                for (Services service : services) {
                    if (service.getService().equals(dataJson.getString("service"))) {
                        super.remove(service);
                    }
                }
                
                result.put("success", "true");
            }
            
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught.");
            } catch (Exception ex) {}
            System.out.println("[ServiceFacadeRest] DELETE Service Error Catched.");
            e.printStackTrace();
        }
        
        return result.toString();
    }
    
    
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
