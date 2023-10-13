package com.jspiders.CArDekhoSpringRest.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jspiders.CArDekhoSpringRest.pojo.AdminPOJO;

@Repository
public class AdminRepository {
	
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static EntityTransaction entityTransaction;
	private static Query query;
	private static String jpql;
	
	private static void openConnection() {
		entityManagerFactory=Persistence.createEntityManagerFactory("REST");
		entityManager=entityManagerFactory.createEntityManager();
		entityTransaction=entityManager.getTransaction();
	}
	private static void closeConnection() {
		if(entityManagerFactory!=null) {
			entityManagerFactory.close();
		}
		if (entityManager!=null) {
			entityManager.close();
			
		}
		if (entityTransaction != null) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}
	

	public AdminPOJO createAccount(AdminPOJO pojo) {
		openConnection();
		entityTransaction.begin();
		
		entityManager.persist(pojo);
		
		entityTransaction.commit();
		closeConnection();
		return pojo;
	}

	public AdminPOJO login(AdminPOJO pojo) {
		openConnection();
		entityTransaction.begin();
		
		AdminPOJO admin = entityManager.find(AdminPOJO.class, pojo.getUsername());
		if (admin != null) {
			if (admin.getPassword().equals(pojo.getPassword())) {
				entityTransaction.commit();
				closeConnection();
				return admin;
			}
		}
		
		entityTransaction.commit();
		closeConnection();
		return null;
		
	}

}
