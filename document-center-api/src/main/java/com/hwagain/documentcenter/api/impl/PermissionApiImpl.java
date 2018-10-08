package com.hwagain.documentcenter.api.impl;

import com.hwagain.documentcenter.api.IPermissionApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author laogaocheng
 * @since 2018-09-20
 */
@Service("permissionApi")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PermissionApiImpl implements IPermissionApi {
	
}
