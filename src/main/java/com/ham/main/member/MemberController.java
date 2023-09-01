



package com.ham.main.member;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.http.HttpRequest;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import oracle.jdbc.proxy.annotation.Post;

	

	

	


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.github.scribejava.core.model.OAuth2AccessToken;


import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ham.main.member.MemberDTO;
import com.ham.main.member.mail.MailSendController;
import com.ham.main.member.mail.MailSendService;
import com.ham.main.util.auth.SNSLogin;
import com.ham.main.util.auth.SnsValue;


@Controller
@RequestMapping("/member/*")
public class MemberController {


	@Autowired
	private MemberService memberService;
	
	
	@Autowired
	private MailSendController mailSendController;
	
	@Autowired
	private MailSendService mailSendService;

      @Inject
	private SnsValue sns;
      
//    @Autowired  
//	private SNSLogin kakaoSns;
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    

	
	@GetMapping(value = "idCheck")
	public String getIdCheck(MemberDTO memberDTO, Model model) throws Exception {
		memberDTO = memberService.getIdCheck(memberDTO);
		
		int result = 0;  //중복
		if(memberDTO == null) {
			result = 1; //중복x
		}
		
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	@PostMapping("memberEmailCheck")
	public ModelAndView getMemberEmailCheck(MemberDTO memberDTO)throws Exception {
	    boolean check = memberService.getMemberEmailCheck(memberDTO);
	    ModelAndView mv = new ModelAndView();

	    mv.addObject("result", check);
	    mv.setViewName("commons/ajaxResult");
	    return mv;
	}
	
	//이용약관
	@GetMapping("memberAgree")
	public void setMemberAgree() throws Exception {

	}
	
	//회원가입
	@GetMapping("memberJoin")
	public ModelAndView setMemberJoin() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/member/memberJoin");
		
		return mv;
	}
	
	@PostMapping("memberJoin")
	public ModelAndView setMemberJoin(MemberDTO memberDTO) throws Exception {
		ModelAndView mv = new ModelAndView();
		int result = memberService.setMemberJoin(memberDTO);
		mv.setViewName("redirect:../");
		
		return mv;
	}
	
	//로그인
	@GetMapping("memberLogin")
	public ModelAndView getMemberLogin(Model model, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
//		String kakaoAuthUrl = kakaoSns.getAuthorizationUrl(session);
		//System.out.println("카카오:" + kakaoAuthUrl);
//		model.addAttribute("urlKakao", kakaoAuthUrl);
		mv.setViewName("/member/memberLogin");
    
        SNSLogin snsLogin = new SNSLogin(sns);
		model.addAttribute("naverUrl", snsLogin.getNaverAuthURL("test"));
		
		return mv;
	}
  

  
  
	
	@PostMapping("memberLogin")
	public ModelAndView getMemberLogin(MemberDTO memberDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		memberDTO = memberService.getMemberLogin(memberDTO);
		
		if(memberDTO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", memberDTO);
			
			mv.setViewName("redirect:../");
		}else {
			mv.addObject("errorMessage", "로그인에 실패했습니다.");
			mv.setViewName("/member/memberLogin");
		}
		
		return mv;
	}
	
	//로그아웃
	@GetMapping("memberLogout")
	public ModelAndView getMemberLogout(HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		session.invalidate();
		mv.setViewName("redirect:../");
		
		return mv;
	}
	
