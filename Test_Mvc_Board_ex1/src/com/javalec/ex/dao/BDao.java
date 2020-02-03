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

import oracle.jdbc.proxy.annotation.Pre;

public class BDao {

	DataSource dataSource = null;
	
	public BDao() {
		// TODO Auto-generated constructor stub
		
	try {
		Context context = new InitialContext();
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
			String  query ="insert into mvc_board(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent) " +
			"values (mvc_board_seq.nextval,?,?,?,SYSDATE,mvc_board_seq.currval,0,0)";
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
			String query = "select bId,bName, bTitle, bContent, bDate, bHit,bGroup,bStep,bIndent from mvc_board "
					+ "by bGroup desc, bStep asc";
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
	
	
}





