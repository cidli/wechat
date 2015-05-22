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
		if (digest != signature) {
			render status:400, text:"verify signature failed"
			return
		}
		
		//验证消息真实性 
		if(echostr != null){
			render echostr	
			return 
		}
		
		//接收普通消息
		def pl = request.XML
		
		println pl.ToUserName
		println pl.FromUserName
		println pl.CreateTime
		println pl.MsgType
		println pl.Content
		println pl.MsgId
		
		
		if(pl.Content.text() == "news"){
			render(contentType:"text/xml") {
				xml{
					ToUserName(pl.FromUserName.text())
					FromUserName(pl.ToUserName.text())
					CreateTime(pl.CreateTime.text())
					MsgType("news")
					ArticleCount(1)
					Articles{
						item{
							Title("dddd")
							Description("ddddddddd")
							Url("http://tieba.baidu.com")
							PicUrl("http://pic.nipic.com/2007-11-09/2007119121849495_2.jpg")
						}
					}
				}			
			}
		}else if(pl.Content.text() == "link"){
			render(contentType:"text/xml") {
				xml{
					ToUserName(pl.FromUserName.text())
					FromUserName(pl.ToUserName.text())
					CreateTime(pl.CreateTime.text())
					MsgType("link")
					Title("公众平台官网链接")
					Description("公众平台官网链接")
					Url("http://pic.nipic.com/2007-11-09/2007119121849495_2.jpg")
				}
			}
		}else{
			render(contentType:"text/xml") {
				xml{
					ToUserName(pl.FromUserName.text())
					FromUserName(pl.ToUserName.text())
					CreateTime(pl.CreateTime.text())
					MsgType(pl.MsgType.text())
					Content("Hello : " + pl.Content.text())
				}
			}
		}
		
		
		
	}
}
