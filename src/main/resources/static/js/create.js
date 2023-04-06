$('#createUser').click(async function () {
    console.log("Saved!")
    let url = '/api/v1/user/'
    let createUser = $('#editUser')
    let msg = $('#errorCreate')
    let checkByName
    console.log(createUser)
    let user =
        {
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            password: $('#password').val(),
        };

    await $.get({
        url: '/api/v1/user/name/' + user.firstName,
        success: (data) => {
            checkByName = data
            console.log("Check by name = " + checkByName)
        },
    });


    if(checkByName === true){
        msg.text("User with name exists")
        msg.show();
        return false
    } else
    if(user.firstName === ""){
        msg.text("Input name user!")
        msg.show();
        return false
    } else if(user.lastName === ""){
        msg.text("Input last name!");
        msg.show();
        return false
    } else if(user.password === ""){
        msg.text("Input password!")
        msg.show();
        return false
    }

    msg.text("")
    msg.hide();
    console.log(user)

    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(user),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function () {
            location.reload();
        },
        error: (err) => {
            alert(err);
        }
    })
})