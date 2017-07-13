// $(document).ready(function () {
//
//     var ooo = $('#megaGosho').attr('data-articleId');
//     /* console.log(ooo);*/
//     loadComments(5, ooo);
//
//     $('#gosho').click(function (event) {
//
//         var $parent = $(event.target).parent();
//
//         var articleId = $parent.attr('data-articleId');
//         var commentContent = $parent.find('textarea').val();
//         var data = JSON.stringify({commentContent: commentContent, articleId: articleId});
//         /*console.log(commentContent);*/
//
//         $.ajax({
//             url: '/comment/create',
//             type: "POST",
//             data: {commentContent: commentContent, articleId: articleId},
//             success: function (data) {
//                 $('#comments').empty();
//                 loadComments(data, articleId);
//                 console.log('current count of comments '+ data);
//
//             },
//             error: function (error) {
//                 console.log(error)
//             }
//         });
//     })
//
// });
//
// function loadComments(numberOfComments, articleId) {
//
//     console.log(numberOfComments);
//     $.ajax({
//
//         url: '/loadComments/' + numberOfComments + '/' + articleId,
//         type: "GET",
//         contentType: "application/json",
//         success: function (data) {
//
//             var $counter =0 ;
//             for (var i = data.length - 1; i >= 0; i--) {
//                 /* var displayDate = (myDate.getMonth()+1) + '/' + (myDate.getDate()) + '/' + myDate.getFullYear();*/
//                 var $div = $('<div></div>');
//                 var entry = data[i];
//                 $div.text(entry['content']);
//                 /*(date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear()*/
//                 var date = new Date(entry['creationDate']);
//                 $div.text(entry['content']);
//                 /*  $('#comments').append($div);*/
//                 $('#datePosted'+$counter).text((date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear() );
//                 $('#'+$counter).text(entry['content']);
//
//                 $counter++;
//                 // console.log($counter);
//
//             }
//         },
//         error: function (error) {
//             console.log(error)
//         }
//     });
// }
//
// $('#likeButton').hover(function () {
//
//     var articleId = $('#likeButton').attr('data-articleId');
//     console.log('article_id = ' + articleId);
//
//
// }
// )

$('#likeButton').click(function () {

    var articleId = $('#likeButton').attr('data-articleId');
    console.log('article_id = ' + articleId);

    $.ajax({
        url: "/article/" + articleId + "/likeCount",
        type: "GET",
        contentType: "application/json",
        success: function (likeCount) {
            console.log(likeCount);
            $("#likeButton").text('Like ' + likeCount);

        },
        error: function (e) {
            console.log("ERROR: ", e);
            //display(e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });

    $.ajax({
        url: "/article/" + articleId + "/likes",
        type: "GET",
        contentType: "application/json",
        success: function (userList) {
            console.log(userList);


            $("#myDropdown").empty();

            userList.forEach(function (user) {
                var $a = $('<p></p>');
                $a.text(user['fullName']);
                $("#myDropdown").append($a);
            });
            myFunction();
        },
        error: function (e) {
            console.log("ERROR: ", e);
            //display(e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
})

// $('#likeButton').click(function () {
//
//         var articleId = $('#likeButton').attr('data-articleId');
//         console.log('article_id = ' + articleId);
//
//         $.ajax({
//             url: "/article/" + articleId + "/comments",
//             type: "GET",
//             contentType: "application/json",
//             success: function (commentList) {
//                 console.log(commentList);
//
//
//                 $("#commentDiv").empty();
//
//                 commentList.forEach(function (comment) {
//                     var $a = $('<p></p>');
//                     $a.text(comment['user']['fullName']);
//                     $a.text(comment['content']);
//
//                     $("#co").append($a);
//                 });
//                 myFunction();
//             },
//             error: function (e) {
//                 console.log("ERROR: ", e);
//                 //display(e);
//             },
//             done: function (e) {
//                 console.log("DONE");
//             }
//         });
//     }
// )

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(event) {
    if (!event.target.matches('.btn-like')) {

        var dropdowns = document.getElementsByClassName("dropdown-menu");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}








