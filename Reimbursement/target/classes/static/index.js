window.addEventListener('load', async () => {

    let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/checkloginstatus', {
        method: 'GET',
        credentials: 'include'
    });

    if(res.status === 200){
        let userObj = await res.json();

        if (res.status === 200){
            if(userObj.role === 'Employee') {
                window.location.href = '/employee/home.html';
            } else if (userObj.role === 'Finance Manager'){
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
        let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/login', {
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify({
                username: usernameInput.value,
                password: passwordInput.value
            })
        });

        console.log(res);
        
        let data = await res.json();
        
        if(res.status === 400 || res.status === 404){
          
            let loginErrorMessage = document.createElement('p');
            let loginDiv = document.querySelector('#login-err-msg');

            loginDiv.innerHTML = '';
            loginErrorMessage.innerHTML = data.message;
            loginErrorMessage.style.color = 'red';
            loginDiv.appendChild(loginErrorMessage);

        } else if (res.status === 500){
            window.location.href = '../../404/404.html';
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
    let userNameInput = document.querySelector('#userName');
    let passWordInput = document.querySelector('#passWord');
    let roleInput = document.querySelector('#role');

    try {
        let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/signup', {
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify({
                username: userNameInput.value,
                password: passWordInput.value,
                firstname: firstnameInput.value,
                lastname: lastnameInput.value,
                email: emailInput.value,
                role: roleInput.value
                
            })
        });

        let data = await res.text();

        if (res.status === 200 || res.status === 201){

            let successSubmittedMessage = document.createElement('p');
            let submitDiv = document.querySelector('#signup-info');
        
            successSubmittedMessage.innerHTML = data;
            successSubmittedMessage.style.color = 'green';
            submitDiv.appendChild(successSubmittedMessage);
        
            setTimeout(() => window.location.reload(), 1500);
        }
        
        else if (res.status === 400 || res.status === 404){
            let signupErrorMessage = document.createElement('p');
            let signupDiv = document.querySelector('#sign-up-err');

            signupDiv.innerHTML = '';
            signupErrorMessage.innerHTML = data;
            signupErrorMessage.style.color = 'red';
        
            signupDiv.appendChild(signupErrorMessage);

        }  else {
                window.location.href = '../../404/404.html';
        }
 
    } catch(err){
        console.log(err);
    }
}