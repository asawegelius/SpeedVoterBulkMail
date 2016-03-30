/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmail.dao;


import java.io.Serializable;
import java.util.Set;

/**
 * Generic repository providing basic CRUD operations
 * 
 * @author Asa Wegelius
 *
 * @param <T>
 *            the entity type
 * @param <ID>
 *            the primary key type
 */
public interface IOlpDao<T, ID extends Serializable> {
	/**
	 * Get the class of the entity
	 * 
	 * @return the entity class
	 */
	Class<T> getEntityClass();

	/**
	 * Find an entity by its primary key
	 * 
	 * @param id
	 *            the entity's primary key
	 * @return the entity
	 */
	T findByID(ID id);



	/**
	 * Get all entities
	 * 
	 * @return a Set of all entities
	 */
	Set<T> getAll();

	/**
	 * Find entities based on a query
	 * 
	 * @param query
	 *            the name of the query
	 * @param params
	 *            the query parameters
	 * @return a Set of the entities
	 */
	Set<T> getAll(String query, Object... params);

	/**
	 * Count all entities
	 * 
	 * @return how many entities there are
	 */
	int count();

	/**
	 * Save an entity
	 * 
	 * @param entity
	 *            the entity to save
	 */
	void save(T entity);

	/**
	 * Updates an entity
	 * 
	 * @param entity
	 *            the entity to update
	 */
	void update(T entity);

	/**
	 * Saves or updates an entity
	 * 
	 * @param entity
	 *            the entity to update or save
	 */
	void saveOrUpdate(T entity);

	/**
	 * Merges an entity
	 * 
	 * @param entity
	 *            the entity to merge
	 */
	void merge(T entity);

	/**
	 * Deletes an entity
	 * 
	 * @param entity
	 *            the entity to delete
	 */
	void delete(T entity);

}