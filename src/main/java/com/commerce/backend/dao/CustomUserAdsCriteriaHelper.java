package com.commerce.backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.model.entity.UserAds;

@Component
public class CustomUserAdsCriteriaHelper {
   
	@PersistenceContext(type  =  PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	private static final Logger loggerS = LoggerFactory.getLogger(CustomUserAdsCriteriaHelper.class);
	@SuppressWarnings("unchecked")
	public List<UserAds> findNearestByCategory(AdsType type, double longitude, double latitude, Integer distance, Pageable pageable, Long category){
		 String sql = "SELECT user_ads.*, ST_Distance(user_ads.geom, poi) / 1000 AS distance_km "
		 		+ "            FROM user_ads user_ads, "
		 		+ "            (select ST_MakePoint(?1, ?2) as poi) as poi "
		 		+ "            WHERE ST_DWithin(user_ads.geom, poi, ?3) ";
		 
		 if(category != null) {
			 sql += " AND category_id = ?5 ";
		 }
		 
		 if(type != null) {
			 sql += " AND type = ?4 ";
		 }
		 sql += " ORDER BY ST_Distance(geom, poi) ";
		 this.loggerS.info("query:" + sql);
		 Query query = this.entityManager.createNativeQuery(sql, UserAds.class);
		  query.setParameter(1, longitude);
		  query.setParameter(2, latitude);
		  query.setParameter(3, distance);
		  if(type.getType() != null) {
			  query.setParameter(4, type.getType());
		  }
		  if(category != null) {
			  query.setParameter(5, category);
		  }
		  
		  this.loggerS.warn("page number %i", pageable.getPageNumber());
		
		  List<UserAds> userAds = query.
				                       setFirstResult(pageable.getPageNumber())
				                     //  .unwrap(org.hibernate.query.NativeQuery.class)
				                    //   .addScalar("geom", new GeolatteGeometryType(PGGeometryTypeDescriptor.INSTANCE))
				                         .setMaxResults(pageable.getPageSize())
	     			                     .getResultList();
	
		 return userAds;
	 }
}
