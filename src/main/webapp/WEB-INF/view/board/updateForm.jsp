<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container my-3">
    <form>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Enter title" name="title" id="title" value="${board.title}">
        </div>

        <div class="form-group">
            <textarea class="form-control summernote" rows="5" id="content" name="content">
                ${board.content}
            </textarea>
        </div>
        <button type="button" onclick="updateById(${board.id})" class="btn btn-primary">글수정완료</button>
    </form>
</div>

<script>
    function updateById(id) {
        // 값 받아오기
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };
        // 수정
        $.ajax({
            type: "put",
            url: "/board/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json" // default : 응답의 mime 타입으로 유추함
        }).done((res) => {
            alert(res.msg);
            location.href = "/";
        }).fail((err) => { 
            alert(err.responseJSON.msg);
        });
    }
</script>

<script>
    $('.summernote').summernote({
        tabsize: 2,
        height: 400
    });
</script>
<%@ include file="../layout/footer.jsp" %>