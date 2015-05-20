package wechat

class DefaultController {

    def index() { 

		String TOKEN = "abcdefg"
		
		// 微信加密签名
		String signature = params.signature;
		// 随机字符串
		String echostr = params.echostr;
		// 时间戳
		String timestamp = params.timestamp;
		// 随机数
		String nonce = params.nonce;
	 
		String[] str = [ TOKEN , timestamp, nonce ];
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHA1().getDigestOfString(bigStr.getBytes())
				.toLowerCase();
	 
		// 确认请求来至微信
		if (digest == signature) {
			render echostr			
		}else{
			render "failed"
		}
		
		
		
	}
}
