package com.estimulo.companymanaging.authoritymanager.controller;

// import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.companymanaging.authoritymanager.exception.IdNotFoundException;
import com.estimulo.companymanaging.authoritymanager.exception.PwMissMatchException;
import com.estimulo.companymanaging.authoritymanager.exception.PwNotFoundException;
import com.estimulo.companymanaging.authoritymanager.servicefacade.AuthorityManagerServiceFacade;
import com.estimulo.hr.affair.to.EmpInfoTO;

@RestController
@RequestMapping("/authoritymanager/*")
public class MemberLogInController {
	ModelMap model = null;
	
	@Autowired
    private AuthorityManagerServiceFacade managerSF;
	
    //private static Gson gson = new GsonBuilder().serializeNulls().create();
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
    public ModelMap LogInCheck(HttpServletRequest request, HttpServletResponse response) {
      //  String viewName = null;
        model = new ModelMap();
        
        try {
            HttpSession session = request.getSession();
            String companyCode = request.getParameter("companyCode");      //COM-01
            String workplaceCode = request.getParameter("workplaceCode");  //BRC-01
            String userId = request.getParameter("userId");               //1111
            String userPassword = request.getParameter("userPassword");   //1111

            EmpInfoTO TO = managerSF.accessToAuthority(companyCode, workplaceCode, userId, userPassword);
            if (TO != null) {       
              // ServletContext application = request.getServletContext();	      // 로그인 성공 했을 경우 그사람의 정보를 클라이언트가 종료되기전 까지 저장 해준다
                session.setAttribute("sessionID", session.getId());
                session.setAttribute("userId", TO.getUserId());                //유저ID
                session.setAttribute("empCode", TO.getEmpCode());              //사원번호
                session.setAttribute("empName", TO.getEmpName());              //사원이름
                session.setAttribute("deptCode", TO.getDeptCode());            //부서코드
                session.setAttribute("deptName", TO.getDeptName());            //부서명
                session.setAttribute("positionCode", TO.getPositionCode());    //직급코드
                session.setAttribute("positionName", TO.getPositionName());    //직급명
                session.setAttribute("companyCode", TO.getCompanyCode());      //회사코드
                session.setAttribute("workplaceCode", workplaceCode);          //사업장코드
                session.setAttribute("workplaceName", TO.getWorkplaceName());  //사업장명
                session.setAttribute("image", TO.getImage()); 				   // 사진 
                session.setAttribute("authorityGroupCode", TO.getUserAuthorityGroupList()); //권한그룹리스트       
                session.setAttribute("authorityGroupMenuList", TO.getUserAuthorityGroupMenuList());  //권한그룹메뉴리스트
                
                // 메뉴 DB에서 가져오기
                String[] allMenuList = managerSF.getAllMenuList();
                session.setAttribute("allMenuList", allMenuList[0]);   // 모든 메뉴 리스트
                session.setAttribute("navMenuList", allMenuList[1]);   // nav바 메뉴 리스트
                session.setAttribute("allMenuList_b", allMenuList[2]);
                System.out.println(allMenuList[0]);
                System.out.println(allMenuList[2]);
            }

        } catch (IdNotFoundException e1) {   
            e1.printStackTrace();
            model.put("errorCode", -2);
            model.put("errorMsg", e1.getMessage());
        } catch (PwNotFoundException e2) {
            e2.printStackTrace();
            model.put("errorCode", -3);
            model.put("errorMsg", e2.getMessage());
        } catch (PwMissMatchException e3) {
            e3.printStackTrace();
            model.put("errorCode", -4);
            model.put("errorMsg", e3.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            model.put("errorCode", -5);
            model.put("errorMsg", e.getMessage());
        }

        // ModelAndView modelAndView = new ModelAndView(viewName, model);
        // mav = new ModelAndView("jsonView", model);
        return model;
    }
}
