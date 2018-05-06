/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "COMMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comments.findAll", query = "SELECT c FROM Comments c")
    , @NamedQuery(name = "Comments.findByPid", query = "SELECT c FROM Comments c WHERE c.commentsPK.pid = :pid")
    , @NamedQuery(name = "Comments.findByCoid", query = "SELECT c FROM Comments c WHERE c.commentsPK.coid = :coid")
    , @NamedQuery(name = "Comments.findByCdate", query = "SELECT c FROM Comments c WHERE c.cdate = :cdate")
    , @NamedQuery(name = "Comments.findByPcomment", query = "SELECT c FROM Comments c WHERE c.pcomment = :pcomment")})
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CommentsPK commentsPK;
    @Column(name = "CDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cdate;
    @Column(name = "PCOMMENT")
    private Integer pcomment;
    @JoinColumn(name = "PID", referencedColumnName = "PID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Posts posts;
    @JoinColumn(name = "UID", referencedColumnName = "UID")
    @ManyToOne
    private Users uid;

    public Comments() {
    }

    public Comments(CommentsPK commentsPK) {
        this.commentsPK = commentsPK;
    }

    public Comments(int pid, int coid) {
        this.commentsPK = new CommentsPK(pid, coid);
    }

    public CommentsPK getCommentsPK() {
        return commentsPK;
    }

    public void setCommentsPK(CommentsPK commentsPK) {
        this.commentsPK = commentsPK;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Integer getPcomment() {
        return pcomment;
    }

    public void setPcomment(Integer pcomment) {
        this.pcomment = pcomment;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
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
        hash += (commentsPK != null ? commentsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comments)) {
            return false;
        }
        Comments other = (Comments) object;
        if ((this.commentsPK == null && other.commentsPK != null) || (this.commentsPK != null && !this.commentsPK.equals(other.commentsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Comments[ commentsPK=" + commentsPK + " ]";
    }
    
}
