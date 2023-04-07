$('#deletUser').on('shown.bs.modal', function (event){
    let button = $(event.relatedTarget) // Button that triggered the modal
    let userId = button.data('userid') // Extract info from data-* attributes

    if (userId) {
        $.get({
            url: '/api/v1/user/' + userId,
            success: (data) => {
                let modal = $(this)
                let r = ""
                let r2 = data.roles
                for (let i=0; i <= r2.length - 1 ; i++) {
                    r = r + " " + r2[i].role
                }
                modal.find('#delUserid').val(data.id)
                modal.find('#del-firstName').val(data.firstName)
                modal.find('#del-lastName').val(data.lastName)
                modal.find('#del-password').val(data.password)
                modal.find('#del-userRoles').val(r)
            },
            error: (err) => {
                alert(err);
            }
        });
    }
})

$('#delUserButton').click( function (e) {
    let modal = $('#deletUser')
    let userId = modal.find('#delUserid').val()
    e.preventDefault();
    $.ajax({
        url: '/api/v1/user/' + userId,
        type: 'DELETE',
        success: function(){
            $('.list-users').empty()
            showAjax()
        },
        error: function (response) {
            alert(response.status + ' ' + response.statusText);
        }
    });
    modal.modal('hide');
});