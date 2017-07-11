function register() {
    var id = $("input[name='regAccountId']").val();
    var pws1 = $("input[name='regPasswords']").val();
    var pws2 = $("input[name='regPasswordsC']").val();
    if (pws1 == pws2 && id != '') {
        $.ajax({
            type: 'post',
            url: '/AccountPlatform',
            data: {
                requestType: "reg",
                regAccountId: id,
                regPws: pws1
            },
            success: function (response) {
                if (response == "success") bella.notify("账户 " + id + " 创建完成，请登录");
                else bella.alert("该用户已存在");
            }
        })
    } else {
        bella.alert("注册信息有误");
    }
}
function login() {
    var id = $("input[name='accountId']").val();
    var pws = $("input[name='passwords']").val();
    $.ajax({
        type: 'post',
        url: '/AccountPlatform',
        data: {
            requestType: "login",
            accountId: id,
            pws: pws
        },
        success: function (response) {
            if (response == "success") {
                bella.notify("账户 " + id + " 登录成功，3秒后跳转");
                setTimeout("window.location.href = 'main.jsp'", 3000);
            }
            else bella.alert("账号或密码错误");
        }
    })

}
function update(butt) {
    var div = $(butt).parent(".bl-view-body");
    var cardName = $(div).find('input[name=cardName]').val();
    var cardTel = $(div).find('input[name=cardTel]').val();
    var cardSex = ($(div).find('input[name=cardSex]').val()=="男")?1:0;
    var cardPos = $(div).find('input[name=cardPos]').val();
    var cardCorp = $(div).find('input[name=cardCorp]').val();

    $.ajax({
        type: 'post',
        url: 'Dao',
        data: {
            requestType: "update",
            cardName: cardName,
            cardTel: cardTel,
            cardSex: cardSex,
            cardPos: cardPos,
            cardCorp: cardCorp
        },
        success: function () {
            bella.scrollToTop();
            bella.notify("名片 " + cardName + " 更新成功，3秒后刷新");
            setTimeout("window.location.href = 'main.jsp'", 3000);
        }
    })

}

function del(butt) {
    var div = $(butt).parent(".bl-view-body");
    div = $(div).parent(".bl-tab-view");
    var cardName = $(div).find('input[name=cardName]').val();

    $.ajax({
        type: 'post',
        url: 'Dao',
        data: {
            requestType: "del",
            cardName: cardName
        },
        success: function () {
            bella.scrollToTop();
            bella.alert("名片 " + cardName + " 删除成功，3秒后刷新");
            setTimeout("window.location.href = 'main.jsp'", 3000);
        }
    })

}
function sel1() {
    $('.head-view').css("display","none");
    $('#welcome-view').slideDown(200).fadeIn(200);
}
function sel2() {
    $('.head-view').css("display","none");
    $('#modify-view').slideDown(200).fadeIn(200);
}
function sel3() {
    $('.head-view').css("display","none");
    $('#new-view').slideDown(200).fadeIn(200);
}