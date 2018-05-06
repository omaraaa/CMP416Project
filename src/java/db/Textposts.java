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
@Table(name = "TEXTPOSTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Textposts.findAll", query = "SELECT t FROM Textposts t")
    , @NamedQuery(name = "Textposts.findByTpid", query = "SELECT t FROM Textposts t WHERE t.tpid = :tpid")
    , @NamedQuery(name = "Textposts.findByText", query = "SELECT t FROM Textposts t WHERE t.text = :text")})
public class Textposts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TPID")
    private Integer tpid;
    @Column(name = "TEXT")
    private String text;
    @JoinColumn(name = "PID", referencedColumnName = "PID")
    @ManyToOne
    private Posts pid;

    public Textposts() {
    }

    public Textposts(Integer tpid) {
        this.tpid = tpid;
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        hash += (tpid != null ? tpid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Textposts)) {
            return false;
        }
        Textposts other = (Textposts) object;
        if ((this.tpid == null && other.tpid != null) || (this.tpid != null && !this.tpid.equals(other.tpid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Textposts[ tpid=" + tpid + " ]";
    }
    
}
