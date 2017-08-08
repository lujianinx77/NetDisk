package com.lu.netdisk.service;

import java.util.List;

import com.lu.netdisk.entities.FileInfo;

public interface FileInfoService {
	public void uploadFile(FileInfo fileinfo);
	public void renameFile(String username,String oldfilename,String newfilename);
	public void moveFile(String username,String oldfilename,String newfilename);
	public void deleteFile(String username,String filename);
	public List<FileInfo> listFile(String username,String path);
	public FileInfo getFileInfo(String username,String filename);
	public void createFolser(String username,String foldername);
}
