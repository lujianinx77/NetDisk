package com.lu.netdisk.service;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lu.netdisk.dao.FileInfoDAOImpl;
import com.lu.netdisk.entities.FileInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"applicationContextTest.xml"})
public class FileInfoServiceImplTest {
	private static Logger log = LoggerFactory.getLogger(FileInfoServiceImplTest.class);
	@Autowired
	private FileInfoService fileInfoService;
	public void setFileInfoService(FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}

	@Test
	public void testSave() {
		FileInfo fileinfo=new FileInfo();
		fileinfo.setFilename("path/ex11122.txt");
		fileinfo.setFilesize(1024);
		fileinfo.setUploaddate(new Date());
		fileinfo.setUsername("lujianxin77");
		fileInfoService.uploadFile(fileinfo);
		//fileinfo.setFilename("path/23.txt");
		Date date = new Date();
		log.debug(new SimpleDateFormat("SS").format(date));
		//fileInfoService.uploadFile(fileinfo);
	}

	@Test
	public void testRenameFile() {
		//fileInfoService.renameFile("lujianxin77", "path/ex.txt", "path/ell.txt");
	}

	@Test
	public void testMoveFile() {
		/*
		 * like
		 *  path/... To lu/...
		 * 	lu/23.txt To path/23.txt
		 * */
		//fileInfoService.deleteFile("lujianxin77", "lu/ex[copy]/");
	}

	@Test
	public void testDeleteFile() {
		//fileInfoService.deleteFile("lujianxin77", "path/ell.txt");
	}

	@Test
	public void testListFile() {
		/*String username ="lujianxin77";
		String filename="path/oe/";
		List<FileInfo> ret = fileInfoService.listFile(username, filename+"%");
		for(FileInfo item : ret)
			log.debug("{}=>{}==?{}",filename,item.getFilename(),filename.equals(item.getFilename()));*/
		
	}

	@Test
	public void testGetFileInfo() {
		FileInfo fileinfo = fileInfoService.getFileInfo("lujianxin77", "path/ex11122.txt");
		log.debug(new SimpleDateFormat("SS").format(fileinfo.getUploaddate()));
	}

}
