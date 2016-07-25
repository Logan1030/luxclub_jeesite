package com.footing.website.modules.sys.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.common.utils.StringUtils;
import com.footing.website.modules.luxclub.utils.DateUtil;
import com.footing.website.modules.luxclub.utils.NumberUtil;
import com.footing.website.modules.sys.dao.SerialNumberDao;
import com.footing.website.modules.sys.entity.SerialNumber;

/**
 * 流水号Service
 * @author yubin
 * @version 2016-07-23
 */
@Service
@Transactional(readOnly = true)
public class SerialNumberService extends CrudService<SerialNumberDao, SerialNumber> {

	public SerialNumber get(Long id) {
		return super.get(id);
	}
	
	public List<SerialNumber> findList(SerialNumber serialNumber) {
		return super.findList(serialNumber);
	}
	
	public Page<SerialNumber> findPage(Page<SerialNumber> page, SerialNumber serialNumber) {
		return super.findPage(page, serialNumber);
	}
	
	@Transactional(readOnly = false)
	public void save(SerialNumber serialNumber) {
		super.save(serialNumber);
	}
	
	@Transactional(readOnly = false)
	public void delete(SerialNumber serialNumber) {
		super.delete(serialNumber);
	}
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW)
    public  String getSerialNumberByDate(String prefix, String appType)throws Exception{
     if (StringUtils.isBlank(prefix)) {
       logger.error("prefix is empty");
       throw new Exception("prefix is empty");
     }
     if (StringUtils.isBlank(appType)) {
       logger.error("appType is empty");
       throw new Exception("appType is empty");
     }
     logger.info("prefix:" + prefix + ";appType:" + appType);
 
     StringBuffer resultSerialNumber = new StringBuffer(prefix);
 
     Calendar calendar = Calendar.getInstance();
 
     Date nowDate = calendar.getTime();
     String nowDateStr = DateUtil.date2Str(nowDate, "yyyyMMdd");
     nowDate = DateUtil.str2Date(nowDateStr, "yyyyMMdd");
     int number = 0;
 
     SerialNumber objParam=new SerialNumber();
     objParam.setAppType(appType);
     SerialNumber serialNumber = dao.get(objParam);
     if (serialNumber == null) {
       logger.info("serialNumber is null");
       number = 1;
 
       serialNumber = new SerialNumber();
     
       serialNumber.setAppType(appType);
       serialNumber.setSerialDate(nowDate);
       serialNumber.setNumber(Integer.valueOf(number));
 
       logger.info("insert a serialNumber");
       dao.insert(serialNumber);
       logger.info("insert a serialNumber successd");
     } else {
       logger.info("serialNumber is exist");
       if (nowDate.after(serialNumber.getSerialDate())) {
         logger.info("nowDate is later than serialDate,so update nowDate to serialDate,and reset number");
         serialNumber.setSerialDate(nowDate);
         number = 1;
       } else {
         logger.info("nowDate is not later than serialDate,so number plus one");
         number = serialNumber.getNumber().intValue() + 1;
       }
       serialNumber.setNumber(Integer.valueOf(number));
 
       logger.info("update a serialNumber");
       dao.update(serialNumber);
       logger.info("update a serialNumber successd");
     }
 
     resultSerialNumber.append(nowDateStr);
     String numberStr = NumberUtil.formatNumber(number, 4);
     resultSerialNumber.append(numberStr);
 
     logger.info("resultSerialNumber:" + resultSerialNumber);
     logger.info("comin in log end..."+Thread.currentThread().getName());
     return resultSerialNumber.toString();
   }
}