package com.commerce.backend.dao;

import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.SystemSetting;

import org.springframework.data.repository.CrudRepository;
@Repository
public interface SystemSettingRepository extends CrudRepository<SystemSetting, Long> {
  SystemSetting findByType(String type);
}
