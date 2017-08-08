package com.lu.netdisk.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lu.netdisk.dao.FileInfoDAO;
import com.lu.netdisk.dao.FileInfoDAOImpl;
import com.lu.netdisk.entities.FileInfo;
@Service
public class FileInfoServiceImpl implements FileInfoService {
	private static Logger log = LoggerFactory.getLogger(FileInfoServiceImpl.class);
	@Autowired
	private FileInfoDAO fileInfoDAO;
	public void setFileInfoDAO(FileInfoDAO fileInfoDAO) {
		this.fileInfoDAO = fileInfoDAO;
	}

	@Override
	public void uploadFile(FileInfo fileinfo) {
		fileInfoDAO.uploadFile(fileinfo);
	}

	@Override
	public void renameFile(String username,String oldfilename, String newfilename) {
		fileInfoDAO.renameFile(username, oldfilename, newfilename);
	}

	@Override
	public void moveFile(String username, String oldfilename,String newfilename) {
		fileInfoDAO.moveFile(username, oldfilename,newfilename);
	}

	@Override
	public void deleteFile(String username,String filename) {
		fileInfoDAO.deleteFile(username, filename);
	}

	@Override
	public List<FileInfo> listFile(String username,String path) {
		return fileInfoDAO.listFile(username,path);
	}

	@Override
	public FileInfo getFileInfo(String username,String filename) {
		return fileInfoDAO.getFileInfo(username, filename);
	}

	@Override
	public void createFolser(String username, String foldername) {
		// TODO Auto-generated method stub
		fileInfoDAO.createFolder(username, foldername);
	}

}
