package com.estimulo.companymanaging.authoritymanager.applicationservice;

import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.companymanaging.authoritymanager.dao.MenuAuthorityDAO;
import com.estimulo.companymanaging.authoritymanager.dao.MenuDAO;
import com.estimulo.companymanaging.authoritymanager.to.MenuAuthorityTO;
import com.estimulo.companymanaging.authoritymanager.to.MenuTO;

@Component
public class MenuApplicationServiceImpl implements MenuApplicationService {
	
	// DAO 참조변수 선언
	@Autowired
	private MenuAuthorityDAO menuAuthorityDAO;
	@Autowired
	private MenuDAO menuDAO;
		
	@Override
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList) {
		menuAuthorityDAO.deleteMenuAuthority(authorityGroupCode);

		for (MenuAuthorityTO bean : menuAuthorityTOList) {
			menuAuthorityDAO.insertMenuAuthority(bean);
		}
	}

	@Override
	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode) {
		ArrayList<MenuAuthorityTO> menuAuthorityTOList = menuAuthorityDAO.selectMenuAuthorityList(authorityGroupCode);
	
		return menuAuthorityTOList;
	}

	@Override
	public String[] getAllMenuList() {
		// 메뉴와 nav메뉴를 담을 변수
		StringBuffer menuList = new StringBuffer();
		StringBuffer menuList_b = new StringBuffer();
		StringBuffer navMenuList = new StringBuffer();
		String[] menuArr = new String[3];
		// nav메뉴 정렬을 위한 treemap
		TreeMap<Integer, MenuTO> treeMap = new TreeMap<>();

		ArrayList<MenuTO> allMenuTOList = menuDAO.selectAllMenuList();

		ArrayList<MenuTO> lv0 = new ArrayList<>();
		ArrayList<MenuTO> lv1 = new ArrayList<>();
		ArrayList<MenuTO> lv2 = new ArrayList<>();

		// 메뉴 레벨별로 나누어 ArrayList에 담기
		for (MenuTO bean : allMenuTOList) {
			if (bean.getMenuURL() != null) {
				String lv = bean.getMenuLevel();
				switch (lv) {
				case "0":
					lv0.add(bean); break;
				case "1":
					lv1.add(bean); break;
				default:
					lv2.add(bean);
				}
			}
		}

		menuList.append("<ul class='list-unstyled components mb-5' id='menuUlTag'>");

		// 레벨0 메뉴
		for (MenuTO bean0 : lv0) {

			menuList.append("<li>");
			menuList.append("<a href=" + bean0.getMenuURL()
					+ " data-toggle='collapse' aria-expanded='false' class='dropdown-toggle'>");
			menuList.append(bean0.getMenuName() + "</a>");
			menuList.append("<ul class='collapse list-unstyled' id=" + bean0.getMenuURL().substring(1) + ">");

			// 레벨1 메뉴
			for (MenuTO bean1 : lv1) {

				menuList.append("<li>");

				// 자식이 없는 레벨1 메뉴
				if (bean1.getChildMenu() == null && bean1.getParentMenuCode().equals(bean0.getMenuCode())) {

					menuList.append("<a href=" + bean1.getMenuURL() + " id=" + bean1.getMenuCode() + " class='m'>"
							+ bean1.getMenuName() + "</a>");

					if (bean1.getNavMenu() != null) {
						treeMap.put(Integer.parseInt(bean1.getNavMenu()), bean1);
					}

					// 자식이 있는 레벨1 메뉴
				} else if (bean1.getChildMenu() != null && bean1.getParentMenuCode().equals(bean0.getMenuCode())) {

					menuList.append("<a href=" + bean1.getMenuURL()
							+ " data-toggle='collapse' aria-expanded='false' class='dropdown-toggle' ");
					menuList.append("id=" + bean1.getMenuCode() + ">" + bean1.getMenuName() + "</a>");
					menuList.append(
							"<ul class='collapse list-unstyled' id=" + bean1.getMenuURL().substring(1) + ">");

					// 레벨2 메뉴
					for (MenuTO bean2 : lv2) {

						if (bean2.getParentMenuCode().equals(bean1.getMenuCode())) {
							menuList.append("<li>");
							menuList.append("<a href=" + bean2.getMenuURL() + " id=" + bean2.getMenuCode()
									+ " class='m'>" + bean2.getMenuName() + "</a>");
							menuList.append("</li>");
						}

						if (bean2.getNavMenu() != null) {
							treeMap.put(Integer.parseInt(bean2.getNavMenu()), bean2);
						}
					}
					menuList.append("</ul>");
				}
				menuList.append("</li>");
			}
			menuList.append("</ul>");
			menuList.append("</li>");
		}
		menuList.append("</ul>");

		//******************************************************************************************
		
		int l=0, j=0, k=0;
		menuList_b.append("<nav class='navbar navbar-expand-sm navbar-light bg-light'>");
		menuList_b.append("<button class='navbar-toggler' type='button' "
				+ "data-toggle='collapse' data-target='#navbarSupportedContent' "
				+ "aria-controls='navbarSupportedContent' aria-expanded='false' aria-label='Toggle navigation'>");
		menuList_b.append("<span class='navbar-toggler-icon'></span>");
		menuList_b.append("</button>");
		menuList_b.append("<div class='collapse navbar-collapse' id='navbarSupportedContent'>");
		//lv0
		for (MenuTO bean0 : lv0) {
			menuList_b.append("<ul class='nav-item dropdown'>");
			menuList_b.append("<a href='#' data-toggle='dropdown' id='navbarDropdown' role='button'"
					+ "aria-haspopup='true' aria-expanded='false' class='nav-link dropdown-toggle'>");
			menuList_b.append(bean0.getMenuName());
			menuList_b.append("</a>&nbsp");
			//lv1
			menuList_b.append("<div class='dropdown-menu' aria-labelledby='navbarDropdown'>");
			for (MenuTO bean1 : lv1) {
				if(bean1.getChildMenu() == null && bean1.getParentMenuCode().equals(bean0.getMenuCode())) {
					if(l!=0) menuList_b.append("<div class='dropdown-divider'></div>");
					menuList_b.append("<a href='"+ bean1.getMenuURL() 
					+"'class='dropdown-item'>" + bean1.getMenuName() + "</a>");
					l++;
				} else if (bean1.getChildMenu() != null && bean1.getParentMenuCode().equals(bean0.getMenuCode())) {
					if(j!=0) {menuList_b.append("<div class='dropdown-divider'></div>");}
					j++;l++;
					menuList_b.append("<a href='#' class='dropdown-item'"
							+ "role='button' id='" + bean1.getMenuCode() +"'"
							+ "data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
							+ bean1.getMenuName() + "</a>");
					menuList_b.append("<ul id='"+bean1.getMenuURL().substring(1)+"'>");
					//lv2
					for (MenuTO bean2 : lv2) {
						if (bean2.getParentMenuCode().equals(bean1.getMenuCode())) {
							menuList_b.append("<li style='list-style-type:disc;'>");
							k++;
							if(k!=0) {menuList_b.append("<div class='dropdown-divider'></div>");}
							
							menuList_b.append("<a href='"+ bean2.getMenuURL() +"'"
									+ "id='" + bean2.getMenuCode()+ "'"
									+ "'class='dropdown-item'>" + bean2.getMenuName() + "</a>");
							menuList_b.append("</li>");
						}
					}
					k=0;
					menuList_b.append("</ul>");
				}
			}
			l=0; 
			menuList_b.append("</div>");
			menuList_b.append("</ul>");
		}
		menuList_b.append("</div>");
		menuList_b.append("</nav>");
		
		//******************************************************************************************
		
		// nav메뉴
		navMenuList.append("<ul class='nav navbar-nav ml-auto'>");
		for (Integer i : treeMap.keySet()) {
			MenuTO bean = treeMap.get(i);
			navMenuList.append("<li class='nav-item'>");
			navMenuList
					.append("<a class='nav-link m' href=" + bean.getMenuURL() + " id=" + bean.getMenuCode() + ">");
			navMenuList.append(bean.getNavMenuName() + "</a>");
			navMenuList.append("</li>");
		}
		navMenuList.append("</ul>");

		menuArr[0] = menuList.toString();
		menuArr[1] = navMenuList.toString();
		menuArr[2] = menuList_b.toString();

		return menuArr;
	}
}
