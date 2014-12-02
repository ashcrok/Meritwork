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
@Table(name = "occupations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Occupations.findAll", query = "SELECT o FROM Occupations o"),
    @NamedQuery(name = "Occupations.findByOccupation", query = "SELECT o FROM Occupations o WHERE o.occupation = :occupation"),
    @NamedQuery(name = "Occupations.findByIndustry", query = "SELECT o FROM Occupations o WHERE o.industry = :industry")})
public class Occupations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "occupation")
    private String occupation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "industry")
    private String industry;

    public Occupations() {
    }

    public Occupations(String occupation) {
        this.occupation = occupation;
    }

    public Occupations(String occupation, String industry) {
        this.occupation = occupation;
        this.industry = industry;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (occupation != null ? occupation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Occupations)) {
            return false;
        }
        Occupations other = (Occupations) object;
        if ((this.occupation == null && other.occupation != null) || (this.occupation != null && !this.occupation.equals(other.occupation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Occupations[ occupation=" + occupation + " ]";
    }
    
    public String toJSONString() {
        JSONObject json = new JSONObject();
        try {
            json.put("occupation"   , this.getOccupation());
            json.put("industry"     , this.getIndustry());
        } catch (Exception e) { e.printStackTrace(); }
        return json.toString();
    }
    
}
