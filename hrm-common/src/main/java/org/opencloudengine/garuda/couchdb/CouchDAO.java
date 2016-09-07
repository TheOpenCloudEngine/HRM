package org.opencloudengine.garuda.couchdb;

import java.io.Serializable;

/**
 * Created by uengine on 2016. 4. 28..
 */
public class CouchDAO implements Serializable {
    /**
     * CouchDB 에서 소트를 실행하는법.
     *
     * 1. ["shell","console",{}]
     *
     * 2. ["shell","console"]
     *
     * 1번은 다음과 같이 표현도 가능하다 : ["shell","console","{}"]
     *
     * 1:startkey 2:endkey 일 경우
     * Key.ComplexKey startKey = new Key().complex(clientJobType).add(executeFrom).add("{}");
     * Key.ComplexKey endKey = new Key().complex(clientJobType).add(executeFrom);
     * ......
     * limit(limit).skip(skip).descending(true).
     * startKey(startKey).endKey(endKey).....
     *
     * 2:startkey 1:endkey 일 경우
     * descending 옵션을 false 로 한다.
     *
     */

    private String _id;
    private String _rev;
    private String docType;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}
