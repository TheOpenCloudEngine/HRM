package org.opencloudengine.garuda.model;

import java.util.List;

/**
 * Created by uengine on 2016. 8. 30..
 */
public class HdfsListInfo {

    List<HdfsFileInfo> fileInfoList;

    int count;

    public List<HdfsFileInfo> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<HdfsFileInfo> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
