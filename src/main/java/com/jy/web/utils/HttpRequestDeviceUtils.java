package com.jy.web.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestDeviceUtils {
	/** Wap���Viaͷ��Ϣ�����е�������Ϣ */
	private static String mobileGateWayHeaders[] = new String[] { "ZXWAP",// �����ṩ��wap��ص�via��Ϣ�����磺Via=ZXWAP
																			// GateWayZTE
																			// Technologies��
			"chinamobile.com",// �й��ƶ���ŵ����wap��أ����磺Via=WTP/1.1
								// GDSZ-PB-GW003-WAP07.gd.chinamobile.com (Nokia
								// WAP Gateway 4.1 CD1/ECD13_D/4.1.04)
			"monternet.com",// �ƶ��������أ����磺Via=WTP/1.1
							// BJBJ-PS-WAP1-GW08.bj1.monternet.com. (Nokia WAP
							// Gateway 4.1 CD1/ECD13_E/4.1.05)
			"infoX",// ��Ϊ�ṩ��wap��أ����磺Via=HTTP/1.1 GDGZ-PS-GW011-WAP2 (infoX-WISG
					// Huawei Technologies)����Via=infoX WAP Gateway V300R001
					// Huawei Technologies
			"XMS 724Solutions HTG",// ���������Ӫ�̵�wap��أ���֪������һ��
			"wap.lizongbo.com",// �Լ�����ʱģ���ͷ��Ϣ
			"Bytemobile",// ò����һ�����ƶ��������ṩ������������������Ч�ʵģ����磺Via=1.1 Bytemobile OSN
							// WebProxy/5.1
	};
	/** �����ϵ�IE��Firefox������ȵ�User-Agent�ؼ�� */
	private static String[] pcHeaders = new String[] { "Windows 98",
			"Windows ME", "Windows 2000", "Windows XP", "Windows NT", "Ubuntu" };
	/** �ֻ��������User-Agent��Ĺؼ�� */
	private static String[] mobileUserAgents = new String[] { "Nokia",// ŵ���ǣ���ɽկ��Ҳд����ģ��ܻ������ֻ�Mozilla/5.0
																		// (Nokia5800
																		// XpressMusic)UC
																		// AppleWebkit(like
																		// Gecko)
																		// Safari/530
			"SAMSUNG",// �����ֻ�
						// SAMSUNG-GT-B7722/1.0+SHP/VPP/R5+Dolfin/1.5+Nextreaming+SMM-MMS/1.2.0+profile/MIDP-2.1+configuration/CLDC-1.1
			"MIDP-2",// j2me2.0��Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2
						// NokiaE75-1 /110.48.125 Profile/MIDP-2.1
						// Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML like
						// Gecko) Safari/413
			"CLDC1.1",// M600/MIDP2.0/CLDC1.1/Screen-240X320
			"SymbianOS",// ���ϵͳ�ģ�
			"MAUI",// MTKɽկ��Ĭ��ua
			"UNTRUSTED/1.0",// ����ɽկ���ua�������ȷ�������ֻ�
			"Windows CE",// Windows CE��Mozilla/4.0 (compatible; MSIE 6.0;
							// Windows CE; IEMobile 7.11)
			"iPhone",// iPhone�Ƿ�Ҳתwap������������ֳ�����˵��Mozilla/5.0 (iPhone; U; CPU
						// iPhone OS 4_1 like Mac OS X; zh-cn) AppleWebKit/532.9
						// (KHTML like Gecko) Mobile/8B117
			"iPad",// iPad��ua��Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X;
					// zh-cn) AppleWebKit/531.21.10 (KHTML like Gecko)
					// Version/4.0.4 Mobile/7B367 Safari/531.21.10
			"Android",// Android�Ƿ�Ҳתwap��Mozilla/5.0 (Linux; U; Android
						// 2.1-update1; zh-cn; XT800 Build/TITA_M2_16.22.7)
						// AppleWebKit/530.17 (KHTML like Gecko) Version/4.0
						// Mobile Safari/530.17
			"BlackBerry",// BlackBerry8310/2.7.0.106-4.5.0.182
			"UCWEB",// ucweb�Ƿ�ֻ��wapҳ�棿 Nokia5800
					// XpressMusic/UCWEB7.5.0.66/50/999
			"ucweb",// Сд��ucwebò����uc�Ĵ��������Mozilla/6.0 (compatible; MSIE 6.0;)
					// Opera ucweb-squid
			"BREW",// ����ֵ�ua�����磺REW-Applet/0x20068888 (BREW/3.1.5.20; DeviceId:
					// 40105; Lang: zhcn) ucweb-squid
			"J2ME",// ����ֵ�ua��ֻ��J2ME�ĸ���ĸ
			"YULONG",// �����ֻ�YULONG-CoolpadN68/10.14 IPANEL/2.0 CTC/1.0
			"YuLong",// ��������
			"COOLPAD",// �������YL-COOLPADS100/08.10.S100 POLARIS/2.9 CTC/1.0
			"TIANYU",// �����ֻ�TIANYU-KTOUCH/V209/MIDP2.0/CLDC1.1/Screen-240X320
			"TY-",// ���TY-F6229/701116_6215_V0230 JUPITOR/2.2 CTC/1.0
			"K-Touch",// ��������K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223
						// Release/30.07.2008 Browser/WAP2.0
			"Haier",// �����ֻ�Haier-HG-M217_CMCC/3.0 Release/12.1.2007
					// Browser/WAP2.0
			"DOPOD",// ���մ��ֻ�
			"Lenovo",// �����ֻ�Lenovo-P650WG/S100 LMP/LML Release/2010.02.22
						// Profile/MIDP2.0 Configuration/CLDC1.1
			"LENOVO",// �����ֻ���磺LENOVO-P780/176A
			"HUAQIN",// �����ֻ�
			"AIGO-",// �����߾�ȻҲ�����ֻ�AIGO-800C/2.04 TMSS-BROWSER/1.0.0 CTC/1.0
			"CTC/1.0",// ���岻��
			"CTC/2.0",// ���岻��
			"CMCC",// �ƶ������ֻ�K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223
					// Release/30.07.2008 Browser/WAP2.0
			"DAXIAN",// �����ֻ�DAXIAN X180 UP.Browser/6.2.3.2(GUI) MMP/2.0
			"MOT-",// Ħ��������MOT-MOTOROKRE6/1.0 LinuxOS/2.4.20 Release/8.4.2006
					// Browser/Opera8.00 Profile/MIDP2.0 Configuration/CLDC1.1
					// Software/R533_G_11.10.54R
			"SonyEricsson",// �����ֻ�SonyEricssonP990i/R100 Mozilla/4.0
							// (compatible; MSIE 6.0; Symbian OS; 405) Opera
							// 8.65 [zh-CN]
			"GIONEE",// �����ֻ�
			"HTC",// HTC�ֻ�
			"ZTE",// �����ֻ�ZTE-A211/P109A2V1.0.0/WAP2.0 Profile
			"HUAWEI",// ��Ϊ�ֻ�
			"webOS",// palm�ֻ�Mozilla/5.0 (webOS/1.4.5; U; zh-CN)
					// AppleWebKit/532.2 (KHTML like Gecko) Version/1.0
					// Safari/532.2 Pre/1.0
			"GoBrowser",// 3g GoBrowser.User-Agent=Nokia5230/GoBrowser/2.0.290
						// Safari
			"IEMobile",// Windows CE�ֻ��Դ��������
			"WAP2.0"// ֧��wap 2.0��
	};

	/**
	 * ��ݵ�ǰ������������жϸ������Ƿ������ֻ��նˣ���Ҫ��������ͷ��Ϣ���Լ�user-Agent���header
	 * 
	 * @param request
	 *            http����
	 * @return ��������ֻ����������򷵻ض�Ӧ�������ַ�
	 */
	public static boolean isMobileDevice(HttpServletRequest request) {
		boolean pcFlag = false;
		boolean mobileFlag = false;
		String via = request.getHeader("Via");
		String userAgent = request.getHeader("user-agent");
		for (int i = 0; via != null && !via.trim().equals("")
				&& i < mobileGateWayHeaders.length; i++) {
			if (via.contains(mobileGateWayHeaders[i])) {
				mobileFlag = true;
				break;
			}
		}
		for (int i = 0; !mobileFlag && userAgent != null
				&& !userAgent.trim().equals("") && i < mobileUserAgents.length; i++) {
			if (userAgent.contains(mobileUserAgents[i])) {
				mobileFlag = true;
				break;
			}
		}
		for (int i = 0; userAgent != null && !userAgent.trim().equals("")
				&& i < pcHeaders.length; i++) {
			if (userAgent.contains(pcHeaders[i])) {
				pcFlag = true;
			}
		}
		if (mobileFlag == true && mobileFlag != pcFlag) {
			return true;
		}
		return false;
	}

}
