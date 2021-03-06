package Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DetailDAO {
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	private Connection getConnection() throws Exception{		
		Context init = new InitialContext();
		
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/pp");
		
		Connection con = ds.getConnection();
		
		return con;
	}
	
	public void resource() {
		if(rs != null) try{rs.close();}catch(Exception e) {e.printStackTrace();}
		if(pstmt != null) try{pstmt.close();}catch(Exception e) {e.printStackTrace();}
		if(con != null) try{con.close();}catch(Exception e) {e.printStackTrace();}		
	}


	public void insertDetail(DetailBean vo) {
		String sql = "";
		int detailnum =0;
		try {
			con = getConnection();

			sql = "select max(detailnum) from details"; 
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				detailnum =  rs.getInt("max(detailnum)") + 1;		 
			}else {		
				detailnum = 1; 
			}
			
			sql ="insert into details(detailnum,name,genre,cla,runtime,price,startdate,enddate,image,content,"
					+ "place,seat,totalreserved,today,starttime) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, detailnum);
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getGenre());
			pstmt.setString(4, vo.getCla());
			pstmt.setInt(5, vo.getRuntime());
			pstmt.setInt(6, vo.getPrice());
			pstmt.setDate(7, vo.getStartdate());
			pstmt.setDate(8, vo.getEnddate());
			pstmt.setString(9, vo.getImage());
			pstmt.setString(10, vo.getContent());
			pstmt.setString(11, vo.getPlace());
			pstmt.setInt(12, vo.getSeat());
			pstmt.setInt(13, vo.getTotalreserved());
			pstmt.setDate(14, vo.getToday());
			pstmt.setString(15, vo.getStarttime());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("insertDetail메소드 에서 예외발생 : " + e);
		}finally {
			resource();
		}
		
	}

	public List<DetailBean> getdetail(String name) {
		String sql="";
		List<DetailBean> detail = new ArrayList<DetailBean>();

		try {
			con = getConnection();

			sql = "select * from details where name=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				DetailBean vo = new DetailBean();
				vo.setDetailnum(rs.getInt("detailnum"));
				vo.setName(rs.getString("name"));
				vo.setGenre(rs.getString("genre"));
				vo.setCla(rs.getString("cla"));
				vo.setRuntime(rs.getInt("runtime"));
				vo.setPrice(rs.getInt("price"));
				vo.setStartdate(rs.getDate("startdate"));
				vo.setEnddate(rs.getDate("enddate"));
				vo.setImage(rs.getString("image"));
				vo.setContent(rs.getString("content"));
				vo.setPlace(rs.getString("place"));
				vo.setSeat(rs.getInt("seat"));
				vo.setTotalreserved(rs.getInt("totalreserved"));
				vo.setToday(rs.getDate("today"));
				vo.setStarttime(rs.getString("starttime"));
				detail.add(vo);
			}
		} catch (Exception e) {
			System.out.println("getdetail메소드 에서 예외발생 : " + e);
		}finally {
			resource();
		}
		return detail;
	}
	
	public DetailBean getdetails(int detail) {
		DetailBean vo = new DetailBean();
		String sql="";
		
		try {
			con = getConnection();

			sql = "select * from details where detailnum=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, detail);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				vo.setDetailnum(rs.getInt("detailnum"));
				vo.setName(rs.getString("name"));
				vo.setGenre(rs.getString("genre"));
				vo.setCla(rs.getString("cla"));
				vo.setRuntime(rs.getInt("runtime"));
				vo.setPrice(rs.getInt("price"));
				vo.setStartdate(rs.getDate("startdate"));
				vo.setEnddate(rs.getDate("enddate"));
				vo.setImage(rs.getString("image"));
				vo.setContent(rs.getString("content"));
				vo.setPlace(rs.getString("place"));
				vo.setSeat(rs.getInt("seat"));
				vo.setTotalreserved(rs.getInt("totalreserved"));
				vo.setToday(rs.getDate("today"));
				vo.setStarttime(rs.getString("starttime"));
			}
		} catch (Exception e) {
			System.out.println("details메소드 에서 예외발생 : " + e);
		}finally {
			resource();
		}
			
		return vo;
	}

	public void UpdateSeat(int num, int sub) {
		String sql = "";
		
		try {
			con = getConnection();

			sql = "update details set totalreserved=? where detailnum=?"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sub);
			pstmt.setInt(2, num);
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("UpdateSeat메소드 에서 예외발생 : " + e);
		}finally {
			resource();
		}
		
	}

	public void detaildelete(String name) {
		String sql="";

		try {
			con = getConnection();

			sql = "delete from details where name=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);

			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("detaildelete메소드 에서 예외발생 : " + e);
		}finally {
			resource();
		}
		
	}
}
