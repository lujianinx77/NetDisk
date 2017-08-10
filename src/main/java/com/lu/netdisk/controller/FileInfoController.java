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
	private final String FILE_UPLOAD_PATH="D:\\SpringMVCFileUpload";	//文件在本地的存储路径
	private final String REAL_FILE_NAME_FORMAT = "yyyyMMddHHmmssSS";	//文件名格式
	private final String[] SIZE_UNIT={"B","K","M","G"};	//文件长度单位
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
		//MultipartFile在js中无法修改文件名，为了防止重名，通过传递参数获取文件名
		String username = (String)session.getAttribute(SESSION_ATTRIBUTE_USERNAME);
		log.debug("filename:{} filesize:{}",filename,uploadFile.getSize());
		//本地文件名为上传时间格式化为到毫秒
		Date createTime= new Date();
		String saveFileName= new SimpleDateFormat(REAL_FILE_NAME_FORMAT).format(createTime);
		String savePath=FILE_UPLOAD_PATH+"\\"+username+"\\";
		//如果目录不存在则创建目录
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
			return;//上传失败之后退出,不添加到数据库
		}
		//文件上传成功后将文件信息添加到数据库
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
	/*主页嵌套一个iframe用于显示不同类型的页面（全部文件、图片、视频等）
	 * 通过GET参数 ?type= 来请求不同的页面
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
	//将文件长度转化为B、KB等单位
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
				if(path.equals(list.get(i).getFilename()))	//跳过文件夹本身
					continue;
				//replaceFirst防止 类似path/path/的目录结构替换后为空
				//split添加-1防止 像 path/ii/ 类型的文件夹 分割后最后一个/后的空格不能分割
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
				//返回格式为JSON
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
			//文件夹无实体目录，只删除数据库中的数据
			if(!item.endsWith("/")){
				fileinfo = fileInfoService.getFileInfo(username, item);
				String realFileName = dateFormat.format(fileinfo.getUploaddate());
				log.debug("删除文件:{}/{}",username,realFileName);
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
		log.info("用户：{} 请求下载文件：{}",username,filename);
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
