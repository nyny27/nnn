package admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.MemberBean;
import member.MemberDAO;
import noticeboard.NoticeboardBean;
import noticeboard.NoticeboardDAO;


@WebServlet("/admin/*")
public class AdminController extends HttpServlet{

	AdminDAO adminDAO = new AdminDAO();
	AdminBean adminBean = new AdminBean();
	NoticeboardBean noticebean = new NoticeboardBean();
	NoticeboardDAO noticeDAO = new NoticeboardDAO();
	
	
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doHandle(request, response);
		}
		
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doHandle(request, response);
		}
		
		protected void doHandle(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			
			
			//MVC 중 View이름을 저장할 변수
			String nextPage = "";
			//한글처리
			request.setCharacterEncoding("utf-8");
			//클라이언트의 웹브라우저로 응답할 데이터 유형 설정
			response.setContentType("text/html;charset=utf-8");
			
			//요청 URL중 2단계 요청 주소를 알아내온다
			String action = request.getPathInfo();  //  /listArticles.do 
			System.out.println("action : " + action);
			
			List<AdminBean> articlesList = null;
			
			if(action.equals("/adminPage.do")) { 
				
				
				nextPage = "/admins/adminMain.jsp";
				
			//회원정보 전체 조회
			} else if(action.equals("/MemberModify.do")) {
				
				//회원 아이디 검색값 받아오기
				String search = "";
				if(request.getParameter("search")==null) {
					search = "";
				} else {
					search = request.getParameter("search");
				}
				
				String selectserach = request.getParameter("selectserach");
				
				System.out.println(selectserach);
				System.out.println(search);
				AdminDAO adminDAO = new AdminDAO();
				
				//페이징 처리 변수
				//회원 총 수 구해오기
				int total = adminDAO.AllMemberCount(search); //회원 총 수
				int pageSize = 5; // 한 페이지당 출력할 게시물 수
				int nowPage = 1; // 현재 페이지
				if(request.getParameter("nowpage") != null) {
					nowPage = Integer.parseInt(request.getParameter("nowpage"));
				}
				int startRow = (nowPage-1)*pageSize; // 첫 번째 게시물 번호
				int endRow = pageSize; // 마지막 게시물 번호 
				int blocksize = 5; // 페이징 네비 사이즈
				int totalPage = total / pageSize + (total%pageSize==0? 0:1); // 총 페이지 수
				int blockfirst = ((nowPage/blocksize)-(nowPage%blocksize==0?1:0)) * blocksize + 1; // 페이징 네비 첫번째 번호
				int blocklast = blockfirst+blocksize-1; // 페이징 네비 마지막 번호
				if(blocklast > totalPage) { // 총 페이지수보다 블럭라스터값이 더 클때 totalPage(총 페이지 수)가 blocklast로된다.
					blocklast = totalPage;
				}
				
				List<AdminBean> list = null;
				
				//회원 전체 정보 조회
				if(selectserach == null) {
					list = adminDAO.getAllmember(startRow, endRow, search);
				} else {
					list = adminDAO.getAllmember(startRow, endRow, search, selectserach);
				}
				
				
				//list에 멤버정보를 담음
				String id = "test";
				
				//검색한 글정보(응답할 데이터)를  VIEW페이지(listArticles.jsp)로 보내서 출력하기 위해
				//임시로 request저장소에 저장 하여 유지 시킨다
				
				//회원정보
				request.setAttribute("list", list);
				
				//페이징 변수
				request.setAttribute("total", total);
				System.out.println(total);
				request.setAttribute("nowpage", nowPage);
				request.setAttribute("blockfirst", blockfirst);
				request.setAttribute("blocksize", blocksize);
				request.setAttribute("blocklast", blocklast);
				request.setAttribute("totalPage", totalPage);
				
				nextPage = "/admins/adminModify.jsp";
				System.out.println(action);
			
			//회원 삭제 요청 (관리자 페이지)
			} else if(action.equals("/MemberDel.do")) {
				
				String[] id = request.getParameterValues("check"); //getParameterValues는 배열값을 받아올때 사용한다 .
				for (int i=0; i<id.length; i++) {
					System.out.println(id[i]);
					adminDAO.MemberDelete(id[i]);
					
				}
				
				nextPage= "/admin/MemberMan.do";
				
			} else if(action.equals("/MemberView.do")) {
				
				String id =request.getParameter("id");
				String nowpage = request.getParameter("nowpage"); 
//				System.out.println(nowpage);
//				System.out.println(id);
				adminBean = adminDAO.MemberView(id);
				
//				System.out.println(adminBean.getEmail());
//				System.out.println(adminBean.getId());
				request.setAttribute("memberInfo", adminBean);
				request.setAttribute("nowpage", nowpage);
				nextPage = "/admins/adminView.jsp";
			
			//회원 정보 수정 관리 요청
			} else if(action.equals("/MemberUpdate.do")) {
				
				MemberBean memberInfo = new MemberBean();
				int result = 0;
				
				memberInfo.setId(request.getParameter("id"));
				memberInfo.setPassword(request.getParameter("password"));
				memberInfo.setName(request.getParameter("name"));
				memberInfo.setPhone(request.getParameter("phone"));
				memberInfo.setEmail(request.getParameter("email"));
				memberInfo.setPoint(Integer.parseInt(request.getParameter("point")));
				memberInfo.setStatus(Integer.parseInt(request.getParameter("status")));
				memberInfo.setIs_admin(Integer.parseInt(request.getParameter("admin")));
				
				result = adminDAO.MemberUpdate(memberInfo);
				
				nextPage= "/admin/MemberModify.do";
			} else if(action.equals("/MemberDelete.do")) {
				int result = 0;
				String id = request.getParameter("id");
				result = adminDAO.MemberDelete(id);
				
				nextPage= "/admin/MemberMan.do";
				
			} else if(action.equals("/MemberSearch.do")) {
				
				
				String search = request.getParameter("search");
				System.out.println(search);
				
				AdminDAO adminDAO = new AdminDAO();
				
				nextPage = "/admins/adminManager.jsp";
				
			//상품관리 페이지 이동
			} else if(action.equals("/ProductMan.do")) {
				
				nextPage = "/admins/productManager.jsp";
				
			//이벤트 관리 페이지 이동
			} else if(action.equals("/EventMan.do")) {
				
				nextPage = "/admins/EventManager.jsp";
				
			//고객 관리 페이지 이동
			} else if(action.equals("/CustomerMan.do")) {
				
				nextPage = "/admins/CustomerManager.jsp";
			
			//문의 답변 페이지 이동
			} else if(action.equals("/Answer.do")) {
				
				nextPage = "/admins/Answer.jsp";
				
			//회원관리 페이지 이동	
			} else if (action.equals("/MemberMan.do")) {
				
				nextPage = "/admins/adminManager.jsp";
			} else if (action.equals("/MemberJoinCount.do")) {
				
				nextPage = "/admins/MemberJoinCount.jsp";
			
			//관리자 페이지 고객센터 관리 페이지 이동 
			} else if (action.equals("/InformationMain.do")) {
				
				
				String n_cate = request.getParameter("n_cate");
				String n_title = request.getParameter("n_title");
				String n_date = request.getParameter("n_date");

				int total = noticeDAO.getAllNotice();
				System.out.println(total);

				if(n_cate != null)total = noticeDAO.getAllnotice(n_cate);
				System.out.println(total);			
				
				int pageSize = 5;
				int nowPage = 1;
				if(request.getParameter("nowPage") != null) nowPage = Integer.parseInt(request.getParameter("nowPage"));
				
				int pageFirst = (nowPage-1) * pageSize;
				int totalPage = total/pageSize + (total%pageSize==0?0:1);
				int blockSize = 10;
				int blockFirst = (nowPage/blockSize-(nowPage%blockSize==0?1:0))*blockSize + 1;
				int blockLast = blockFirst + blockSize -1;
				
				if(blockLast>totalPage) blockLast=totalPage;
				
				List<NoticeboardBean> noticeList = noticeDAO.noticeList(pageFirst, pageSize);
				
				if(n_cate != null)noticeList = noticeDAO.noticeList(n_cate, pageFirst, pageSize);
					request.setAttribute("noticeList", noticeList);
					request.setAttribute("blockSize", blockSize);
					request.setAttribute("blockFirst", blockFirst);
					request.setAttribute("blockLast", blockLast);
					request.setAttribute("totalPage", totalPage);
					request.setAttribute("nowPage", nowPage);
					request.setAttribute("n_cate", n_cate);
					
					System.out.println(n_cate);
				
				nextPage = "/admins/informationMain.jsp";
				
			//관리자 페이지 공지사항 관리 이동 	
			} else if(action.equals("/informationwrite.do")) {
				
					nextPage = "/admins/informationwrite.jsp";
					
			//관리자 페이지 글 작성
			} else if(action.equals("/insertWrite.do")) {
			
			String n_cate = request.getParameter("n_cate");
			String n_title = request.getParameter("n_title");
			String n_content = request.getParameter("n_content");
			
			noticebean.setN_cate(n_cate);
			noticebean.setN_title(n_title);
			noticebean.setN_content(n_content);
			noticebean.setN_date(new Timestamp(System.currentTimeMillis()));
			
			noticeDAO.insertNoticeboard(noticebean);
			nextPage = "/admin/InformationMain.do";	
			
			//관리자 페이지 공지 글 상세보기
			} else if(action.equals("/viewNotice.do")) {
				
				String n_num = request.getParameter("n_num");
				
				noticebean = noticeDAO.viewNotice(Integer.parseInt(n_num));
				request.setAttribute("notice", noticebean);
				
				nextPage = "/admins/adminviewNotice.jsp";
				
				
			}
			//디스패치 방식으로 포워딩 (재요청)
			request.getRequestDispatcher(nextPage).forward(request, response);
		
		}// doHandle END
		
				
	
	
}
