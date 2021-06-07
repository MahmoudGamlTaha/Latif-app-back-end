package com.commerce.backend.service;


import com.commerce.backend.constants.AdsType;
import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.UserAdsConverter;
import com.commerce.backend.converter.UserAdsToVoConverter;
import com.commerce.backend.dao.*;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.dto.*;
import com.commerce.backend.model.entity.*;
import com.commerce.backend.model.request.userAds.*;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.product.ProductDetailsResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import static java.lang.System.currentTimeMillis;

@Service
public class UserAdsServiceImpl implements UserAdsService {

	@PersistenceContext(type  =  PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	private UserPetsAdsRepository userPetsAdsRepository;
	private UserServiceAdsRepository userServiceAdsRepository;
	private UserItemsAdsRepository userItemsAdsRepository;
	private UserMedicalAdsRepository userMedicalAdsRepository;
	private ItemObjectCategoryService itemCategory;
	private UserAdsToVoConverter userAdsToVoConverter;
	private UserAdsConverter userAdsConverter;
	private UserAdsImageRepository userAdsImageRepository;
	private CustomUserAdsRepo customUserAdsRepo;
	private CustomUserAdsCriteriaHelper customUserAdsCriteriaHelper;
	private final UserService userService;
	private final Path rootLocation = Paths.get("upload");
	@Value("${swagger.host.path}")
	private String path;
	private static final Logger loggerS = LoggerFactory.getLogger(UserAdsServiceImpl.class);
	@Autowired
	public UserAdsServiceImpl(UserPetsAdsRepository userPetsAdsRepository,
							  UserServiceAdsRepository userServiceAdsRepository,
							  UserItemsAdsRepository userItemsAdsRepository, UserMedicalAdsRepository userMedicalAdsRepository,
							  UserAdsToVoConverter userAdsToVoConverter, UserAdsConverter userAdsConverter,
							  UserAdsImageRepository userAdsImageRepository,
							  CustomUserAdsRepo repo,
							  CustomUserAdsCriteriaHelper customUserAdsCriteriaHelper,
							  ItemObjectCategoryService itemCategory, UserService userService) {



		this.userPetsAdsRepository = userPetsAdsRepository;
		this.userServiceAdsRepository = userServiceAdsRepository;
		this.userItemsAdsRepository = userItemsAdsRepository;
		this.userMedicalAdsRepository = userMedicalAdsRepository;
		this.userAdsToVoConverter = userAdsToVoConverter;
		this.userAdsConverter = userAdsConverter;
		this.userAdsImageRepository = userAdsImageRepository;
		this.customUserAdsRepo = repo;
		this.customUserAdsCriteriaHelper = customUserAdsCriteriaHelper;
		this.itemCategory = itemCategory;
		this.userService = userService;
	}

	@Override
	public ProductDetailsResponse findByUrl(String url) {
		return null;
	}

	@Override
	public BasicResponse getAll(AdsType type, LocationRequest location, Integer page, Integer size, String sort, Long category, Float minPrice,
								Float maxPrice) {
		try {
			Pageable pageable = PageRequest.of(page, size);
			List<UserAdsVO> collect = new ArrayList<>();
			if(location != null)
			{
				Page<UserAds> ads = customUserAdsRepo.findAll(location.getLongitude(), location.getLatitude(), type.getType(), pageable);
				ads.forEach((ad) -> collect.add(userAdsToVoConverter.apply(ad)));
			}else if(type != null){
				Page<UserAds> ads = customUserAdsRepo.findUserAdsByType(type.getType(), pageable);
				ads.forEach((ad) -> collect.add(userAdsToVoConverter.apply(ad)));
			}

			return res(collect, true, null);
		}catch(Exception ex) {
			
			 BasicResponse response = new BasicResponse();
			 HashMap<String, Object> hashMap = new HashMap<String, Object>();
			 hashMap.put("success",false);
			 hashMap.put("error", ex.getMessage());
			 response.setResponse(hashMap);
			 return response;
		}
	}

	@Override
	public Long getAllCount(UserAdsVO userAdsVO, Float minPrice, Float maxPrice) {
		return 0L;
	}

	@Override
	public List<UserAdsVO> getRelatedAds(UserAdsVO userAd) {
	//	List<UserAds> userAds = this.userAdsRepository.getReleatedAds(userAd);
		return null;
	}

	@Override
	public List<UserAdsVO> getNewlyAddedAds(AdsType adsType, Long Category) {
	//	this.userAdsRepository.getNewlyAddedAds(adsType, Category);
		return null;
	}

	@Deprecated
	@Override
	public List<UserAdsVO> getNearByAds(Integer page, Integer size, String sort, Long category, Float minPrice,
			Float maxPrice, UserAdsVO adsCriteria) {
	//	List<UserAds> userAds = this.userAdsRepository.getNearByAds(page, size, sort, category, minPrice, maxPrice, adsCriteria);
		return null;
	}

	@Override
	public BasicResponse findNearby(AdsType type ,Double longitude, Double latitude, Integer distance, Integer page, Integer size, Long category)
	{
		try {
			
		Pageable pageable = PageRequest.of(page, size);
		List<UserAds> ads = customUserAdsCriteriaHelper.findNearestByCategory(type, longitude, latitude, distance, pageable, category);
		UserAds single = ads.stream().findFirst().orElse(null);
		
		List<UserAdsVO> collect = new ArrayList<>();
		ads.forEach((ad) -> collect.add(userAdsToVoConverter.apply(ad)));

		BasicResponse response = res(collect, true, pageable); 
		if(single != null) {
		 response.getResponse().put(MessageType.TotalItems.getMessage(), ads.size());
		 response.getResponse().put(MessageType.TotalPages.getMessage(), single.getTotalPage() + 1);
		}
		 return response;
		}catch(Exception ex) {
			ex.printStackTrace();
			return res(ex, false, null);
		}
	}
	@Deprecated
	@Override
	public List<UserAdsVO> getNearByAdsByCategory(AdsType adsType, Long Category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicResponse findAdsById(Long id) {
		try{
			UserAds ad = (UserAds) customUserAdsRepo.findById(id).orElse(null);
			UserAdsVO vo = userAdsToVoConverter.apply(ad);
			return res(vo, true, null);
		}
		catch (Exception e)
		{e.printStackTrace();
			return res(e, false, null);
		}
	}
	@Override
	public List<UserAdsVO> searchItemDisplay(String keyword, Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicResponse myAds(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		///String principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		User user = userService.getCurrentUser();
		if(user != null){
			Page<UserAds> ads = customUserAdsRepo.findCreateByUser(user.getId(), pageable);
			List<UserAdsVO> collect = new ArrayList<>();
			ads.forEach((ad) -> collect.add(userAdsToVoConverter.apply(ad)));
			return resHelper.res(collect , true, MessageType.Success.getMessage(), pageable);
		}
		return resHelper.res(null , false, MessageType.Missing.getMessage(), null);
	}

	@Override
	public List<UserAdsVO> getInterested(Long userId, String token) {
		// TODO Auto-generated method stub
		return null;
	}
    @Transactional(propagation = Propagation.REQUIRED)
	@Override
	public BasicResponse createUserAds(DynamicAdsRequest<String, Object> ads, List<String> files, List<MultipartFile> file, boolean external) {
		BasicResponse response = new BasicResponse();
		try {
		UserAds entity = this.userAdsConverter.convertRequestToEntity(ads);
		this.loggerS.info("external", external);
		entity.setExternalLink(external);
		this.saveEntityFiles(entity, files, file, ads.getType(), external);
		if(ads.getType() == AdsType.ACCESSORIES) 
		{
			this.userItemsAdsRepository.save((UserAccAds)entity);
		}
		else if(ads.getType() == AdsType.PET_CARE) {
			this.userMedicalAdsRepository.save((UserMedicalAds)entity);
		}
		else if(ads.getType() == AdsType.PETS || ads.getType() == AdsType.Dogs) {
			//((UserPetAds) entity).setImage("upload/"+ ads.getType() + file.get(0).getName());	
	    	this.userPetsAdsRepository.save((UserPetAds)entity);
		}
		else if(ads.getType() == AdsType.SERVICE) {
		   this.userServiceAdsRepository.save((UserServiceAds)entity);
		} else if(ads.getType() == AdsType.DRIVER) {
			
		}
		
		response.setSuccess(true);
		response.setMsg("Ads created successfully ");
		HashMap<String ,Object> map = new HashMap<String, Object>();
		UserAdsVO userAdVo = userAdsToVoConverter.apply(entity);
		map.put("data", userAdVo);
		map.put("id", entity.getId());
		response.setResponse(map);
		return response;
	}catch(Exception ex) {
		this.loggerS.info("error", ex.getMessage());
		ex.printStackTrace();
		response.setMsg(ex.getMessage());
		
	}
	return response;
	}

	@Override
	public BasicResponse updateUserAds(UpdateAdRequest<String, Object> request, List<String> fileList, List<MultipartFile> files) {
		if(request.getId() == null)
		{
			return res(MessageType.Missing.getMessage(), false, null);
		}
		try {
			UserAds ad = customUserAdsRepo.findById(request.getId()).orElse(null);
			if(ad == null)
			{
				return res("Not Found", false, null);
			}
			if(fileList != null || files != null)
			{
				this.saveEntityFiles(ad, fileList, files, ad.getType(), ad.getExternalLink());
			}
			UserAds dd = userAdsConverter.updateAd(request, ad);
			UserAdsVO updatedAd = userAdsToVoConverter.apply(customUserAdsRepo.save(dd));
			return res(updatedAd, true, null);
		}catch (Exception ex)
		{
			return res(ex, false, null);
		}
	}

	@Override
	public Long getAllCount(String category, Float minPrice, Float maxPrice, String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicResponse getCreateForm(adTypeRequest adsType, Long category){

		InputStream is = null;
		String adType = null;
		ClassPathResource filePath = null;
		BasicResponse response = new BasicResponse();
		HashMap<String, Object> mapResponse = new HashMap<String, Object>(); 
		try {
			if(category != null)
			{
				adType = itemCategory.findById(category).getName().toLowerCase();
				filePath = new ClassPathResource("jsonFiles/"+adType+"Rs.json");
			}
			if (filePath == null || !filePath.exists())
			{
				adType = adsType.getAdsType().getType().toLowerCase();
				filePath = new ClassPathResource("jsonFiles/"+adType+"Rs.json");
			}
			is = filePath.getInputStream();
		
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(
					new InputStreamReader(is, StandardCharsets.UTF_8));
			response.setMsg(MessageType.Success.getMessage());
			response.setSuccess(true);
            mapResponse.put(MessageType.Data.getMessage(), jsonObject);
			response.setResponse(mapResponse);
			
		} catch (Exception e) {
			response.setMsg(e.getMessage());
			response.setSuccess(false);
		} finally {
			if(is != null) {
			  try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response.setMsg(e.getMessage());
				response.setSuccess(false);
			
			}
			}
		}
		return response;
	}
	
	@Override
	public BasicResponse getFilterForm(adTypeRequest adsType, Long category){

		InputStream is = null;
		String adType = null;
		ClassPathResource filePath = null;
		BasicResponse response = new BasicResponse();
		HashMap<String, Object> mapResponse = new HashMap<String, Object>(); 
		try {
			if(category != null)
			{
				adType = itemCategory.findById(category).getName().toLowerCase();
				filePath = new ClassPathResource("jsonFilter/"+adType+"Rs.json");
			}
			if (filePath == null || !filePath.exists())
			{
				adType = adsType.getAdsType().getType().toLowerCase();
				filePath = new ClassPathResource("jsonFilter/"+adType+"Rs.json");
			}
			is = filePath.getInputStream();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(
					new InputStreamReader(is, StandardCharsets.UTF_8));
			response.setMsg(MessageType.Success.getMessage());
			response.setSuccess(true);
            mapResponse.put(MessageType.Data.getMessage(), jsonObject);
			response.setResponse(mapResponse);
			
		} catch (Exception e) {
			response.setMsg(e.getMessage());
			response.setSuccess(false);
		} finally {
			if(is != null) {
			  try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response.setMsg(e.getMessage());
				response.setSuccess(false);
			
			}
			}
		}
		return response;
	}

	@Override
	@SuppressWarnings("unchecked")
	public BasicResponse adsFiltration(AdsFiltrationRequest<String, Object> ads, Pageable pageable) {
		Query query = userAdsConverter.buildFilterQuery(ads);
		List<UserAds> userAds = query
				.setFirstResult(pageable.getPageNumber())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
		List<UserAdsVO> collect = new ArrayList<>();
		userAds.forEach((ad) -> collect.add(userAdsToVoConverter.apply(ad)));
		return res(collect, true, pageable);
	}


	@Override
	public <T> UserAdsVO savePet(UserPetsAdsRequest userPetsAdsRequest) {
		return new UserPetAdsVO();
	}
	
	public boolean saveEntityFiles(UserAds entity, List<String> fileList, List<MultipartFile> files, AdsType type, boolean external) {
		try {	
		if(files != null && !external) {
			String filename = currentTimeMillis() + "-" + StringUtils.cleanPath(Objects.requireNonNull(files.get(0).getOriginalFilename()));
			filename = filename.toLowerCase().replaceAll(" ", "-");
			try {
				Files.copy(files.get(0).getInputStream(), rootLocation.resolve(filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
			((UserPetAds) entity).setImage("/upload/ads/" + type.getType()+ "/" + filename);
			if(files.size() > 1)
			{
				UserAdsImage userAdsImage = new UserAdsImage();
				for (int i = 1; i < files.size(); i++)
				{
					userAdsImage.setImage("/upload/ads/" + type.getType()+ "/"  + filename);
					userAdsImage.setUserAdsImage((UserPetAds) entity);
					userAdsImageRepository.save(userAdsImage);
				}

			}
		}else if(external && fileList != null) {
			
			fileList.stream().forEach(path -> {
				UserAdsImage userAdsImage = new UserAdsImage();
				userAdsImage.setImage(path);
				userAdsImage.setIsExternalLink(true);
				userAdsImage.setUserAdsImage(entity);
				userAdsImageRepository.save(userAdsImage);
			});
		}
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
    
    public BasicResponse res(Object obj, boolean sucess, Pageable pageable  )
	{
		BasicResponse res = new BasicResponse();
		HashMap<String, Object> map = new HashMap<>();

		if( obj instanceof Exception) {
		 map.put(MessageType.Data.getMessage(), ((Exception) obj).getStackTrace());
		} else {
			map.put(MessageType.Data.getMessage(),  obj);
			if(pageable != null) {
		    	map.put(MessageType.CurrentPage.getMessage(), pageable.getPageNumber());
			}
		}
		res.setSuccess(sucess);
		res.setResponse(map);
		return res;
	}

	@Override
	public BasicResponse adActivation(Long id, boolean activate) {
		if(id == null)
		{
			return resHelper.res(null , false, MessageType.Missing.getMessage(), null);
		}
		try {
			UserAds ad = customUserAdsRepo.findById(id).orElse(null);
			if(ad == null)
			{
				resHelper.res(null , false, MessageType.Missing.getMessage(), null);
			}
			if(userService.isAuthorized(ad.getCreatedBy()) || userService.isAdmin()) {
				ad.setActive(activate);
				customUserAdsRepo.save(ad);
				return resHelper.res(ad , true, MessageType.Success.getMessage(), null);
			}else{
				return resHelper.res(null , false, MessageType.NotAuthorized.getMessage(), null);
			}
		}catch (Exception ex){
			 return resHelper.res(ex , false, MessageType.Missing.getMessage(), null);
		}
	}
}
