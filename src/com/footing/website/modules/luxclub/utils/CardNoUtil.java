package com.footing.website.modules.luxclub.utils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.footing.website.modules.luxclub.dao.MemberInfoDao;

/**
 * 会员卡生成util
 * 8位卡号(4位大写字母+4位数字)
 * @author liuguoqing
 *
 */
@Lazy(false)
@Component
public class CardNoUtil {
    private final Logger logger = LoggerFactory.getLogger(CardNoUtil.class);
    /** 字母部分长度 */
    private final int letterLenght = 4;
    /** 数字部分长度 */
    private final int numberLenght = 4;
    /** 密码长度 */
    private final int passwordLenght = 6;
    /** 密码随机字符串*/
    private final String passwordStr = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    
    private ConcurrentHashMap<String, String> cardnoMap = new ConcurrentHashMap<String, String>();

    @Autowired
    public MemberInfoDao memberInfoDao;
    
    /**
     * 判断cache中是否有cardno
     */
    public boolean isContain(String cardno) {
        for (int i = 0; i < cardno.length(); i++) {
            int len = checkCardno(cardno);
            if (len > 0) {
                return true;
            }
        }
        cardno = null;
        return false;
    }    
    
    /**
     * 卡号添加到cacheMap
     */
    @PostConstruct
    public void initCardnoCache(){
        long begin = System.currentTimeMillis();
        int count = 0;
        int total = memberInfoDao.countAllMemberCardno();
        int pageSize = 500;
        for (int i = 0; i <= total / pageSize; i++) {
            List<String> tempList = memberInfoDao.findCardnoListByPage(i * pageSize, pageSize);
            if (tempList.size() > 0) {
                this.addCacheMap(tempList);
                count+= tempList.size();
            }
        }
        logger.info("=== 会员卡初始化加载到缓存中... 数量：{}, cost:{}", count,(System.currentTimeMillis()-begin));
    }
    
    // 将卡号添加到缓存map中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addCacheMap(List<String> cardnoList) {
        for (int i = 0; i < cardnoList.size(); i++) {
            String key = cardnoList.get(i);
            ConcurrentHashMap nowhash = null;
            nowhash = cardnoMap;
            for (int j = 0; j < key.length(); j++) {
                char word = key.charAt(j);
                Object wordMap = nowhash.get(word);
                if (wordMap != null) {
                    nowhash = (ConcurrentHashMap) wordMap;
                } else {
                    ConcurrentHashMap<String, String> newWordHash = new ConcurrentHashMap<String, String>();
                    newWordHash.put("isEnd", "0");
                    nowhash.put(word, newWordHash);
                    nowhash = newWordHash;
                }
                if (j == key.length() - 1) {
                    nowhash.put("isEnd", "1");
                }
            }
        }
    }
    
    /**
     * 检查一个字符串从begin位置起开始是否有cardno符合， 如果有符合的cardno值，返回值为匹配cardno的长度，否则返回0
     * @param cardno
     * @return
     */
    @SuppressWarnings("unchecked")
    private int checkCardno(String cardno) {
        ConcurrentHashMap<String, String> nowhash = null;
        nowhash = cardnoMap;
        int maxMatchRes = 0;
        int res = 0;
        int l = cardno.length();
        char word = 0;
        for (int i = 0; i < l; i++) {
            word = cardno.charAt(i);
            Object wordMap = nowhash.get(word);
            if (wordMap != null) {
                res++;
                nowhash = (ConcurrentHashMap<String, String>) wordMap;
                if (((String) nowhash.get("isEnd")).equals("1")) {
//                    if (flag == 1) {
//                        wordMap = null;
//                        nowhash = null;
//                        cardno = null;
//                        return res;
//                    } else {
                        maxMatchRes = res;
//                    }
                }
            } else {
                cardno = null;
                nowhash = null;
                return maxMatchRes;
            }
        }
        cardno = null;
        nowhash = null;
        return maxMatchRes;
    }

    /**
     * 批量生成不重复的8位会员卡号(4位字母+4位数字)
     * @param count
     * @return map<卡号, 密码>
     */
    public ConcurrentHashMap<String, String> generateCardNo(Long count) {
        ConcurrentHashMap<String, String> cardMap = new ConcurrentHashMap<String, String>();
        for (int i = 0; i < count;) {
            String cardno = this.getRandomCardNo(letterLenght, numberLenght);
            if (!cardMap.containsKey(cardno) && !this.isContain(cardno)) {
                    String password = getRandomPassword(passwordLenght);
                    cardMap.put(cardno, password);
                    i++;
            }
        }
        return cardMap;
    }

    public String getRandomCardNo(Integer letterLenght, Integer numberLenght) {
        StringBuffer strBuf = new StringBuffer();
        Random random = new Random();
        // 字母
        for (int i = 0; i < letterLenght;) {
            char ch = (char) (65 + random.nextInt(26));
            if (!(ch == 'O' || ch == 'I')) {// 排除[O、I]
                strBuf.append(ch);
                i++;
            }
        }
        for (int i = 0; i < numberLenght; i++) {// 数字[2-8]
            strBuf.append(String.valueOf((int) (Math.random() * 8 + 2)));
        }
        return strBuf.toString();
    }

    public String getRandomPassword(int length) {
        Random random = new Random();
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(passwordStr.length());// [0,55)
            strBuf.append(passwordStr.charAt(number));
        }
        return strBuf.toString();
    }

}
