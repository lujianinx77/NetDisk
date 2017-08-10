package com.lu.netdisk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lu.netdisk.entities.FileInfo;
import com.lu.netdisk.service.FileInfoService;

@Controller
@RequestMapping(value="netDisk")
public class FileInfoController {
	private static Logger log = LoggerFactory.getLogger(FileInfoController.class);
	private final String GET_FILE_LIST_ACTION = "getFileListAction";
	private final String RENAME_FILE_ACTION = "renameFileAction";
	private final String MOVE_FILE_ACTION = "moveFileAction";
	private final String DELETE_FILE_ACTION = "deleteFilesAction";
	private final String CREATE_FOLDER_ACTION = "createFolderAction";
	private final String FILE_INFO_ACTION="fileInfo";
	private final String PATH="netDisk/";
	private final String FILE_INFO_ALL_PAGE=PATH+"all";
	private final String FILE_INFO_PICTURE_PAGE=PATH+"picture";
	private final String FILE_UPLOAD_ACTION = "fileUploadAction";
	private final String FILE_DOWNLOAD_ACTION="fileDownloadAction";
	private final String RESPONSE_SUCCESS = "success";
	private final String RESPONSE_FAIL = "fail";
	private final String FILE_UPLOAD_PATH="D:\\SpringMVCFileUpload";	//�ļ��ڱ��صĴ洢·��
	private final String REAL_FILE_NAME_FORMAT = "yyyyMMddHHmmssSS";	//�ļ�����ʽ
	private final String[] SIZE_UNIT={"B","K","M","G"};	//�ļ����ȵ�λ
	private final String SESSION_ATTRIBUTE_USERNAME = "username";
	@Autowired
	private FileInfoService fileInfoService;
	public void setFileInfoService(FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}
	@RequestMapping(value=FILE_UPLOAD_ACTION,method=RequestMethod.POST)
	public void fileUploadAction(@RequestParam("path")String path
			,@RequestParam("fileName")String filename
			,@RequestParam("file")MultipartFile uploadFile
			,HttpServletResponse response
			,HttpSession session){
		//MultipartFile��js���޷��޸��ļ�����Ϊ�˷�ֹ������ͨ�����ݲ�����ȡ�ļ���
		String username = (String)session.getAttribute(SESSION_ATTRIBUTE_USERNAME);
		log.debug("filename:{} filesize:{}",filename,uploadFile.getSize());
		//�����ļ���Ϊ�ϴ�ʱ���ʽ��Ϊ������
		Date createTime= new Date();
		String saveFileName= new SimpleDateFormat(REAL_FILE_NAME_FORMAT).format(createTime);
		String savePath=FILE_UPLOAD_PATH+"\\"+username+"\\";
		//���Ŀ¼�������򴴽�Ŀ¼
		File directory = new File(savePath);
		if(!directory.exists())	
			directory.mkdirs();
		String saveName=saveFileName;
		log.debug("save to path:{}",savePath+saveName);
		File file=new File(savePath,saveName);
		try {
			uploadFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			log.error(e.getMessage());
			if(file.exists())
				file.delete();
			try {
				response.getWriter().write(RESPONSE_FAIL);
			} catch (IOException e1) {
				log.error(e.getMessage());
			}
			return;//�ϴ�ʧ��֮���˳�,����ӵ����ݿ�
		}
		//�ļ��ϴ��ɹ����ļ���Ϣ��ӵ����ݿ�
		FileInfo fileinfo= new FileInfo();
		fileinfo.setFilename(path+filename);
		fileinfo.setFilesize((int)uploadFile.getSize());
		fileinfo.setUploaddate(createTime);
		fileinfo.setUsername(username);
		fileInfoService.uploadFile(fileinfo);
		try {
			response.getWriter().write(RESPONSE_SUCCESS);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	/*��ҳǶ��һ��iframe������ʾ��ͬ���͵�ҳ�棨ȫ���ļ���ͼƬ����Ƶ�ȣ�
	 * ͨ��GET���� ?type= ������ͬ��ҳ��
	 * */
	@RequestMapping(value=FILE_INFO_ACTION,method=RequestMethod.GET)
	public String fileInfoPage(@RequestParam("type")String type){
		log.debug("fileInfoPage type="+type);
		switch(type){
			case "all":
				return FILE_INFO_ALL_PAGE;
			default:
				return FILE_INFO_PICTURE_PAGE;
		}
		
	}
	//���ļ�����ת��ΪB��KB�ȵ�λ
	public String sizeConvert(int size){
		int index=0;
		while(size >= 1024){
			size /=1024;
			index ++;
		}
		return String.format("%d%s", size,SIZE_UNIT[index]);
	}
	@RequestMapping(value=GET_FILE_LIST_ACTION,method=RequestMethod.POST)
	public void getFileList(@RequestParam("path") String path,HttpServletResponse response
			,HttpSession session){
		String username = (String) session.getAttribute(SESSION_ATTRIBUTE_USERNAME);
		List<FileInfo> list = fileInfoService.listFile(username, path+"%");
		String ret="{";
		if(list != null){
			String type="";
			String name="";
			for(int i=0;i<list.size();i++){
				if(path.equals(list.get(i).getFilename()))	//�����ļ��б���
					continue;
				//replaceFirst��ֹ ����path/path/��Ŀ¼�ṹ�滻��Ϊ��
				//split���-1��ֹ �� path/ii/ ���͵��ļ��� �ָ�����һ��/��Ŀո��ָܷ�
				String[] split = list.get(i).getFilename().replaceFirst(path, "").split("/",-1);
				if(split.length > 1){
					type="path";
				}
				else{
					String[] ext = list.get(i).getFilename().split("\\.");
					if(ext.length == 1)
						type="";
					else
						type=ext[ext.length-1];
				}
				name=split[0];
				//���ظ�ʽΪJSON
				ret+=String.format("\"%s\":{\"ext\":\"%s\",\"size\":\"%s\",\"createtime\":\"%s\"}",name,
						type,sizeConvert(list.get(i).getFilesize()),new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getUploaddate()));
				if(i!=list.size()-1)
					ret+=",";
			}
		}
		ret+="}";
		log.debug(ret);
		try {
			response.getWriter().write(ret);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	@RequestMapping(value=RENAME_FILE_ACTION,method=RequestMethod.POST)
	public void renameFileAction(@RequestParam("oldName") String oldName
			,@RequestParam("newName") String newName
			,HttpServletResponse response
			,HttpSession session)
	{
		String username = (String) session.getAttribute(SESSION_ATTRIBUTE_USERNAME);
		fileInfoService.moveFile(username, oldName, newName);
		try {
			response.getWriter().write(RESPONSE_SUCCESS);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	@RequestMapping(value=MOVE_FILE_ACTION,method=RequestMethod.POST)
	public void moveFileAction(@RequestParam("oldName") String oldName
			,@RequestParam("newName") String newName
			,HttpServletResponse response
			,HttpSession session)
	{
		String username = (String) session.getAttribute(SESSION_ATTRIBUTE_USERNAME);
		fileInfoService.moveFile(username, oldName, newName);
		try {
			response.getWriter().write(RESPONSE_SUCCESS);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	@RequestMapping(value=DELETE_FILE_ACTION,method=RequestMethod.POST)
	public void deleteFilesAction(@RequestParam(value="names[]")String[] names
			,HttpServletResponse response
			,HttpSession session){
		String username = (String) session.getAttribute(SESSION_ATTRIBUTE_USERNAME);
		FileInfo fileinfo = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(REAL_FILE_NAME_FORMAT);
		for(String item : names){
			//�ļ�����ʵ��Ŀ¼��ֻɾ�����ݿ��е�����
			if(!item.endsWith("/")){
				fileinfo = fileInfoService.getFileInfo(username, item);
				String realFileName = dateFormat.format(fileinfo.getUploaddate());
				log.debug("ɾ���ļ�:{}/{}",username,realFileName);
				File file = new File(FILE_UPLOAD_PATH+"/"+username+"/"+realFileName);
				file.delete();
			}
			fileInfoService.deleteFile(username, item);
		}
		try {
			response.getWriter().write(RESPONSE_SUCCESS);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	@RequestMapping(value=CREATE_FOLDER_ACTION,method=RequestMethod.POST)
	public void createFolderAction(@RequestParam("folderName")String foldername
			,HttpServletResponse response
			,HttpSession session){
		if(!foldername.endsWith("/")){
			try {
				response.getWriter().write(RESPONSE_FAIL);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		String username = (String) session.getAttribute(SESSION_ATTRIBUTE_USERNAME);
		fileInfoService.createFolser(username, foldername);
		try {
			response.getWriter().write(RESPONSE_SUCCESS);
		} catch (IOException e) {
			log.error(e.getMessage());
		}	
	}
	@RequestMapping(value=FILE_DOWNLOAD_ACTION,method=RequestMethod.POST)
	public void  fileDownloadAction(@RequestParam("fileName")String filename
			,HttpServletResponse response
			,HttpSession session){
		String username = (String) session.getAttribute(SESSION_ATTRIBUTE_USERNAME);
		FileInfo fileinfo = fileInfoService.getFileInfo(username, filename);
		String realfilename = new SimpleDateFormat(REAL_FILE_NAME_FORMAT).format(fileinfo.getUploaddate());
		String filepath = FILE_UPLOAD_PATH+"/"+username+"/";
		log.info("�û���{} ���������ļ���{}",username,filename);
		response.setCharacterEncoding("utf-8");
		response.setContentLength(fileinfo.getFilesize());
	    response.setContentType("multipart/form-data");
	    String[] split = fileinfo.getFilename().split("/");
	    response.setHeader("Content-Disposition", "attachment;fileName=" + split[split.length-1]);
	    try {
			InputStream is = new FileInputStream(filepath+realfilename);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length = 0;
			while((length = is.read(b))>0){
				os.write(b,0,length);
			}
			os.close();
			is.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
