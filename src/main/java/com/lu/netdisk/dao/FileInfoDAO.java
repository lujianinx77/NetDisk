package com.lu.netdisk.dao;

import java.util.List;

import com.lu.netdisk.entities.FileInfo;

public interface FileInfoDAO {
	public void uploadFile(FileInfo fileinfo);
	public void renameFile(String username,String oldfilename,String newfilename);
	public void moveFile(String username,String oldfilename,String newfilename);
	public void deleteFile(String username,String filename);
	public List<FileInfo> listFile(String username,String path);
	public FileInfo getFileInfo(String username,String filename);
	public void createFolder(String username,String foldername);
}
