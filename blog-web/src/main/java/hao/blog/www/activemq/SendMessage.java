package hao.blog.www.activemq;
import com.ucpaas.restDemo.client.AbsRestClient;
import com.ucpaas.restDemo.client.JsonReqClient;

public class SendMessage {
	static AbsRestClient InstantiationRestAPI() {
		return new JsonReqClient();
	}

	public static void testSendSms(String sid, String token, String appid, String templateid, String param, String mobile, String uid) {
		try {
			String result = InstantiationRestAPI().sendSms(sid, token, appid, templateid, param, mobile, uid);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendMessages(String code,String phone){
		String sid = "11848df7706e1e7055fe0396d1bd22bd";
		String token = "f26ffb8884fa5d973a89c69c38e0b94b";
		String appid = "6e85310f912442d2b073c59036c95dd6";
		String templateid = "430148";
		String param = code;
		String mobile = phone;
		String uid = "";
		testSendSms(sid, token, appid, templateid, param, mobile, uid);
		System.out.println("222");
	}
}
