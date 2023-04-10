async function showUser() {
    const url = '/api/v2/user/viewUser'
    let pageInfo = $('#userInfoPage')
    let navbar = $('#navBarInfo')

    await $.getJSON({
        url: url
    })
        .done(function (data) {
            navbar.append(`
                        <span >${data.firstName}</span>
                        <span style="font-weight: lighter;">with roles:</span>
                        <span style="font-weight: lighter;" >${data.roles.map(role => " " + role.role)}</span>
                        `
            )

            pageInfo.append(
                `<tr>
                    <td> ${data.id}</td>
                    <td> ${data.firstName}</td>
                    <td> ${data.lastName}</td>
                    <td> ${data.roles.map(role => " " + role.role)}</td>
                </tr>`
            )
        })
}

showUser()