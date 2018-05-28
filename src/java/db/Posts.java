/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "POSTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Posts.findAll", query = "SELECT p FROM Posts p")
    , @NamedQuery(name = "Posts.findByPid", query = "SELECT p FROM Posts p WHERE p.pid = :pid")
    , @NamedQuery(name = "Posts.findByTitle", query = "SELECT p FROM Posts p WHERE p.title = :title")
    , @NamedQuery(name = "Posts.findByCreatetiondate", query = "SELECT p FROM Posts p WHERE p.createtiondate = :createtiondate")
    , @NamedQuery(name = "Posts.findByTxt", query = "SELECT p FROM Posts p WHERE p.txt = :txt")
    , @NamedQuery(name = "Posts.findByLnk", query = "SELECT p FROM Posts p WHERE p.lnk = :lnk")
    , @NamedQuery(name = "Posts.findByRnd", query = "SELECT p FROM Posts p WHERE p.rnd = :rnd")})
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PID")
    private Integer pid;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "CREATETIONDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtiondate;
    @Column(name = "TXT")
    private String txt;
    @Column(name = "LNK")
    private String lnk;
    @Lob
    @Column(name = "IMAGE")
    private Serializable image;
    @Column(name = "RND")
    private Integer rnd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "posts")
    private Collection<Comments> commentsCollection;
    @JoinColumn(name = "UID", referencedColumnName = "UID")
    @ManyToOne
    private Users uid;

    public Posts() {
    }

    public Posts(Integer pid) {
        this.pid = pid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatetiondate() {
        return createtiondate;
    }

    public void setCreatetiondate(Date createtiondate) {
        this.createtiondate = createtiondate;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getLnk() {
        return lnk;
    }

    public void setLnk(String lnk) {
        this.lnk = lnk;
    }

    public Serializable getImage() {
        return image;
    }

    public void setImage(Serializable image) {
        this.image = image;
    }

    public Integer getRnd() {
        return rnd;
    }

    public void setRnd(Integer rnd) {
        this.rnd = rnd;
    }

    @XmlTransient
    public Collection<Comments> getCommentsCollection() {
        return commentsCollection;
    }

    public void setCommentsCollection(Collection<Comments> commentsCollection) {
        this.commentsCollection = commentsCollection;
    }

    public Users getUid() {
        return uid;
    }

    public void setUid(Users uid) {
        this.uid = uid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pid != null ? pid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Posts)) {
            return false;
        }
        Posts other = (Posts) object;
        if ((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Posts[ pid=" + pid + " ]";
    }
    
}
