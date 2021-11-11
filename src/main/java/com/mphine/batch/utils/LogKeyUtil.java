/*
 * KT GiGAeyes version 1.0
 *
 *  Copyright ⓒ 2017 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
package com.mphine.batch.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

/**
 * 
 * @FileName    : LogKeyUtil.java
 * @Date        : 2018. 12. 10.
 * @Author      : hkpark
 * @Description : LogKey 생성을 위한 Util class
 * @History     : [20181210] 최초작성.
 * 
 */

@Component
public class LogKeyUtil {	
	
	public String getRandomKey() {
		
		StringBuffer sb = new StringBuffer();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREA);		
		
		sb.append("LOGKEY_");
		sb.append(sdf.format(new Date()));
		sb.append("_");		
		
		for (int i = 0; i < 5; i++) {
			if (ThreadLocalRandom.current().nextBoolean()) {
				sb.append((char)(ThreadLocalRandom.current().nextInt(26) + 65));
			} else {
				sb.append(ThreadLocalRandom.current().nextInt(10));
			}
		}	
		
		return sb.toString();
	}
}
