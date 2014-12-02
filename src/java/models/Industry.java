/*
 * File created by ashcrok
 *                 Mihai Pricop
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "industry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Industry.findAll", query = "SELECT i FROM Industry i"),
    @NamedQuery(name = "Industry.findByIndustry", query = "SELECT i FROM Industry i WHERE i.industry = :industry"),
    @NamedQuery(name = "Industry.findByTemplate", query = "SELECT i FROM Industry i WHERE i.template = :template")})
public class Industry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "industry")
    private String industry;
    @Size(max = 1024)
    @Column(name = "template")
    private String template;

    public Industry() {
    }

    public Industry(String industry) {
        this.industry = industry;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (industry != null ? industry.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Industry)) {
            return false;
        }
        Industry other = (Industry) object;
        if ((this.industry == null && other.industry != null) || (this.industry != null && !this.industry.equals(other.industry))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Industry[ industry=" + industry + " ]";
    }
    
    public String toJSONString() {
        JSONObject json = new JSONObject();
        try {
            json.put("industry" , this.getIndustry());
            json.put("template" , this.getTemplate());
        } catch (Exception e) { e.printStackTrace(); }
        return json.toString();
    }
    
}
