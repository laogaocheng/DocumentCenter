package com.hwagain.documentcenter.api.impl;

import com.hwagain.documentcenter.api.IAppApi;
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
@Service("appApi")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AppApiImpl implements IAppApi {
	
}
