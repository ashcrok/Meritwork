/*
 * File created by ashcrok
 *                 Mihai Pricop
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;

/**
 *
 * @author ashcrok
 */
@Entity
@Table(name = "services")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Services.findAll", query = "SELECT s FROM Services s"),
    @NamedQuery(name = "Services.findById", query = "SELECT s FROM Services s WHERE s.id = :id"),
    @NamedQuery(name = "Services.findByAuthor", query = "SELECT s FROM Services s WHERE s.author = :author"),
    @NamedQuery(name = "Services.findByService", query = "SELECT s FROM Services s WHERE s.service = :service"),
    @NamedQuery(name = "Services.findByOccupation", query = "SELECT s FROM Services s WHERE s.occupation = :occupation"),
    @NamedQuery(name = "Services.findByIndustry", query = "SELECT s FROM Services s WHERE s.industry = :industry"),
    @NamedQuery(name = "Services.findByLocation", query = "SELECT s FROM Services s WHERE s.location = :location"),
    @NamedQuery(name = "Services.findByContact", query = "SELECT s FROM Services s WHERE s.contact = :contact"),
    @NamedQuery(name = "Services.findByContactPrivacy", query = "SELECT s FROM Services s WHERE s.contactPrivacy = :contactPrivacy"),
    @NamedQuery(name = "Services.findByDescription", query = "SELECT s FROM Services s WHERE s.description = :description"),
    @NamedQuery(name = "Services.findByTemplate", query = "SELECT s FROM Services s WHERE s.template = :template"),
    @NamedQuery(name = "Services.findByCreatedAt", query = "SELECT s FROM Services s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Services.findByUpdatedAt", query = "SELECT s FROM Services s WHERE s.updatedAt = :updatedAt")})
public class Services implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "author")
    private int author;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "service")
    private String service;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "occupation")
    private String occupation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "industry")
    private String industry;
    @Size(max = 100)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contact_privacy")
    private boolean contactPrivacy;
    @Size(max = 1024)
    @Column(name = "description")
    private String description;
    @Column(name = "template")
    private Integer template;
    @Column(name = "created_at")
    private Integer createdAt;
    @Column(name = "updated_at")
    private Integer updatedAt;

    public Services() {
    }

    public Services(Integer id) {
        this.id = id;
    }

    public Services(Integer id, int author, String service, String occupation, String industry, String contact, boolean contactPrivacy) {
        this.id = id;
        this.author = author;
        this.service = service;
        this.occupation = occupation;
        this.industry = industry;
        this.contact = contact;
        this.contactPrivacy = contactPrivacy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean getContactPrivacy() {
        return contactPrivacy;
    }

    public void setContactPrivacy(boolean contactPrivacy) {
        this.contactPrivacy = contactPrivacy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTemplate() {
        return template;
    }

    public void setTemplate(Integer template) {
        this.template = template;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Services)) {
            return false;
        }
        Services other = (Services) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Services[ id=" + id + " ]";
    }
    
    public String toJSONString() {
        JSONObject json = new JSONObject();
        try {
            json.put("author"           , this.getAuthor());
            json.put("service"          , this.getService());
            json.put("occupation"       , this.getOccupation());
            json.put("industry"         , this.getIndustry());
            json.put("location"         , this.getLocation());
            json.put("contact"          , this.getContact());
            json.put("contact_privacy"  , this.getContactPrivacy());
            json.put("description"      , this.getDescription());
            json.put("template"         , this.getTemplate());
            json.put("created_at"       , this.getCreatedAt());
            json.put("updated_at"       , this.getUpdatedAt());
        } catch (Exception e) { e.printStackTrace(); }
        
        return json.toString();
    }
    
}
