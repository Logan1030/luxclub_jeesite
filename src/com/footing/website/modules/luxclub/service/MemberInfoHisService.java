package com.footing.website.modules.luxclub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.modules.luxclub.dao.MemberInfoHisDao;
import com.footing.website.modules.luxclub.entity.MemberInfoHis;

/**
 * 会员卡信息历史Service
 * @author liuguoqing
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class MemberInfoHisService extends CrudService<MemberInfoHisDao, MemberInfoHis> {

	public MemberInfoHis get(Long id) {
		return super.get(id);
	}
	
	public List<MemberInfoHis> findList(MemberInfoHis memberInfoHis) {
		return super.findList(memberInfoHis);
	}
	
	public Page<MemberInfoHis> findPage(Page<MemberInfoHis> page, MemberInfoHis memberInfoHis) {
		return super.findPage(page, memberInfoHis);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberInfoHis memberInfoHis) {
		super.save(memberInfoHis);
	}
	
}