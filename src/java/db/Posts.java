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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    , @NamedQuery(name = "Posts.findByTpid", query = "SELECT p FROM Posts p WHERE p.tpid = :tpid")
    , @NamedQuery(name = "Posts.findByLpid", query = "SELECT p FROM Posts p WHERE p.lpid = :lpid")
    , @NamedQuery(name = "Posts.findByIpid", query = "SELECT p FROM Posts p WHERE p.ipid = :ipid")})
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
    @Column(name = "TPID")
    private Integer tpid;
    @Column(name = "LPID")
    private Integer lpid;
    @Column(name = "IPID")
    private Integer ipid;
    @JoinTable(name = "VIEWED", joinColumns = {
        @JoinColumn(name = "PID", referencedColumnName = "PID")}, inverseJoinColumns = {
        @JoinColumn(name = "UID", referencedColumnName = "UID")})
    @ManyToMany
    private Collection<Users> usersCollection;
    @JoinTable(name = "SBMTS", joinColumns = {
        @JoinColumn(name = "PID", referencedColumnName = "PID")}, inverseJoinColumns = {
        @JoinColumn(name = "SID", referencedColumnName = "SID")})
    @ManyToMany
    private Collection<Streams> streamsCollection;
    @OneToMany(mappedBy = "pid")
    private Collection<Imageposts> imagepostsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "posts")
    private Collection<Comments> commentsCollection;
    @OneToMany(mappedBy = "pid")
    private Collection<Textposts> textpostsCollection;
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

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public Integer getLpid() {
        return lpid;
    }

    public void setLpid(Integer lpid) {
        this.lpid = lpid;
    }

    public Integer getIpid() {
        return ipid;
    }

    public void setIpid(Integer ipid) {
        this.ipid = ipid;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @XmlTransient
    public Collection<Streams> getStreamsCollection() {
        return streamsCollection;
    }

    public void setStreamsCollection(Collection<Streams> streamsCollection) {
        this.streamsCollection = streamsCollection;
    }

    @XmlTransient
    public Collection<Imageposts> getImagepostsCollection() {
        return imagepostsCollection;
    }

    public void setImagepostsCollection(Collection<Imageposts> imagepostsCollection) {
        this.imagepostsCollection = imagepostsCollection;
    }

    @XmlTransient
    public Collection<Comments> getCommentsCollection() {
        return commentsCollection;
    }

    public void setCommentsCollection(Collection<Comments> commentsCollection) {
        this.commentsCollection = commentsCollection;
    }

    @XmlTransient
    public Collection<Textposts> getTextpostsCollection() {
        return textpostsCollection;
    }

    public void setTextpostsCollection(Collection<Textposts> textpostsCollection) {
        this.textpostsCollection = textpostsCollection;
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
