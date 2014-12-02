/*
 * File created by ashcrok
 *                 Mihai Pricop
 */
package models.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
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
import org.json.JSONArray;
import org.json.JSONObject;
import models.Menus;

/**
 *
 * @author ashcrok
 */
@Stateless
@Path("menus")
public class MenusFacadeREST extends AbstractFacade<Menus> {
    @PersistenceContext(unitName = "testsPU")
    private EntityManager em;

    public MenusFacadeREST() {
        super(Menus.class);
    }
    
    
    @GET
    @Path("/find/{sessionId}")
    @Produces({"application/json"})
    public String findAllMenus(@PathParam("sessionId") String sessionId) {
        
        String result = "";
        int type = -1;
        List<Menus> menus = super.findAll();
        
        
        try {
            URL url = new URL(tools.Config.serverUrl + "wr/users/loggedIn");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = (new JSONObject().put("session", sessionId)).toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                    result += output;
            }

            conn.disconnect();
            
            JSONObject loggedUserJson = new JSONObject(result);
            String loggedUserType = loggedUserJson.getJSONObject("user").getString("type");
            
            
            if (loggedUserType.equals("guest")) { type = 0; }
            else if (loggedUserType.equals("user")) { type = 1; }
            else if (loggedUserType.equals("admin")) { type = 2; }
            else { type = -1; }
            
        } catch (Exception e) {
            result += "ERROR";
        }
        
        try {
            JSONArray menusJson = new JSONArray();
            for (int i=0; i<menus.size(); i++) {
                if (Integer.parseInt(menus.get(i).getPermission()) <= type) {
                    JSONObject menu = new JSONObject(menus.get(i).toJSONString());
                    menusJson.put(menu);
                }
            }
            JSONObject resultJson = new JSONObject();
            resultJson.put("menus",menusJson);
            result = resultJson.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Menus entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Menus entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Menus find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Menus> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Menus> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
