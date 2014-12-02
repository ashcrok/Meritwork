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
import java.net.URLDecoder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import models.Users;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ashcrok
 */
@Stateless
@Path("users")
public class UsersFacadeREST extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "testsPU")
    private EntityManager em;

    public UsersFacadeREST() {
        super(Users.class);
    }

    @POST
    @Path("/add")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String addNewUser(String data) {
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("password") ||
                !dataJson.has("email") ||
                !dataJson.has("firstName") ||
                !dataJson.has("lastName")) {
                    result.put("success", "false");
                    result.put("errorMessage", "Fields missing.");
            } else {
                
                int timestamp = (int) (System.currentTimeMillis() / 1000L);
                Users user = new Users();
                user.setPassword(dataJson.getString("password"));
                user.setEmail(dataJson.getString("email"));
                user.setFirstName(dataJson.getString("firstName"));
                user.setLastName(dataJson.getString("lastName"));
                user.setAvatar("https://en.opensuse.org/images/0/0b/Icon-user.png");
                
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "primary email");
                jsonObject.put("content", dataJson.getString("email"));
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject);
                user.setContact(jsonArray.toString());
                
                user.setCreatedAt(timestamp);
                user.setType("user");
                super.create(user);
                
                result.put("success", "true");
            }
            
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught.");
            } catch (Exception ex) {}
            System.out.println("ADD New User Error Catched.");
            e.printStackTrace();
        }
        
        return result.toString(); 
    }
    
    
    
    @POST
    @Path("/login")
    @Consumes({"multipart/form-data", "application/x-www-form-urlencoded"})
    @Produces({"application/json"})
    public String login(String data) {
        String result = "";
        
        String[] parameters = data.split("&");
        
        String email        = null;
        String password     = null;
        
        for (String parameter : parameters) {
            if (parameter.split("=")[0].equals("email")) {
                try {
                    email = URLDecoder.decode(parameter.split("=")[1], "UTF-8");
                }catch(Exception e) { result += "UTF error firstName"; }
            }
            if (parameter.split("=")[0].equals("password")) {
                try {
                    password = URLDecoder.decode(parameter.split("=")[1], "UTF-8");
                }catch(Exception e) { result += "UTF error firstName"; }
            }
        }
        
        if (email != null && password != null) {
            List<Users> users = super.findAll();
            int userId = 0;
            boolean success = false;
            for (Users user : users) {
                if (!(user.getEmail() == null)) {
                    if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        success = true;
                        userId = user.getId();
                    }
                }
            }
            if (success) {
                
                try {
                    URL url = new URL(tools.Config.serverUrl + "wr/session/create");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "multipart/form-data");

                    String input = "id=" + userId;

                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

//                    if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//                            throw new RuntimeException("Failed : HTTP error code : "
//                                    + conn.getResponseCode());
//                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                                    (conn.getInputStream())));

                    String output;
                    while ((output = br.readLine()) != null) {
                            result += output;
                    }

                    conn.disconnect();
                    
                } catch (Exception e) {
                    result += "ERROR";
                }
                
            } else {
                result += "false";
            }
        } else {
            result += "false";
        }
        
        return result; 
    }
    
    
    @GET
    @Path("/guestLogin")
    @Produces({"application/json"})
    public String guestLogin() {
        JSONObject result = new JSONObject();
        String response = "";
        
        int userId = 0;
        
        Users user = this.find(userId);
        
        try {
            URL url = new URL(tools.Config.serverUrl + "wr/session/create");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data");

            String input = "id=" + userId;

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                response += output;
            }

            conn.disconnect();

        } catch (Exception e) {
            response += "ERROR";
        }
        
        try {
            JSONObject loggedUser = new JSONObject();
            loggedUser.put("success", "true");
            loggedUser.put("user", new JSONObject(user.toJSONString()));
            
            result.put("session", response);
            result.put("loggedUser", loggedUser);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return result.toString();
    }
    
    
    @Path("/changePassword")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String changePassword(String data) {
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("sessionId") ||
                !dataJson.has("currentPassword") ||
                !dataJson.has("newPassword")) {
                    result.put("success", "false");
                    result.put("errorMessage", "Fields missing.");
            } else {
                
                /* START: GET USER AFTER SESSION ID */
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
                int userId = sessionCallJson.getInt("payload");

                Users user = this.find(userId);
                /* END: FOUND USER AFTER SESSION ID */ 
                
                String userPassword = user.getPassword();
                
                if (!userPassword.equals(dataJson.getString("currentPassword"))) {
                    result.put("success", "false");
                    result.put("errorMessage", "Current password wrong.");
                } else {
                    user.setPassword(dataJson.getString("newPassword"));
                    this.edit(user);
                    result.put("success", "true");
                }
                
            }
            
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught.");
            } catch (Exception ex) { ex.printStackTrace(); }
            System.out.println("Change Password Error Catched.");
            e.printStackTrace();
        }
        
        return result.toString();
    }
    
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String update(String data) {
        JSONObject result = new JSONObject();
        
        try {
            JSONObject dataJson = new JSONObject(data);
            
            if (!dataJson.has("sessionId") ||
                !dataJson.has("user")) {
                    result.put("success", "false");
                    result.put("errorMessage", "Fields missing.");
            } else {
                
                /* START: GET USER AFTER SESSION ID */
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
                int userId = sessionCallJson.getInt("payload");

                Users user = this.find(userId);
                /* END: FOUND USER AFTER SESSION ID */ 
                
                
                JSONObject new_user = dataJson.getJSONObject("user");
                if (user.getContact() == null || !user.getContact().equals(new_user.getJSONArray("contact").toString())) {
                    user.setContact(new_user.get("contact").toString());
                    result.put("contact", "changed");
                } 
                if (user.getAvatar() == null || !user.getAvatar().equals(new_user.getString("avatar"))) {
                    user.setAvatar(new_user.getString("avatar"));
                    result.put("avatar", "changed");
                }
                if (new_user.has("location") && (user.getLocation() == null || !user.getLocation().equals(new_user.getString("location")))) {
                    user.setLocation(new_user.getString("location"));
                    result.put("location", "changed");
                }
                if (user.getPrivacy() != new_user.getInt("privacy")) {
                    user.setPrivacy(new_user.getInt("privacy"));
                    result.put("privacy", "changed");
                }
                
                super.edit(user);
                
                result.put("success", "true");
                
            }
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("errorMessage", "Exception caught.");
            } catch (Exception ex) { ex.printStackTrace(); }
            System.out.println("Update User Error Catched.");
            e.printStackTrace();
        }
        
        return result.toString();
    }
    
    
    
    @POST
    @Path("/loggedIn")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String loggedIn(String data) {
        JSONObject result = new JSONObject();
        
        if (data == null || data.equals("")) {
            try {
                result.put("success", "false");
                result.put("error"  , "Cookie doesn't exist");
            } catch (Exception ex) {}
            return result.toString();
        }
        
        try {
            String sessionId;
            try {
                sessionId = data.split("=")[1];
            } catch(Exception e) {
                sessionId = (new JSONObject(data).getString("session"));
            }
            
            URL url = new URL(tools.Config.serverUrl + "wr/session/" + sessionId);
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
            int userId = sessionCallJson.getInt("payload");
            
            Users user = this.find(userId);
            
            result.put("success", "true");
            result.put("user", new JSONObject(user.toJSONString()));

            conn.disconnect();
        } catch (Exception e) {
            try {
                result.put("success", "false");
                result.put("error"  , "Exception caught");
            } catch (Exception ex) {}
        }
        
        return result.toString();
    }

    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
