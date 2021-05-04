package com.commerce.backend.dao;

import java.math.BigInteger;
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
		 String sql = "SELECT page_info.* ,user_ads.*, ST_Distance(user_ads.geom, poi) / 1000 AS distance_km "
		 		+ "            FROM user_ads user_ads, "
		 		+ "            (select ST_MakePoint(:long, :lat) as poi) as poi, page_quey "
		 		+ "            WHERE ST_DWithin(user_ads.geom, poi, :dist) ";
	     
		 String paging = "(select count(*) total_item, count(*)/:size total_page from user_ads  WHERE ST_DWithin(user_ads.geom,  ST_MakePoint(:long, :lat), :dist)";
		 if(type != AdsType.ALL) {
			 sql += " AND type = :type ";
			 paging+=" AND type = :type ";
		 }
		 if(category != null) {
			 sql += " AND category_id = :cat ";
			 paging+=" AND category_id = :cat ";
		 }
		
		 sql += " ORDER BY ST_Distance(geom, poi) ";
		
		 paging +=") as page_info";
		 sql = sql.replace("page_quey", paging);
		 this.loggerS.info("query:" + sql);
		 Query query = this.entityManager.createNativeQuery(sql, UserAds.class);
		  query.setParameter("long", longitude);
		  query.setParameter("lat", latitude);
		  query.setParameter("dist", distance);
		  query.setParameter("size", pageable.getPageSize());
		  if(type !=  AdsType.ALL) {
			  query.setParameter("type", type.getType());
		  }
		  if(category != null) {
			  query.setParameter("cat", category);
		  }
		    	
		  List<UserAds> userAds = query.
				                       setFirstResult((int) pageable.getOffset())
				                     //  .unwrap(org.hibernate.query.NativeQuery.class)
				                    //   .addScalar("geom", new GeolatteGeometryType(PGGeometryTypeDescriptor.INSTANCE))
				                         .setMaxResults(pageable.getPageSize())
				                       
	     			                     .getResultList();	
	/*	userAds.stream().forEach(arg0 ->
		{
			this.loggerS.info("VV"+String.valueOf(arg0.getTotalPage()));
			this.loggerS.info("TT"+String.valueOf(arg0.getTotalItem()));
		});
		*/
		 return userAds;
	 }
	 public Object getCountByCategory(Long categoryId){
		 String queryString = "SELECT Count(*) FROM user_ads WHERE category_id = :catId";
		 Query query = entityManager.createNativeQuery(queryString).setParameter("catId", categoryId);
		 System.out.println(query.getSingleResult());
		 return query.getSingleResult();
	 }
}
