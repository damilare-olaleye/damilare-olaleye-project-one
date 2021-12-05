window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });
  
    if (res.status === 200) {
        let userObj = await res.json();
  
        if (userObj.role === 'Employee') {
            window.location.href = '/employee/home.html';
        }
    } else if (res.status === 401 || res.status === 404)  {
        window.location.href = '/404/404.html';
    }

  });

let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async() => {

  let res = await fetch('http://localhost:8080/logout', {
    method: 'POST', 
    credentials: 'include'
  });
  
  if (res.status === 200){
    window.location.href = '../../authentication/index.html';
  } else if(res.status === 400 || res.status == 404){
    window.location.href = '../../404/404.html';

  }

});

// update employee reimbursement
let updateEmployeeReimbButton = document.querySelector('#update-reimb-btn');

updateEmployeeReimbButton.addEventListener('click', async() => {

    let reimbursementIdInput = document.querySelector('#reimbsId');
    let reimbStatusInput = document.querySelector('#reimbStatus');

    let formData = new FormData();
    formData.append('reimbId', reimbursementIdInput.value);
    formData.append('status', reimbStatusInput.value);

    let res = await fetch('http://localhost:8080/updateReimbursementStatus', {
        method: 'POST', 
        credentials: 'include',
        body: formData
    });

    let postData = await res.text();

    console.log(postData);

    if(res.status === 200 || res.status === 201){

        let successUpdatedMessage = document.createElement('p');
        let submitStatusDiv = document.querySelector('#update-info');

        console.log(submitStatusDiv);

        successUpdatedMessage.innerHTML = postData;
        successUpdatedMessage.style.color = 'green';
        submitStatusDiv.appendChild(successUpdatedMessage);

        setTimeout(() => window.location.reload(), 1000);

    } else if(res.status === 400 || res.status === 404){
        console.log(postData);

        let errorUpdatedMessage = document.createElement('p');
        let submitStatusDiv = document.querySelector('#update-info');

        errorUpdatedMessage.innerHTML = postData;
        errorUpdatedMessage.style.color = 'red';
        submitStatusDiv.appendChild(errorUpdatedMessage);
        

    } 
});

