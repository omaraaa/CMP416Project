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
public class OrdersPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PKID")
    private int pkid;
    @Basic(optional = false)
    @Column(name = "UID")
    private int uid;

    public OrdersPK() {
    }

    public OrdersPK(int pkid, int uid) {
        this.pkid = pkid;
        this.uid = uid;
    }

    public int getPkid() {
        return pkid;
    }

    public void setPkid(int pkid) {
        this.pkid = pkid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pkid;
        hash += (int) uid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersPK)) {
            return false;
        }
        OrdersPK other = (OrdersPK) object;
        if (this.pkid != other.pkid) {
            return false;
        }
        if (this.uid != other.uid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.OrdersPK[ pkid=" + pkid + ", uid=" + uid + " ]";
    }
    
}
