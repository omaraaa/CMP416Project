/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "IMAGEPOSTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imageposts.findAll", query = "SELECT i FROM Imageposts i")
    , @NamedQuery(name = "Imageposts.findByIpid", query = "SELECT i FROM Imageposts i WHERE i.ipid = :ipid")})
public class Imageposts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IPID")
    private Integer ipid;
    @Lob
    @Column(name = "IMAGE")
    private Serializable image;
    @JoinColumn(name = "PID", referencedColumnName = "PID")
    @ManyToOne
    private Posts pid;

    public Imageposts() {
    }

    public Imageposts(Integer ipid) {
        this.ipid = ipid;
    }

    public Integer getIpid() {
        return ipid;
    }

    public void setIpid(Integer ipid) {
        this.ipid = ipid;
    }

    public Serializable getImage() {
        return image;
    }

    public void setImage(Serializable image) {
        this.image = image;
    }

    public Posts getPid() {
        return pid;
    }

    public void setPid(Posts pid) {
        this.pid = pid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipid != null ? ipid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imageposts)) {
            return false;
        }
        Imageposts other = (Imageposts) object;
        if ((this.ipid == null && other.ipid != null) || (this.ipid != null && !this.ipid.equals(other.ipid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Imageposts[ ipid=" + ipid + " ]";
    }
    
}
