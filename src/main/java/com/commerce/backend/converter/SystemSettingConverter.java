package com.commerce.backend.converter;

import java.util.function.Function;

import com.commerce.backend.model.dto.SystemSettingVO;
import com.commerce.backend.model.entity.SystemSetting;

public class SystemSettingConverter implements Function<SystemSetting, SystemSettingVO> {

	@Override
	public SystemSettingVO apply(SystemSetting system) {
		SystemSettingVO systemVo = new SystemSettingVO();
		systemVo.setType(system.getType());
		systemVo.setId(system.getId());
		systemVo.setValue(system.getDescription());
		systemVo.setValueAr(system.getDescriptionAr());
		return systemVo;
	}

}
