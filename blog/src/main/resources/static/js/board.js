let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{	// function(){}를 사용하지 않고 ()=>{}를 사용한 이유 : this를 바인딩하기 위해서!!!
			this.save();
		});
		
		$("#btn-delete").on("click", ()=>{	// function(){}를 사용하지 않고 ()=>{}를 사용한 이유 : this를 바인딩하기 위해서!!!
			this.deleteById();
		});
		
		$("#btn-update").on("click", ()=>{	// function(){}를 사용하지 않고 ()=>{}를 사용한 이유 : this를 바인딩하기 위해서!!!
			this.update();
		});
		
		$("#btn-reply-save").on("click", ()=>{	// function(){}를 사용하지 않고 ()=>{}를 사용한 이유 : this를 바인딩하기 위해서!!!
			this.replySave();
		});
	},
	
	save: function(){
		//alert('user의 save함수 호출됨');
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		//console.log(data);
		
		// ajax 호출 시 default가 비동기 호출
		// ajax가 통신을 성공 후 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
		$.ajax({
			type : "POST",
			url : "/api/board",
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
			dataType : "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript Object로 변경
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			location.href="/";
		}).fail(function(){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	},
	
	deleteById: function(){
		//alert('user의 save함수 호출됨');
		
		let id = $("#id").text();
		
		//console.log(data);
		
		// ajax 호출 시 default가 비동기 호출
		// ajax가 통신을 성공 후 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
		$.ajax({
			type : "DELETE",
			url : "/api/board/"+id,
			dataType : "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript Object로 변경
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			location.href="/";
		}).fail(function(){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	},
	
	update: function(){
		//alert('user의 save함수 호출됨');
		
		let id = $("#id").val();
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		//console.log(data);
		
		// ajax 호출 시 default가 비동기 호출
		// ajax가 통신을 성공 후 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
		$.ajax({
			type : "POST",
			url : "/api/board/"+id,
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
			dataType : "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript Object로 변경
		}).done(function(resp){
			alert("글수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	},
	
	replySave: function(){
		//alert('user의 save함수 호출됨');
		
		let data = {
			userId: $("#userId").val(),
			content: $("#reply-content").val(),
			boardId: $("#boardId").val()
		};
		
		//console.log(data);
		
		// ajax 호출 시 default가 비동기 호출
		// ajax가 통신을 성공 후 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
		$.ajax({
			type : "POST",
			url : `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
			dataType : "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript Object로 변경
		}).done(function(resp){
			alert("댓글작성이 완료되었습니다.");
			location.href=`/board/${data.boardId}`;
		}).fail(function(){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	},
	
	replyDelete: function(boardId,replyId){
		$.ajax({
			type : "DELETE",
			url : `/api/board/${boardId}/reply/${replyId}`,
			contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
			dataType : "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript Object로 변경
		}).done(function(resp){
			alert("댓글삭제 성공");
			location.href=`/board/${boardId}`;
		}).fail(function(){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	}
}

index.init();