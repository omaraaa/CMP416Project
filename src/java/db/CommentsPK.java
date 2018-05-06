/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author User
 */
@Embeddable
public class CommentsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PID")
    private int pid;
    @Basic(optional = false)
    @Column(name = "COID")
    private int coid;

    public CommentsPK() {
    }

    public CommentsPK(int pid, int coid) {
        this.pid = pid;
        this.coid = coid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCoid() {
        return coid;
    }

    public void setCoid(int coid) {
        this.coid = coid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pid;
        hash += (int) coid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentsPK)) {
            return false;
        }
        CommentsPK other = (CommentsPK) object;
        if (this.pid != other.pid) {
            return false;
        }
        if (this.coid != other.coid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.CommentsPK[ pid=" + pid + ", coid=" + coid + " ]";
    }
    
}
