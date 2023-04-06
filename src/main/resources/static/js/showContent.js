function show() {
    console.log("Start content")
    const listUsers = document.querySelector('.list-users')
    let output = '';
    const url = '/api/v1/user'

    fetch(url)
        .then(res => res.json())
        .then(date => {
                date.forEach(u => {
                    console.log(u)
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
            console.log(data)
            data.forEach(u => {
                console.log(u.firstName)
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

showJq()
//show()