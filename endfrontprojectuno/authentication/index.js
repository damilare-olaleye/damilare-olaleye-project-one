window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        method: 'GET',
        credentials: 'include'
    });

    if(res.status === 200){
        let userObj = await res.json();

        if (res.status === 200){
            if(data.role === 'Employee') {
                window.location.href = '/employee/home.html';
            } else if (data.role === 'Finance Manager'){
                window.location.href = '/finance-manager/finance-manager.html'; 
            }
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
    const usernameInput = document.querySelector('#username');
    const passwordInput = document.querySelector('#password');

    try {
        let res = await fetch('http://localhost:8080/login', {
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

            loginErrorMessage.innerHTML = '';

        } else if (res.status === 404){
            window.location.href = '../../404/404.html';
        }

        if (res.status === 200){
            if(data.role === 'Employee') {
                window.location.href = '/employee/home.html';
            } else if (data.role === 'Finance Manager'){
                window.location.href = '/finance-manager/finance-manager.html'; 
            }
        } else if (res.status === 404){
            window.location.href = '../../404/404.html';
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
    const firstnameInput = document.querySelector('#firstname');
    const lastnameInput = document.querySelector('#lastname');
    const emailInput = document.querySelector('#email');
    const usernameInput = document.querySelector('#username');
    const passwordInput = document.querySelector('#password');
    const roleInput = document.querySelector('#role');

    let tbodyElement = document.querySelector("#signup-info");
    tbodyElement.innerHTML = '';

    try {
        let res = await fetch('http://localhost:8080/signup', {
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify({
                username: usernameInput.value,
                password: passwordInput.value,
                firstname: firstnameInput.value,
                lastname: lastnameInput.value,
                email: emailInput.value,
                role: roleInput.value
                
            })
        });

        let data = await res.json();
        
        if(res.status === 400 || res.status === 404){
            let signupErrorMessage = document.createElement('p');
            let signupDiv = document.querySelector('#signup-info');

            signupErrorMessage.innerHTML = data.message;
            signupErrorMessage.style.color = 'red';
            signupDiv.appendChild(signupErrorMessage);
            signupErrorMessage.innerHTML = '';

        }

        if (res.status === 200){
            if(data.role === 'Employee') {
                window.location.href = '/employee/home.html';
            } else if (data.role === 'Finance Manager'){
                window.location.href = '/finance-manager/finance-manager.html'; 
            }
        }
    } catch(err){
        console.log(err);
    }
}
