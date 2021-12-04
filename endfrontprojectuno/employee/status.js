window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'Finance Manager') {
            window.location.href = 'finance-manager.html';
        }
    } else if (res.status === 401 || res.status === 404)  {
        window.location.href = '/404/404.html';
    }

    populateReimbursementStatus();
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

async function populateReimbursementStatus() {

    let res = await fetch('http://localhost:8080/myReimbursementStatus',{
        credentials: 'include', 
        method: 'GET'

    });

    let tbodyElement = document.querySelector("#status-table tbody");
    tbodyElement.innerHTML = '';

    let statusArray = await res.json();

    console.log(tbodyElement);
    console.log(statusArray);

    for (let i = 0; i < statusArray.length; i++) {

        console.log(i);

        let status = statusArray[i];

        let tr = document.createElement('tr');

        let tableColOne = document.createElement('td');
        tableColOne.innerHTML = status.reimbId;

        let tableColTwo = document.createElement('td');
        tableColOne.innerHTML = status.submitted;

        let tableColThree = document.createElement('td');
        tableColThree.innerHTML = status.resolved;

        let tableColFour = document.createElement('td');
        tableColFour.innerHTML = status.type;

        let tableColFive = document.createElement('td');
        tableColFive.innerHTML = status.description;

        let tableColSix = document.createElement('td');
        tableColSix.innerHTML = status.amount;

        let tableColSeven = document.createElement('td');
        tableColSeven.innerHTML = status.author;

        let tableColEight = document.createElement('td');
        tableColEight.innerHTML = status.resolver;

        tr.appendChild(tableColOne);
        tr.appendChild(tableColTwo);
        tr.appendChild(tableColThree);
        tr.appendChild(tableColFour);
        tr.appendChild(tableColFive);
        tr.appendChild(tableColSix);
        tr.appendChild(tableColSeven);
        tr.appendChild(tableColEight);

        tbodyElement.appendChild(tr);

        console.log("I am here");

    }

}


