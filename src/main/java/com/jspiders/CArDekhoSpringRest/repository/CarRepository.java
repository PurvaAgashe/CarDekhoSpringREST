package com.jspiders.CArDekhoSpringRest.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jspiders.CArDekhoSpringRest.pojo.CarPOJO;
@Repository
public class CarRepository {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager EntityManager;
	private static EntityTransaction EntityTransaction;
	private static Query query;

	private static void openConnection() {
		entityManagerFactory = Persistence.createEntityManagerFactory("REST");
		EntityManager = entityManagerFactory.createEntityManager();
		EntityTransaction = EntityManager.getTransaction();
	}
	private static void closeConnection() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
		if (EntityManager != null) {
			EntityManager.close();
		}
		if (EntityTransaction != null) {
			if (EntityTransaction.isActive()) {
				EntityTransaction.rollback();
			}
		}
	}
	public CarPOJO addCar(CarPOJO pojo) {
		openConnection();
		EntityTransaction.begin();

		EntityManager.persist(pojo);

		EntityTransaction.commit();
		closeConnection();
		return pojo;
	}

	public CarPOJO searchCar(int id) {
		openConnection();
		EntityTransaction.begin();

		CarPOJO car = EntityManager.find(CarPOJO.class, id);

		EntityTransaction.commit();
		closeConnection();
		return car;
	}

	public List<CarPOJO> searchAllCars() {
		openConnection();
		EntityTransaction.begin();
		String jpql = "from CarPOJO";
		query = EntityManager.createQuery(jpql);
		List<CarPOJO> cars = query.getResultList();

		EntityTransaction.commit();
		closeConnection();
		return cars;
	}

	public CarPOJO removeCar(int id) {
		openConnection();
	EntityTransaction.begin();

		CarPOJO car = EntityManager.find(CarPOJO.class, id);
		if (car != null) {
		EntityManager.remove(car);
		EntityTransaction.commit();
		closeConnection();
		return car;
	}
		EntityTransaction.commit();
		closeConnection();
		return null;
	}

	public CarPOJO updateCar(CarPOJO pojo) {
		openConnection();
		EntityTransaction.begin();
		
		CarPOJO car=EntityManager.find(CarPOJO.class, pojo.getId());
		car.setName(pojo.getName());
		car.setModel(pojo.getModel());
		car.setBrand(pojo.getBrand());
		car.setFuel(pojo.getFuel());
		car.setPrice(pojo.getPrice());
		
		EntityManager.persist(car);
		EntityTransaction.commit();
		return car;
	}

}
