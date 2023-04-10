$('#editUser').on('shown.bs.modal', function (event){
    const button = event.relatedTarget
    const userid = button.getAttribute('data-bs-userId')
    event.preventDefault();
    if (userid) {
        $.get({
            url: '/api/v1/user/' + userid,
            success: (data) => {
                $.get({
                        url: '/api/v1/user/roles',
                    success: (roles) => {
                        roles.forEach(r =>{
                            modal.find('#edit-roles').append(`
                                     <option 
                                     value="${r.id}">
                                            ${r.role}
                                     </option>`);
                        })
                        }
                })
                let modal = $(this)
                modal.find('#userId').val(data.id)
                modal.find('#edit-firstName').val(data.firstName)
                modal.find('#edit-lastName').val(data.lastName)
                modal.find('#edit-password').val(data.password)
            },
            error: (err) => {
                alert(err);
            }
        });
    }
})
//
$('#saveUserButton').click(async function (e) {
    let modal = $('#editUser')
    let rolesId = Number($('#edit-roles').val());
    let user;
    e.preventDefault();
    await $.getJSON({
        url: '/api/v1/user/roles/' + rolesId
    })
        .done(function(data){
            user =
                {
                    id: modal.find('#userId').val(),
                    firstName: modal.find('#edit-firstName').val(),
                    lastName: modal.find('#edit-lastName').val(),
                    password: modal.find('#edit-password').val(),
                    roles:
                        [
                            {
                                id: rolesId,
                                role: data.role
                            }
                        ],
                };
        })
        .fail(function () {
            alert("error");
        });

    $.ajax({
        url: '/api/v1/user/',
        type: 'PATCH',
        data: JSON.stringify(user),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(){
            $('.list-users').empty()
            showAjax()
        },
        error: (err) => {
            alert(err);
        }
    })
    modal.modal('hide');
});