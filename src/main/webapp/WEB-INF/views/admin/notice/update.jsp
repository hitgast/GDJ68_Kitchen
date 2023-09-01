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
            border: 1px solid black;
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
        #title{
        	margin-bottom: 30px;
        }
        .contents{
        	border: 1px solid black;
        }
        #vitalCheck{
        	margin-left: 15px;
        }
        #noticeTitle{
            position: relative;
            left: 11px;
            width: 803px;
            height: 32px;
            margin-top: 10px;
    		margin-bottom: 10px;
    		margin-bottom: 5px;
        }
        .tableTh{
            width: 80px;
            background-color: bisque;
            text-align: center;
        }
        td{
            padding-left: 10px;
        }
        #butDiv{
            text-align: center;
        }
        #addBtn{
            border-radius: 5px;
            border: none;
            cursor: pointer;
            background-color: bisque;
            height: 32px;
            margin: 50px;
        }
        
        #foreachDiv{
        	margin-top: 10px;
        }
        .fileDelete{
        	font-weight: bold;
        	color: red;
		    position: relative;
		    left: 740px;
		    cursor: pointer;
        }
        .fd{
        	font-weight: bold;
        	color: red;
		    position: relative;
		    left: 420px;
		    cursor: pointer;
        }
        .fileDiv{
            margin: 10px;
        	border-bottom: 1px solid black;
        }
        .files{
        	margin: 10px;
        }
        #fileAddDiv{
        	margin: 10px;
        	margin-top: 20px;
			border-top: 1px solid black;
        }
        #fileAddBtn{
        	border-radius: 5px;
        	border: none;
            cursor: pointer;
            background-color: bisque;
            margin: 15px;
            width: 90px;
        }
        
        table img{
        	width: 80px;
        	margin-right: 10px;
        }
    </style>
</head>
<body>
	<c:import url="/WEB-INF/views/temp/header.jsp"></c:import>
	<div id="nav01">
        <a href="/notice/list">공지사항</a>
        <a href="https://nonchalant-session-026.notion.site/bfda421401da41cb9d2d9622a99c8584?pvs=4" target="_blank">자주찾는 질문</a>
    </div>
    <div id="nav02">
        <div id="title">
            <h2>공지사항 수정</h2>
        </div>
        
        <form method="post" id="frm" enctype="multipart/form-data">
            <table>
                <tr>
                    <th class="tableTh">제목</th>
                    <td>
                        <input type="checkbox" id="vitalCheck" name="vitalCheck" value="${kto.vitalCheck}"> 필독
                        <input type="text" id="noticeTitle" name="noticeTitle" value="${kto.noticeTitle}">
                    </td>
                </tr>
                <tr>
                    <th class="tableTh">내용</th>
                    <td>
                        <textarea cols="110" rows="30" id="noticeContents" name="noticeContents">${kto.noticeContents}</textarea>
                    </td>
                </tr>
                <tr>
                    <th class="tableTh">첨부 파일</th>
                    <td>
                    	<c:forEach items="${kto.ktos}" var="f">
                    		<div id="foreachDiv">
		                        <img src="/resources/upload/notice/${f.fileName}">
		                        <span class="fileDelete" data-delete-num="${f.fileNum}">✕</span>
                    		</div>
                    		<span>${f.originalName}</span>
                    	</c:forEach>
                    	
                    	<div id="fileAddDiv">
	                    	<button type="button" id="fileAddBtn">파일 추가</button>
                    	</div>
                    	<div id="fileList">
                    		
                    	</div>
                    </td>
                </tr>
            </table>

            <div id="butDiv">
                <button id="addBtn">등록하기</button>
            </div>
        </form>
    </div>
    
	<script>
		// 필독 체크여부 확인
		if('${kto.vitalCheck}' === "1"){
			$("#vitalCheck").prop("checked", true);
		} else{
			$("#vitalCheck").prop("checked", false);
		}
	
		// 필독 체크시
		$("#vitalCheck").click(function(){    
            if($("#vitalCheck").is(":checked") == true){
                $("#vitalCheck").val("1");
            } else{
                $("#vitalCheck").val("0");
            }
        });
		
		// file delete
		$(".fileDelete").click(function() {
			let check = confirm("삭제시 복구 불가");
			let num = this.getAttribute("data-delete-num");
			
			if(check){
				$(this).parent().next().remove();
				this.parentNode.remove();
				this.remove();
				count--;
				
				$.ajax({
					type:"get",
					url:"fileDelete?fileNum=" + num,
				});
			}
		});
		
		// 기존 file add
		let count = 0;
		let idx = 0;
		if($(".fileDelete") != null){
			count = $(".fileDelete").length;
		}
		
		
		$("#fileAddBtn").click(function() {
			
			if(count >= 5){
				alert("최대 5개의 파일만 업로드 가능합니다");
				
				return;
			}
			let r = "<div class='fileDiv'>";
			r += "<input type='file' name='files' class='files'>";
			r += "<span class='fd' data-id='file" + idx + "'>✕</span>";
			r += "</div>";
			
			$("#fileList").append(r);
			
			count++;
			idx++;
		});
		
		// 추가된 file delete
		$("#fileList").on("click", ".fd", function() {
			$(this).parent().remove();
			
			count--;
		});
	</script>
</body>
</html>