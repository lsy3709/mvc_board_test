package com.javalec.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalec.ex.dto.BDto;

public class BDao {

	DataSource dataSource = null;
	
	public BDao() {
		// TODO Auto-generated constructor stub
		
	try {
		Context context =  new InitialContext();
		dataSource = (DataSource)context.lookup("java:comp/env/jdbc/orcl");
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
	
	public void write(String bName, String bTitle, String bContent) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0 )";
//			String  query ="insert into mvc_board(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) " +
//			"values (mvc_board_seq.nextval,?,?,?,mvc_board_seq.currval,0,0)";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			int rn = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(connection != null)connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			e2.printStackTrace();
			}
		}
	}
	
	public ArrayList<BDto> list() {
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "select * from mvc_board order by bGroup desc, bStep asc";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(connection != null)connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return dtos;
	}
	
	public BDto contentView(String bId) {
		
		upHit(bId);
		
		BDto dto = new BDto();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		
		try {
			conn = dataSource.getConnection();
			
			String query = "select * from mvc_board where bId = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				int bId2 = Integer.parseInt(bId);
				dto = new BDto(bId2, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
						
			}
					
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	} finally {
		try {
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(conn != null)conn.close();
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
		
	}
		return dto;
		
		
	}
	
	public void modify(String bId, String bName, String bTitle, String bContent) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "update mvc_board set bName = ?, bTitle = ? , bContent = ? where bId =?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			int rn = pstmt.executeUpdate();
							
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	public void delete(String bId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "delete from mvc_board where bId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			int rs = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	public BDto reply_view(String bId) {
		BDto dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "select * from mvc_board where bId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			rs = pstmt.executeQuery();

			if(rs.next()) {
				int bId2 = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				dto = new BDto(bId2, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return dto;
	}
	
		
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent) {
	
		replyShape(bGroup, bStep);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent)"+
			" values (mvc_board_seq.nextval,?,?,?,?,?,?)";
					
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep)+1);
			pstmt.setInt(6, Integer.parseInt(bIndent)+1);
			
			int rn = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
	}
	
	private void replyShape(String strGroup, String strStep) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strStep));
			int rn = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	private void upHit(String bId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bId);
			
			int rn = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
		
	}
	
}