	//회원정보(Mypage)
	@GetMapping("memberPage")
	public ModelAndView getMemberPage(HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		memberDTO = memberService.getMemberPage(memberDTO);
		mv.addObject("kto", memberDTO);
		mv.setViewName("member/memberPage");
		
		return mv;
	}
	
   
  

	
	// 카카오 로그인 성공시 callback
//	@RequestMapping(value = "/callbackKakao", method = { RequestMethod.GET, RequestMethod.POST })
//	public String callbackKakao(Model model, @RequestParam String code, @RequestParam String state, HttpSession session) throws Exception {
//		System.out.println("로그인 성공 callbackKakao");
//		OAuth2AccessToken oauthToken;
//		oauthToken = kakaoSns.getAccessToken(session, code, state);
//		// 로그인 사용자 정보를 읽어온다
//		String apiResult = kakaoSns.getUserProfile(oauthToken);
//			
//		JSONParser jsonParser = new JSONParser();
//		JSONObject jsonObj;
//			
//		jsonObj = (JSONObject) jsonParser.parse(apiResult);
//		JSONObject response_obj = (JSONObject) jsonObj.get("kakao_account");	
//		JSONObject response_obj2 = (JSONObject) response_obj.get("profile");
//		System.out.println(jsonObj);
//		Long response_obj3 = (Long) jsonObj.get("id");
//		
//		// 프로필 조회
//		String email = (String) response_obj.get("email");
//		String name = (String) response_obj2.get("nickname");
//		System.out.println(response_obj3);
//		String id = response_obj3.toString();
//		// 세션에 사용자 정보 등록
//		// session.setAttribute("islogin_r", "Y");
//		MemberDTO memberDTO = new MemberDTO();
//		memberDTO.setName(name);
//		memberDTO.setEmail(email);
//		session.setAttribute("signIn", apiResult);
//		System.out.println(apiResult);
//		session.setAttribute("email", email);
//		session.setAttribute("name", name);
//		
//		session.setAttribute("member", memberDTO);
//		SnsMemberDTO snsMemberDTO = new SnsMemberDTO();
//		System.out.println(id);
//		snsMemberDTO.setSnsId(id);
//		snsMemberDTO.setSnsName(name);
//		snsMemberDTO.setSnsEmail(email);
//		long result = memberService.getKakaoLogin(snsMemberDTO);
//		if(memberService.getKakaoLogin(snsMemberDTO)>0) {
//			
//		}else {
//			memberService.setKakaoJoin(snsMemberDTO);
//		}
//		
//	
//		return "/home";
//	}
//	
	
	    




	

	
	//sns로그인
	@RequestMapping(value = "/auth/{service}/callback", method = {RequestMethod.GET,RequestMethod.POST})
	public String snsLoginCallback(@PathVariable String service, Model model,@RequestParam("code") String code,@RequestParam("state") String state, HttpSession session) throws Exception{
	    System.out.println(code);
	    System.out.println(state);
		logger.info("snsLoginCallback: service={}", service);
	      SnsValue sns = null;
	      if(StringUtils.equals("naver", service)) {
	    	  sns = sns;
	      }
	      
	      //1.code를 이용해서 access_token받기
	      //2.access_token을 이용해서 profile받아오기
	      
	      SNSLogin snsLogin = new SNSLogin(sns);
	      SnsMemberDTO snsMemberDTO = snsLogin.getUserProfile(code); //1,2번 동시
	      System.out.println("Profile>>" + snsMemberDTO);
		      
//	      3.DB에 해당 유저가 존재하는지 체크
	      snsMemberDTO = memberService.getBySns(snsMemberDTO);  
          if(snsMemberDTO == null) {
        	  model.addAttribute("result","존재하지 않는 사용자입니다 가입해주세요");
          }else {
        	  model.addAttribute("result",snsMemberDTO.getSnsName() + "님 반갑습니다.");
        	  //4. 존재 시 강제 로그인, 미존재시 가입페이지로
        	  
        	  session.setAttribute("member", snsMemberDTO);
          }
          
	      return "commons/loginResult";
	}
	
	

	
	  @PostMapping("phoneAuth")
	    @ResponseBody
	    public Boolean phoneAuth(String phone, HttpSession session) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setPhone(phone);
	        try { // 이미 가입된 전화번호가 있으면
	            if(memberService.memberTelCount(memberDTO) > 0) 
	                return true; 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        String code = memberService.sendRandomMessage(phone);
	        session.setAttribute("rand", code);
	        
	        return false;
	    }

	    @PostMapping("phoneAuthOk")
	    @ResponseBody
	    public Boolean phoneAuthOk(HttpServletRequest request) {
	        String rand = (String) request.getSession().getAttribute("rand");
	        String code = (String) request.getParameter("code");

	        System.out.println(rand + " : " + code);

	        if (rand.equals(code)) {
	        	request.getSession().removeAttribute("rand");
	            return false;
	        } 

	        return true;
	    }
	    
//       업데이트
  
  
  	@GetMapping("onGoing")
	public String getOnGoing() throws Exception{
		
		return "/mypage/onGoing";
	}
	
	@GetMapping("complete")
	public String getComplete() throws Exception{
		
		return "/mypage/complete";
	}
	
	@GetMapping("cancle")
	public String getCancle() throws Exception{
		
		return "/mypage/cancle";
	}
	
	@GetMapping("productDetail")
	public String getProductDetail() throws Exception {
		
		return "/mypage/productDetail";
	}
	
	@GetMapping("info")
	public String getInfo(MemberDTO memDTO, HttpSession session) throws Exception{
		memDTO.setPassword("1234");
		memDTO.setId("qwe");
		memDTO = memberService.getCheckInfo(memDTO);
		
		session.setAttribute("kto", memDTO);
		
		return "/mypage/info";
	}
	
	@GetMapping("checkInfo")
	public String checkInfo(MemberDTO memDTO, HttpSession session, Model model) throws Exception {
		memDTO = (MemberDTO)session.getAttribute("kto");
		
		memDTO = memberService.getCheckInfo(memDTO);
		
		model.addAttribute("kto", memDTO);
		
		return "/mypage/checkInfo";
	}
	
	@GetMapping("memberUpdate")
	public String setUpdate() throws Exception {
		
		return "/mypage/memberUpdate";
	}
	@PostMapping("memberUpdate")
	public String setUpdate(MemberDTO memDTO) throws Exception {
		int result = memberService.setUpdate(memDTO);
		
		return "redirect:/mypage/info";
	}
	
	@PostMapping("delete")
	public String setDelete(MemberDTO memDTO, HttpSession session) throws Exception{
		memberService.setDelete(memDTO);
		session.invalidate();
		System.out.println(memDTO.getId());
		
		return "redirect:/";
	}
	
  
  
  
}	   
	

