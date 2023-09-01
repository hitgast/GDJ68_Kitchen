<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<c:import url="/WEB-INF/views/temp/bootStrap.jsp"></c:import>
	<style>
        #nav01{
        	height: 60px;
        	line-height: 60px; 
            border-top: 1px solid black;
    		border-bottom: 1px solid black;
            padding-right: 700px;
            text-align: center;
            margin-bottom: 150px;
            font-size: 18px;
        }
        #nav01 a{
            display: inline-block;
            width: 120px;
            text-decoration: none;
        }
        #nav02{
            width: 1000px;
            margin: auto;
        }
        .contents{
        	line-height: 40px; 
            border-bottom: 1px solid black;
            padding-left: 30px;
            position: relative;
            font-size: 17px;
        }
        .conTitle{
        	font-weight: bold;
        }
        .contents span:nth-child(2){
        	padding-left: 10px;
        	font-weight: bold;
        	color: red;
        }
        .contents span:nth-child(3){
            position: absolute;
            left: 180px;
        }
        .contents span:nth-child(4){
            position: absolute;
            right: 70px;
        }
        #title{
        	margin-bottom: 30px;
        }
        #title h2{
            display: inline-block;
        }
        #addBtn{
            position: relative;
            left: 600px;
            bottom: 6px;
            height: 32px;
            border: none;
            cursor: pointer;
        }
        #deleteBtn{
            position: relative;
            left: 600px;
            bottom: 6px;
            height: 32px;
            border: none;
            cursor: pointer;
        }
        
        #nav03{
        	text-align: center;
		    margin-top: 50px;
		    font-size: 20px;
		    font-weight: bold;
        }
        #nav03 a{
        	margin-right: 5px;
        }
        
    </style>
</head>
<body>
	<c:import url="/WEB-INF/views/temp/header.jsp"></c:import>
	
	<div id="nav01">
        <a href="/notice/list">공지사항</a>
        <a href="https://www.notion.so/d87dd9eefdde460f90a773b47fa833f3?pvs=4" target="_blank">자주찾는 질문</a>
    </div>
    <div id="nav02">
        <div id="title">
            <h2>공지사항 관리</h2>
            <button id="addBtn">공지사항 작성</button>
            <button id="deleteBtn">삭제</button>
        </div>

		<div class="contents conTitle">
			<input type="checkbox" id="allCheck">
            <span></span>
            <span>제목</span>
            <span>작성날짜</span>
       	</div>
		<c:forEach items="${list}" var="kto">
			<div class="contents">
				<input type="checkbox" class="check01" value="${kto.noticeNum}">
				<c:choose>
					<c:when test="${kto.vitalCheck == '1'}">					
			            <span>필독</span>
					</c:when>
					<c:otherwise>
						<span></span>
					</c:otherwise>
				</c:choose>
	            <span><a href="detail?noticeNum=${kto.noticeNum}">${kto.noticeTitle}</a></span>
	            <span>${kto.noticeDate}</span>
        	</div>
		</c:forEach>
    </div>
    
    <div id="nav03">
    	<c:if test="${pager.page > 1}">
	    	<a href="list?page=${pager.page - 1}">
		    	<span>◁</span>
	    	</a>
	    </c:if>
    
    	<c:forEach var="i" begin="${pager.startNum}" end="${pager.lastNum}">
    		<a href="list?page=${i}" class="pageColor">${i}</a>
    	</c:forEach>
    	
    	<c:if test="${pager.page < pager.totalPage}">
	    	<a href="list?page=${pager.page + 1}">
	    		<span>▷</span>
	    	</a>
    	</c:if>
    </div>

    <script>
    	// 현재 페이지의 a태그 색깔 변화
    	$(".pageColor").each(function(i, e) {
			if($(this).text() == ${pager.page}){
				$(this).css("color", "red");
			} 
		});
    
        // 공지사항 전체 체크/해제
        $("#allCheck").click(function(){
            
            if($("#allCheck").is(":checked")){
                for(i of $(".check01")){
                    i.checked = true;
                }
                
                $(".check01").each(function(i, e) {
                	noticeNum.push(e.value);
				});
                
            } else{
                for(i of $(".check01")){
                    i.checked = false;
                }
                
                noticeNum = [];
            }
            console.log(noticeNum);
        });

        // 공지사항 단일글 체크해제 시 전체체크 해제
        $(".check01").each(function(i, e){
        
            $(e).click(function(){
                if($(e).is(":checked") == false){
                    $("#allCheck").prop("checked", false);
                } 
            });
        });

        // 공지사항 작성 button 클릭시
        $("#addBtn").click(function(){
            $(location).attr("href", "add");
        });
        
        // 체크 클릭시 각 체크박스의 값 넘기기
       	let noticeNum = [];
        $(".check01").click(function() {
        	
        	let valIndex = noticeNum.indexOf($(this).val()); 
        	
        	if($(this).is(":checked")){
        		noticeNum.push($(this).val());
        		//noticeNum[noticeNum.length] = $(this).val();
        	} else{ 
        		noticeNum.splice(valIndex, 1);
        	}     
        	console.log(noticeNum);
        });
        
        // delete 버튼
        $("#deleteBtn").click(function() {
        	
        	if(noticeNum.length == 0){
        		alert("삭제할 게시물 선택하세요.");
        	}
        	
        	noticeNum.forEach(function(e) {
	        	$.ajax({
	        		type:"post",
	        		url:"delete",
	        		data:{
	        			noticeNum:e
	        		},
	        		success:function(){
	        			$(location).attr("href", "list");
	        		}
	        	});	        		
			});
		});
        
    </script>
</body>
</html>