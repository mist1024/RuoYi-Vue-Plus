package com.go.view.admin.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author fc
 * @since 2022-12-22
 */
@TableName("go_view_file")
public class GoviewFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String fileName;

    private Integer fileSize;

    private String fileSuffix;

    /**
     * 虚拟路径
     */
    private String virtualKey;

    /**
     * 相对路径
     */
    private String relativePath;

    /**
     * 绝对路径
     */
    private String absolutePath;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }
    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

	public String getVirtualKey() {
		return virtualKey;
	}

	public void setVirtualKey(String virtualKey) {
		this.virtualKey = virtualKey;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}





}
