async function loadRoles() {
await $.get({
    url: '/api/v1/user/roles',
    success: (roles) => {
        console.log( roles.map)
        roles.forEach(r =>{
            $('#select-roles').append(`
                                     <option 
                                     value="${r.id}">
                                            ${r.role}
                                     </option>`);
        })
    }
})
}
loadRoles()

$('#createUser').click(async function (e) {
    console.log("Saved!")
    //e.preventDefault();
    let url = '/api/v1/user/'
    let createUser = $('#editUser')
    let msg = $('#errorCreate')
    let checkByName
    let id = $('#select-roles').val()
    let role = $('#select-roles option:selected').text().replace(/[^a-zа-яё]/gi, '')
    console.log(createUser)
    let user =
        {
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            password: $('#password').val(),
            roles: [
                {
                id: parseInt(id[0]),
                role: role,
                authority: role
                }
        ]
        };
    console.log(user)

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
            $('.list-users').empty()
            showAjax()
        },
        error: (err) => {
            alert(err);
        }
    })
})