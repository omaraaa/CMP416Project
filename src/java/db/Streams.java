/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "STREAMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Streams.findAll", query = "SELECT s FROM Streams s")
    , @NamedQuery(name = "Streams.findBySid", query = "SELECT s FROM Streams s WHERE s.sid = :sid")
    , @NamedQuery(name = "Streams.findBySname", query = "SELECT s FROM Streams s WHERE s.sname = :sname")
    , @NamedQuery(name = "Streams.findBySdesc", query = "SELECT s FROM Streams s WHERE s.sdesc = :sdesc")})
public class Streams implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SID")
    private Integer sid;
    @Column(name = "SNAME")
    private String sname;
    @Column(name = "SDESC")
    private String sdesc;
    @JoinTable(name = "SUBS", joinColumns = {
        @JoinColumn(name = "SID", referencedColumnName = "SID")}, inverseJoinColumns = {
        @JoinColumn(name = "UID", referencedColumnName = "UID")})
    @ManyToMany
    private Collection<Users> usersCollection;
    @JoinTable(name = "MODS", joinColumns = {
        @JoinColumn(name = "SID", referencedColumnName = "SID")}, inverseJoinColumns = {
        @JoinColumn(name = "UID", referencedColumnName = "UID")})
    @ManyToMany
    private Collection<Users> usersCollection1;
    @ManyToMany(mappedBy = "streamsCollection")
    private Collection<Posts> postsCollection;

    public Streams() {
    }

    public Streams(Integer sid) {
        this.sid = sid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSdesc() {
        return sdesc;
    }

    public void setSdesc(String sdesc) {
        this.sdesc = sdesc;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection1() {
        return usersCollection1;
    }

    public void setUsersCollection1(Collection<Users> usersCollection1) {
        this.usersCollection1 = usersCollection1;
    }

    @XmlTransient
    public Collection<Posts> getPostsCollection() {
        return postsCollection;
    }

    public void setPostsCollection(Collection<Posts> postsCollection) {
        this.postsCollection = postsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Streams)) {
            return false;
        }
        Streams other = (Streams) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Streams[ sid=" + sid + " ]";
    }
    
}
