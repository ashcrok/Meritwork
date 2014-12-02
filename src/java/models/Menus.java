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
@Table(name = "menus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menus.findAll", query = "SELECT m FROM Menus m"),
    @NamedQuery(name = "Menus.findById", query = "SELECT m FROM Menus m WHERE m.id = :id"),
    @NamedQuery(name = "Menus.findByTitle", query = "SELECT m FROM Menus m WHERE m.title = :title"),
    @NamedQuery(name = "Menus.findByGlyphicon", query = "SELECT m FROM Menus m WHERE m.glyphicon = :glyphicon"),
    @NamedQuery(name = "Menus.findByPath", query = "SELECT m FROM Menus m WHERE m.path = :path"),
    @NamedQuery(name = "Menus.findByPermission", query = "SELECT m FROM Menus m WHERE m.permission = :permission"),
    @NamedQuery(name = "Menus.findByCategory", query = "SELECT m FROM Menus m WHERE m.category = :category"),
    @NamedQuery(name = "Menus.findByCreatedAt", query = "SELECT m FROM Menus m WHERE m.createdAt = :createdAt"),
    @NamedQuery(name = "Menus.findByUpdatedAt", query = "SELECT m FROM Menus m WHERE m.updatedAt = :updatedAt")})
public class Menus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "glyphicon")
    private String glyphicon;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "path")
    private String path;
    @Size(max = 25)
    @Column(name = "permission")
    private String permission;
    @Size(max = 25)
    @Column(name = "category")
    private String category;
    @Column(name = "created_at")
    private Integer createdAt;
    @Column(name = "updated_at")
    private Integer updatedAt;

    public Menus() {
    }

    public Menus(Integer id) {
        this.id = id;
    }

    public Menus(Integer id, String title, String glyphicon, String path) {
        this.id = id;
        this.title = title;
        this.glyphicon = glyphicon;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGlyphicon() {
        return glyphicon;
    }

    public void setGlyphicon(String glyphicon) {
        this.glyphicon = glyphicon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        if (!(object instanceof Menus)) {
            return false;
        }
        Menus other = (Menus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tools.Menus[ id=" + id + " ]";
    }
    
    public String toJSONString() {
        JSONObject json = new JSONObject();
        try {
            json.put("id"           , this.getId());
            json.put("title"        , this.getTitle());
            json.put("glyphicon"    , this.getGlyphicon());
            json.put("path"         , this.getPath());
            json.put("permission"   , this.getPermission());
            json.put("category"     , this.getCategory());
            json.put("created_at"   , this.getCreatedAt());
            json.put("updated_at"   , this.getUpdatedAt()); 
        } catch (Exception e) { e.printStackTrace(); }
        return json.toString();
    }
    
}
