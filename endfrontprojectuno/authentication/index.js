window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        method: 'GET',
        credentials: 'include'
    });

    if(res.status === 200){
        let userObj = await res.json();

        if(userObj.role === 'Employee'){
            window.location.href = '#';
        } else if (userObj.role === 'Finance manager'){
            window.location.href = '#';
        }
    }

});

// Login

let loginButton = document.querySelector('#login');
loginButton.addEventListener('click', loginButtonClicked);

function loginButtonClicked() {
    login();
}

async function login() {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');

    try {
        let res = await fetch('http://localhost:8080:login', {
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify({
                username: usernameInput.value,
                password: passwordInput.value
            })
        });

        let data = await res.json();
        
        if(res.status === 400){
            let loginErrorMessage = document.createElement('p');
            let loginDiv = document.querySelector('#login-info');

            loginErrorMessage.innerHTML = data.message;
            loginErrorMessage.style.color = 'red';
            loginDiv.appendChild(loginErrorMessage);
        }

        if (res.status === 200){
            if(data.role === 'Employee') {
                window.location.href = '#.html';
            } else if (data.role === 'Finance mamager'){
                window.location.href = '#.html';
            }
        }
    } catch(err){
        console.log(err);
    }
}

// Sign up

let signupButton = document.querySelector('#signup');
signupButton.addEventListener('click', signupButtonClicked);

function signupButtonClicked() {
    signup();
}

async function signup() {
    let firstnameInput = document.querySelector('#firstname');
    let lastnameInput = document.querySelector('#lastname');
    let emailInput = document.querySelector('#email');
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');
    let userRoleRadio = document.querySelector('#userRadio');

    try {
        let res = await fetch('http://localhost:8080:signup', {
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify({
                username: usernameInput.value,
                password: passwordInput.value,
                firstname: firstnameInput.value,
                lastname: lastnameInput.value,
                email: emailInput.value,
                role: userRoleRadio.value,
            })
        });

        let data = await res.json();
        
        if(res.status === 400){
            let signupErrorMessage = document.createElement('p');
            let signupDiv = document.querySelector('#signup-info');

            signupErrorMessage.innerHTML = data.message;
            signupErrorMessage.style.color = 'red';
            signupDiv.appendChild(signupErrorMessage);
        }

        if (res.status === 200){
            if(data.role === 'Employee') {
                window.location.href = '#.html';
            } else if (data.role === 'Finance Manager'){
                window.location.href = '#.html';
            }
        }
    } catch(err){
        console.log(err);
    }
}
