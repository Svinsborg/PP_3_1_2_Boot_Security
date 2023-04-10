async function show() {
    const listUsers = document.querySelector('.list-users')
    let output = '';
    const url = '/api/v1/user'

    await fetch(url)
        .then(res => res.json())
        .then(date => {
                date.forEach(u => {
                    output += `
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.firstName}</td>
                        <td>${u.lastName}</td>
                        <td>${u.roles.map(role => " " + role.role)}</td>
                
                        <td>
                            <button type="button" class="btn btn-info"                                    
                                    data-bs-toggle="modal"
                                    data-bs-target="#editUser"
                                    data-bs-userId="${u.id}"
                                    data-row="${u}">
                                EDIT
                            </button>
                        </td>
                
                        <td>
                            <button type="button" class="btn btn-danger"
                                    data-bs-toggle="modal"
                                    data-bs-target="#deletUser"
                                    data-userId="${u.id}"
                                    data-row="${u}">
                                DELETE
                            </button>
                        </td>
                    </tr>
                `;
                })
                listUsers.innerHTML = output
            }
        )
}

async function showJq() {
    await $.getJSON({
        url: '/api/v1/user'
    })
        .done(function (data) {
            let listUsersAjax = $('.list-users')
            data.forEach(u => {
                listUsersAjax.append(
                    `<tr>
                        <td>${u.id}</td>
                        <td>${u.firstName}</td>
                        <td>${u.lastName}</td>
                        <td>${u.roles.map(role => " " + role.role)}</td>
                
                        <td>
                            <button type="button" class="btn btn-info"                                    
                                    data-bs-toggle="modal"
                                    data-bs-target="#editUser"
                                    data-bs-userId="${u.id}"
                                    data-row="${u}">
                                EDIT
                            </button>
                        </td>                
                        <td>
                            <button type="button" class="btn btn-danger"
                                    data-bs-toggle="modal"
                                    data-bs-target="#deletUser"
                                    data-userId="${u.id}"
                                    data-row="${u}">
                                DELETE
                            </button>
                        </td>
                    </tr>`
                )
            })
        })
        .fail(function () {
            alert("error");
        })
}

async function showAjax() {

    await $.ajax({
        url: '/api/v1/user',
        type: "get",
        cache: false,
        success:(function (data) {
            data.forEach(u => {
                $('.list-users').append(
                    `<tr>
                        <td>${u.id}</td>
                        <td>${u.firstName}</td>
                        <td>${u.lastName}</td>
                        <td>${u.roles.map(role => " " + role.role)}</td>                
                        <td>
                            <button type="button" class="btn btn-info"                                    
                                    data-bs-toggle="modal"
                                    data-bs-target="#editUser"
                                    data-bs-userId="${u.id}"
                                    data-row="${u}">
                                EDIT
                            </button>
                        </td>                
                        <td>
                            <button type="button" class="btn btn-danger"
                                    data-bs-toggle="modal"
                                    data-bs-target="#deletUser"
                                    data-userId="${u.id}"
                                    data-row="${u}">
                                DELETE
                            </button>
                        </td>
                    </tr>`
                )
            })
        })
    })
}

showAjax();

//showJq()
//show()