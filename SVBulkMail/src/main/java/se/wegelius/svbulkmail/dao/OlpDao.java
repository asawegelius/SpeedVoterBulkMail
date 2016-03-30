/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmail.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.reflect.ParameterizedType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;


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
public class OlpDao<T, ID extends Serializable> implements IOlpDao<T, ID> {

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public OlpDao() {
		this.type = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public OlpDao( Class<T> type) {
		super();
		this.type = type;
	}

	/**
	 * Save an entity
	 * 
	 * @param entity
	 *            the entity to save
	 */
        @Override
	public void save(T entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
                            e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (HibernateException e) {
                            e.printStackTrace();
			}
		}
	}

	/**
	 * Updates an entity
	 * 
	 * @param entity
	 *            the entity to update
	 */
        @Override
	public void update(T entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.update(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
                session.close();
	}

	/**
	 * Saves or updates an entity
	 * 
	 * @param entity
	 *            the entity to update or save
	 */
        @Override
	public void saveOrUpdate(T entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Merges an entity
	 * 
	 * @param entity
	 *            the entity to merge
	 */
        @Override
	public void merge(T entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.merge(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
                session.close();
	}

	/**
	 * Find an entity by its primary key
	 * 
	 * @param id
	 *            the entity's primary key
	 * @return the entity
	 */
	@SuppressWarnings("unchecked")
        @Override
	public T findByID(ID id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		T obj = null;
		try {
			session.beginTransaction();
			obj = (T) session.get(type, id);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * Get all entities
	 * 
	 * @return a Set of all entities
	 */
	@SuppressWarnings("unchecked")
        @Override
	public Set<T> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<T> objects = null;
		try {
			session.beginTransaction();
			Query query = session.createQuery("from " + type.getName());
			objects = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
		if( objects != null)
			return new HashSet<>(objects);
		return null;
	}

	/**
	 * Find entities based on a query
	 * 
	 * @param query
	 *            the name of the query
	 * @return a Set of the entities
	 */
	@SuppressWarnings("unchecked")
	public Set<T> getAll(String query) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<T> objects = null;
		try {
			session.beginTransaction();
			objects = session.createQuery(query).list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
                session.close();
		if( objects != null)
			return new HashSet<>(objects);
		return null;
	}

	/**
	 * Find entities based on a query
	 * 
     * @param queryString
	 * @param params
	 *            the query parameters
	 * @return a Set of the entities
	 */
	@SuppressWarnings("unchecked")
        @Override
	public Set<T> getAll(String queryString, Object... params) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery(queryString);
		List<T> objects = null;
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		try {
			objects = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
                session.close();
		if( objects != null)
			return new HashSet<>(objects);
		return null;
	}

	/**
	 * Deletes an entity
	 * 
	 * @param entity
	 *            the entity to delete
	 */
        @Override
	public void delete(T entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.delete(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}



	/**
	 * Get the class of the entity
	 * 
	 * @return the entity class
	 */
        @Override
	public Class<T> getEntityClass() {
		return type;
	}

	/**
	 * Count all entities
	 * 
	 * @return how many entities there are
	 */
        @Override
	public int count() {
		Set<T> all = getAll();
		return all.size();
	}
}
