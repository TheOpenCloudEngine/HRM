package org.opencloudengine.garuda.model;

import java.util.List;

/**
 * Created by uengine on 2016. 8. 30..
 */
public class HdfsListStatus {

    List<HdfsFileStatus> fileInfoList;

    int count;

    public List<HdfsFileStatus> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<HdfsFileStatus> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "HdfsListStatus{" +
                "fileInfoList=" + fileInfoList +
                ", count=" + count +
                '}';
    }
}
