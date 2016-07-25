package com.footing.website.modules.luxclub.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.modules.luxclub.entity.SmsRecord;
import com.footing.website.modules.sms.SmsUtils;
import com.footing.website.modules.luxclub.dao.SmsRecordDao;

/**
 * 短信Service
 * @author yubin
 * @version 2016-07-21
 */
@Service
@Transactional(readOnly = true)
public class SmsRecordService extends CrudService<SmsRecordDao, SmsRecord> {
	private static Logger logger = LoggerFactory.getLogger(SmsRecordService.class); 
	public SmsRecord get(Long id) {
		return super.get(id);
	}
	
	public List<SmsRecord> findList(SmsRecord smsRecord) {
		return super.findList(smsRecord);
	}
	
	public Page<SmsRecord> findPage(Page<SmsRecord> page, SmsRecord smsRecord) {
		return super.findPage(page, smsRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsRecord smsRecord) {
		super.save(smsRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsRecord smsRecord) {
		super.delete(smsRecord);
	}
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
	public void sendSmsRecord(String mobile,String content,String ip){
		Map<String, String> pp=SmsUtils.sendSms(mobile,content);
		logger.info(pp.toString());
		SmsRecord smsRecord=new SmsRecord();
		smsRecord.setContent(content);
		smsRecord.setIp(ip);
		smsRecord.setMobile(mobile);
		smsRecord.setMtmsgid(pp.get("mtmsgid"));
		smsRecord.setMtstat(pp.get("mterrcode"));
		smsRecord.setSendstatus("1");
		smsRecord.setTemplatetype("1");
		dao.insert(smsRecord);
	}
	
}