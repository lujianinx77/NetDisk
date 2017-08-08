package com.lu.netdisk.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lu.login.controller.UserInfoController;
import com.lu.netdisk.entities.FileInfo;
@Repository
public class FileInfoDAOImpl implements FileInfoDAO {
	private static Logger log = LoggerFactory.getLogger(FileInfoDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void uploadFile(FileInfo fileinfo) {
		Session session = sessionFactory.getCurrentSession();
		try{
			session.getTransaction().begin();
			session.persist(fileinfo);
			session.getTransaction().commit();
			log.debug("文件:{},[以记录到数据库]",fileinfo.getFilename());
		}catch(Exception e){
			log.error(e.getMessage());
			session.getTransaction().rollback();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void renameFile(String username,String oldfilename, String newfilename) {
		String hql = "update "+FileInfo.class.getName()+" e set e.filename=:newfilename where e.username=:username and e.filename=:oldfilename";
		Session session = sessionFactory.getCurrentSession();
		try{
			session.getTransaction().begin();
			Query query = session.createQuery(hql);
			query.setParameter("newfilename", newfilename);
			query.setParameter("oldfilename", oldfilename);
			query.setParameter("username", username);
			int ret = query.executeUpdate();
			session.getTransaction().commit();
			log.debug("重命名文件个数:{}.",ret);
		}catch(Exception e){
			log.error(e.getMessage());
			session.getTransaction().rollback();
		}
	}

	@Override
	public void moveFile(String username, String oldfilename,String newfilename) {
		String hql = "select e from "+FileInfo.class.getName()+" e where e.username=:username and e.filename like :path";
		Session session = sessionFactory.getCurrentSession();
		try{
			session.getTransaction().begin();
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("path", oldfilename+"%");
			List<FileInfo> ret = query.getResultList();
			session.getTransaction().commit();
			log.debug("find count:{}",ret.size());
			for(FileInfo item : ret){
				String oldname=item.getFilename();
				String newname = oldname.replace(oldfilename, newfilename);
				renameFile(username,oldname,newname);
				log.debug("{} To {}",oldname,oldname);
			}
		}catch(Exception e){
			log.error(e.getMessage());
			session.getTransaction().rollback();
		}
	}

	@Override
	public void deleteFile(String username, String filename) {
		String hql="delete "+FileInfo.class.getName()+" e where username=:username and filename like :filename";
		Session session = sessionFactory.getCurrentSession();
		try{
			session.getTransaction().begin();
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("filename", filename+"%");
			int ret = query.executeUpdate();
			session.getTransaction().commit();
			log.debug("删除文件{},个数{}.",filename,ret);
		}catch(Exception e){
			log.error(e.getMessage());
			session.getTransaction().rollback();
		}
	}

	@Override
	public List<FileInfo> listFile(String username,String path) {
		String hql = "select e from "+FileInfo.class.getName()+" e where e.username=:username and e.filename like :path";
		Session session = sessionFactory.getCurrentSession();
		try{
			session.getTransaction().begin();
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("path", path);
			List<FileInfo> ret = query.getResultList();
			session.getTransaction().commit();
			log.debug("用户 {},文件名:{} 共存放 {} 个文件.",username,path,ret.size());
			return (ret.size() == 0 ? null:ret);
		}catch(Exception e){
			log.error(e.getMessage());
			session.getTransaction().rollback();
		}
		
		return null;
	}

	@Override
	public FileInfo getFileInfo(String username,String filename) {
		String hql = "select e from "+FileInfo.class.getName()+" e where e.username=:username and e.filename=:filename";
		Session session = sessionFactory.getCurrentSession();
		try{
			session.getTransaction().begin();
			Query<FileInfo> query = session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("filename", filename);
			FileInfo ret = null;
			if(query.getResultList().size()==0)
				ret = null;
			else
				ret = query.getSingleResult();
			session.getTransaction().commit();
			log.debug("获取文件信息:{}",ret == null?"没有找到文件 "+filename:ret.getFilename());
			return ret;
		}catch(Exception e){
			log.error(e.getMessage());
			session.getTransaction().rollback();
			return null;
		}
	}

	@Override
	public void createFolder(String username, String foldername) {
		FileInfo fileinfo = getFileInfo(username,foldername);
		if(fileinfo != null)
			return;
		fileinfo = new FileInfo();
		fileinfo.setUsername(username);
		fileinfo.setFilename(foldername);
		fileinfo.setFilesize(0);
		fileinfo.setUploaddate(new Date());
		uploadFile(fileinfo);
	}

}
