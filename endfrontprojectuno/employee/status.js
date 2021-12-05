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

    getAndPopulateReibursementStatus();
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

async function getAndPopulateReibursementStatus() {

    let res = await fetch('http://localhost:8080/myReimbursementStatus',{
        method: 'GET',
        credentials: 'include'
       
    });

    let statusArray = await res.json();
    let tbodyElement = document.querySelector("#status-table tbody");
  
    for (let i = 0; i < statusArray.length; i++) { 

        let myStatus = statusArray[i];

        let tr = document.createElement('tr');

        let tableColOne = document.createElement('td');
        tableColOne.innerHTML = myStatus.reimbId;

        let tableColTwo = document.createElement('td');
        tableColTwo.innerHTML = myStatus.submitted;

        let tableColThree = document.createElement('td');
        tableColThree.innerHTML = myStatus.resolved;

        let tableColFour = document.createElement('td');
        tableColFour.innerHTML = myStatus.status;

        let tableColFive = document.createElement('td');
        tableColFive.innerHTML = myStatus.type;

        let tableColSix = document.createElement('td');
        tableColSix.innerHTML = myStatus.description;

        let tableColSeven = document.createElement('td');
        tableColSeven.innerHTML = myStatus.amount;

        let tableColEight = document.createElement('td');
        tableColEight.innerHTML = myStatus.author;

        let tableColNine = document.createElement('td');
        tableColNine.innerHTML = myStatus.resolver;

        tr.appendChild(tableColOne);
        tr.appendChild(tableColTwo);
        tr.appendChild(tableColThree);
        tr.appendChild(tableColFour);
        tr.appendChild(tableColFive);
        tr.appendChild(tableColSix);
        tr.appendChild(tableColSeven);
        tr.appendChild(tableColEight);
        tr.appendChild(tableColNine);

        tbodyElement.appendChild(tr);

    }

}


