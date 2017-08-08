package com.lu.login.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lu.login.entities.UserInfo;

@Repository
public class UserInfoDAOImpl implements UserInfoDAO{
	private static Logger log = LoggerFactory.getLogger(UserInfoDAOImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public UserInfo login(String username, String password) {
		String hql="select e from "+UserInfo.class.getName()+" e where e.username=:username and e.password =:password";
		Session session = sessionFactory.getCurrentSession();
		try{
			session.getTransaction().begin();
			Query<UserInfo> query=session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("password", password);
			UserInfo ret;
			if(query.getResultList().size() == 0)
				ret = null;
			else
				ret = (UserInfo)query.getSingleResult();
			session.getTransaction().commit();
			return ret;
		}catch(Exception e)
		{
			log.error(e.getMessage());
			session.getTransaction().rollback();
			return null;
		}
	}

	@Override
	public void save(UserInfo userinfo) {
		if(getUserInfoByUsername(userinfo.getUsername()) != null)
			return;
		Session session = sessionFactory.getCurrentSession();
		try{
			session.getTransaction().begin();
			session.persist(userinfo);
			session.getTransaction().commit();
		}catch(Exception e){
			log.error(e.getMessage());
			session.getTransaction().rollback();
		}
		
	}

	@Override
	public UserInfo getUserInfoByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		String hql="select e from "+UserInfo.class.getName()+" e where e.username=:username";
		try{
			session.getTransaction().begin();
			Query<UserInfo> query = session.createQuery(hql);
			query.setParameter("username", username);
			UserInfo ret;
			if(query.getResultList().size() == 0)
				ret = null;
			else
				ret = (UserInfo)query.getSingleResult();
			session.getTransaction().commit();
			return ret;
			
		}catch(Exception e){
			log.error(e.getMessage());
			session.getTransaction().rollback();
			return null;
		}
	}

	@Override
	public Boolean modifyPasswordByEmail(String username, String email, String newPassword) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select e from "+UserInfo.class.getName()+" e where e.username=:username and e.email=:email";
		try{
			session.getTransaction().begin();
			Query<UserInfo> query = session.createQuery(hql);
			query.setParameter("username",username);
			query.setParameter("email", email);
			UserInfo userInfo;
			if(query.getResultList().size() == 0){
				session.getTransaction().commit();
				return false;
			}
			userInfo = query.getSingleResult();
			userInfo.setPassword(newPassword);
			session.update(userInfo);
			session.getTransaction().commit();
			return true;
		}catch(Exception e)
		{
			log.error(e.getMessage());
			session.getTransaction().rollback();
			return false;
		}
	}

}
