package com.ham.main.member;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.scribejava.core.model.Response;
import com.ham.main.util.SendMessage;


@Service
public class MemberService {

	@Autowired
	private MemberDAO memberDAO;
	

	public MemberDTO getIdCheck(MemberDTO memberDTO) throws Exception {
		return memberDAO.getIdCheck(memberDTO);
	}
	
	public boolean getMemberEmailCheck(MemberDTO memberDTO) throws Exception {
	    memberDTO = memberDAO.getMemberByEmail(memberDTO);

	    boolean check = true;

	    if (memberDTO != null) {
	        check = false;
	    }
	    return check;
	}
	
	//회원가입
	public int setMemberJoin(MemberDTO memberDTO) throws Exception {
		int result = memberDAO.setMemberJoin(memberDTO);
		
		result = memberDAO.setMemberRole(memberDTO);

		
		return result;
	}
	




	//로그인
	public MemberDTO getMemberLogin(MemberDTO memberDTO) throws Exception {
		return memberDAO.getMemberLogin(memberDTO);
	}
	
	//회원정보
	public MemberDTO getMemberPage(MemberDTO memberDTO) throws Exception {
		return memberDAO.getMemberLogin(memberDTO);
	}
	
	
	
	public int setKakaoJoin(SnsMemberDTO snsMemberDTO) throws Exception {
		return memberDAO.setKakaoJoin(snsMemberDTO);
	}
	
	public long getKakaoLogin(SnsMemberDTO snsMemberDTO) throws Exception {
		return memberDAO.getKakaoLogin(snsMemberDTO);
	}

	
	public String sendRandomMessage(String phone) {
        SendMessage message = new SendMessage();
        Random random = new Random();
        String numStr = "";
        for (int i = 0; i < 6; i++) {
            String randomNum = Integer.toString(random.nextInt(10));
            numStr += randomNum;
        }
     
        System.out.println("회원가입 문자 인증 => " + numStr);
        message.sendMsg(phone, numStr);

        return numStr;
    }
	
	 public Long memberTelCount(MemberDTO memberDTO) throws Exception{
	    	return memberDAO.memberTelCount(memberDTO);
	 }
	 
	 public SnsMemberDTO getBySns(SnsMemberDTO snsMemberDTO) throws Exception{
	    	return memberDAO.getBySnsNaver(snsMemberDTO);
	 }
	 
	 public MemberDTO getDetail(MemberDTO memberDTO) throws Exception{
	    	return memberDAO.getDetail(memberDTO);
	 }
	 
	//  admin 권한 부여, 삭제
	   public int adminAdd(MemberDTO memberDTO) throws Exception{
	    	return memberDAO.adminAdd(memberDTO);
	    }
	    
	    public int adminRemove(MemberDTO memberDTO) throws Exception{
	    	return memberDAO.adminRemove(memberDTO);
	    }
	//  partner 권한 부여, 삭제
	    public int partnerAdd(MemberDTO memberDTO) throws Exception{
	    	return memberDAO.adminAdd(memberDTO);
	    }
	    
	    public int partnerRemove(MemberDTO memberDTO) throws Exception{
	    	return memberDAO.adminRemove(memberDTO);
	    }

//    민준 업데이트
       public MemberDTO getCheckInfo(MemberDTO memDTO) throws Exception{
		
		   return memberDAO.getCheckInfo(memDTO);
	}
	
       public int setUpdate(MemberDTO memDTO) throws Exception {
		int result = memberDAO.setUpdate(memDTO);
		
		return result;
		}
      
       public int setDelete(MemberDTO memDTO) throws Exception{
		
		return memberDAO.setDelete(memDTO);
	}

}
	    
	
	



